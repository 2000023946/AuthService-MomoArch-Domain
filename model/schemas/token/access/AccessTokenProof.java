package com.authservice.domain.model.schemas.token.access;

import com.authservice.domain.model.schemas.token.abstractions.AbstractTokenProof;

/**
 * A sealed proof for a validated Access Token schema.
 * <p>
 * Serves as a <strong>certificate of validity</strong> that guarantees:
 * <ul>
 * <li>The {@link AccessTokenSchema#getUserId()} is present and correctly
 * formatted</li>
 * <li>The underlying token ID, issuedAt, and expiresAt fields are valid</li>
 * <li>It is safe to use this schema for aggregate reconstitution</li>
 * </ul>
 * </p>
 *
 * <p>
 * By design, this class is immutable and final. It represents the final
 * verification step in the schema lifecycle before creating a domain aggregate.
 * </p>
 */
public final class AccessTokenProof extends AbstractTokenProof<AccessTokenSchema> {

    /**
     * Constructs a proof from a validated {@link AccessTokenSchema}.
     *
     * @param schema the validated Access Token schema
     */
    AccessTokenProof(AccessTokenSchema schema) {
        super(schema);
    }

}
