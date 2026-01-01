package com.authservice.domain.model.schemas.interfaces;

/**
 * Primary domain service responsible for transforming validated
 * requirements into immutable schema proofs.
 * <p>
 * This interface defines the contract for services that can take a
 * domain requirement (which encapsulates raw or parsed data) and produce
 * a fully validated {@link ISchemaProof}. Implementations are responsible
 * for enforcing capability-guarded access, type safety, and validation rules
 * defined in the target schema factory.
 * </p>
 *
 * <p>
 * Typical usage involves:
 * <ol>
 * <li>Providing a requirement that has been fully validated via its
 * step builder or mapping layer.</li>
 * <li>Delegating the construction process to the authorized factory
 * to ensure correct schema instantiation.</li>
 * <li>Returning a sealed {@link ISchemaProof} that the aggregate
 * can safely consume.</li>
 * </ol>
 * </p>
 */
public interface ISchemaService {

    /**
     * Orchestrates the secure creation of a schema proof from a requirement.
     * <p>
     * This method enforces the double-handshake protocol: only the
     * factory authorized for the provided requirement can consume it and
     * produce a valid {@link ISchemaProof}.
     * </p>
     *
     * @param <S>         The type of schema proof expected (usually an
     *                    implementation of {@link ISchemaProof})
     * @param <F>         The type of factory authorized to consume the requirement
     *                    (implements {@link ISchemaFactory})
     * @param <R>         The type of requirement that carries the validated input
     *                    data (implements {@link ISchemaRequirement})
     * @param requirement The specific requirement instance to be transformed
     * @return A fully validated and immutable {@link ISchemaProof} instance
     * @throws NullPointerException  if the requirement is null
     * @throws IllegalStateException if the requirement cannot be consumed by the
     *                               factory
     */
    <S extends ISchemaProof, F extends ISchemaFactory<S, R>, R extends ISchemaRequirement<F>> S createSchema(
            R requirement);
}
