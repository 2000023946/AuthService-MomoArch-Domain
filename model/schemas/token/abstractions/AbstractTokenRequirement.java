package com.authservice.domain.model.schemas.token.abstractions;

import com.authservice.domain.model.aggregates.mapping.interfaces.IMap;
import com.authservice.domain.model.schemas.interfaces.ISchemaFactory;
import com.authservice.domain.model.schemas.interfaces.ISchemaRequirement;

/**
 * Base requirement for token-specific schema construction.
 * <p>
 * This abstraction defines a <strong>token-scoped validation boundary</strong>
 * between untrusted {@link IMap} input and the construction of an
 * {@link AbstractTokenSchema}. A requirement instance is bound to exactly
 * one token schema factory and may only be consumed by that factory.
 * </p>
 *
 * <p>
 * This class exists as a specialization of the general schema requirement
 * pattern in order to:
 * <ul>
 * <li>Constrain requirements to token schema families</li>
 * <li>Prevent cross-token or cross-factory misuse</li>
 * <li>Express token intent explicitly at the type level</li>
 * </ul>
 * </p>
 *
 * @param <F> the concrete token schema factory authorized to consume this
 *            requirement
 */
public abstract class AbstractTokenRequirement<F extends ISchemaFactory<? extends AbstractTokenProof<? extends AbstractTokenSchema>, ? extends ISchemaRequirement<F>>>
        implements ISchemaRequirement<F> {

    private final F target;
    private final IMap mapping;

    /**
     * Creates a token schema requirement bound to a specific factory.
     * <p>
     * The provided mapping is considered untrusted until it is validated
     * and transformed into a token schema by the authorized factory.
     * </p>
     *
     * @param mapping the raw token-related data
     * @param target  the only factory authorized to consume this requirement
     */
    protected AbstractTokenRequirement(IMap mapping, F target) {
        this.mapping = mapping;
        this.target = target;
    }

    /**
     * Validates that the requesting factory is authorized to consume
     * this requirement.
     *
     * @param receiver the factory attempting to consume the requirement
     * @throws IllegalArgumentException if the receiver is not the authorized
     *                                  factory
     */
    protected final void validateReceiver(F receiver) {
        if (receiver != target) {
            throw new IllegalArgumentException(
                    "Token schema requirement consumed by an unauthorized factory instance.");
        }
    }

    /**
     * Provides access to the underlying mapping after enforcing
     * factory authorization.
     * <p>
     * This method must not be overridden.
     * </p>
     *
     * @param receiver the token schema factory requesting the mapping
     * @return the raw mapping authorized for token schema construction
     */
    @Override
    public final IMap getMapping(F receiver) {
        validateReceiver(receiver);
        return mapping;
    }
}
