package com.authservice.domain.model.aggregates.user;

import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationProof;
import com.authservice.domain.model.aggregates.user.requirements.UserCreationRequirement;

/**
 * A proof issued exclusively when a brand new user is created in the system.
 * <p>
 * This class extends {@link AbstractUserProof} and serves as a final marker
 * that the {@link UserAggregate} has been successfully created.
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
 * UserAggregate newUser = ...;
 * UserCreationProof creationProof = new UserCreationProof(newUser);
 *
 * // Access the aggregate safely
 * UserAggregate authorizedAggregate = creationProof.getAggregate();
 * }</pre>
 */
public final class UserCreationProof extends AbstractUserProof
        implements IDomainCreationProof<UserCreationRequirement> {
    private final UserCreationRequirement requirement;

    /**
     * Constructs a new {@link UserCreationProof} for the provided
     * {@link UserAggregate}.
     *
     * @param aggregate   the fully initialized {@link UserAggregate} representing
     *                    the
     *                    new user
     * 
     * @param requirement sicne the factory has multiple methods
     * @throws IllegalArgumentException if the aggregate is null
     */
    UserCreationProof(UserAggregate aggregate, UserCreationRequirement requirement) {
        super(aggregate);
        this.requirement = requirement;
    }

    @Override
    public UserCreationRequirement getSourceRequirement() {
        return requirement;
    }

}
