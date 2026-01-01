package com.authservice.domain.model.aggregates.token.refreshToken;

import com.authservice.domain.model.aggregates.token.Token;
import com.authservice.domain.model.valueobjects.UUIDValueObject;
import java.time.LocalDateTime;

/**
 * Specialized Token Aggregate used to refresh access sessions.
 * <p>
 * This aggregate is pinned to a specific {@code sessionId} to facilitate
 * token rotation and session management. It extends the base {@link Token}
 * abstraction to inherit common temporal and identity logic.
 * </p>
 * * @author Mohamed Abucar
 */
public final class RefreshTokenAggregate extends Token {

    /** The lifespan of a refresh token in days. */
    private static final int EXPIRY_DAYS = 7;

    /** The unique identifier of the session this refresh token is bound to. */
    private final UUIDValueObject sessionId;

    /**
     * Constructor for creating a BRAND NEW RefreshToken.
     * <p>
     * Used during the Login or Token Refresh flows. It generates a new
     * identity and calculates expiration based on the 7-day policy.
     * </p>
     *
     * @param sessionId The {@link UUIDValueObject} of the active session
     *                  associated with this token.
     */
    RefreshTokenAggregate(UUIDValueObject sessionId) {
        super();
        this.sessionId = sessionId;
    }

    /**
     * Constructor for RECONSTITUTING a RefreshToken from persistence.
     * <p>
     * This constructor is utilized by the {@code RefreshTokenReconstitutionFactory}
     * to reanimate a token's state from the database.
     * </p>
     *
     * @param tokenId   The existing {@link UUIDValueObject} from the SQL Token
     *                  table.
     * @param issuedAt  The original {@link LocalDateTime} of token issuance.
     * @param expiresAt The original {@link LocalDateTime} of expiration.
     * @param sessionId The {@link UUIDValueObject} of the pinned session.
     */
    RefreshTokenAggregate(
            UUIDValueObject tokenId,
            LocalDateTime issuedAt,
            LocalDateTime expiresAt,
            UUIDValueObject sessionId) {
        super(tokenId, issuedAt, expiresAt);
        this.sessionId = sessionId;
    }

    /**
     * Neutralizes the token and issues a certified Proof of Deactivation.
     * <p>
     * Once deactivated, the token is permanently invalid. This is typically
     * invoked during Token Rotation to prevent Replay Attacks.
     * </p>
     *
     * @return A {@link TokenDeactivationProof} confirming the state change.
     * @throws IllegalStateException if the token is already revoked.
     */
    public TokenDeactivationProof deactivate() {
        if (this.getIsRevoked()) {
            throw new IllegalStateException("Token is already revoked.");
        }
        this.isRevoked = true;
        return new TokenDeactivationProof(this.getTokenId(), LocalDateTime.now());
    }

    /**
     * Validates the token against a session context and issues a certified Proof
     * upon success.
     * <p>
     * This transition moves the system from a simple boolean check to a
     * proof-based authorization model. If the session matches and the token
     * is not expired, a {@link RefreshValidationProof} is generated.
     * </p>
     *
     * @param sessionIdToMatch The {@link UUIDValueObject} of the session being
     *                         refreshed.
     * @param ownerId          The {@link UUIDValueObject} of the user who owns the
     *                         session.
     * @return An {@link java.util.Optional} containing the
     *         {@link RefreshValidationProof}
     *         if valid; otherwise empty.
     */
    public java.util.Optional<RefreshValidationProof> validateFor(
            UUIDValueObject sessionIdToMatch,
            UUIDValueObject ownerId) {

        // Logical Guard: Temporal and Contextual check
        if (this.isExpired() || !this.sessionId.equals(sessionIdToMatch)) {
            return java.util.Optional.empty();
        }

        // Issue the Proof (Internal constructor)
        return java.util.Optional.of(new RefreshValidationProof(
                ownerId,
                this.sessionId,
                LocalDateTime.now()));
    }

    /**
     * Defines the expiry policy for Refresh Tokens in minutes.
     * <p>
     * Calculated as: {@code 60 minutes * 24 hours * 7 days}.
     * </p>
     *
     * @return The total lifespan of the token in minutes as an {@code int}.
     */
    @Override
    protected int getExpiryMinutes() {
        return 60 * 24 * EXPIRY_DAYS;
    }

    /**
     * Retrieves the session identifier associated with this token.
     * 
     * @return The {@link UUIDValueObject} representing the session owner.
     * 
     */
    public UUIDValueObject getSessionId() {
        return sessionId;
    }
}