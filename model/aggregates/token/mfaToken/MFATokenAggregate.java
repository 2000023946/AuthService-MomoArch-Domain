package com.authservice.domain.model.aggregates.token.mfaToken;

import com.authservice.domain.model.aggregates.OpaqueToken;
import com.authservice.domain.model.valueobjects.MFACodeValueObject;
import com.authservice.domain.model.valueobjects.UUIDValueObject;
import java.time.LocalDateTime;

/**
 * Specialized Token Aggregate used for Multi-Factor Authentication.
 * Encapsulates a structured MFA code (e.g., TOTP or SMS code) pinned to a
 * specific User.
 * *
 * <p>
 * Matches the 'MFAToken' table in the 3NF schema, linking the base Token
 * metadata to a specific User and a validated MFA value.
 * </p>
 */
public class MFATokenAggregate extends OpaqueToken {

    private static final int EXPIRES = 10; // indicates 10 minutes

    /** The unique identifier of the user who must verify this MFA code. */
    private final UUIDValueObject userId;

    private final MFACodeValueObject code;

    /**
     * Constructor for creating a BRAND NEW MFAToken.
     * Use this when a user successfully provides credentials and triggers the MFA
     * flow.
     *
     * @param code   The MFAValueObject containing the code and its validation
     *               logic
     * @param userId The ID of the user undergoing MFA
     */
    MFATokenAggregate(MFACodeValueObject code, UUIDValueObject userId) {
        super();
        this.userId = userId;
        this.code = code;
    }

    /**
     * Constructor for RECONSTITUTING an MFAToken from the database.
     * Used by Repository Adapters to load the state of a pending MFA challenge.
     *
     * @param tokenId   Existing identifier from the SQL Token table
     * @param code      The MFAValueObject reconstituted from the database string
     * @param issuedAt  The original timestamp of the MFA request
     * @param expiresAt The original expiration timestamp
     * @param userId    The ID of the user associated with this MFA attempt
     */
    MFATokenAggregate(UUIDValueObject tokenId, MFACodeValueObject code, LocalDateTime issuedAt,
            LocalDateTime expiresAt, UUIDValueObject userId) {
        super(tokenId, issuedAt, expiresAt);
        this.userId = userId;
        this.code = code;
    }

    /**
     * Verifies if this MFA token is valid for a specific user.
     * 
     * @param userIdToMatch the user ID provided during the MFA verification step
     * 
     * @return true if the token is active and belongs to the specified user
     */
    public boolean isValidFor(UUIDValueObject userIdToMatch) {
        return this.isActive() && this.userId.equals(userIdToMatch);
    }

    /**
     * Gets the associated UserId for this MFA token.
     * 
     * @return the UUIDValueObject of the user
     */
    public UUIDValueObject getUserId() {
        return userId;
    }

    /**
     * Gets the associated MFA Code value
     * 
     * @return the MFACode valueObject of the user
     */
    public MFACodeValueObject getCode() {
        return code;
    }

    @Override
    protected int getExpiry() {
        return EXPIRES;
    }
}