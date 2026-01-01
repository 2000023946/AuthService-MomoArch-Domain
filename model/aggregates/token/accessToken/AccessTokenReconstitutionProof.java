package com.authservice.domain.model.aggregates.token.accessToken;

import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionProof;
import com.authservice.domain.model.aggregates.token.accessToken.requirements.AccessTokenReconstitutionRequirement;

/**
 * A certified proof that an {@link AccessTokenAggregate} has been successfully
 * reconstituted.
 * <p>
 * This class guarantees that the resulting aggregate was rehydrated through
 * a legitimate {@link AccessTokenReconstitutionRequirement}.
 * </p>
 * * @author Mohamed Abucar
 */
public final class AccessTokenReconstitutionProof
        implements IDomainReconstitutionProof<AccessTokenReconstitutionRequirement> {

    /** The successfully rehydrated token aggregate. */
    private final AccessTokenAggregate aggregate;

    /** The requirement instance that provided the source data. */
    private final AccessTokenReconstitutionRequirement source;

    /**
     * Constructs a new reconstitution proof.
     *
     * @param aggregate The reanimated {@link AccessTokenAggregate}.
     * @param source    The {@link AccessTokenReconstitutionRequirement} used in the
     *                  handshake.
     */
    public AccessTokenReconstitutionProof(AccessTokenAggregate aggregate, AccessTokenReconstitutionRequirement source) {
        this.aggregate = aggregate;
        this.source = source;
    }

    /** @return The reanimated {@link AccessTokenAggregate}. */
    public AccessTokenAggregate getAggregate() {
        return aggregate;
    }

    /** @return The original {@link AccessTokenReconstitutionRequirement}. */
    @Override
    public AccessTokenReconstitutionRequirement getSourceRequirement() {
        return source;
    }
}