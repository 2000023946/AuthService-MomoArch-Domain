package com.authservice.domain.model.aggregates.user;

import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionFactory;

import com.authservice.domain.model.aggregates.user.requirements.UserReconstitutionRequirement;
import com.authservice.domain.model.schemas.user.UserSchema;
import com.authservice.domain.model.valueobjects.EmailValueObject;
import com.authservice.domain.model.valueobjects.PasswordHashValueObject;
import com.authservice.domain.model.valueobjects.UUIDValueObject;
import com.authservice.domain.ports.IHashingService;
import java.time.LocalDateTime;

/**
 * Specialized factory responsible for rehydrating (reconstituting) existing
 * {@link UserAggregate} instances
 * from persisted {@link UserSchema} data.
 * <p>
 * This factory implements a strict <strong>Handshake Protocol</strong> with the
 * corresponding
 * {@link UserReconstitutionRequirement}, ensuring that only authorized
 * requirements can trigger
 * the reconstitution of a user.
 * </p>
 * <p>
 * Responsibilities include:
 * <ul>
 * <li>Validating the requirement against this factory</li>
 * <li>Mapping persisted schema data into domain value objects</li>
 * <li>Reconstituting the password hash using the {@link IHashingService}</li>
 * <li>Creating a fully initialized {@link UserAggregate}</li>
 * <li>Issuing a {@link UserReconstitutionProof} to signal successful
 * reconstitution</li>
 * </ul>
 * </p>
 *
 * <h2>Usage Example</h2>
 * 
 * <pre>{@code
 * IHashingService hashingService = ...;
 * UserReconstitutionFactory factory = new UserReconstitutionFactory(hashingService);
 *
 * UserReconstitutionRequirement requirement = UserReconstitutionRequirement.builder()
 *                                         .withFactory(factory)
 *                                         .build(schema);
 *
 * IUserReconstitutionProof proof = factory.reconstitute(requirement);
 * UserAggregate existingUser = proof.getAggregate();
 * }</pre>
 */
public class UserReconstitutionFactory
        implements IDomainReconstitutionFactory<UserReconstitutionProof, UserReconstitutionRequirement> {

    /** Hashing service used to handle password reconstitution. */
    private final IHashingService hashingService;

    /**
     * Constructs a new {@link UserReconstitutionFactory} with the given hashing
     * service.
     *
     * @param hashingService the {@link IHashingService} used for password handling
     */
    public UserReconstitutionFactory(IHashingService hashingService) {
        this.hashingService = hashingService;
    }

    /**
     * Reconstitutes a {@link UserAggregate} based on the provided
     * {@link IUserReconstitutionRequirement}.
     * <p>
     * This method implements a <strong>double-dispatch handshake</strong>:
     * <ol>
     * <li>The {@link UserReconstitutionRequirement} validates that this factory is
     * authorized to consume it</li>
     * <li>The factory maps the persisted {@link UserSchema} into domain-level value
     * objects and aggregates</li>
     * </ol>
     * </p>
     * <p>
     * Steps performed internally:
     * <ul>
     * <li>Extracts the user ID, email, and password hash from the schema</li>
     * <li>Reconstitutes the {@link PasswordHashValueObject} using the hashing
     * service</li>
     * <li>Rehydrates metadata fields: verification status, failed login attempts,
     * creation/update timestamps, and last password reset request</li>
     * <li>Constructs a fully initialized {@link UserAggregate}</li>
     * <li>Returns a {@link UserReconstitutionProof} wrapping the aggregate</li>
     * </ul>
     * </p>
     *
     * @param requirement the user reconstitution requirement authorized for this
     *                    factory
     * @return a {@link IUserReconstitutionProof} confirming successful
     *         reconstitution
     * @throws SecurityException  if the requirement was not authorized for this
     *                            factory
     * @throws ClassCastException if the requirement is not a
     *                            {@link UserReconstitutionRequirement} instance
     */
    public UserReconstitutionProof reconstitute(UserReconstitutionRequirement requirement) {
        // Double-dispatch handshake: ensure requirement opens only for this factory
        UserReconstitutionRequirement concreteReq = (UserReconstitutionRequirement) requirement;
        UserSchema schema = concreteReq.getAuthorizedSchema(this);

        UUIDValueObject userIdVO = new UUIDValueObject(schema.getUserId());
        EmailValueObject emailVO = new EmailValueObject(schema.getEmail());

        // Reconstitute the password hash without creating a new hash
        PasswordHashValueObject hashVO = PasswordHashValueObject.reconstitute(
                schema.getPasswordHash(),
                hashingService);

        LocalDateTime created = schema.getCreatedAt();
        LocalDateTime updated = schema.getUpdatedAt();
        LocalDateTime lastReset = schema.getLastPasswordResetRequestAt();

        // Create the user aggregate with all persisted properties
        UserAggregate user = new UserAggregate(
                userIdVO,
                emailVO,
                hashVO,
                schema.isVerified(),
                schema.getFailedLoginAttempts(),
                created,
                updated,
                lastReset);

        // Return proof of successful reconstitution
        return new UserReconstitutionProof(user, concreteReq);
    }

}
