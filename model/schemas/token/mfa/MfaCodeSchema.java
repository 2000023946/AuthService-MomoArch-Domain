package com.authservice.domain.model.schemas.token.mfa;

import java.time.LocalDateTime;

import com.authservice.domain.model.schemas.token.abstractions.AbstractTokenSchema;

/**
 * Schema representing the structural data of an MFA (multi-factor
 * authentication) code.
 * <p>
 * This schema extends {@link AbstractTokenSchema} by including:
 * <ul>
 * <li>{@code code} – the numeric MFA code</li>
 * <li>{@code userId} – the identifier of the user the code is associated
 * with</li>
 * </ul>
 * </p>
 *
 * <p>
 * As with all token schemas, this class:
 * <ul>
 * <li>Is anemic and immutable</li>
 * <li>Contains only validated, transport-safe raw values</li>
 * <li>Serves as a stable intermediary prior to aggregate reconstitution</li>
 * </ul>
 * </p>
 */
public final class MfaCodeSchema extends AbstractTokenSchema {

    private final int code;
    private final String userId;

    /**
     * Constructs an MFA code schema from validated raw values.
     *
     * @param tokenId   the raw token identifier (UUID string)
     * @param issuedAt  the issuance timestamp
     * @param expiresAt the expiration timestamp
     * @param code      the numeric MFA code
     * @param userId    the identifier of the associated user
     */
    MfaCodeSchema(
            String tokenId,
            LocalDateTime issuedAt,
            LocalDateTime expiresAt,
            int code,
            String userId) {

        super(tokenId, issuedAt, expiresAt);
        this.code = code;
        this.userId = userId;
    }

    /**
     * Returns the numeric MFA code.
     *
     * @return the MFA code
     */
    public int getCode() {
        return code;
    }

    /**
     * Returns the identifier of the user associated with this MFA code.
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
