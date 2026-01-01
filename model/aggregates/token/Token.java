package com.authservice.domain.model.aggregates.token;

import com.authservice.domain.model.valueobjects.UUIDValueObject;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base abstraction for all Token Aggregates within the system.
 * <p>
 * This class encapsulates common token attributes such as identity,
 * issuance timestamps, and expiration logic, adhering to the
 * 3NF database schema requirements.
 * </p>
 * * @author Mohamed Abucar
 */
public abstract class Token {

    protected boolean isRevoked;

    /** The unique identifier for this specific token instance. */
    private final UUIDValueObject tokenId;

    /** The timestamp indicating when this token was generated. */
    private final LocalDateTime issuedAt;

    /** The timestamp indicating when this token becomes invalid. */
    private final LocalDateTime expiresAt;

    /**
     * Constructor for creating a BRAND NEW Token.
     * <p>
     * Automatically generates a unique ID and calculates expiration
     * based on the concrete implementation's {@link #getExpiryMinutes()} policy.
     * </p>
     */
    protected Token() {
        this.tokenId = new UUIDValueObject(UUID.randomUUID().toString());
        this.issuedAt = LocalDateTime.now();
        this.expiresAt = issuedAt.plusMinutes(getExpiryMinutes());
        this.isRevoked = false;
    }

    /**
     * Constructor for RECONSTITUTING an existing Token from state.
     *
     * @param tokenId   The existing {@link UUIDValueObject} identifier for the
     *                  token.
     * @param issuedAt  The {@link LocalDateTime} when the token was originally
     *                  issued.
     * @param expiresAt The {@link LocalDateTime} when the token is set to expire.
     */
    protected Token(UUIDValueObject tokenId, LocalDateTime issuedAt, LocalDateTime expiresAt) {
        this.tokenId = tokenId;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.isRevoked = false;
    }

    /**
     * Template method to define the lifespan of the concrete token type.
     *
     * @return The number of minutes before the token expires as an {@code int}.
     */
    protected abstract int getExpiryMinutes();

    /**
     * Validates if the token is still within its active lifespan.
     *
     * @return {@code true} if the current time is before the expiration,
     *         {@code false} otherwise.
     */
    public final boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    /** @return The unique {@link UUIDValueObject} of the token. */
    public final UUIDValueObject getTokenId() {
        return tokenId;
    }

    /** @return The {@link LocalDateTime} of issuance. */
    public final LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    /** @return The {@link LocalDateTime} of expiration. */
    public final LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public final boolean getIsRevoked() {
        return isRevoked;
    }
}