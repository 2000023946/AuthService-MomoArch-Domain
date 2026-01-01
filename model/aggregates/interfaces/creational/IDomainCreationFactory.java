package com.authservice.domain.model.aggregates.interfaces.creational;

/**
 * Base interface representing a factory responsible for orchestrating domain
 * creation.
 * <p>
 * Acts as the <strong>orchestrator</strong> in the handshake protocol. Each
 * factory
 * consumes an authorized requirement and produces a corresponding creation
 * proof.
 * </p>
 * <p>
 * This interface enforces the <strong>double-dispatch handshake</strong>
 * pattern:
 * the factory can only create an entity if the requirement explicitly
 * authorizes it.
 * </p>
 *
 * <h2>Type Parameters</h2>
 * <ul>
 * <li>{@code P} – the type of proof that will be issued by this factory</li>
 * <li>{@code R} – the type of requirement that this factory can consume</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 * 
 * <pre>{@code
 * public class UserCreationFactory implements IDomainCreationFactory<UserCreationProof, UserCreationRequirement> {
 *
 *     @Override
 *     public UserCreationProof create(UserCreationRequirement requirement) {
 *         // Validate handshake: requirement only opens for this factory
 *         String email = requirement.getEmail();
 *         String password = requirement.getPassword();
 *
 *         UserAggregate user = new UserAggregate(email, password);
 *         return new UserCreationProof(user, requirement);
 *     }
 * }
 * }</pre>
 *
 * @param <P> the type of proof produced by this factory
 * @param <R> the type of requirement consumed by this factory
 */
public interface IDomainCreationFactory<P extends IDomainCreationProof<R>, R extends IDomainCreationRequirement<? extends IDomainCreationFactory<P, R>>> {

    /**
     * Orchestrates the creation of a domain entity based on the provided
     * requirement.
     *
     * @param requirement the requirement authorizing this creation
     * @return a proof confirming successful creation
     * @throws SecurityException if the requirement is not authorized for this
     *                           factory
     */
    P create(R requirement);
}
