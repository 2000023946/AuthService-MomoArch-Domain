package com.authservice.domain.model.parser.jwtParser.buildStages;

import com.authservice.domain.model.aggregates.parser.jwtParser.JwtParserFactory;
import com.authservice.domain.model.aggregates.parser.jwtParser.JwtParserRequirement;

/**
 * First stage of the staged builder for {@link JwtParserRequirement}.
 * <p>
 * This stage declares the <strong>target parser factory</strong> that will be
 * authorized to consume the resulting requirement.
 * </p>
 *
 * <p>
 * The target factory acts as a <em>capability owner</em>: once declared,
 * the constructed {@link JwtParserRequirement} may only be consumed by
 * this specific factory instance.
 * </p>
 *
 * <p>
 * This stage must be completed before any parsing input can be supplied.
 * </p>
 */
public interface TargetStage {

    /**
     * Declares the parser factory that this requirement applies to.
     *
     * @param target the parser factory authorized to consume the requirement
     * @return the next builder stage requiring the string to be parsed
     */
    StringToParseStage withTarget(JwtParserFactory target);
}
