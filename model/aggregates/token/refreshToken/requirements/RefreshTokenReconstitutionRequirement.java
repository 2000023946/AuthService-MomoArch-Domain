package com.authservice.domain.model.aggregates.token.refreshToken.requirements;

import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionRequirement;
import com.authservice.domain.model.aggregates.token.refreshToken.RefreshTokenReconstitutionFactory;
import com.authservice.domain.model.schemas.token.refresh.RefreshTokenSchema;

/**
 * Capability-guarded requirement for reanimating a RefreshToken from state.
 * * @author Mohamed Abucar
 */
public final class RefreshTokenReconstitutionRequirement
        implements IDomainReconstitutionRequirement<RefreshTokenReconstitutionFactory> {

    private final RefreshTokenSchema schema;
    private final RefreshTokenReconstitutionFactory authorizedFactory;

    private RefreshTokenReconstitutionRequirement(RefreshTokenSchema schema,
            RefreshTokenReconstitutionFactory factory) {
        this.schema = schema;
        this.authorizedFactory = factory;
    }

    /**
     * Handshake: Retrieves the schema if the caller is the authorized factory
     * instance.
     * * @param caller The {@link RefreshTokenReconstitutionFactory} attempting
     * access.
     * 
     * @return The {@link RefreshTokenSchema}.
     */
    public RefreshTokenSchema getAuthorizedSchema(RefreshTokenReconstitutionFactory caller) {
        if (this.authorizedFactory != caller) {
            throw new SecurityException(
                    "Momo-Arch Handshake Failed: Unauthorized RefreshToken Reconstitution Factory.");
        }
        return this.schema;
    }

    public static IFactoryStage builder() {
        return new Builder();
    }

    public interface IFactoryStage {
        ISchemaStage withFactory(RefreshTokenReconstitutionFactory f);
    }

    public interface ISchemaStage {
        RefreshTokenReconstitutionRequirement build(RefreshTokenSchema s);
    }

    private static class Builder implements IFactoryStage, ISchemaStage {
        private RefreshTokenReconstitutionFactory factory;

        @Override
        public ISchemaStage withFactory(RefreshTokenReconstitutionFactory f) {
            this.factory = f;
            return this;
        }

        @Override
        public RefreshTokenReconstitutionRequirement build(RefreshTokenSchema s) {
            return new RefreshTokenReconstitutionRequirement(s, this.factory);
        }
    }
}