package com.authservice.domain.model.schemas.interfaces;

/**
 * Factory responsible for creating {@link ISchema} instances from validated
 * schema requirements.
 * <p>
 * This interface defines the <strong>second phase</strong> of schema creation:
 * <ol>
 * <li>The requirement validates and extracts data from a raw {@link IMap}</li>
 * <li>The factory consumes the requirement to produce a concrete schema</li>
 * </ol>
 * </p>
 *
 * <p>
 * Factories do not accept raw maps directly. This design ensures that
 * structural validation and key enforcement occur <em>before</em> schema
 * instantiation.
 * </p>
 *
 * @param <S> the concrete schema type produced by this factory
 * @param <R> the requirement type authorized to create {@code S}
 */
public interface ISchemaFactory<S extends ISchemaProof, R extends ISchemaRequirement<? extends ISchemaFactory<S, R>>> {

    /**
     * Create a new schema from a validated requirement.
     *
     * @param requirement a validated schema requirement
     * @return an immutable schema instance
     */
    S create(R requirement);
}
