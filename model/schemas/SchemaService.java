package com.authservice.domain.model.schemas;

import java.util.Objects;

import com.authservice.domain.model.schemas.interfaces.ISchemaFactory;
import com.authservice.domain.model.schemas.interfaces.ISchemaProof;
import com.authservice.domain.model.schemas.interfaces.ISchemaRequirement;
import com.authservice.domain.model.schemas.interfaces.ISchemaService;

/**
 * Concrete implementation of the {@link IReconstitutionService}.
 * <p>
 * The {@code ReconstitutionService} acts as the central orchestrator for safely
 * transforming validated requirements into immutable schema proofs.
 * It leverages the {@link SchemaRegistry} to resolve authorized factories
 * and ensures that only the intended factory can consume a given requirement.
 * </p>
 *
 * <p>
 * This service enforces a <strong>double-dispatch protocol</strong>:
 * <ol>
 * <li>Look up the factory associated with the requirement using the
 * registry.</li>
 * <li>Delegate the creation of the schema proof to the factory.</li>
 * <li>Return the fully validated, immutable {@link ISchemaProof} to the
 * caller.</li>
 * </ol>
 * </p>
 *
 * <p>
 * By centralizing this orchestration, the service ensures that aggregates
 * are only ever constructed from data that has passed validation and type
 * safety checks.
 * </p>
 */
public final class SchemaService implements ISchemaService {

    /**
     * The registry responsible for resolving schema factories.
     * Should be pre-configured and ideally locked for thread safety.
     */
    private final SchemaRegistry registry;

    /**
     * Constructs a {@code ReconstitutionService} with a given registry.
     *
     * @param registry the {@link SchemaRegistry} used to resolve factories
     * @throws NullPointerException if the provided registry is null
     */
    public SchemaService(SchemaRegistry registry) {
        this.registry = Objects.requireNonNull(registry, "SchemaRegistry cannot be null");
    }

    /**
     * Reconstitutes a schema proof from a validated requirement.
     * <p>
     * This method performs a secure orchestration:
     * <ol>
     * <li>Retrieves the authorized factory from the hardened registry.</li>
     * <li>Invokes the factory's {@code create()} method with the requirement.</li>
     * <li>Returns a fully validated {@link ISchemaProof} instance.</li>
     * </ol>
     * </p>
     *
     * @param <S>         the type of schema proof to return
     * @param <F>         the type of factory authorized to consume the requirement
     * @param <R>         the type of requirement carrying the data
     * @param requirement the specific requirement instance to transform
     * @return a fully validated, immutable {@link ISchemaProof} instance
     * @throws NullPointerException     if the requirement is null
     * @throws IllegalArgumentException if no factory is registered for the
     *                                  requirement type
     */
    @Override
    public <S extends ISchemaProof, F extends ISchemaFactory<S, R>, R extends ISchemaRequirement<F>> S createSchema(
            R requirement) {

        Objects.requireNonNull(requirement, "Requirement cannot be null");

        // Retrieve the authorized factory from the hardened registry
        ISchemaFactory<S, R> factory = registry.getFactory(requirement);

        // Delegate construction to the factory and return the validated proof
        return factory.create(requirement);
    }
}
