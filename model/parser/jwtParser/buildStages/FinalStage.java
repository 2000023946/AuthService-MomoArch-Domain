package com.authservice.domain.model.parser.jwtParser.buildStages;

import com.authservice.domain.model.aggregates.parser.jwtParser.JwtParserRequirement;

/**
 * Final stage of the staged builder for {@link JwtParserRequirement}.
 * <p>
 * This stage completes the construction of an immutable,
 * capability-guarded {@link JwtParserRequirement}.
 * </p>
 *
 * <p>
 * Once built, the resulting requirement:
 * <ul>
 * <li>Is bound to a specific parser factory</li>
 * <li>Encapsulates the raw JWT input</li>
 * <li>Can only be consumed by the authorized factory</li>
 * </ul>
 * </p>
 */
public interface FinalStage {

    /**
     * Builds the immutable {@link JwtParserRequirement}.
     *
     * @return a fully configured JWT parser requirement
     */
    JwtParserRequirement build();
}
