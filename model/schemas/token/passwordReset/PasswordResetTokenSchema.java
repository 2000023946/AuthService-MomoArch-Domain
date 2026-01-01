package com.authservice.domain.model.schemas.token.passwordReset;

import java.time.LocalDateTime;

import com.authservice.domain.model.schemas.token.abstractions.AbstractTokenSchema;

/**
 * Schema representing a Password Reset Token.
 * <p>
 * This schema extends {@link AbstractTokenSchema} by including the
 * {@code userId}
 * associated with the password reset process.
 * </p>
 *
 * <p>
 * While structurally identical to the {@link VerificationTokenSchema}, this
 * class
 * is semantically distinct: it is used <strong>exclusively for password reset
 * operations</strong>.
 * Maintaining a separate class prevents accidental misuse of tokens across
 * different domains (verification vs password reset).
 * </p>
 *
 * <p>
 * This class is immutable, anemic, and transport-safe, serving as a stable
 * intermediate representation prior to aggregate reconstitution.
 * </p>
 */
public final class PasswordResetTokenSchema extends AbstractTokenSchema {

    private final String userId;

    /**
     * Constructs a password reset token schema from validated raw values.
     *
     * @param tokenId   the raw token identifier (UUID string)
     * @param issuedAt  the issuance timestamp
     * @param expiresAt the expiration timestamp
     * @param userId    the user identifier associated with this token
     */
    PasswordResetTokenSchema(
            String tokenId,
            LocalDateTime issuedAt,
            LocalDateTime expiresAt,
            String userId) {

        super(tokenId, issuedAt, expiresAt);
        this.userId = userId;
    }

    /**
     * Returns the identifier of the user associated with this password reset token.
     * <p>
     * This value is expected to be converted into a domain-specific
     * user ID value object during aggregate reconstitution.
     * </p>
     *
     * @return the raw user identifier
     */
    public String getUserId() {
        return userId;
    }
}
