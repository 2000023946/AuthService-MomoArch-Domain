package com.authservice.domain.model.schemas.session;

import com.authservice.domain.model.aggregates.mapping.interfaces.IMap;
import com.authservice.domain.model.schemas.interfaces.ISchemaFactory;

/**
 * Factory responsible for creating {@link SessionSchema} instances.
 * <p>
 * Acts as the <strong>Information Expert</strong> for session construction:
 * it knows which fields to extract from an {@link IMap} and ensures
 * that the created schema is structurally valid.
 */
public final class SessionFactory implements ISchemaFactory<SessionSchemaProof, SessionRequirement> {

    /** Private constructor to enforce usage of the static factory method */
    private SessionFactory() {
    }

    /**
     * Creates a {@link SessionSchema} from a given requirement.
     * <p>
     * Uses the <strong>Capability-Based Security</strong> pattern: the
     * {@link SessionRequirement} will only yield data to this authorized factory.
     *
     * @param requirement the session requirement containing raw mapping
     * @return a new, fully populated {@link SessionSchema}
     */
    @Override
    public SessionSchemaProof create(SessionRequirement requirement) {
        IMap map = requirement.getMapping(this);

        SessionSchema sessionSchema = new SessionSchema(
                map.getString("sessionId"),
                map.getString("userId"),
                map.getDateTime("createdAt"),
                map.getDateTime("lastActivityAt"),
                map.getDateTime("expiresAt"),
                map.getBoolean("isRevoked"));

        return SessionSchemaProof.create(sessionSchema);
    }

    /**
     * Static factory method to obtain a {@link SessionFactory} instance.
     *
     * @return a new {@link SessionFactory} instance
     */
    public static SessionFactory createFactory() {
        return new SessionFactory();
    }
}
