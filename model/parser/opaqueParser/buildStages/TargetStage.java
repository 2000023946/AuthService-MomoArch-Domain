package com.authservice.domain.model.parser.opaqueParser.buildStages;

import com.authservice.domain.model.aggregates.parser.opaqueParser.OpaqueParserFactory;

/**
 * First stage of the {@link OpaqueParserRequirement} builder.
 * <p>
 * Declares the target {@link OpaqueParserFactory} that is authorized
 * to consume the requirement.
 */
public interface TargetStage {

    /**
     * Specifies the target parser factory.
     *
     * @param target the authorized {@link OpaqueParserFactory}
     * @return the next stage requiring the opaque token string
     */
    StringToParseStage withTarget(OpaqueParserFactory target);
}
