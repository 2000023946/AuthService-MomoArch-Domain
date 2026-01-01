package com.authservice.domain.model.aggregates.user;

import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationFactory;

import com.authservice.domain.model.aggregates.user.requirements.UserCreationRequirement;
import com.authservice.domain.model.services.credentialService.registration.RegistrationProof;
import com.authservice.domain.model.valueobjects.EmailValueObject;
import com.authservice.domain.model.valueobjects.PasswordHashValueObject;
import com.authservice.domain.model.valueobjects.PasswordValueObject;
import com.authservice.domain.model.valueobjects.UUIDValueObject;
import com.authservice.domain.ports.IHashingService;
import java.util.UUID;

/**
 * Specialized factory responsible for creating <strong>new</strong>
 * {@link UserAggregate} instances.
 * <p>
 * This factory implements a strict <strong>Handshake Protocol</strong> with the
 * corresponding
 * {@link UserCreationRequirement}, ensuring that only authorized requirements
 * can trigger
 * the creation of a user.
 * </p>
 * <p>
 * Responsibilities include:
 * <ul>
 * <li>Validating the requirement against this factory</li>
 * <li>Hashing user passwords using the provided {@link IHashingService}</li>
 * <li>Generating a unique {@link UUIDValueObject} for the new user</li>
 * <li>Creating a fully initialized {@link UserAggregate}</li>
 * <li>Issuing a {@link UserCreationProof} to signal successful creation</li>
 * </ul>
 * </p>
 *
 * <h2>Usage Example</h2>
 * 
 * <pre>{@code
 * IHashingService hashingService = ...;
 * UserCreationFactory factory = new UserCreationFactory(hashingService);
 *
 * UserCreationRequirement requirement = UserCreationRequirement.builder()
 *                                         .withFactory(factory)
 *                                         .build(registrationProof);
 *
 * IUserCreationProof proof = factory.create(requirement);
 * UserAggregate newUser = proof.getAggregate();
 * }</pre>
 */
public class UserCreationFactory implements IDomainCreationFactory<UserCreationProof, UserCreationRequirement> {

    /** Hashing service used to securely hash passwords during user creation. */
    private final IHashingService hashingService;

    /**
     * Constructs a new {@link UserCreationFactory} with the given hashing service.
     *
     * @param hashingService the {@link IHashingService} used to hash passwords
     */
    public UserCreationFactory(IHashingService hashingService) {
        this.hashingService = hashingService;
    }

    /**
     * Creates a new {@link UserAggregate} based on the provided
     * {@link IUserCreationRequirement}.
     * <p>
     * This method implements a <strong>double-dispatch handshake</strong>:
     * <ol>
     * <li>The {@link UserCreationRequirement} validates that this factory is
     * authorized to consume it</li>
     * <li>The factory retrieves the registration proof and orchestrates
     * domain-level operations to create the user</li>
     * </ol>
     * </p>
     * <p>
     * Steps performed internally:
     * <ul>
     * <li>Extracts email and password from the {@link RegistrationProof}</li>
     * <li>Hashes the password using {@link IHashingService}</li>
     * <li>Generates a unique {@link UUIDValueObject} for the user</li>
     * <li>Constructs a {@link UserAggregate} with the above data</li>
     * <li>Returns a {@link UserCreationProof} wrapping the aggregate</li>
     * </ul>
     * </p>
     *
     * @param requirement the user creation requirement authorized for this factory
     * @return a {@link IUserCreationProof} confirming successful creation
     * @throws SecurityException  if the requirement was not authorized for this
     *                            factory
     * @throws ClassCastException if the requirement is not a
     *                            {@link UserCreationRequirement} instance
     */
    public UserCreationProof create(UserCreationRequirement requirement) {
        // Double-dispatch handshake: ensure requirement opens only for this factory
        UserCreationRequirement concreteReq = (UserCreationRequirement) requirement;
        RegistrationProof rawData = concreteReq.getAuthorizedProof(this);

        EmailValueObject emailVO = rawData.getEmail();
        PasswordValueObject passwordVO = rawData.getPassword();

        // Hash the password at the domain level
        PasswordHashValueObject hashVO = PasswordHashValueObject.create(passwordVO, hashingService);

        // Generate unique ID for new user
        UUIDValueObject userId = new UUIDValueObject(UUID.randomUUID().toString());

        // Create the user aggregate
        UserAggregate user = new UserAggregate(userId, emailVO, hashVO);

        // Return proof of successful creation
        return new UserCreationProof(user, concreteReq);
    }
}
