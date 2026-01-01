package com.authservice.domain.model.aggregates.token.refreshToken;

import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationProof;
import com.authservice.domain.model.aggregates.token.refreshToken.requirements.RefreshTokenCreationRequirement;

/**
 * A certified proof of a successfully birthed {@link RefreshTokenAggregate}.
 * * @author Mohamed Abucar
 */
public final class RefreshTokenCreationProof implements IDomainCreationProof<RefreshTokenCreationRequirement> {

    private final RefreshTokenAggregate aggregate;
    private final RefreshTokenCreationRequirement source;

    public RefreshTokenCreationProof(RefreshTokenAggregate aggregate, RefreshTokenCreationRequirement source) {
        this.aggregate = aggregate;
        this.source = source;
    }

    /** @return The certified {@link RefreshTokenAggregate}. */
    public RefreshTokenAggregate getAggregate() {
        return aggregate;
    }

    @Override
    public RefreshTokenCreationRequirement getSourceRequirement() {
        return source;
    }
}