package com.authservice.domain.model.schemas.session;

import com.authservice.domain.model.schemas.abstractions.AbstractSchemaProof;

/**
 * Represents a validated proof of a {@link SessionSchemaProof}.
 * <p>
 * Serves as a "Certificate of Validity": if a {@link SessionProof} exists,
 * the underlying schema is guaranteed to contain all required fields,
 * properly formatted, and authorized for consumption.
 */
public final class SessionSchemaProof extends AbstractSchemaProof<SessionSchema> {

    /**
     * Constructs a new {@link SessionProof}.
     *
     * @param schema the validated {@link SessionSchema}
     */
    private SessionSchemaProof(SessionSchema schema) {
        super(schema);
    }

    /**
     * Factory method to create a {@link SessionSchemaProof}.
     *
     * @param schema the validated {@link SessionSchema}
     * @return a new {@link SessionSchemaProof} instance
     */
    static SessionSchemaProof create(SessionSchema schema) {
        return new SessionSchemaProof(schema);
    }
}
