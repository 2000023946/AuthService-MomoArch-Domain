package com.authservice.domain.model.aggregates.token.accessToken.requirements;

import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionRequirement;
import com.authservice.domain.model.aggregates.token.accessToken.AccessTokenReconstitutionFactory;
import com.authservice.domain.model.schemas.token.access.AccessTokenSchema;

/**
 * Capability-guarded requirement for the "reanimation" of an existing
 * AccessToken from state.
 * <p>
 * This class ensures that only the authorized
 * {@link AccessTokenReconstitutionFactory}
 * can access the underlying {@link AccessTokenSchema} during the rehydration
 * process.
 * </p>
 * * @author Mohamed Abucar
 */
public final class AccessTokenReconstitutionRequirement
        implements IDomainReconstitutionRequirement<AccessTokenReconstitutionFactory> {

    /** The raw infrastructure data snapshot of the access token. */
    private final AccessTokenSchema schema;

    /** The specific factory instance authorized to perform this handshake. */
    private final AccessTokenReconstitutionFactory authorizedFactory;

    /**
     * Private constructor used by the staged builder.
     *
     * @param schema  The {@link AccessTokenSchema} containing persisted state.
     * @param factory The {@link AccessTokenReconstitutionFactory} authorized for
     *                this requirement.
     */
    private AccessTokenReconstitutionRequirement(AccessTokenSchema schema, AccessTokenReconstitutionFactory factory) {
        if (schema == null || factory == null) {
            throw new IllegalArgumentException("Schema and authorized factory must not be null.");
        }
        this.schema = schema;
        this.authorizedFactory = factory;
    }

    /**
     * Handshake: Retrieves the schema if the caller is the authorized factory
     * instance.
     *
     * @param caller The {@link AccessTokenReconstitutionFactory} attempting to
     *               access the data.
     * @return The {@link AccessTokenSchema} for processing.
     * @throws SecurityException If the caller is not the factory instance specified
     *                           during construction.
     */
    public AccessTokenSchema getAuthorizedSchema(AccessTokenReconstitutionFactory caller) {
        if (this.authorizedFactory != caller) {
            throw new SecurityException("Momo-Arch Handshake Failed: Unauthorized AccessToken Reconstitution Factory.");
        }
        return this.schema;
    }

    /**
     * Initializes the staged builder workflow.
     *
     * @return The {@link IFactoryStage} to begin the construction process.
     */
    public static IFactoryStage builder() {
        return new Builder();
    }

    // --- Staged Builder ---

    /** Initial stage: Define the authorized factory. */
    public interface IFactoryStage {
        /**
         * @param f The authorized {@link AccessTokenReconstitutionFactory}.
         * @return The
         *         next stage.
         */
        ISchemaStage withFactory(AccessTokenReconstitutionFactory f);
    }

    /** Final stage: Provide the schema and build. */
    public interface ISchemaStage {
        /**
         * @param s The {@link AccessTokenSchema}.
         * @return The constructed requirement.
         */
        AccessTokenReconstitutionRequirement build(AccessTokenSchema s);
    }

    private static class Builder implements IFactoryStage, ISchemaStage {
        private AccessTokenReconstitutionFactory factory;

        @Override
        public ISchemaStage withFactory(AccessTokenReconstitutionFactory f) {
            this.factory = f;
            return this;
        }

        @Override
        public AccessTokenReconstitutionRequirement build(AccessTokenSchema s) {
            return new AccessTokenReconstitutionRequirement(s, this.factory);
        }
    }
}