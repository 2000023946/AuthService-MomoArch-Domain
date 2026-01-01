package com.authservice.domain.model.parser.opaqueParser.buildStages;

import com.authservice.domain.model.aggregates.parser.opaqueParser.OpaqueParserRequirement;

/**
 * Final stage of the {@link OpaqueParserRequirement} builder.
 * <p>
 * Builds the immutable requirement.
 */
public interface FinalStage {

    /**
     * Builds the {@link OpaqueParserRequirement}.
     *
     * @return the fully constructed requirement
     */
    OpaqueParserRequirement build();
}
