package com.authservice.domain.model.aggregates.session.requirements;

import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationRequirement;
import com.authservice.domain.model.aggregates.session.SessionCreationFactory;
import com.authservice.domain.model.services.credentialService.login.SuccessfulAuthProof;
import com.authservice.domain.model.valueobjects.UUIDValueObject;

/**
 * Capability-guarded requirement for the "birth" of a new Session.
 * <p>
 * This version enforces the "Login-First" policy by requiring a
 * {@link SuccessfulAuthProof} as a prerequisite for session initialization.
 * </p>
 */
public final class SessionCreationRequirement implements IDomainCreationRequirement<SessionCreationFactory> {

    private final SuccessfulAuthProof loginProof;
    private final SessionCreationFactory authorizedFactory;

    private SessionCreationRequirement(SuccessfulAuthProof loginProof,
            SessionCreationFactory factory) {
        this.loginProof = loginProof;
        this.authorizedFactory = factory;
    }

    /**
     * Handshake: Extracts the UserID from the LoginProof only for the authorized
     * factory.
     * * @param caller The {@link SessionCreationFactory} instance.
     * 
     * @return The {@link UUIDValueObject} extracted from the login evidence.
     */
    public UUIDValueObject getUserId(SessionCreationFactory caller) {
        verifyHandshake(caller);
        return this.loginProof.getUser().get().getUserId();
    }

    private void verifyHandshake(SessionCreationFactory caller) {
        if (this.authorizedFactory != caller) {
            throw new SecurityException("Momo-Arch Handshake Failed: Unauthorized Session Factory.");
        }
    }

    public static IFactoryStage builder() {
        return new Builder();
    }

    public interface IFactoryStage {
        IProofStage withFactory(SessionCreationFactory f);
    }

    public interface IProofStage {
        IExpiryStage withLoginProof(SuccessfulAuthProof proof);
    }

    public interface IExpiryStage {
        SessionCreationRequirement build();
    }

    private static class Builder implements IFactoryStage, IProofStage, IExpiryStage {
        private SessionCreationFactory factory;
        private SuccessfulAuthProof loginProof;

        @Override
        public IProofStage withFactory(SessionCreationFactory f) {
            this.factory = f;
            return this;
        }

        @Override
        public IExpiryStage withLoginProof(SuccessfulAuthProof p) {
            this.loginProof = p;
            return this;
        }

        @Override
        public SessionCreationRequirement build() {
            return new SessionCreationRequirement(this.loginProof, this.factory);
        }
    }
}