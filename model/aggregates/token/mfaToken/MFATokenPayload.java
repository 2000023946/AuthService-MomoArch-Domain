package com.authservice.domain.model.aggregates.token.mfaToken;

import com.authservice.domain.model.aggregates.IPayload;

/**
 * Payload for creating an MFA Token.
 * <p>
 * Contains only the attributes necessary to create a new MFA Token
 * Metadata such as tokenId or timestamps are not included.
 */
public final class MFATokenPayload implements IPayload {

    private final String userId;

    private final String code;

    /**
     * Constructs an AccessPayload with the given userId and scope.
     *
     * @param userId the ID of the user
     * @param code   the code for the MFA token
     */
    public MFATokenPayload(String userId, String code) {
        this.userId = userId;
        this.code = code;
    }

    /**
     * @return the ID of the user
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @return the code of the token
     */
    public String getCode() {
        return code;
    }

}
