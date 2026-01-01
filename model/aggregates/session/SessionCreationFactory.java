package com.authservice.domain.model.aggregates.session;

import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationFactory;
import com.authservice.domain.model.aggregates.session.requirements.SessionCreationRequirement;
import com.authservice.domain.model.valueobjects.UUIDValueObject;
import java.util.UUID;

/**
 * Domain factory responsible for the controlled creation ("birth")
 * of {@link SessionAggregate} instances.
 *
 * <p>
 * This factory represents the <strong>only authorized mechanism</strong>
 * for creating new active sessions within the domain. It enforces
 * capability-based access through a strict handshake with
 * {@link SessionCreationRequirement} and produces a
 * {@link SessionCreationProof} as a cryptographic-style certificate
 * of successful creation.
 * </p>
 *
 * <h2>Architectural Responsibilities</h2>
 * <ul>
 * <li>Validates authority through requirement-based handshakes</li>
 * <li>Generates domain identities for session and refresh token</li>
 * <li>Ensures aggregates are born in a valid and consistent state</li>
 * <li>Seals the result in an immutable domain proof</li>
 * </ul>
 *
 * <p>
 * This factory is intentionally stateless. All security guarantees
 * are enforced through the requirement handshake rather than internal
 * mutable state.
 * </p>
 *
 * <h2>Momo-Architecture Compliance</h2>
 * <p>
 * This class participates in the <strong>Creation Triple Handshake</strong>:
 * </p>
 * <ol>
 * <li>The {@link SessionCreationRequirement} guards creation data</li>
 * <li>This factory proves authorization by identity</li>
 * <li>A {@link SessionCreationProof} certifies successful creation</li>
 * </ol>
 */
public final class SessionCreationFactory
        implements IDomainCreationFactory<SessionCreationProof, SessionCreationRequirement> {

    /**
     * Creates a new {@link SessionAggregate} from an authorized requirement.
     *
     * <p>
     * This method performs the full domain creation lifecycle:
     * </p>
     * <ol>
     * <li>Executes a capability handshake with the requirement</li>
     * <li>Generates unique domain identities</li>
     * <li>Instantiates the aggregate in a valid initial state</li>
     * <li>Seals the result in a creation proof</li>
     * </ol>
     *
     * <p>
     * If the calling context is not authorized, the requirement will
     * actively reject access and abort creation.
     * </p>
     *
     * @param requirement the capability-guarded creation requirement
     * @return a domain-certified proof of successful session creation
     *
     * @throws SecurityException if the requirement rejects the handshake
     */
    @Override
    public SessionCreationProof create(SessionCreationRequirement requirement) {

        /*
         * Step 1: Capability Handshake
         * The requirement releases its guarded data only if this
         * factory instance is the authorized caller.
         */
        UUIDValueObject userId = requirement.getUserId(this);

        /*
         * Step 2: Domain Identity Generation
         * New sessions receive unique identifiers for both the
         * session itself and its refresh token lineage.
         */
        UUIDValueObject sessionId = new UUIDValueObject(UUID.randomUUID().toString());
        UUIDValueObject refreshTokenId = new UUIDValueObject(UUID.randomUUID().toString());

        /*
         * Step 3: Aggregate Birth
         * The aggregate constructor is package-private, enforcing
         * factory-only creation.
         */
        SessionAggregate session = new SessionAggregate(
                sessionId,
                userId,
                refreshTokenId);

        /*
         * Step 4: Proof Sealing
         * The proof cryptographically binds the aggregate to
         * its originating requirement.
         */
        return new SessionCreationProof(session, requirement);
    }
}
