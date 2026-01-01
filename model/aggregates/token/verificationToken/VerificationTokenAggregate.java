package com.authservice.domain.model.aggregates.token.verificationToken;

import com.authservice.domain.model.aggregates.OpaqueToken;
import com.authservice.domain.model.valueobjects.UUIDValueObject;
import java.time.LocalDateTime;

/**
 * Specialized Token Aggregate used for email verification.
 * Linked to a specific UserId to ensure the verification process is bound to
 * the correct user.
 * *
 * <p>
 * Matches the 'VerificationToken' table in the 3NF schema, providing the
 * link between a base Token metadata and a specific User identity.
 * </p>
 */
public class VerificationTokenAggregate extends OpaqueToken {
    private static final int EXPIRES = 1; // represents 1 day

    /** The unique identifier of the user this verification token is assigned to. */
    private final UUIDValueObject userId;

    /**
     * Constructor for creating a BRAND NEW VerificationToken.
     * Use this when a user registers or requests a new verification email.
     *
     * @param userId The ID of the user being verified
     */
    VerificationTokenAggregate(UUIDValueObject userId) {
        super();
        this.userId = userId;
    }

    /**
     * Constructor for RECONSTITUTING a VerificationToken from the database.
     * Used by Repository Adapters to load the historical state of a token.
     *
     * @param tokenId   Existing identifier from the SQL Token table
     * @param issuedAt  The original timestamp of token issuance
     * @param expiresAt The original expiration timestamp
     * @param userId    The ID of the user associated with this token
     */
    VerificationTokenAggregate(UUIDValueObject tokenId, LocalDateTime issuedAt,
            LocalDateTime expiresAt, UUIDValueObject userId) {
        super(tokenId, issuedAt, expiresAt);
        this.userId = userId;
    }

    /**
     * Logic to check if this token is valid for a specific user ID.
     * Performs a compound check on activity and ownership.
     * 
     * @param userIdToMatch the user ID to verify against
     * 
     * @return true if the token is active and belongs to the specified user
     */
    public boolean isValidFor(UUIDValueObject userIdToMatch) {
        return this.isActive() && this.userId.equals(userIdToMatch);
    }

    /**
     * Gets the associated UserId for this token.
     * 
     * @return the UUIDValueObject representing the user owner
     */
    public UUIDValueObject getUserId() {
        return userId;
    }

    @Override
    protected int getExpiry() {
        return 60 * 60 * 24 * EXPIRES;
    }
}