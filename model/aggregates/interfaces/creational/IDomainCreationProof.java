package com.authservice.domain.model.aggregates.interfaces.creational;

/**
 * Base interface representing a proof or certificate of successful domain
 * creation.
 * <p>
 * Acts as the <strong>certificate</strong> in the handshake protocol. Each
 * proof
 * encapsulates the result of a domain creation operation and maintains a
 * reference
 * to the requirement that authorized it.
 * </p>
 *
 * <h2>Type Parameters</h2>
 * <ul>
 * <li>{@code R} – the type of requirement that produced this proof</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 * 
 * <pre>{@code
 * public class UserCreationProof implements IDomainCreationProof<UserCreationRequirement> {
 *     private final UserAggregate user;
 *     private final UserCreationRequirement source;
 *
 *     public UserCreationProof(UserAggregate user, UserCreationRequirement source) {
 *         this.user = user;
 *         this.source = source;
 *     }
 *
 *     @Override
 *     public UserCreationRequirement getSourceRequirement() {
 *         return source;
 *     }
 *
 *     public UserAggregate getUser() {
 *         return user;
 *     }
 * }
 * }</pre>
 *
 * @param <R> the type of requirement that generated this proof
 */
public interface IDomainCreationProof<R extends IDomainCreationRequirement<?>> {

    /**
     * Returns the requirement that authorized this creation proof.
     *
     * @return the source requirement
     */
    R getSourceRequirement();
}
