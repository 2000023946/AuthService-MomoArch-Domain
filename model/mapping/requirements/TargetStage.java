package com.authservice.domain.model.aggregates.mapping.requirements;

import org.jetbrains.annotations.NotNull;

import com.authservice.domain.model.aggregates.mapping.capsule.MappingFactory;

/**
 * First stage in the claims-mapping requirement builder.
 * <p>
 * This stage declares the target type for which claims will be mapped.
 * The target is provided as a {@link Class} reference rather than a
 * live instance.
 */
public interface TargetStage {

    /**
     * Declares the target class that this requirement applies to.
     *
     * @param target target class
     * @return next builder stage requiring a validated token proof
     */
    JsonStringStage forTarget(@NotNull MappingFactory target);
}
