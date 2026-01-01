package com.authservice.domain.model.schemas.interfaces;

import com.authservice.domain.model.aggregates.mapping.interfaces.IMap;

/**
 * Defines the validation contract required to produce a schema.
 * <p>
 * A schema requirement consumes a raw {@link IMap} and performs all
 * structural validation, key enforcement, and extraction necessary
 * to construct a stable {@link ISchema}.
 * </p>
 *
 * <p>
 * Requirements are <strong>factory-aware</strong> and may only be consumed
 * by their authorized {@link ISchemaFactory}. This prevents accidental or
 * invalid schema construction.
 * </p>
 *
 * @param <F> the factory type authorized to consume this requirement
 */
public interface ISchemaRequirement<F extends ISchemaFactory<? extends ISchemaProof, ? extends ISchemaRequirement<F>>> {

    /**
     * Validate the underlying mapping and expose it to the receiving factory.
     *
     * @param receiver the factory requesting the validated mapping
     * @return a validated mapping suitable for schema creation
     */
    IMap getMapping(F receiver);
}
