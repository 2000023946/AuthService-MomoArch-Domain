package com.authservice.domain.model.aggregates.session;

import com.authservice.domain.model.valueobjects.UUIDValueObject;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Aggregate Root representing a user's authentication session.
 * <p>
 * Models the lifecycle through Proof-Oriented logic, issuing certified
 * state-certificates (Proofs) rather than simple booleans.
 * </p>
 * * @author Mohamed Abucar
 */
public class SessionAggregate {
    private static final int EXPIRES_DAYS = 90;

    private final UUIDValueObject sessionId;
    private final UUIDValueObject userId;
    private final LocalDateTime createdAt;
    private LocalDateTime lastActivityAt;
    private LocalDateTime expiresAt;
    private boolean isRevoked;

    /**
     * Internal Constructor for Session Creation.
     *
     * @param sessionId      The unique session identifier.
     * @param userId         The owning user identifier.
     * @param refreshTokenId The associated refresh token identifier.
     */
    SessionAggregate(UUIDValueObject sessionId, UUIDValueObject userId, UUIDValueObject refreshTokenId) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.lastActivityAt = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plusDays(EXPIRES_DAYS);
        this.isRevoked = false;
    }

    /**
     * Internal Constructor for Reconstitution.
     */
    SessionAggregate(UUIDValueObject sessionId, UUIDValueObject userId, UUIDValueObject refreshTokenId,
            LocalDateTime createdAt, LocalDateTime lastActivityAt, LocalDateTime expiresAt, boolean isRevoked) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.lastActivityAt = lastActivityAt;
        this.expiresAt = expiresAt;
        this.isRevoked = isRevoked;
    }

    /**
     * Certifies the deactivation of the session.
     *
     * @return A {@link SessionDeactivationProof} confirming the revocation.
     * @throws IllegalStateException if the session is already revoked.
     */
    public SessionDeactivationProof deactivate() {
        if (this.isRevoked) {
            throw new IllegalStateException("Session is already revoked.");
        }
        this.isRevoked = true;
        return new SessionDeactivationProof(this.sessionId, LocalDateTime.now());
    }

    /**
     * Attempts to certify that the session is active.
     *
     * @return An {@link Optional} of {@link SessionActiveProof} if valid; otherwise
     *         empty.
     */
    public Optional<SessionActiveProof> getActiveProof() {
        if (isRevoked || isExpired()) {
            return Optional.empty();
        }
        return Optional.of(new SessionActiveProof(this.sessionId, this.userId, LocalDateTime.now()));
    }

    /**
     * Attempts to certify that the session has expired.
     *
     * @return An {@link Optional} of {@link SessionExpiryProof} if expired;
     *         otherwise empty.
     */
    public Optional<SessionExpiryProof> getExpiryProof() {
        if (isExpired()) {
            return Optional.of(new SessionExpiryProof(this.sessionId, LocalDateTime.now()));
        }
        return Optional.empty();
    }

    /**
     * Checks temporal expiration.
     *
     * @return {@code true} if current time > expiresAt.
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    /**
     * Updates activity "pulse".
     *
     * @throws IllegalStateException if revoked.
     */
    public void updateLastActivity() {
        if (this.isRevoked) {
            throw new IllegalStateException("Cannot update activity on a revoked session.");
        }
        this.lastActivityAt = LocalDateTime.now();
    }

    // --- Getters ---
    public UUIDValueObject getSessionId() {
        return sessionId;
    }

    public UUIDValueObject getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastActivityAt() {
        return lastActivityAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public boolean isRevoked() {
        return isRevoked;
    }
}