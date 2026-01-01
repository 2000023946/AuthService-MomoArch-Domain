package com.authservice.domain.model.aggregates.interfaces.reconstitution;

/**
 * Represents a certificate that an Aggregate has been successfully reanimated
 * from a {@link IDomainReconstitutionRequirement}.
 * <p>
 * Acts as the <strong>proof of reconstitution</strong> in the handshake
 * protocol.
 * Each proof maintains a reference to the original requirement that authorized
 * its creation,
 * preserving traceability and integrity.
 * </p>
 *
 * <h2>Type Parameters</h2>
 * <ul>
 * <li>{@code R} – the type of requirement that generated this proof</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 * 
 * <pre>{@code
 * public class UserReconstitutionProof implements IDomainReconstitutionProof<UserReconstitutionRequirement> {
 *     private final UserAggregate user;
 *     private final UserReconstitutionRequirement source;
 *
 *     public UserReconstitutionProof(UserAggregate user, UserReconstitutionRequirement source) {
 *         this.user = user;
 *         this.source = source;
 *     }
 *
 *     @Override
 *     public UserReconstitutionRequirement getSourceRequirement() {
 *         return source;
 *     }
 *
 *     public UserAggregate getAggregate() {
 *         return user;
 *     }
 * }
 * }</pre>
 *
 * @param <R> the type of requirement that produced this proof
 */
public interface IDomainReconstitutionProof<R extends IDomainReconstitutionRequirement<?>> {

    /**
     * Returns the requirement that authorized this reconstitution.
     *
     * @return the source requirement
     */
    R getSourceRequirement();
}
