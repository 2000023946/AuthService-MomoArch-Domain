package com.authservice.domain.model.aggregates.token.refreshToken;

import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionFactory;
import com.authservice.domain.model.aggregates.token.refreshToken.requirements.RefreshTokenReconstitutionRequirement;
import com.authservice.domain.model.schemas.token.refresh.RefreshTokenSchema;
import com.authservice.domain.model.valueobjects.UUIDValueObject;

/**
 * Anchor Factory for rehydrating RefreshTokens from persistence.
 * * @author Mohamed Abucar
 */
public final class RefreshTokenReconstitutionFactory
        implements
        IDomainReconstitutionFactory<RefreshTokenReconstitutionProof, RefreshTokenReconstitutionRequirement> {

    @Override
    public RefreshTokenReconstitutionProof reconstitute(RefreshTokenReconstitutionRequirement requirement) {
        // Step 1: Handshake
        RefreshTokenSchema schema = requirement.getAuthorizedSchema(this);

        // Step 2: Mapping
        UUIDValueObject tokenId = new UUIDValueObject(schema.getTokenId());
        UUIDValueObject sessionId = new UUIDValueObject(schema.getSessionId());

        // Step 3: Rehydrate (Package-private constructor)
        RefreshTokenAggregate token = new RefreshTokenAggregate(
                tokenId,
                schema.getIssuedAt(),
                schema.getExpiresAt(),
                sessionId);

        // Step 4: Issue Proof
        return new RefreshTokenReconstitutionProof(token, requirement);
    }
}