package com.authservice.domain.model.aggregates.session;

import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionFactory;
import com.authservice.domain.model.aggregates.session.requirements.SessionReconstitutionRequirement;
import com.authservice.domain.model.schemas.session.SessionSchema;
import com.authservice.domain.model.valueobjects.UUIDValueObject;

/**
 * Specialized Factory responsible for reanimating (rehydrating) existing
 * {@link SessionAggregate} instances
 * from persisted {@link SessionSchema} state.
 *
 * <p>
 * This factory enforces the <strong>Momo-Architecture</strong>
 * triple-handshake:
 * <ol>
 * <li>The {@link SessionReconstitutionRequirement} verifies the caller
 * factory</li>
 * <li>Primitive state values from the {@link SessionSchema} are mapped to
 * domain {@link UUIDValueObject}s</li>
 * <li>The {@link SessionAggregate} is rehydrated and sealed into a
 * {@link SessionReconstitutionProof}</li>
 * </ol>
 * </p>
 *
 * <p>
 * This class is immutable and thread-safe; all state transformations occur
 * during reconstitution.
 * </p>
 *
 * @see SessionAggregate
 * @see SessionReconstitutionRequirement
 * @see SessionReconstitutionProof
 * @see SessionSchema
 * @see IDomainReconstitutionFactory
 */
public final class SessionReconstitutionFactory
        implements IDomainReconstitutionFactory<SessionReconstitutionProof, SessionReconstitutionRequirement> {

    /**
     * Reanimates a {@link SessionAggregate} from the provided
     * {@link SessionReconstitutionRequirement}.
     *
     * <p>
     * Steps performed:
     * <ol>
     * <li>Handshake: Ensure that the caller is authorized to access the schema</li>
     * <li>Primitive Mapping: Convert schema strings/IDs to domain value
     * objects</li>
     * <li>Hydration: Create a fully initialized {@link SessionAggregate}</li>
     * <li>Proof Sealing: Wrap the aggregate in a
     * {@link SessionReconstitutionProof}</li>
     * </ol>
     * </p>
     *
     * @param requirement the capability-guarded requirement containing the
     *                    persisted session state
     * @return a {@link SessionReconstitutionProof} containing the live
     *         {@link SessionAggregate} and source requirement
     * @throws SecurityException        if the caller factory is not authorized
     * @throws IllegalArgumentException if the requirement or underlying schema is
     *                                  null
     */
    @Override
    public SessionReconstitutionProof reconstitute(SessionReconstitutionRequirement requirement) {
        // Step 1: Execute Handshake
        SessionSchema schema = requirement.getAuthorizedSchema(this);

        // Step 2: Map Schema Primitives to Domain Value Objects
        UUIDValueObject sessionId = new UUIDValueObject(schema.getSessionId());
        UUIDValueObject userId = new UUIDValueObject(schema.getUserId());

        // Step 3: Reconstitute the Aggregate (Using the hydration constructor)
        SessionAggregate session = new SessionAggregate(
                sessionId,
                userId,
                userId, schema.getCreatedAt(),
                schema.getLastActivityAt(),
                schema.getExpiresAt(),
                schema.isRevoked());

        // Step 4: Seal in Proof
        return new SessionReconstitutionProof(session, requirement);
    }
}
