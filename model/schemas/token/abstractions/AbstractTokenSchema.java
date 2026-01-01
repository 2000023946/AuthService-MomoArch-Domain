package com.authservice.domain.model.schemas.token.abstractions;

import java.time.LocalDateTime;

import com.authservice.domain.model.schemas.interfaces.ISchema;

/**
 * Base schema for all token-related aggregates.
 * <p>
 * This class represents an <strong>anemic, immutable field schema</strong>
 * that captures the structural data required to reconstitute a token
 * aggregate from validated input.
 * </p>
 *
 * <p>
 * Token schemas intentionally expose <em>raw, transport-safe types</em>
 * (such as {@link String} and {@link LocalDateTime}) rather than domain
 * value objects. This allows validation and normalization to occur
 * <em>before</em> domain construction, while keeping schemas free of
 * business behavior.
 * </p>
 *
 * <p>
 * As a schema, this type:
 * <ul>
 * <li>Contains no business logic</li>
 * <li>Is immutable once constructed</li>
 * <li>Serves solely as an intermediate representation</li>
 * </ul>
 * </p>
 */
public abstract class AbstractTokenSchema implements ISchema {

    private final String tokenId;
    private final LocalDateTime issuedAt;
    private final LocalDateTime expiresAt;

    /**
     * Constructs a token schema from validated raw values.
     *
     * @param tokenId   the raw token identifier (UUID string)
     * @param issuedAt  the timestamp at which the token was issued
     * @param expiresAt the timestamp at which the token expires
     */
    protected AbstractTokenSchema(
            String tokenId,
            LocalDateTime issuedAt,
            LocalDateTime expiresAt) {

        this.tokenId = tokenId;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    /**
     * Returns the raw token identifier.
     * <p>
     * This value is expected to be converted into a domain-specific
     * identifier value object during aggregate reconstitution.
     * </p>
     *
     * @return the raw token identifier string
     */
    public final String getTokenId() {
        return tokenId;
    }

    /**
     * Returns the issuance timestamp of the token.
     *
     * @return the issuance time
     */
    public final LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    /**
     * Returns the expiration timestamp of the token.
     *
     * @return the expiration time
     */
    public final LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}
