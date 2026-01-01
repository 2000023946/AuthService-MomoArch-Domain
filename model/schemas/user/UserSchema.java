package com.authservice.domain.model.schemas.user;

import java.time.LocalDateTime;

import com.authservice.domain.model.schemas.interfaces.ISchema;

/**
 * Represents the structural data of a user account.
 * <p>
 * This schema encapsulates all fields required to reconstruct a
 * {@link UserAggregate},
 * including credentials, verification status, login attempt counters, and
 * timestamps.
 * <p>
 * Instances are immutable. Fields are final and exposed only via getters.
 * The {@link UserFactory} is responsible for safe construction of this schema.
 */
public final class UserSchema implements ISchema {

    /** Unique identifier for the user */
    private final String userId;

    /** Email address of the user */
    private final String email;

    /** Hashed password of the user */
    private final String passwordHash;

    /** Whether the user's email has been verified */
    private final boolean verified;

    /** Number of consecutive failed login attempts */
    private final int failedLoginAttempts;

    /** Timestamp when the user was created */
    private final LocalDateTime createdAt;

    /** Timestamp of the last update to the user's profile */
    private final LocalDateTime updatedAt;

    /** Timestamp of the last password reset request, may be null */
    private final LocalDateTime lastPasswordResetRequestAt;

    /**
     * Constructs a new {@link UserSchema}.
     *
     * @param userId                     unique user ID
     * @param email                      user's email address
     * @param passwordHash               hashed password
     * @param verified                   email verification status
     * @param failedLoginAttempts        failed login attempt count
     * @param createdAt                  timestamp of creation
     * @param updatedAt                  timestamp of last update
     * @param lastPasswordResetRequestAt timestamp of last password reset request
     */
    UserSchema(String userId, String email, String passwordHash, boolean verified,
            int failedLoginAttempts, LocalDateTime createdAt, LocalDateTime updatedAt,
            LocalDateTime lastPasswordResetRequestAt) {
        this.userId = userId;
        this.email = email;
        this.passwordHash = passwordHash;
        this.verified = verified;
        this.failedLoginAttempts = failedLoginAttempts;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastPasswordResetRequestAt = lastPasswordResetRequestAt;
    }

    /** @return the unique user ID */
    public String getUserId() {
        return userId;
    }

    /** @return the user's email address */
    public String getEmail() {
        return email;
    }

    /** @return the hashed password */
    public String getPasswordHash() {
        return passwordHash;
    }

    /** @return true if email verified, false otherwise */
    public boolean isVerified() {
        return verified;
    }

    /** @return the number of consecutive failed login attempts */
    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    /** @return the creation timestamp */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /** @return the last update timestamp */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /** @return the timestamp of the last password reset request, may be null */
    public LocalDateTime getLastPasswordResetRequestAt() {
        return lastPasswordResetRequestAt;
    }
}
