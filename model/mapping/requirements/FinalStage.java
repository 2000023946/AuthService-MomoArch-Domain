package com.authservice.domain.model.aggregates.mapping.requirements;

/**
 * Final stage in the construction of a {@link MappingRequirement}.
 * <p>
 * At this stage, all mandatory dependencies have been provided:
 * <ul>
 * <li>A validated token proof</li>
 * <li>A JSON parser for claim extraction</li>
 * </ul>
 *
 * <p>
 * This interface exposes a single operation to materialize the
 * fully-configured {@code MappingRequirement}.
 *
 * <p>
 * The staged builder pattern ensures that it is impossible to reach
 * this stage without satisfying all prior security and parsing
 * requirements.
 */
public interface FinalStage {

    /**
     * Builds the fully configured {@link MappingRequirement}.
     *
     * @return an immutable claims mapping requirement
     */
    MappingRequirement build();
}
