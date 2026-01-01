package com.authservice.domain.model.schemas.token.access;

import java.time.LocalDateTime;

import com.authservice.domain.model.schemas.token.abstractions.AbstractTokenSchema;

/**
 * Schema representing the structural data of an Access Token.
 * <p>
 * This schema extends {@link AbstractTokenSchema} by adding the
 * {@code userId} field required to establish authorization context
 * for access-controlled operations.
 * </p>
 *
 * <p>
 * As a schema, this class:
 * <ul>
 * <li>Contains no business or authorization logic</li>
 * <li>Holds only validated, transport-safe field values</li>
 * <li>Acts as an immutable intermediary during aggregate reconstitution</li>
 * </ul>
 * </p>
 *
 * <p>
 * All fields are assumed to have been validated prior to construction
 * by the corresponding access token schema requirement and factory.
 * </p>
 */
public final class AccessTokenSchema extends AbstractTokenSchema {

    private final String userId;

    /**
     * Constructs an access token schema from validated raw values.
     *
     * @param tokenId   the raw access token identifier (UUID string)
     * @param issuedAt  the issuance timestamp
     * @param expiresAt the expiration timestamp
     * @param userId    the raw user identifier associated with the token
     */
    AccessTokenSchema(
            String tokenId,
            LocalDateTime issuedAt,
            LocalDateTime expiresAt,
            String userId) {

        super(tokenId, issuedAt, expiresAt);
        this.userId = userId;
    }

    /**
     * Returns the raw user identifier associated with this access token.
     * <p>
     * This value is expected to be converted into a domain-specific
     * user identifier value object during aggregate reconstitution.
     * </p>
     *
     * @return the raw user identifier
     */
    public String getUserId() {
        return userId;
    }
}
