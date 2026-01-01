package com.authservice.domain.model.schemas.token.refresh;

import com.authservice.domain.model.schemas.token.abstractions.AbstractTokenProof;

/**
 * A sealed proof for a validated Refresh Token schema.
 * <p>
 * Guarantees that:
 * <ul>
 * <li>The {@link RefreshTokenSchema#getSessionId()} is present and correctly
 * formatted</li>
 * <li>The underlying token ID, issuedAt, and expiresAt fields are valid</li>
 * <li>The schema is safe for aggregate reconstitution</li>
 * </ul>
 * </p>
 *
 * <p>
 * Immutable and final, this proof marks the last step in validation
 * before a Refresh Token aggregate can be instantiated.
 * </p>
 */
public final class RefreshTokenSchemaProof extends AbstractTokenProof<RefreshTokenSchema> {

    /**
     * Constructs a proof from a validated {@link RefreshTokenSchema}.
     *
     * @param schema the validated Refresh Token schema
     */
    RefreshTokenSchemaProof(RefreshTokenSchema schema) {
        super(schema);
    }
}
