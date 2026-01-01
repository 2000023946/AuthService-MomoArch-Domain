package com.authservice.domain.model.schemas.token.verification;

import java.time.LocalDateTime;

import com.authservice.domain.model.schemas.token.abstractions.AbstractTokenSchema;

/**
 * Schema representing a Verification Token.
 * <p>
 * This schema extends {@link AbstractTokenSchema} by including the
 * {@code userId}
 * associated with the verification process (e.g., email verification, account
 * activation).
 * </p>
 *
 * <p>
 * Although structurally identical to other token schemas, this class is
 * semantically distinct: it is used <strong>exclusively for account
 * verification</strong>.
 * This separation ensures that verification logic cannot accidentally consume
 * tokens intended for other purposes (e.g., password reset).
 * </p>
 *
 * <p>
 * This class is immutable, anemic, and transport-safe, intended solely as
 * a stable intermediate representation prior to aggregate reconstitution.
 * </p>
 */
public final class VerificationTokenSchema extends AbstractTokenSchema {

    private final String userId;

    /**
     * Constructs a verification token schema from validated raw values.
     *
     * @param tokenId   the raw token identifier (UUID string)
     * @param issuedAt  the issuance timestamp
     * @param expiresAt the expiration timestamp
     * @param userId    the user identifier associated with this token
     */
    VerificationTokenSchema(
            String tokenId,
            LocalDateTime issuedAt,
            LocalDateTime expiresAt,
            String userId) {

        super(tokenId, issuedAt, expiresAt);
        this.userId = userId;
    }

    /**
     * Returns the identifier of the user associated with this verification token.
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
