package com.authservice.domain.model.aggregates.session.requirements;

import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionRequirement;
import com.authservice.domain.model.aggregates.session.SessionReconstitutionFactory;
import com.authservice.domain.model.schemas.session.SessionSchema;

/**
 * Capability-guarded requirement representing the
 * <strong>reconstitution gate</strong> for an existing
 * {@code SessionAggregate}.
 *
 * <p>
 * This requirement encapsulates persisted session state in the form of a
 * {@link SessionSchema} and strictly controls access to that state through
 * an identity-based handshake with a {@link SessionReconstitutionFactory}.
 * </p>
 *
 * <h2>Architectural Role</h2>
 * <p>
 * In Momo-Architecture terms, this class represents the
 * <em>Input Gate of Reanimation</em>:
 * </p>
 * <ul>
 * <li>The schema represents the <strong>dead state</strong></li>
 * <li>The factory acts as the <strong>necromancer</strong></li>
 * <li>This requirement enforces who may rehydrate the aggregate</li>
 * </ul>
 *
 * <p>
 * The guarded schema cannot be accessed directly. Only the exact factory
 * instance bound at construction time may unlock it.
 * </p>
 *
 * <h2>Security Model</h2>
 * <p>
 * Authorization is enforced via <strong>instance identity</strong>,
 * not type checks. Any factory other than the authorized instance will
 * trigger an immediate handshake failure.
 * </p>
 *
 * @see SessionReconstitutionFactory
 * @see SessionSchema
 */
public final class SessionReconstitutionRequirement
        implements IDomainReconstitutionRequirement<SessionReconstitutionFactory> {

    /**
     * Persisted representation of the session aggregate.
     */
    private final SessionSchema schema;

    /**
     * The sole factory instance authorized to reconstitute this schema.
     */
    private final SessionReconstitutionFactory authorizedFactory;

    /**
     * Private constructor enforcing controlled construction via
     * the staged builder.
     *
     * @param schema  the persisted session state to be rehydrated
     * @param factory the factory authorized to unlock this requirement
     *
     * @throws IllegalArgumentException if either argument is {@code null}
     */
    private SessionReconstitutionRequirement(
            SessionSchema schema,
            SessionReconstitutionFactory factory) {

        if (schema == null || factory == null) {
            throw new IllegalArgumentException(
                    "Schema and authorized factory cannot be null.");
        }

        this.schema = schema;
        this.authorizedFactory = factory;
    }

    /**
     * Executes the reconstitution handshake.
     *
     * <p>
     * The guarded {@link SessionSchema} is released only if the caller
     * is the exact factory instance bound to this requirement.
     * </p>
     *
     * @param caller the factory attempting to rehydrate the session
     * @return the guarded session schema
     *
     * @throws SecurityException if the caller is not authorized
     */
    public SessionSchema getAuthorizedSchema(SessionReconstitutionFactory caller) {
        if (this.authorizedFactory != caller) {
            throw new SecurityException(
                    "Handshake Failed: Unauthorized Session Reconstitution Attempt.");
        }
        return this.schema;
    }

    /**
     * Begins staged construction of a {@code SessionReconstitutionRequirement}.
     *
     * <p>
     * The builder enforces correct assembly order:
     * </p>
     * <ol>
     * <li>Bind the authorized factory</li>
     * <li>Provide the persisted schema</li>
     * </ol>
     *
     * @return the first builder stage
     */
    public static IFactoryStage builder() {
        return new Builder();
    }

    /**
     * Builder stage requiring the authorized reconstitution factory.
     */
    public interface IFactoryStage {

        /**
         * Binds the factory authorized to unlock the schema.
         *
         * @param f the authorized {@link SessionReconstitutionFactory}
         * @return the schema stage
         */
        ISchemaStage withFactory(SessionReconstitutionFactory f);
    }

    /**
     * Builder stage requiring the persisted session schema.
     */
    public interface ISchemaStage {

        /**
         * Finalizes construction of the requirement.
         *
         * @param s the persisted {@link SessionSchema}
         * @return a fully initialized reconstitution requirement
         */
        SessionReconstitutionRequirement build(SessionSchema s);
    }

    /**
     * Internal staged builder implementation.
     */
    private static final class Builder
            implements IFactoryStage, ISchemaStage {

        /**
         * Authorized factory captured during construction.
         */
        private SessionReconstitutionFactory factory;

        /**
         * {@inheritDoc}
         */
        @Override
        public ISchemaStage withFactory(SessionReconstitutionFactory f) {
            this.factory = f;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public SessionReconstitutionRequirement build(SessionSchema s) {
            return new SessionReconstitutionRequirement(s, this.factory);
        }
    }
}
