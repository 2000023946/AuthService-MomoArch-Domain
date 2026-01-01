package com.authservice.domain.model.aggregates.token.mfaToken;

import com.authservice.domain.model.aggregates.ITokenFactory;
import com.authservice.domain.model.aggregates.claims.mfa.MFATokenClaims;
import com.authservice.domain.model.valueobjects.MFACodeValueObject;
import com.authservice.domain.model.valueobjects.UUIDValueObject;
import java.time.LocalDateTime;

/**
 * Factory class for creating and reconstituting {@link MFATokenAggregate}
 * instances.
 * <p>
 * This factory handles the construction of MFA tokens from both payloads
 * (creation)
 * and persisted claims (reconstitution). It ensures that raw strings and
 * primitive values
 * are converted into domain-specific Value Objects and that timestamps are
 * parsed correctly.
 * </p>
 * <p>
 * Creation involves generating a new MFA token for a user with a one-time code.
 * Reconstitution rebuilds an existing token aggregate from raw database values,
 * including token ID, user ID, timestamps, revocation status, and the MFA code.
 * </p>
 */
public class MFATokenFactory implements ITokenFactory<MFATokenPayload, MFATokenClaims, MFATokenAggregate> {

    /**
     * Creates a new {@link MFATokenAggregate} for a user challenge.
     * <p>
     * Generates a new token ID internally and wraps the raw user ID and code in
     * Value Objects.
     * </p>
     *
     * @param payload paylaod to create from the object
     * @return a newly created {@link MFATokenAggregate}
     */
    public MFATokenAggregate create(MFATokenPayload payload) {
        MFACodeValueObject codeVO = new MFACodeValueObject(payload.getCode());
        UUIDValueObject userIdVO = new UUIDValueObject(payload.getUserId());

        return new MFATokenAggregate(codeVO, userIdVO);
    }

    /**
     * Reconstitutes an existing {@link MFATokenAggregate} from persisted data.
     * <p>
     * Converts raw database values into domain-specific Value Objects and parses
     * timestamps.
     * Handles the full state including token ID, user ID, MFA code, issuance and
     * expiration times,
     * and revocation status.
     * </p>
     *
     * @param claims the claims for token
     * @return a reconstituted {@link MFATokenAggregate}
     * @throws java.time.format.DateTimeParseException if the date strings cannot be
     *                                                 parsed
     */
    public MFATokenAggregate reconstitute(MFATokenClaims claims) {

        UUIDValueObject tokenIdVO = new UUIDValueObject(claims.getTokenId());
        UUIDValueObject userIdVO = new UUIDValueObject(claims.getUserId());
        MFACodeValueObject codeVO = new MFACodeValueObject(claims.getCode());

        LocalDateTime issuedAt = LocalDateTime.parse(claims.getIssuedAt());
        LocalDateTime expiresAt = LocalDateTime.parse(claims.getExpiresAt());

        return new MFATokenAggregate(
                tokenIdVO,
                codeVO,
                issuedAt,
                expiresAt,
                userIdVO);
    }
}
