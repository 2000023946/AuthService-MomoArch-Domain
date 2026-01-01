package com.authservice.domain.model.aggregates.token.accessToken;

import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionFactory;
import com.authservice.domain.model.aggregates.token.accessToken.requirements.AccessTokenReconstitutionRequirement;
import com.authservice.domain.model.schemas.token.access.AccessTokenSchema;
import com.authservice.domain.model.valueobjects.UUIDValueObject;

/**
 * Specialized Factory responsible for the rehydration of existing AccessTokens.
 * <p>
 * Implements the {@link IDomainReconstitutionFactory} interface to enforce
 * the 1-1-1 relationship between Factory, Requirement, and Proof.
 * </p>
 * * @author Mohamed Abucar
 */
public final class AccessTokenReconstitutionFactory
        implements IDomainReconstitutionFactory<AccessTokenReconstitutionProof, AccessTokenReconstitutionRequirement> {

    /**
     * Reconstitutes an AccessToken from a persisted schema.
     * <p>
     * 1. Performs the security handshake with the requirement.<br>
     * 2. Maps raw schema primitives into {@link UUIDValueObject}s.<br>
     * 3. Rehydrates the {@link AccessTokenAggregate} using the specialized
     * constructor.<br>
     * 4. Issues the final {@link AccessTokenReconstitutionProof}.
     * </p>
     *
     * @param requirement The authorized
     *                    {@link AccessTokenReconstitutionRequirement}.
     * @return A certified {@link AccessTokenReconstitutionProof}.
     */
    @Override
    public AccessTokenReconstitutionProof reconstitute(AccessTokenReconstitutionRequirement requirement) {
        // Step 1: Handshake
        AccessTokenSchema schema = requirement.getAuthorizedSchema(this);

        // Step 2: Mapping primitives to Domain Value Objects
        UUIDValueObject tokenId = new UUIDValueObject(schema.getTokenId());
        UUIDValueObject userId = new UUIDValueObject(schema.getUserId());

        // Step 3: Reconstitute the Aggregate (Package-private constructor)
        AccessTokenAggregate token = new AccessTokenAggregate(
                tokenId,
                userId,
                schema.getIssuedAt(),
                schema.getExpiresAt());

        // Step 4: Finalize Certification
        return new AccessTokenReconstitutionProof(token, requirement);
    }
}