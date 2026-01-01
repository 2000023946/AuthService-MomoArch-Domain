package com.authservice.domain.model.aggregates.token.refreshToken;

import com.authservice.domain.model.valueobjects.UUIDValueObject;
import java.time.LocalDateTime;

/**
 * Certified evidence that a Refresh Token has been successfully neutralized.
 * <p>
 * This proof is public for verification by security observers and persistence
 * adapters, but carries a package-private constructor to ensure it is only
 * issued by the {@link RefreshTokenAggregate} after a valid state transition.
 * </p>
 *
 * @author Mohamed Abucar
 */
public final class TokenDeactivationProof {

    /** The unique identifier of the token that was revoked. */
    private final UUIDValueObject tokenId;

    /** The timestamp indicating when the revocation was certified by the domain. */
    private final LocalDateTime revokedAt;

    /**
     * Package-private constructor to prevent unauthorized spoofing of
     * token deactivation events.
     *
     * @param tokenId   The {@link UUIDValueObject} of the neutralized token.
     * @param revokedAt The {@link LocalDateTime} of the certified revocation.
     */
    TokenDeactivationProof(UUIDValueObject tokenId, LocalDateTime revokedAt) {
        if (tokenId == null || revokedAt == null) {
            throw new IllegalArgumentException("TokenDeactivationProof requires non-null identity and timestamp.");
        }
        this.tokenId = tokenId;
        this.revokedAt = revokedAt;
    }

    /**
     * @return The {@link UUIDValueObject} of the neutralized token.
     */
    public UUIDValueObject getTokenId() {
        return tokenId;
    }

    /**
     * @return The {@link LocalDateTime} when the revocation was certified.
     */
    public LocalDateTime getRevokedAt() {
        return revokedAt;
    }
}