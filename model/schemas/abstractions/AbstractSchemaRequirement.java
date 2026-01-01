package com.authservice.domain.model.schemas.abstractions;

import com.authservice.domain.model.aggregates.mapping.interfaces.IMap;
import com.authservice.domain.model.schemas.interfaces.ISchemaFactory;
import com.authservice.domain.model.schemas.interfaces.ISchemaProof;
import com.authservice.domain.model.schemas.interfaces.ISchemaRequirement;

/**
 * Base implementation of {@link ISchemaRequirement} that enforces
 * factory-specific consumption of raw mappings.
 * <p>
 * This abstraction establishes a <strong>strict authorization boundary</strong>
 * between untrusted {@link IMap} input and schema construction. A requirement
 * instance is bound to exactly one {@link ISchemaFactory}, and may only be
 * consumed by that factory.
 * </p>
 *
 * <p>
 * By enforcing receiver validation at runtime, this class prevents:
 * <ul>
 * <li>Accidental reuse of requirements across factories</li>
 * <li>Invalid schema construction paths</li>
 * <li>Bypassing of validation and normalization logic</li>
 * </ul>
 * </p>
 *
 * <p>
 * Concrete subclasses are responsible for interpreting and validating the
 * mapping contents, while this base class guarantees controlled access and
 * consumption semantics.
 * </p>
 *
 * @param <F> the concrete schema factory authorized to consume this requirement
 */
public abstract class AbstractSchemaRequirement<F extends ISchemaFactory<? extends ISchemaProof, ? extends AbstractSchemaRequirement<F>>>
        implements ISchemaRequirement<F> {

    private final F target;
    private final IMap mapping;

    /**
     * Creates a schema requirement bound to a specific factory instance.
     * <p>
     * The provided mapping is considered <em>untrusted</em> until consumed
     * by the authorized factory and transformed into a schema.
     * </p>
     *
     * @param mapping the raw mapping to be validated and normalized
     * @param target  the only factory authorized to consume this requirement
     */
    protected AbstractSchemaRequirement(IMap mapping, F target) {
        this.mapping = mapping;
        this.target = target;
    }

    /**
     * Validates that the given receiver is the factory this requirement
     * was originally bound to.
     *
     * @param receiver the factory attempting to consume this requirement
     * @throws IllegalArgumentException if the receiver is not the authorized
     *                                  factory
     */
    protected final void validateReceiver(F receiver) {
        if (receiver != target) {
            throw new IllegalArgumentException(
                    "Schema requirement consumed by an unauthorized factory instance.");
        }
    }

    /**
     * Template method that enforces receiver authorization before exposing
     * the underlying mapping.
     * <p>
     * This method must not be overridden. Subclasses should perform all
     * semantic validation prior to schema construction.
     * </p>
     *
     * @param receiver the schema factory requesting the mapping
     * @return the raw mapping authorized for schema construction
     */
    @Override
    public final IMap getMapping(F receiver) {
        validateReceiver(receiver);
        return mapping;
    }
}
