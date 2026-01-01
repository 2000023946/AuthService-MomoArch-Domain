package com.authservice.domain.model.aggregates.token.refreshToken;

import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationFactory;
import com.authservice.domain.model.aggregates.token.refreshToken.requirements.RefreshTokenCreationRequirement;
import com.authservice.domain.model.valueobjects.UUIDValueObject;

/**
 * Anchor Factory for creating brand new RefreshTokens.
 * * @author Mohamed Abucar
 */
public final class RefreshTokenCreationFactory
        implements IDomainCreationFactory<RefreshTokenCreationProof, RefreshTokenCreationRequirement> {

    @Override
    public RefreshTokenCreationProof create(RefreshTokenCreationRequirement requirement) {
        // Step 1: Handshake
        UUIDValueObject sessionId = requirement.getSessionId(this);

        // Step 2: Birth Aggregate
        RefreshTokenAggregate token = new RefreshTokenAggregate(sessionId);

        // Step 3: Issue Proof
        return new RefreshTokenCreationProof(token, requirement);
    }
}