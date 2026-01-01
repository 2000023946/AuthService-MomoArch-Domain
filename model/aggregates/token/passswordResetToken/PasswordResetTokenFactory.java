package com.authservice.domain.model.aggregates.token.passswordResetToken;

import com.authservice.domain.model.valueobjects.UUIDValueObject;
import java.time.LocalDateTime;
import com.authservice.domain.model.aggregates.ITokenFactory;
import com.authservice.domain.model.aggregates.claims.passwordReset.PasswordResetTokenClaims;

/**
 * Factory responsible for the creation and reconstitution of
 * PasswordResetTokenAggregates.
 * <p>
 * Ensures that all identifiers and timestamps are correctly parsed and
 * validated
 * before the aggregate enters the Domain Layer.
 */
public class PasswordResetTokenFactory
        implements ITokenFactory<PasswordResetPayload, PasswordResetTokenClaims, PasswordResetTokenAggregate> {

    /**
     * Creates a brand new PasswordResetToken.
     * Use this when a user initiates the "Forgot Password" process.
     *
     * @param payload paylaod to make the token
     * @return A fully initialized PasswordResetTokenAggregate.
     */
    public PasswordResetTokenAggregate create(PasswordResetPayload payload) {
        // Validation of ID strings happens during VO instantiation
        UUIDValueObject userIdVO = new UUIDValueObject(payload.getUserId());

        return new PasswordResetTokenAggregate(userIdVO);
    }

    /**
     * Reconstitutes a PasswordResetToken from raw database state.
     * <p>
     * Implementation follows the "DB returns str" rule, parsing all primitive
     * types into Domain types.
     *
     * @param claims the claims used to make the factory
     * @return A reconstituted PasswordResetTokenAggregate.
     * @throws java.time.format.DateTimeParseException if date strings are
     *                                                 malformed.
     */
    public PasswordResetTokenAggregate reconstitute(PasswordResetTokenClaims claims) {

        // 1. Convert raw strings to Value Objects
        UUIDValueObject tokenIdVO = new UUIDValueObject(claims.getTokenId());
        UUIDValueObject userIdVO = new UUIDValueObject(claims.getUserId());

        // 2. Parse ISO Strings to Domain Date Types (Fails fast on invalid storage
        // data)
        LocalDateTime issuedAt = LocalDateTime.parse(claims.getIssuedAt());
        LocalDateTime expiresAt = LocalDateTime.parse(claims.getExpiresAt());

        // 3. Rebuild the Aggregate using package-private access
        return new PasswordResetTokenAggregate(
                tokenIdVO,
                issuedAt,
                expiresAt,
                userIdVO);
    }
}