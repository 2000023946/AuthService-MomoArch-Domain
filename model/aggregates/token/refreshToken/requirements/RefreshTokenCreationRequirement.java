package com.authservice.domain.model.aggregates.token.refreshToken.requirements;

import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationRequirement;
import com.authservice.domain.model.aggregates.session.SessionActiveProof;
import com.authservice.domain.model.aggregates.token.refreshToken.RefreshTokenCreationFactory;
import com.authservice.domain.model.aggregates.token.refreshToken.RefreshValidationProof;
import com.authservice.domain.model.services.credentialService.login.SuccessfulAuthProof;
import com.authservice.domain.model.valueobjects.UUIDValueObject;

/**
 * Capability-guarded requirement for the "birth" of a new RefreshToken.
 * <p>
 * This requirement enforces a dual-lock security policy:
 * 1. A {@link SessionActiveProof} to verify the session is currently valid.
 * 2. An Authorization Proof (Login or Rotation) to justify the token issuance.
 * </p>
 *
 * @author Mohamed Abucar
 */
public final class RefreshTokenCreationRequirement implements IDomainCreationRequirement<RefreshTokenCreationFactory> {

    private final SessionActiveProof sessionProof;
    private final RefreshTokenCreationFactory authorizedFactory;

    /**
     * Private constructor for the requirement.
     *
     * @param sessionProof The certified evidence of an active session.
     * @param factory      The authorized {@link RefreshTokenCreationFactory}.
     */
    private RefreshTokenCreationRequirement(SessionActiveProof sessionProof, RefreshTokenCreationFactory factory) {
        this.sessionProof = sessionProof;
        this.authorizedFactory = factory;
    }

    /**
     * Handshake: Retrieves the Session ID from the session proof for the authorized
     * factory.
     *
     * @param caller The {@link RefreshTokenCreationFactory} instance attempting
     *               access.
     * @return The {@link UUIDValueObject} of the pinned session.
     * @throws SecurityException If the handshake fails.
     */
    public UUIDValueObject getSessionId(RefreshTokenCreationFactory caller) {
        verifyHandshake(caller);
        return this.sessionProof.getSessionId();
    }

    /**
     * Handshake: Retrieves the User ID from the session proof for the authorized
     * factory.
     *
     * @param caller The {@link RefreshTokenCreationFactory} instance attempting
     *               access.
     * @return The {@link UUIDValueObject} of the user owner.
     */
    public UUIDValueObject getUserId(RefreshTokenCreationFactory caller) {
        verifyHandshake(caller);
        return this.sessionProof.getUserId();
    }

    private void verifyHandshake(RefreshTokenCreationFactory caller) {
        if (this.authorizedFactory != caller) {
            throw new SecurityException("Momo-Arch Handshake Failed: Unauthorized RefreshToken Factory.");
        }
    }

    /** @return The first stage of the staged builder. */
    public static IFactoryStage builder() {
        return new Builder();
    }

    // --- Staged Builder Interfaces ---

    public interface IFactoryStage {
        /** @param f Authorized factory instance. @return Next stage. */
        ISessionStage withFactory(RefreshTokenCreationFactory f);
    }

    public interface ISessionStage {
        /** @param proof Certified active session evidence. @return Next stage. */
        IAuthorizationStage withSession(SessionActiveProof proof);
    }

    public interface IAuthorizationStage {
        /**
         * * Authorize via initial Login event.
         * 
         * @param proof The certified {@link LoginProof}.
         * @return The finalized requirement.
         */
        RefreshTokenCreationRequirement authorizeInitial(SuccessfulAuthProof proof);

        /**
         * * Authorize via Token Rotation (Exchange).
         * 
         * @param proof The certified {@link RefreshValidationProof}.
         * @return The finalized requirement.
         */
        RefreshTokenCreationRequirement authorizeRotation(RefreshValidationProof proof);
    }

    private static class Builder implements IFactoryStage, ISessionStage, IAuthorizationStage {
        private RefreshTokenCreationFactory factory;
        private SessionActiveProof sessionProof;

        @Override
        public ISessionStage withFactory(RefreshTokenCreationFactory f) {
            this.factory = f;
            return this;
        }

        @Override
        public IAuthorizationStage withSession(SessionActiveProof proof) {
            this.sessionProof = proof;
            return this;
        }

        @Override
        public RefreshTokenCreationRequirement authorizeInitial(SuccessfulAuthProof proof) {
            if (proof == null)
                throw new IllegalArgumentException("LoginProof required for initial token.");
            return new RefreshTokenCreationRequirement(this.sessionProof, this.factory);
        }

        @Override
        public RefreshTokenCreationRequirement authorizeRotation(RefreshValidationProof proof) {
            if (proof == null)
                throw new IllegalArgumentException("RefreshValidationProof required for rotation.");
            return new RefreshTokenCreationRequirement(this.sessionProof, this.factory);
        }
    }
}