package com.authservice.domain.model.aggregates.token.refreshToken;

import com.authservice.domain.model.valueobjects.UUIDValueObject;
import java.time.LocalDateTime;

/**
 * Internal domain-certified evidence of a successful Refresh Token validation.
 * <p>
 * This class is package-private to ensure that only the
 * {@link RefreshTokenAggregate}
 * can issue it, and only the factories within this package can consume it.
 * </p>
 *
 * @author Mohamed Abucar
 */
public final class RefreshValidationProof {

    private final UUIDValueObject userId;
    private final UUIDValueObject sessionId;
    private final LocalDateTime validatedAt;

    /**
     * Constructs the proof.
     *
     * @param userId      The {@link UUIDValueObject} of the user owner.
     * @param sessionId   The {@link UUIDValueObject} of the verified session.
     * @param validatedAt The {@link LocalDateTime} of the verification event.
     */
    RefreshValidationProof(UUIDValueObject userId, UUIDValueObject sessionId, LocalDateTime validatedAt) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.validatedAt = validatedAt;
    }

    /** @return The {@link UUIDValueObject} of the user. */
    public UUIDValueObject getUserId() {
        return userId;
    }

    /** @return The {@link UUIDValueObject} of the session. */
    public UUIDValueObject getSessionId() {
        return sessionId;
    }

    /** @return The {@link LocalDateTime} when validation occurred. */
    public LocalDateTime getValidatedAt() {
        return validatedAt;
    }
}