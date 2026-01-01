package com.authservice.domain.model.aggregates.user;

import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionProof;
import com.authservice.domain.model.aggregates.user.requirements.UserReconstitutionRequirement;

/**
 * A proof issued when an existing user is rehydrated (reconstituted) from a
 * persisted {@link com.authservice.domain.model.aggregates.user.UserSchema}.
 * <p>
 * This class extends {@link AbstractUserProof} and serves as a final marker
 * that the {@link UserAggregate} has been successfully reconstructed from
 * storage.
 * </p>
 * <p>
 * Instances of this class are <strong>immutable</strong> and wrap a fully
 * initialized
 * {@link UserAggregate}, which can be accessed via {@link #getAggregate()}.
 * </p>
 *
 * <h2>Usage Example</h2>
 * 
 * <pre>{@code
 * UserAggregate existingUser = ...; // Rehydrated from schema
 * UserReconstitutionProof reconstitutionProof = new UserReconstitutionProof(existingUser);
 *
 * // Access the aggregate safely
 * UserAggregate authorizedAggregate = reconstitutionProof.getAggregate();
 * }</pre>
 */
public final class UserReconstitutionProof extends AbstractUserProof
        implements IDomainReconstitutionProof<UserReconstitutionRequirement> {
    private final UserReconstitutionRequirement requirement;

    /**
     * Constructs a new {@link UserReconstitutionProof} for the provided
     * {@link UserAggregate}.
     *
     * @param aggregate   the fully initialized {@link UserAggregate} representing
     *                    the
     *                    rehydrated user
     * @param requirement the requirement that would make the proof
     * @throws IllegalArgumentException if the aggregate is null
     */
    UserReconstitutionProof(UserAggregate aggregate, UserReconstitutionRequirement requirement) {
        super(aggregate);
        this.requirement = requirement;
    }

    @Override
    public UserReconstitutionRequirement getSourceRequirement() {
        return requirement;
    }
}
