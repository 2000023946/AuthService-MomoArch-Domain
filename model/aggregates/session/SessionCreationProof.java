package com.authservice.domain.model.aggregates.session;

import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationProof;
import com.authservice.domain.model.aggregates.session.requirements.SessionCreationRequirement;

/**
 * Domain-certified proof representing the successful creation of a
 * {@link SessionAggregate}.
 *
 * <p>
 * This proof serves as a <strong>cryptographic-style certificate</strong>
 * within the domain model, attesting that a {@link SessionAggregate} has been
 * created exclusively through an authorized
 * {@link com.authservice.domain.model.aggregates.session.SessionCreationFactory}
 * and in strict accordance with the supplied
 * {@link SessionCreationRequirement}.
 * </p>
 *
 * <h2>Architectural Role</h2>
 * <ul>
 * <li>Acts as the immutable result of the domain creation handshake.</li>
 * <li>Binds the created aggregate to its originating requirement.</li>
 * <li>Provides a verifiable audit trail for creation flows.</li>
 * </ul>
 *
 * <p>
 * This object is intentionally minimal and immutable. It contains no behavior
 * beyond identity and traceability guarantees.
 * </p>
 *
 * <h2>Immutability & Thread Safety</h2>
 * <p>
 * This class is immutable and thread-safe provided that the referenced
 * {@link SessionAggregate} is treated as an aggregate root and mutated only
 * through valid domain operations.
 * </p>
 */
public final class SessionCreationProof
        implements IDomainCreationProof<SessionCreationRequirement> {

    /**
     * The session aggregate produced by the authorized creation process.
     */
    private final SessionAggregate aggregate;

    /**
     * The capability-guarded requirement that authorized and initiated
     * the creation of the aggregate.
     */
    private final SessionCreationRequirement source;

    /**
     * Constructs a new {@code SessionCreationProof}.
     *
     * <p>
     * This constructor should be invoked exclusively by the authorized
     * {@code SessionCreationFactory} after successful completion of the
     * creation handshake.
     * </p>
     *
     * @param aggregate the newly created session aggregate
     * @param source    the requirement that authorized and triggered creation
     */
    public SessionCreationProof(
            SessionAggregate aggregate,
            SessionCreationRequirement source) {
        this.aggregate = aggregate;
        this.source = source;
    }

    /**
     * Returns the {@link SessionAggregate} produced by the creation process.
     *
     * <p>
     * This aggregate represents a fully-initialized domain entity whose
     * invariants have been validated at creation time.
     * </p>
     *
     * @return the created session aggregate
     */
    public SessionAggregate getAggregate() {
        return aggregate;
    }

    /**
     * Returns the source requirement that authorized and initiated
     * this creation.
     *
     * <p>
     * The returned requirement provides traceability and serves as a
     * verifiable link back to the capability-guarded creation request.
     * </p>
     *
     * @return the originating session creation requirement
     */
    @Override
    public SessionCreationRequirement getSourceRequirement() {
        return source;
    }
}
