package com.authservice.domain.model.aggregates.user;

/**
 * Represents a sealed proof containing a fully initialized
 * {@link UserAggregate}.
 * <p>
 * This abstract class acts as the final boundary marker in the reconstitution
 * process,
 * ensuring that only authorized consumers can access the underlying aggregate.
 * </p>
 * <p>
 * Instances of subclasses are <strong>immutable</strong> once constructed.
 * The proof encapsulates the aggregate, enforcing that it cannot be null.
 * </p>
 *
 * <h2>Usage Example</h2>
 * 
 * <pre>{@code
 * UserAggregate user = ...;
 * AbstractUserProof proof = new ConcreteUserProof(user);
 *
 * // Access the underlying aggregate
 * UserAggregate authorizedAggregate = proof.getAggregate();
 * }</pre>
 */
public abstract class AbstractUserProof {

    /** The fully initialized {@link UserAggregate} wrapped by this proof. */
    private final UserAggregate aggregate;

    /**
     * Protected constructor for creating a proof with the given aggregate.
     *
     * @param aggregate the {@link UserAggregate} to wrap
     * @throws IllegalArgumentException if the aggregate is null
     */
    protected AbstractUserProof(UserAggregate aggregate) {
        if (aggregate == null) {
            throw new IllegalArgumentException("Proof cannot wrap a null aggregate.");
        }
        this.aggregate = aggregate;
    }

    /**
     * Returns the wrapped {@link UserAggregate} to authorized consumers.
     * <p>
     * This method exposes the aggregate in a controlled manner, serving as the
     * final access point in the reconstitution lifecycle.
     * </p>
     *
     * @return the fully initialized {@link UserAggregate}
     */
    public final UserAggregate getAggregate() {
        return this.aggregate;
    }
}
