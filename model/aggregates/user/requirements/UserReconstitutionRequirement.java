package com.authservice.domain.model.aggregates.user.requirements;

import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionRequirement;
import com.authservice.domain.model.aggregates.user.UserReconstitutionFactory;
import com.authservice.domain.model.schemas.user.UserSchema;

/**
 * Encapsulates persisted user state for reconstitution into a live
 * {@link com.authservice.domain.model.aggregates.user.UserAggregate}.
 * <p>
 * This class acts as the <strong>Gate</strong> in the Reanimation
 * Momo-Architecture handshake.
 * Only the {@link UserReconstitutionFactory} authorized during construction can
 * unlock the contained
 * {@link UserSchema}. This enforces a strict double-dispatch handshake protocol
 * for rehydrating aggregates.
 * </p>
 * <p>
 * The construction of a {@code UserReconstitutionRequirement} follows a
 * <strong>staged builder pattern</strong>:
 * first the factory must be provided, then the schema. This ensures the
 * requirement cannot exist
 * without being tied to a specific authorized factory.
 * </p>
 *
 * <h2>Usage Example</h2>
 * 
 * <pre>{@code
 * UserReconstitutionFactory factory = ...;
 * UserSchema schema = ...;
 *
 * UserReconstitutionRequirement requirement = UserReconstitutionRequirement.builder()
 *                                              .withFactory(factory)
 *                                              .build(schema);
 *
 * UserSchema authorizedSchema = requirement.getAuthorizedSchema(factory);
 * }</pre>
 */
public final class UserReconstitutionRequirement
        implements IDomainReconstitutionRequirement<UserReconstitutionFactory> {

    /** The persisted schema representing the "dead" state of the user aggregate. */
    private final UserSchema schema;

    /** The factory authorized to access this requirement. */
    private final UserReconstitutionFactory authorizedFactory;

    /**
     * Private constructor to enforce builder usage.
     *
     * @param schema  the persisted {@link UserSchema}
     * @param factory the factory authorized to access this schema
     * @throws IllegalArgumentException if either argument is null
     */
    private UserReconstitutionRequirement(UserSchema schema, UserReconstitutionFactory factory) {
        if (schema == null || factory == null) {
            throw new IllegalArgumentException("Schema and authorized factory cannot be null.");
        }
        this.schema = schema;
        this.authorizedFactory = factory;
    }

    /**
     * Retrieves the {@link UserSchema} if the caller is the authorized factory.
     * <p>
     * Enforces the Momo-Architecture handshake: only the factory instance specified
     * during construction can access the internal schema.
     * </p>
     *
     * @param caller the factory attempting to access the schema
     * @return the authorized {@link UserSchema}
     * @throws SecurityException if the caller is not the authorized factory
     */
    public UserSchema getAuthorizedSchema(UserReconstitutionFactory caller) {
        if (this.authorizedFactory != caller) {
            throw new SecurityException("Handshake Failed: Unauthorized Reconstitution Attempt.");
        }
        return this.schema;
    }

    /**
     * Begins the staged builder for creating a
     * {@link UserReconstitutionRequirement}.
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
         * Sets the authorized {@link UserReconstitutionFactory} for this requirement.
         *
         * @param f the factory to authorize
         * @return the next builder stage to provide the schema
         */
        ISchemaStage withFactory(UserReconstitutionFactory f);
    }

    /**
     * Builder stage requiring the persisted schema.
     */
    public interface ISchemaStage {
        /**
         * Builds the {@link UserReconstitutionRequirement} with the provided schema.
         *
         * @param s the persisted {@link UserSchema} representing the user state
         * @return a fully constructed {@link UserReconstitutionRequirement}
         */
        UserReconstitutionRequirement build(UserSchema s);
    }

    /**
     * Concrete staged builder implementation for
     * {@link UserReconstitutionRequirement}.
     */
    private static class Builder implements IFactoryStage, ISchemaStage {

        private UserReconstitutionFactory factory;

        @Override
        public ISchemaStage withFactory(UserReconstitutionFactory f) {
            this.factory = f;
            return this;
        }

        @Override
        public UserReconstitutionRequirement build(UserSchema s) {
            return new UserReconstitutionRequirement(s, this.factory);
        }
    }
}
