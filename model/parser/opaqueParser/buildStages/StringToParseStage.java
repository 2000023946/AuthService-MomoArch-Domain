package com.authservice.domain.model.parser.opaqueParser.buildStages;

/**
 * Second stage of the {@link OpaqueParserRequirement} builder.
 * <p>
 * Supplies the raw opaque token string to be parsed.
 */
public interface StringToParseStage {

    /**
     * Specifies the opaque token string.
     *
     * @param s the raw token string
     * @return the next stage requiring a token repository
     */
    RepositoryStage withStringToParse(String s);
}
