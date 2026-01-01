package com.authservice.domain.model.aggregates.token.accessToken;

import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationFactory;
import com.authservice.domain.model.aggregates.token.accessToken.requirements.AccessTokenCreationRequirement;
import com.authservice.domain.model.valueobjects.UUIDValueObject;

/**
 * Specialized Factory responsible for the creation of brand new AccessTokens.
 * <p>
 * Implements the {@link IDomainCreationFactory} interface to enforce the 1-1-1
 * relationship between Factory, Requirement, and Proof.
 * </p>
 * * @author Mohamed Abucar
 */
public final class AccessTokenCreationFactory
        implements IDomainCreationFactory<AccessTokenCreationProof, AccessTokenCreationRequirement> {

    /**
     * Executes the birth process for an AccessToken.
     * <p>
     * 1. Performs the security handshake with the requirement.<br>
     * 2. Instantiates the {@link AccessTokenAggregate} using internal domain
     * constructors.<br>
     * 3. Seals the result in an {@link AccessTokenCreationProof}.
     * </p>
     *
     * @param requirement The authorized {@link AccessTokenCreationRequirement}.
     * @return A certified {@link AccessTokenCreationProof}.
     */
    @Override
    public AccessTokenCreationProof create(AccessTokenCreationRequirement requirement) {
        // Step 1: Handshake to extract user context
        UUIDValueObject userId = requirement.getUserId(this);

        // Step 2: Birth the Aggregate (Constructor is package-private in
        // AccessTokenAggregate)
        AccessTokenAggregate token = new AccessTokenAggregate(userId);

        // Step 3: Issue the Proof
        return new AccessTokenCreationProof(token, requirement);
    }
}