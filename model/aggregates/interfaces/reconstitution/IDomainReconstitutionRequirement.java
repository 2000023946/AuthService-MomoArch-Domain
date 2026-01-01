package com.authservice.domain.model.aggregates.interfaces.reconstitution;

/**
 * Represents the entry gate for existing domain state (the "dead state").
 * <p>
 * Each reconstitution requirement guards a persisted {@code Schema} and
 * requires
 * a specific {@link IDomainReconstitutionFactory} to unlock and consume it.
 * </p>
 * <p>
 * This interface enforces the <strong>Reanimation Handshake Protocol</strong>:
 * only the factory authorized to consume this requirement can access its
 * internal state.
 * </p>
 *
 * <h2>Type Parameters</h2>
 * <ul>
 * <li>{@code F} – the factory type authorized to consume this requirement</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 * 
 * <pre>{@code
 * public class UserReconstitutionRequirement implements IDomainReconstitutionRequirement<UserReconstitutionFactory> {
 *     private final UserSchema schema;
 *
 *     public UserReconstitutionRequirement(UserSchema schema) {
 *         this.schema = schema;
 *     }
 *
 *     public UserSchema getAuthorizedSchema(UserReconstitutionFactory factory) {
 *         if (factory != authorizedFactory) {
 *             throw new SecurityException("Unauthorized factory access.");
 *         }
 *         return schema;
 *     }
 * }
 * }</pre>
 *
 * @param <F> the factory type authorized to consume this requirement
 */
public interface IDomainReconstitutionRequirement<F extends IDomainReconstitutionFactory<?, ?>> {
    // The handshake method will be implemented in concrete classes
    // e.g., UserSchema getAuthorizedSchema(F factory);
}
