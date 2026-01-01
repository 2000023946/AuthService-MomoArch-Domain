package com.authservice.domain.model.aggregates.interfaces.reconstitution;

/**
 * Specialized factory interface that orchestrates the reanimation of domain
 * aggregates
 * from a {@link IDomainReconstitutionRequirement}.
 * <p>
 * Acts as the <strong>orchestrator</strong> in the Reconstitution Handshake
 * Protocol.
 * Each factory is generically bound to:
 * <ul>
 * <li>{@code P} – the type of proof it produces</li>
 * <li>{@code R} – the type of requirement it consumes</li>
 * </ul>
 * </p>
 * <p>
 * The factory enforces a strict one-to-one correspondence between requirement
 * and proof,
 * ensuring that reconstitution flows remain fully traceable and type-safe.
 * </p>
 *
 * <h2>Type Parameters</h2>
 * <ul>
 * <li>{@code P} – the type of proof produced by this factory</li>
 * <li>{@code R} – the type of requirement consumed by this factory</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 * 
 * <pre>{@code
 * public class UserReconstitutionFactory
 *         implements IDomainReconstitutionFactory<UserReconstitutionProof, UserReconstitutionRequirement> {
 *
 *     @Override
 *     public UserReconstitutionProof reconstitute(UserReconstitutionRequirement requirement) {
 *         // Perform handshake: ensure requirement authorizes this factory
 *         UserSchema schema = requirement.getAuthorizedSchema(this);
 *
 *         UserAggregate user = new UserAggregate(
 *                 schema.getId(),
 *                 schema.getEmail(),
 *                 PasswordHashValueObject.reconstitute(schema.getPasswordHash()));
 *
 *         return new UserReconstitutionProof(user, requirement);
 *     }
 * }
 * }</pre>
 *
 * @param <P> the type of proof produced by this factory
 * @param <R> the type of requirement consumed by this factory
 */
public interface IDomainReconstitutionFactory<P extends IDomainReconstitutionProof<R>, R extends IDomainReconstitutionRequirement<? extends IDomainReconstitutionFactory<P, R>>> {

    /**
     * Reanimates a domain aggregate based on the provided requirement.
     * <p>
     * This method implements the <strong>double-dispatch handshake</strong>:
     * <ol>
     * <li>The requirement validates that this factory is authorized to consume
     * it</li>
     * <li>The factory produces a proof encapsulating the reconstituted
     * aggregate</li>
     * </ol>
     * </p>
     *
     * @param requirement the requirement authorizing this reconstitution
     * @return a proof confirming successful reanimation of the aggregate
     * @throws SecurityException  if the requirement is not authorized for this
     *                            factory
     * @throws ClassCastException if the requirement type does not match this
     *                            factory's expected type
     */
    P reconstitute(R requirement);
}
