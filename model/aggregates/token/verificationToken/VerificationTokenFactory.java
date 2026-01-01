package com.authservice.domain.model.aggregates.token.verificationToken;

import com.authservice.domain.model.valueobjects.UUIDValueObject;

import java.time.LocalDateTime;

import com.authservice.domain.model.aggregates.ITokenFactory;
import com.authservice.domain.model.aggregates.claims.verfication.VerificationTokenClaims;

/**
 * Factory class for creating and reconstituting
 * {@link VerificationTokenAggregate}
 * instances.
 * <p>
 * This implementation of {@link ITokenFactory} handles
 * {@link VerificationTokenPayload} for token creation
 * and {@link VerificationTokenClaims} for token reconstitution. It encapsulates
 * the
 * logic to convert
 * between payloads, claims, and domain aggregates.
 * </p>
 * <p>
 * Creation involves generating a new token aggregate with a user ID, while
 * reconstitution
 * rebuilds an existing token aggregate from stored claims, including token ID
 * and timestamps.
 * </p>
 */
public class VerificationTokenFactory
        implements ITokenFactory<VerificationTokenPayload, VerificationTokenClaims, VerificationTokenAggregate> {

    /**
     * Creates a new {@link VerificationTokenAggregate} from the given
     * {@link VerificationTokenPayload}.
     * <p>
     * Converts the primitive user ID from the payload into a
     * {@link UUIDValueObject}
     * and constructs a new verification token aggregate.
     * </p>
     *
     * @param payload the verification token payload containing user identification
     * @return a new {@link VerificationTokenAggregate} instance
     */
    @Override
    public VerificationTokenAggregate create(VerificationTokenPayload payload) {
        UUIDValueObject userIdVO = new UUIDValueObject(payload.getUserId());
        return new VerificationTokenAggregate(userIdVO);
    }

    /**
     * Reconstitutes an existing {@link VerificationTokenAggregate} from the given
     * {@link VerificationTokenClaims}.
     * <p>
     * Converts primitive claim values into value objects and parses timestamp
     * strings into {@link LocalDateTime}.
     * Constructs a fully populated verification token aggregate including token ID,
     * user
     * ID, issuance time, and expiration time.
     * </p>
     *
     * @param claims the verification token claims containing token ID, user ID, and
     *               timestamps
     * @return a reconstituted {@link VerificationTokenAggregate} instance
     */
    @Override
    public VerificationTokenAggregate reconstitute(VerificationTokenClaims claims) {
        UUIDValueObject userIdVO = new UUIDValueObject(claims.getUserId());
        UUIDValueObject tokenIdVO = new UUIDValueObject(claims.getTokenId());

        LocalDateTime issuedAt = LocalDateTime.parse(claims.getIssuedAt());
        LocalDateTime expiresAt = LocalDateTime.parse(claims.getExpiresAt());

        return new VerificationTokenAggregate(tokenIdVO, issuedAt, expiresAt, userIdVO);
    }
}
