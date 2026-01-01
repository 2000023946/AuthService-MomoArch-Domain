package com.authservice.domain.model.aggregates;

import com.authservice.domain.model.aggregates.interfaces.IDomainReconstitutionService;
import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationFactory;
import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationProof;
import com.authservice.domain.model.aggregates.interfaces.creational.IDomainCreationRequirement;
import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionFactory;
import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionProof;
import com.authservice.domain.model.aggregates.interfaces.reconstitution.IDomainReconstitutionRequirement;

/**
 * Central application-layer orchestrator for the Momo Architecture.
 * <p>
 * This class is the concrete implementation of
 * {@link IDomainReconstitutionService}
 * and serves as the <strong>single execution engine</strong> for both:
 * </p>
 * <ul>
 * <li><strong>Creation</strong> (birth of new domain aggregates)</li>
 * <li><strong>Reconstitution</strong> (rehydration of existing aggregates)</li>
 * </ul>
 *
 * <p>
 * The orchestrator performs no domain logic itself. Its sole responsibility is
 * to:
 * </p>
 * <ol>
 * <li>Resolve the correct Factory for a given Requirement</li>
 * <li>Delegate execution to that Factory</li>
 * <li>Return a domain-certified Proof</li>
 * </ol>
 *
 * <p>
 * Factory resolution is delegated to specialized registries:
 * </p>
 * <ul>
 * <li>{@link CreationRegistry} for creation flows</li>
 * <li>{@link ReconstitutionRegistry} for reconstitution flows</li>
 * </ul>
 *
 * <p>
 * This class represents the <strong>final boundary</strong> between the
 * application
 * layer and the domain model. All domain entry and exit points are mediated
 * through
 * Requirements and Proofs.
 * </p>
 *
 * <h2>Thread Safety</h2>
 * <p>
 * This class is thread-safe provided the supplied registries are fully
 * configured
 * and locked before concurrent access begins.
 * </p>
 */
public final class ReconstitutionOrchestrator implements IDomainReconstitutionService {

    /**
     * Registry responsible for resolving creation factories.
     */
    private final CreationRegistry creationRegistry;

    /**
     * Registry responsible for resolving reconstitution factories.
     */
    private final ReconstitutionRegistry reconstitutionRegistry;

    /**
     * Constructs a new orchestrator with the provided registries.
     *
     * @param creationRegistry       the registry used to resolve creation factories
     * @param reconstitutionRegistry the registry used to resolve reconstitution
     *                               factories
     * @throws IllegalArgumentException if either registry is {@code null}
     */
    public ReconstitutionOrchestrator(
            CreationRegistry creationRegistry,
            ReconstitutionRegistry reconstitutionRegistry) {

        if (creationRegistry == null || reconstitutionRegistry == null) {
            throw new IllegalArgumentException("Registries must not be null.");
        }
        this.creationRegistry = creationRegistry;
        this.reconstitutionRegistry = reconstitutionRegistry;
    }

    /**
     * Executes a domain <strong>creation</strong> flow.
     * <p>
     * The process consists of:
     * </p>
     * <ol>
     * <li>Resolving the {@link IDomainCreationFactory} associated with the
     * concrete Requirement type</li>
     * <li>Performing the factory–requirement handshake</li>
     * <li>Returning a domain-certified {@link IDomainCreationProof}</li>
     * </ol>
     *
     * @param <P>         the concrete creation proof type
     * @param <R>         the concrete creation requirement type
     * @param requirement the creation requirement acting as the input gate
     * @return a domain-certified creation proof
     * @throws RuntimeException if no compatible factory can be resolved
     *                          or if the handshake fails
     */
    @Override
    @SuppressWarnings("unchecked")
    public <P extends IDomainCreationProof<R>, R extends IDomainCreationRequirement<F>, F extends IDomainCreationFactory<P, R>> P create(
            R requirement) {

        IDomainCreationFactory<P, R> factory = (IDomainCreationFactory<P, R>) creationRegistry.getFactory(
                (Class<? extends IDomainCreationRequirement<IDomainCreationFactory<P, R>>>) requirement.getClass());

        return factory.create(requirement);
    }

    /**
     * Executes a domain <strong>reconstitution</strong> flow.
     * <p>
     * The process consists of:
     * </p>
     * <ol>
     * <li>Resolving the {@link IDomainReconstitutionFactory} associated with the
     * concrete Requirement type</li>
     * <li>Performing the factory–requirement handshake</li>
     * <li>Returning a domain-certified {@link IDomainReconstitutionProof}</li>
     * </ol>
     *
     * @param <P>         the concrete reconstitution proof type
     * @param <R>         the concrete reconstitution requirement type
     * @param requirement the reconstitution requirement guarding persisted state
     * @return a domain-certified reconstitution proof
     * @throws RuntimeException if no compatible factory can be resolved
     *                          or if the handshake fails
     */
    @Override
    @SuppressWarnings("unchecked")
    public <P extends IDomainReconstitutionProof<R>, R extends IDomainReconstitutionRequirement<F>, F extends IDomainReconstitutionFactory<P, R>> P reconstitute(
            R requirement) {

        IDomainReconstitutionFactory<P, R> factory = (IDomainReconstitutionFactory<P, R>) reconstitutionRegistry
                .getFactory(
                        (Class<? extends IDomainReconstitutionRequirement<IDomainReconstitutionFactory<P, R>>>) requirement
                                .getClass());

        return factory.reconstitute(requirement);
    }
}
