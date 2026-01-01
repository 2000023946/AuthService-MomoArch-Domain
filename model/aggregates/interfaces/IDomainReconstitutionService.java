package com.authservice.domain.model.aggregates.interfaces;

import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationFactory;
import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationProof;
import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationRequirement;
import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionFactory;
import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionProof;
import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionRequirement;

/**
 * The Domain Reconstitution Engine (DRE) orchestrator.
 * <p>
 * This interface represents the <strong>primary application-layer
 * gateway</strong>
 * into the Domain Model for both:
 * </p>
 * <ul>
 * <li><strong>Creation</strong> (birth of new aggregates)</li>
 * <li><strong>Reconstitution</strong> (rehydration of existing aggregates)</li>
 * </ul>
 *
 * <p>
 * Implementations of this service are responsible for:
 * </p>
 * <ol>
 * <li>Accepting a domain Requirement (Creation or Reconstitution)</li>
 * <li>Resolving the correct Factory for that Requirement</li>
 * <li>Executing the domain handshake</li>
 * <li>Returning a domain-certified Proof</li>
 * </ol>
 *
 * <p>
 * This service intentionally exposes <em>only</em> Requirements and Proofs.
 * No raw schemas, payloads, or aggregates may cross this boundary directly.
 * </p>
 *
 * <h2>Architectural Role</h2>
 * <ul>
 * <li>Acts as the sole entry point from the application layer into the
 * domain</li>
 * <li>Enforces strict separation between creation and reconstitution flows</li>
 * <li>Guarantees that all aggregates are domain-certified via Proofs</li>
 * </ul>
 */
public interface IDomainReconstitutionService {

    /**
     * Executes a domain <strong>creation</strong> flow.
     * <p>
     * This operation transforms a creation Requirement into a domain-certified
     * creation Proof by:
     * </p>
     * <ol>
     * <li>Discovering the appropriate {@link IDomainCreationFactory}</li>
     * <li>Performing the factory–requirement handshake</li>
     * <li>Producing a {@link IDomainCreationProof}</li>
     * </ol>
     *
     * @param <P>         the concrete creation proof type
     * @param <R>         the concrete creation requirement type
     * @param <F>         the concrete creation of the factory
     * @param requirement the creation requirement acting as the input gate
     * @return a domain-certified creation proof
     * @throws RuntimeException if no compatible factory can be resolved
     *                          or if the handshake fails
     */
    <P extends IDomainCreationProof<R>, R extends IDomainCreationRequirement<F>, F extends IDomainCreationFactory<P, R>> P create(
            R requirement);

    /**
     * Executes a domain <strong>reconstitution</strong> flow.
     * <p>
     * This operation transforms a reconstitution Requirement into a
     * domain-certified reconstitution Proof by:
     * </p>
     * <ol>
     * <li>Discovering the appropriate {@link IDomainReconstitutionFactory}</li>
     * <li>Performing the factory–requirement handshake</li>
     * <li>Producing a {@link IDomainReconstitutionProof}</li>
     * </ol>
     *
     * @param <P>         the concrete reconstitution proof type
     * @param <R>         the concrete reconstitution requirement type
     * @param <F>         the concrete creation of the factory
     * @param requirement the reconstitution requirement guarding persisted state
     * @return a domain-certified reconstitution proof
     * @throws RuntimeException if no compatible factory can be resolved
     *                          or if the handshake fails
     */
    <P extends IDomainReconstitutionProof<R>, R extends IDomainReconstitutionRequirement<F>, F extends IDomainReconstitutionFactory<P, R>> P reconstitute(
            R requirement);

}
