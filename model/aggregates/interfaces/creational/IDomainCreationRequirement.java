package com.authservice.domain.model.aggregates.interfaces.creational;

/**
 * Base interface representing a domain-level creation requirement.
 * <p>
 * Acts as the <strong>gate</strong> in the handshake protocol. Each requirement
 * defines the preconditions and necessary data to create a domain entity.
 * </p>
 * <p>
 * Generically parameterized by a factory type {@code F} that is allowed to
 * consume
 * this requirement. This ensures that only authorized factories can process the
 * requirement,
 * enforcing a strict <strong>handshake protocol</strong>.
 * </p>
 *
 * <h2>Type Parameters</h2>
 * <ul>
 * <li>{@code F} – the type of factory authorized to process this
 * requirement</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 * 
 * <pre>{@code
 * public class UserCreationRequirement implements IDomainCreationRequirement<UserCreationFactory> {
 *     private final String email;
 *     private final String password;
 *
 *     public UserCreationRequirement(String email, String password) {
 *         this.email = email;
 *         this.password = password;
 *     }
 *
 *     public String getEmail() {
 *         return email;
 *     }
 * 
 *     public String getPassword() {
 *         return password;
 *     }
 * }
 * }</pre>
 *
 * @param <F> the factory type authorized to consume this requirement
 */
public interface IDomainCreationRequirement<F extends IDomainCreationFactory<?, ?>> {
    // Marker interface for handshake: requires a Factory of type F
}
