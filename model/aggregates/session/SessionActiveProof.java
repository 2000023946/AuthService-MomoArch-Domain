package com.authservice.domain.model.aggregates.session;

import com.authservice.domain.model.valueobjects.UUIDValueObject;
import java.time.LocalDateTime;

/**
 * Certified evidence that a session is currently active and valid.
 * <p>
 * This proof is public for verification by downstream factories, but carries
 * a package-private constructor to ensure it can only be issued by the
 * {@link SessionAggregate} after a successful invariant check.
 * </p>
 *
 * @author Mohamed Abucar
 */
public final class SessionActiveProof {

    private final UUIDValueObject sessionId;
    private final UUIDValueObject userId;
    private final LocalDateTime verifiedAt;

    /**
     * Package-private constructor to prevent external spoofing.
     *
     * @param sessionId  The verified {@link UUIDValueObject} of the session.
     * @param userId     The {@link UUIDValueObject} of the session owner.
     * @param verifiedAt The {@link LocalDateTime} when the state was certified.
     */
    SessionActiveProof(UUIDValueObject sessionId, UUIDValueObject userId, LocalDateTime verifiedAt) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.verifiedAt = verifiedAt;
    }

    /** @return The unique {@link UUIDValueObject} of the verified session. */
    public UUIDValueObject getSessionId() {
        return sessionId;
    }

    /** @return The {@link UUIDValueObject} of the user who owns the session. */
    public UUIDValueObject getUserId() {
        return userId;
    }

    /** @return The {@link LocalDateTime} indicating when this proof was issued. */
    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }
}
