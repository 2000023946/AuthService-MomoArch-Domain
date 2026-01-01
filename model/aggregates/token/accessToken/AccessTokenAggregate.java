package com.authservice.domain.model.aggregates.token.accessToken;

import com.authservice.domain.model.aggregates.token.Token;
import com.authservice.domain.model.valueobjects.UUIDValueObject;

import java.time.LocalDateTime;

/**
 * Specialized Token Aggregate used for authorizing API requests.
 * <p>
 * This aggregate provides the link between a base Token and a specific User
 * context,
 * ensuring that authorization is scoped correctly.
 * </p>
 */
public final class AccessTokenAggregate extends Token {

    /** Default lifespan for access tokens as per security policy. */
    private static final int LIFESPAN_MINUTES = 5;

    /** The unique identifier of the user who owns this access token. */
    private final UUIDValueObject userId;

    /**
     * Constructor for creating a BRAND NEW AccessToken.
     * <p>
     * Use this when a user logs in or refreshes their session to generate
     * a fresh authorization credential.
     * </p>
     *
     * @param userId The {@link UUIDValueObject} of the user to whom this token is
     *               issued.
     */
    AccessTokenAggregate(UUIDValueObject userId) {
        super();
        this.userId = userId;
    }

    /**
     * Constructor for RECONSTITUTING an existing AccessToken from persistence.
     * <p>
     * Guided by the {@code AccessTokenReconstitutionFactory} via the Triple
     * Handshake.
     * </p>
     *
     * @param tokenId   The {@link UUIDValueObject} unique identifier.
     * @param userId    The {@link UUIDValueObject} of the owner.
     * @param issuedAt  The {@link LocalDateTime} of original issuance.
     * @param expiresAt The {@link LocalDateTime} of recorded expiration.
     */
    AccessTokenAggregate(
            UUIDValueObject tokenId,
            UUIDValueObject userId,
            LocalDateTime issuedAt,
            LocalDateTime expiresAt) {
        super(tokenId, issuedAt, expiresAt);
        this.userId = userId;
    }

    /**
     * Implements the expiry policy for Access Tokens.
     *
     * @return Constant value of 5 minutes.
     */
    @Override
    protected int getExpiryMinutes() {
        return LIFESPAN_MINUTES;
    }

    /**
     * @return The {@link UUIDValueObject} representing the owner of the token.
     */
    public UUIDValueObject getUserId() {
        return userId;
    }
}