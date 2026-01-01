package com.authservice.domain.model.aggregates.token.passswordResetToken;

import com.authservice.domain.model.aggregates.OpaqueToken;
import com.authservice.domain.model.valueobjects.UUIDValueObject;
import java.time.LocalDateTime;

/**
 * Specialized Token Aggregate used for account recovery and password resets.
 * Linked to a specific UserId to ensure the reset flow is bound to the correct
 * identity.
 * *
 * <p>
 * Matches the 'PasswordResetToken' table in the 3NF schema, establishing the
 * relationship between a generic Token and the User requesting recovery.
 * </p>
 */
public class PasswordResetTokenAggregate extends OpaqueToken {
    private static final int EXPIRES = 15; // the Access Tokens expires 15 minutes after made

    /** The unique identifier of the user who requested the password reset. */
    private final UUIDValueObject userId;

    /**
     * Constructor for creating a BRAND NEW PasswordResetToken.
     * Use this in the "Forgot Password" Use Case.
     * 
     * @param userId The ID of the user whose password is being reset
     */
    PasswordResetTokenAggregate(UUIDValueObject userId) {
        super();
        this.userId = userId;
    }

    /**
     * Constructor for RECONSTITUTING a PasswordResetToken from the database.
     * Used by Repository Adapters to load existing recovery state.
     *
     * @param tokenId   Existing identifier from the SQL Token table
     * @param issuedAt  The original timestamp of the request
     * @param expiresAt The original expiration timestamp
     * @param userId    The ID of the user associated with this token
     */
    PasswordResetTokenAggregate(UUIDValueObject tokenId, LocalDateTime issuedAt,
            LocalDateTime expiresAt, UUIDValueObject userId) {
        super(tokenId, issuedAt, expiresAt);
        this.userId = userId;
    }

    /**
     * Verifies if this token is valid for a specific user ID.
     * 
     * @param userIdToMatch the user ID provided in the reset request
     * 
     * @return true if the token is active and belongs to the specified user
     */
    public boolean isValidFor(UUIDValueObject userIdToMatch) {
        return this.isActive() && this.userId.equals(userIdToMatch);
    }

    /**
     * Gets the associated UserId for this token.
     * 
     * @return the UUIDValueObject of the user
     */
    public UUIDValueObject getUserId() {
        return userId;
    }

    @Override
    protected int getExpiry() {
        return EXPIRES;
    }
}