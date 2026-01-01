package com.authservice.domain.model.parser.jwtParser.buildStages;

import com.authservice.domain.model.aggregates.parser.jwtParser.IJwtLibrary;

/**
 * Third stage in the staged builder for creating a JWT parser.
 * <p>
 * This stage provides the JWT library implementation that the parser
 * will use to verify tokens and extract payloads.
 * </p>
 *
 * <p>
 * This stage must be completed before the final parser can be built.
 * </p>
 */
public interface JwtLibraryStage {

    /**
     * Supplies the JWT library used for token verification.
     *
     * @param library the JWT library implementation
     * @return the final builder stage, allowing the parser to be built
     */
    JsonParserStage withLibrary(IJwtLibrary library);
}
