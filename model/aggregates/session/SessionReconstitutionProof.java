package com.authservice.domain.model.aggregates.session;

import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionProof;
import com.authservice.domain.model.aggregates.session.requirements.SessionReconstitutionRequirement;

/**
 * A certified proof that a {@link SessionAggregate} has been successfully
 * reconstituted
 * from persisted state.
 *
 * <p>
 * This class represents the <strong>Output Certificate</strong> in the
 * Momo-Architecture reconstitution flow:
 * </p>
 * <ul>
 * <li>The {@link #getAggregate()} method exposes the live aggregate</li>
 * <li>The {@link #getSourceRequirement()} method links back to the original
 * {@link SessionReconstitutionRequirement} that authorized the
 * reconstitution</li>
 * </ul>
 *
 * <p>
 * This class is immutable and thread-safe once constructed.
 * </p>
 *
 * @see SessionAggregate
 * @see SessionReconstitutionRequirement
 * @see IDomainReconstitutionProof
 */
public final class SessionReconstitutionProof
        implements IDomainReconstitutionProof<SessionReconstitutionRequirement> {

    /**
     * The live {@link SessionAggregate} reconstituted from persisted state.
     */
    private final SessionAggregate aggregate;

    /**
     * The original requirement that authorized the reconstitution.
     */
    private final SessionReconstitutionRequirement source;

    /**
     * Constructs a new proof binding the reconstituted aggregate to its
     * originating requirement.
     *
     * @param aggregate the live session aggregate
     * @param source    the requirement that authorized this reconstitution
     *
     * @throws IllegalArgumentException if either {@code aggregate} or
     *                                  {@code source} is {@code null}
     */
    public SessionReconstitutionProof(SessionAggregate aggregate, SessionReconstitutionRequirement source) {
        if (aggregate == null) {
            throw new IllegalArgumentException("Aggregate cannot be null.");
        }
        if (source == null) {
            throw new IllegalArgumentException("Source requirement cannot be null.");
        }

        this.aggregate = aggregate;
        this.source = source;
    }

    /**
     * Returns the reconstituted {@link SessionAggregate}.
     *
     * @return the live session aggregate
     */
    public SessionAggregate getAggregate() {
        return aggregate;
    }

    /**
     * Returns the originating {@link SessionReconstitutionRequirement} that
     * authorized this reconstitution.
     *
     * @return the source requirement
     */
    @Override
    public SessionReconstitutionRequirement getSourceRequirement() {
        return source;
    }
}
