package com.authservice.domain.model.aggregates.session;

import java.time.LocalDateTime;

import com.authservice.domain.model.valueobjects.UUIDValueObject;

/**
 * Certified evidence that a session has reached its temporal expiration.
 * <p>
 * Issued when the current system time exceeds the session's internal
 * expiration boundary.
 * </p>
 *
 * @author Mohamed Abucar
 */
public final class SessionExpiryProof {

    private final UUIDValueObject sessionId;
    private final LocalDateTime expiredAt;

    /**
     * Package-private constructor to bind expiration detection to the
     * aggregate's temporal logic.
     *
     * @param sessionId The {@link UUIDValueObject} of the expired session.
     * @param expiredAt The {@link LocalDateTime} when expiration was recorded.
     */
    SessionExpiryProof(UUIDValueObject sessionId, LocalDateTime expiredAt) {
        this.sessionId = sessionId;
        this.expiredAt = expiredAt;
    }

    /** @return The {@link UUIDValueObject} of the session that is now invalid. */
    public UUIDValueObject getSessionId() {
        return sessionId;
    }

    /** @return The {@link LocalDateTime} of the expiration event. */
    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }
}