package com.authservice.domain.model.aggregates.token.accessToken.requirements;

import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationRequirement;
import com.authservice.domain.model.aggregates.token.accessToken.AccessTokenCreationFactory;
import com.authservice.domain.model.aggregates.token.refreshToken.RefreshValidationProof;
import com.authservice.domain.model.valueobjects.UUIDValueObject;

/**
 * Capability-guarded requirement for the "birth" of a new AccessToken.
 * <p>
 * This class enforces that only an authorized
 * {@link AccessTokenCreationFactory}
 * can access the user data contained within a certified
 * {@link RefreshValidationProof}.
 * </p>
 * * @author Mohamed Abucar
 */
public final class AccessTokenCreationRequirement implements IDomainCreationRequirement<AccessTokenCreationFactory> {

    /** The certified proof required to authorize token creation. */
    private final RefreshValidationProof validationProof;

    /** The specific factory instance authorized to process this requirement. */
    private final AccessTokenCreationFactory authorizedFactory;

    /**
     * Private constructor used by the staged builder.
     *
     * @param proof   The {@link RefreshValidationProof} authorizing this request.
     * @param factory The {@link AccessTokenCreationFactory} authorized for this
     *                handshake.
     */
    private AccessTokenCreationRequirement(RefreshValidationProof proof, AccessTokenCreationFactory factory) {
        this.validationProof = proof;
        this.authorizedFactory = factory;
    }

    /**
     * Handshake: Retrieves the UserID from the proof if the caller is authorized.
     *
     * @param caller The {@link AccessTokenCreationFactory} instance attempting
     *               access.
     * @return The {@link UUIDValueObject} extracted from the validation proof.
     * @throws SecurityException If the caller is not the factory specified during
     *                           construction.
     */
    public UUIDValueObject getUserId(AccessTokenCreationFactory caller) {
        if (this.authorizedFactory != caller) {
            throw new SecurityException("Momo-Arch Handshake Failed: Unauthorized AccessToken Factory.");
        }
        return this.validationProof.getUserId();
    }

    /**
     * Initializes the staged builder workflow.
     *
     * @return The {@link IFactoryStage} to begin construction.
     */
    public static IFactoryStage builder() {
        return new Builder();
    }

    // --- Staged Builder Interfaces ---

    /** Initial stage: Define the authorized factory. */
    public interface IFactoryStage {
        /**
         * @param f The authorized {@link AccessTokenCreationFactory}.
         * @return The next stage for providing proof.
         */
        IProofStage withFactory(AccessTokenCreationFactory f);
    }

    /** Final stage: Provide the validation proof and build. */
    public interface IProofStage {
        /**
         * @param proof The {@link RefreshValidationProof} issued by the auth service.
         * @return The fully constructed {@link AccessTokenCreationRequirement}.
         */
        AccessTokenCreationRequirement build(RefreshValidationProof proof);
    }

    /**
     * Concrete implementation of the staged builder.
     */
    private static class Builder implements IFactoryStage, IProofStage {
        private AccessTokenCreationFactory factory;

        @Override
        public IProofStage withFactory(AccessTokenCreationFactory f) {
            this.factory = f;
            return this;
        }

        @Override
        public AccessTokenCreationRequirement build(RefreshValidationProof proof) {
            return new AccessTokenCreationRequirement(proof, this.factory);
        }
    }
}