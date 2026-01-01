package com.authservice.domain.model.parser.interfaces;

/**
 * Primary entry point for the token parsing module.
 * <p>
 * This interface defines the contract for services responsible for parsing
 * and validating tokens, such as JWTs, opaque tokens, or other structured
 * authorization artifacts.
 * Implementations are expected to:
 * <ul>
 * <li>Resolve the appropriate {@link IParserFactory} based on the provided
 * requirement.</li>
 * <li>Perform capability-guarded access, ensuring that only authorized
 * factories can extract data from the underlying payload.</li>
 * <li>Validate token structure, type, and any business-specific claims.</li>
 * <li>Return a type-safe {@link IParsedProof} representing the validated token
 * contents.</li>
 * </ul>
 * </p>
 *
 * <p>
 * This design follows the <strong>Momo-Architecture</strong> principle of
 * separating raw parsing logic from domain aggregates, ensuring that domain
 * entities are only ever constructed from fully validated data.
 * </p>
 */
public interface IParsingService {

    /**
     * Parses the token according to the provided requirement and returns a
     * validated proof of the token's contents.
     *
     * <p>
     * The parsing process follows these steps:
     * <ol>
     * <li>Resolve the correct {@link IParserFactory} from the requirement.</li>
     * <li>Perform capability-based validation to ensure only authorized
     * factories can access the underlying token payload.</li>
     * <li>Extract fields according to the schema defined by the requirement.</li>
     * <li>Construct and return a {@link IParsedProof} representing
     * the fully validated token.</li>
     * </ol>
     * </p>
     *
     * @param <P>         The type of parsed proof returned. Must implement
     *                    {@link IParsedProof}.
     * @param <F>         The type of parser factory responsible for producing the
     *                    parser.
     *                    Must implement {@link IParserFactory} for the
     *                    corresponding parser and requirement.
     * @param <R>         The type of requirement describing how the parser should
     *                    process the token.
     *                    Must implement {@link IParserRequirement} for the
     *                    associated factory.
     * @param requirement The parser requirement containing all necessary
     *                    metadata and capability information to safely parse the
     * @return A validated and fully constructed instance of {@link IParsedProof},
     *         encapsulating the token's data and guaranteeing structural
     *         correctness.
     * @throws IllegalArgumentException If the requirement is invalid or
     *                                  incompatible
     *                                  with the resolved parser factory.
     * @throws ParsingException         If the token cannot be parsed or fails
     *                                  validation.
     */
    <P extends IParsedProof, F extends IParserFactory<? extends IParser<P>, R>, R extends IParserRequirement<F>> P parse(
            R requirement);
}
