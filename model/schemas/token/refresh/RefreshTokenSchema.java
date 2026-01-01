package com.authservice.domain.model.schemas.token.refresh;

import java.time.LocalDateTime;

import com.authservice.domain.model.schemas.token.abstractions.AbstractTokenSchema;

/**
 * Schema representing the structural data of a Refresh Token.
 * <p>
 * This schema extends {@link AbstractTokenSchema} by including a
 * {@code sessionId} field, which ties the refresh token to a specific
 * authentication session. This enables server-side session management,
 * including token invalidation, rotation, and revocation.
 * </p>
 *
 * <p>
 * As a schema, this class is:
 * <ul>
 * <li>Anemic and immutable</li>
 * <li>Transport-safe, holding only validated raw field values</li>
 * <li>Intended solely as an intermediate representation prior to
 * aggregate reconstitution</li>
 * </ul>
 * </p>
 */
public final class RefreshTokenSchema extends AbstractTokenSchema {

    private final String sessionId;

    /**
     * Constructs a refresh token schema from validated raw values.
     *
     * @param tokenId   the raw refresh token identifier (UUID string)
     * @param issuedAt  the issuance timestamp
     * @param expiresAt the expiration timestamp
     * @param sessionId the raw session identifier associated with this token
     */
    RefreshTokenSchema(
            String tokenId,
            LocalDateTime issuedAt,
            LocalDateTime expiresAt,
            String sessionId) {

        super(tokenId, issuedAt, expiresAt);
        this.sessionId = sessionId;
    }

    /**
     * Returns the session identifier associated with this refresh token.
     * <p>
     * This value is expected to be converted into a domain-specific
     * session ID value object during aggregate reconstitution.
     * </p>
     *
     * @return the raw session identifier
     */
    public String getSessionId() {
        return sessionId;
    }
}
