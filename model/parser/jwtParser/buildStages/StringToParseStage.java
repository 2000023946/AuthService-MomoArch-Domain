package com.authservice.domain.model.parser.jwtParser.buildStages;

import com.authservice.domain.model.aggregates.parser.jwtParser.JwtParserRequirement;

/**
 * Second stage of the staged builder for {@link JwtParserRequirement}.
 * <p>
 * This stage supplies the raw input string that will be parsed by the
 * authorized JWT parser factory.
 * </p>
 *
 * <p>
 * The provided string is treated as <strong>untrusted input</strong> and
 * will only be parsed once the requirement is consumed by the authorized
 * factory.
 * </p>
 */
public interface StringToParseStage {

    /**
     * Supplies the raw string to be parsed as a JWT.
     *
     * @param stringToParse the unvalidated JWT string
     * @return the secret key stage
     */
    SecretKeyStage withStringToParse(String stringToParse);
}
