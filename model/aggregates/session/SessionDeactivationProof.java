package com.authservice.domain.model.aggregates.session;

import java.time.LocalDateTime;

import com.authservice.domain.model.valueobjects.UUIDValueObject;

/**
 * Certified evidence that a session has been successfully revoked.
 * <p>
 * Serves as a termination certificate for the session lifecycle, used to
 * notify security observers and infrastructure adapters.
 * </p>
 *
 * @author Mohamed Abucar
 */
public final class SessionDeactivationProof {

    private final UUIDValueObject sessionId;
    private final LocalDateTime revokedAt;

    /**
     * Package-private constructor to ensure the deactivation was
     * performed by the domain aggregate logic.
     *
     * @param sessionId The {@link UUIDValueObject} of the revoked session.
     * @param revokedAt The {@link LocalDateTime} of the deactivation event.
     */
    SessionDeactivationProof(UUIDValueObject sessionId, LocalDateTime revokedAt) {
        this.sessionId = sessionId;
        this.revokedAt = revokedAt;
    }

    /** @return The {@link UUIDValueObject} of the deactivated session. */
    public UUIDValueObject getSessionId() {
        return sessionId;
    }

    /** @return The {@link LocalDateTime} when the revocation was finalized. */
    public LocalDateTime getRevokedAt() {
        return revokedAt;
    }
}