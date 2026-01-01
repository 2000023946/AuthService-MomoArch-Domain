package com.authservice.domain.model.schemas.session;

import java.time.LocalDateTime;

import com.authservice.domain.model.schemas.interfaces.ISchema;

/**
 * Represents the structural data of a user session.
 * <p>
 * This schema holds all necessary information for a session aggregate,
 * including creation and expiration timestamps, last activity tracking,
 * and revocation status.
 * <p>
 * This is an immutable data holder. All fields are final and exposed
 * via getters. Instances should only be constructed via {@link SessionFactory}.
 */
public final class SessionSchema implements ISchema {

    /** Unique identifier for the session */
    private final String sessionId;

    /** Identifier for the associated user */
    private final String userId;

    /** Timestamp of session creation */
    private final LocalDateTime createdAt;

    /** Timestamp of the user's last activity within this session */
    private final LocalDateTime lastActivityAt;

    /** Timestamp when the session expires */
    private final LocalDateTime expiresAt;

    /** Flag indicating whether the session has been revoked */
    private final boolean isRevoked;

    /**
     * Constructs a new {@link SessionSchema}.
     *
     * @param sessionId      unique identifier of the session
     * @param userId         associated user ID
     * @param createdAt      creation timestamp
     * @param lastActivityAt last activity timestamp
     * @param expiresAt      expiration timestamp
     * @param isRevoked      revocation status
     */
    SessionSchema(String sessionId, String userId, LocalDateTime createdAt,
            LocalDateTime lastActivityAt, LocalDateTime expiresAt, boolean isRevoked) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.lastActivityAt = lastActivityAt;
        this.expiresAt = expiresAt;
        this.isRevoked = isRevoked;
    }

    /** @return the session ID */
    public String getSessionId() {
        return sessionId;
    }

    /** @return the user ID */
    public String getUserId() {
        return userId;
    }

    /** @return the session creation timestamp */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /** @return the last activity timestamp */
    public LocalDateTime getLastActivityAt() {
        return lastActivityAt;
    }

    /** @return the session expiration timestamp */
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    /** @return true if the session is revoked, false otherwise */
    public boolean isRevoked() {
        return isRevoked;
    }
}
