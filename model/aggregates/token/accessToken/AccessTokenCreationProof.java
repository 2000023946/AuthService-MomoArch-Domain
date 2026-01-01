package com.authservice.domain.model.aggregates.token.accessToken;

import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationProof;
import com.authservice.domain.model.aggregates.token.accessToken.requirements.AccessTokenCreationRequirement;

/**
 * A certified proof that an {@link AccessTokenAggregate} has been successfully
 * created.
 * <p>
 * This class guarantees that the resulting aggregate was birthed through
 * a legitimate {@link AccessTokenCreationRequirement}.
 * </p>
 * * @author Mohamed Abucar
 */
public final class AccessTokenCreationProof implements IDomainCreationProof<AccessTokenCreationRequirement> {

    /** The successfully created token aggregate. */
    private final AccessTokenAggregate aggregate;

    /** The requirement instance that authorized the creation. */
    private final AccessTokenCreationRequirement source;

    /**
     * Constructs a new creation proof.
     *
     * @param aggregate The birthed {@link AccessTokenAggregate}.
     * @param source    The {@link AccessTokenCreationRequirement} used in the
     *                  handshake.
     */
    public AccessTokenCreationProof(AccessTokenAggregate aggregate, AccessTokenCreationRequirement source) {
        this.aggregate = aggregate;
        this.source = source;
    }

    /** @return The certified {@link AccessTokenAggregate}. */
    public AccessTokenAggregate getAggregate() {
        return aggregate;
    }

    /** @return The original {@link AccessTokenCreationRequirement}. */
    @Override
    public AccessTokenCreationRequirement getSourceRequirement() {
        return source;
    }
}