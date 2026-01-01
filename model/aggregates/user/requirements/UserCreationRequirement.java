package com.authservice.domain.model.aggregates.user.requirements;

import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationRequirement;
import com.authservice.domain.model.aggregates.user.UserCreationFactory;
import com.authservice.domain.model.services.credentialService.registration.RegistrationProof;

/**
 * Encapsulates the registration data required to create a new
 * {@link com.authservice.domain.model.aggregates.user.UserAggregate}.
 * <p>
 * This class acts as the <strong>Gate</strong> in the Momo-Architecture
 * creation handshake.
 * Only the {@link UserCreationFactory} authorized during construction can
 * unlock the contained
 * {@link RegistrationProof}. This enforces a strict double-dispatch handshake
 * protocol.
 * </p>
 * <p>
 * The construction of a {@code UserCreationRequirement} follows a
 * <strong>staged builder pattern</strong>:
 * first the factory must be provided, then the registration proof. This ensures
 * that
 * a requirement cannot exist without being tied to a specific factory.
 * </p>
 *
 * <h2>Usage Example</h2>
 * 
 * <pre>{@code
 * UserCreationFactory factory = ...;
 * RegistrationProof proof = ...;
 *
 * UserCreationRequirement requirement = UserCreationRequirement.builder()
 *                                         .withFactory(factory)
 *                                         .build(proof);
 *
 * RegistrationProof authorizedProof = requirement.getAuthorizedProof(factory);
 * }</pre>
 */
public final class UserCreationRequirement implements IDomainCreationRequirement<UserCreationFactory> {

    /** The registration proof containing user credentials and data. */
    private final RegistrationProof proof;

    /** The factory authorized to access this requirement. */
    private final UserCreationFactory authorizedFactory;

    /**
     * Private constructor to enforce builder usage.
     *
     * @param proof   the registration proof
     * @param factory the factory authorized to access this proof
     * @throws IllegalArgumentException if either argument is null
     */
    private UserCreationRequirement(RegistrationProof proof, UserCreationFactory factory) {
        if (proof == null || factory == null) {
            throw new IllegalArgumentException("Creation data and authorized factory cannot be null.");
        }
        this.proof = proof;
        this.authorizedFactory = factory;
    }

    /**
     * Retrieves the {@link RegistrationProof} if the caller is the authorized
     * factory.
     * <p>
     * Enforces the Momo-Architecture handshake: only the factory instance specified
     * during construction can access the internal registration proof.
     * </p>
     *
     * @param caller the factory attempting to access the proof
     * @return the authorized {@link RegistrationProof}
     * @throws SecurityException if the caller is not the authorized factory
     */
    public RegistrationProof getAuthorizedProof(UserCreationFactory caller) {
        if (this.authorizedFactory != caller) {
            throw new SecurityException("Handshake Failed: Unauthorized Factory Access.");
        }
        return this.proof;
    }

    /**
     * Begins the staged builder for creating a {@link UserCreationRequirement}.
     *
     * @return the first stage of the builder requiring the factory
     */
    public static IFactoryStage builder() {
        return new Builder();
    }

    // --- Staged Builder Interfaces ---

    /**
     * Builder stage requiring the authorized factory.
     */
    public interface IFactoryStage {
        /**
         * Sets the authorized {@link UserCreationFactory} for this requirement.
         *
         * @param f the factory to authorize
         * @return the next builder stage to provide the registration proof
         */
        IProofStage withFactory(UserCreationFactory f);
    }

    /**
     * Builder stage requiring the registration proof.
     */
    public interface IProofStage {
        /**
         * Builds the {@link UserCreationRequirement} with the provided registration
         * proof.
         *
         * @param p the {@link RegistrationProof} containing user data
         * @return a fully constructed {@link UserCreationRequirement}
         */
        UserCreationRequirement build(RegistrationProof p);
    }

    /**
     * Concrete staged builder implementation for {@link UserCreationRequirement}.
     */
    private static class Builder implements IFactoryStage, IProofStage {

        private UserCreationFactory factory;

        @Override
        public IProofStage withFactory(UserCreationFactory f) {
            this.factory = f;
            return this;
        }

        @Override
        public UserCreationRequirement build(RegistrationProof p) {
            return new UserCreationRequirement(p, this.factory);
        }
    }
}
