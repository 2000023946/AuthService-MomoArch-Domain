package com.authservice.domain.model.aggregates.token.refreshToken;

import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionProof;
import com.authservice.domain.model.aggregates.token.refreshToken.requirements.RefreshTokenReconstitutionRequirement;

/**
 * Certified proof of a successfully reconstituted
 * {@link RefreshTokenAggregate}.
 * * @author Mohamed Abucar
 */
public final class RefreshTokenReconstitutionProof
        implements IDomainReconstitutionProof<RefreshTokenReconstitutionRequirement> {

    private final RefreshTokenAggregate aggregate;
    private final RefreshTokenReconstitutionRequirement source;

    public RefreshTokenReconstitutionProof(RefreshTokenAggregate aggregate,
            RefreshTokenReconstitutionRequirement source) {
        this.aggregate = aggregate;
        this.source = source;
    }

    /** @return The reanimated {@link RefreshTokenAggregate}. */
    public RefreshTokenAggregate getAggregate() {
        return aggregate;
    }

    @Override
    public RefreshTokenReconstitutionRequirement getSourceRequirement() {
        return source;
    }
}