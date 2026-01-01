package com.authservice.domain.model.parser.opaqueParser.buildStages;

import com.authservice.domain.ports.ITokenRepository;

/**
 * Third stage of the {@link OpaqueParserRequirement} builder.
 * <p>
 * Supplies the repository used to retrieve token JSON payloads.
 */
public interface RepositoryStage {

    /**
     * Specifies the repository for retrieving token JSON.
     *
     * @param repo the {@link ITokenRepository} instance
     * @return the next stage requiring a JSON parser
     */
    JsonParserStage withRepository(ITokenRepository repo);
}
