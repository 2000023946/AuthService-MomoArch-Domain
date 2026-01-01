package com.authservice.domain.model.aggregates.token.verificationToken;

import com.authservice.domain.model.aggregates.IPayload;

/**
 * Payload for creating an VerificationToken
 * <p>
 * Contains only the attributes necessary to create a new
 * VerificationToken
 * Metadata such as tokenId or timestamps are not included.
 */
public final class VerificationTokenPayload implements IPayload {

    private final String userId;

    /**
     * Constructs an AccessPayload with the given userId and scope.
     *
     * @param userId the ID of the user
     */
    public VerificationTokenPayload(String userId) {
        this.userId = userId;
    }

    /**
     * @return the ID of the user
     */
    public String getUserId() {
        return userId;
    }

}