package com.authservice.domain.model.parser;

import java.util.Objects;

import com.authservice.domain.model.aggregates.parser.interfaces.IParsedProof;
import com.authservice.domain.model.aggregates.parser.interfaces.IParser;
import com.authservice.domain.model.aggregates.parser.interfaces.IParserFactory;
import com.authservice.domain.model.aggregates.parser.interfaces.IParserRequirement;
import com.authservice.domain.model.aggregates.parser.interfaces.IParsingService;

/**
 * Central orchestrator for parsing and validating tokens in the system.
 * <p>
 * The {@code ParsingService} provides a high-level API for safely converting
 * token representations (JWTs, opaque tokens, or custom token types) into
 * validated, domain-safe {@link IParsedProof} instances.
 * </p>
 *
 * <p>
 * Responsibilities:
 * <ul>
 * <li>Resolve the appropriate parser factory from the
 * {@link ParserRegistry}.</li>
 * <li>Perform capability-guarded parsing using the provided
 * {@link IParserRequirement}.</li>
 * <li>Return type-safe {@link IParsedProof} objects for downstream
 * aggregates.</li>
 * <li>Abstract away all low-level parsing logic from domain services.</li>
 * </ul>
 * </p>
 *
 * <p>
 * This service adheres to the <strong>Momo-Architecture</strong> principles:
 * capability-based security, separation of concerns, and decoupling domain
 * aggregates from external data representations.
 * </p>
 */
public class ParsingService implements IParsingService {

    /**
     * Registry used to look up parser instances based on the requirement.
     * <p>
     * Ensures that each requirement is handled by its authorized parser,
     * enforcing capability-based access to token data.
     * </p>
     */
    private final ParserRegistry registry;

    /**
     * Constructs a new {@code ParsingService} with a given {@link ParserRegistry}.
     *
     * @param registry the {@link ParserRegistry} used to resolve parser factories
     *                 and obtain parser instances
     * @throws NullPointerException if {@code registry} is null
     */
    public ParsingService(ParserRegistry registry) {
        this.registry = Objects.requireNonNull(registry, "ParserRegistry cannot be null");
    }

    /**
     * Parses a token according to the provided requirement, returning a fully
     * validated proof.
     * <p>
     * The parsing workflow is as follows:
     * <ol>
     * <li>Resolve the appropriate parser from the {@link ParserRegistry}.</li>
     * <li>Cast the parser to the specific generic type expected by the caller.</li>
     * <li>Invoke {@link IParser#parse()} to produce a validated
     * {@link IParsedProof} instance.</li>
     * <li>Return the proof to the caller, guaranteeing structural correctness
     * and capability-based authorization.</li>
     * </ol>
     * </p>
     *
     * @param <P>         The type of parsed proof returned. Must implement
     *                    {@link IParsedProof}.
     * @param <F>         The type of parser factory producing the parser. Must
     *                    extend
     *                    {@link IParserFactory} for a parser producing {@code P}
     *                    from requirement {@code R}.
     * @param <R>         The type of parser requirement describing how to process
     *                    the token.
     *                    Must implement {@link IParserRequirement} for factory
     *                    {@code F}.
     * @param requirement The requirement object containing both the raw token and
     *                    capability information for authorization.
     * @return A fully validated {@link IParsedProof} instance representing
     *         the token's contents.
     * @throws IllegalArgumentException If the requirement is incompatible with
     *                                  the resolved parser.
     * @throws ParsingException         If the token cannot be parsed or fails
     *                                  validation.
     */
    @Override
    public <P extends IParsedProof, F extends IParserFactory<? extends IParser<P>, R>, R extends IParserRequirement<F>> P parse(
            R requirement) {
        // 1. Retrieve the parser from the registry
        IParser<?> rawParser = registry.getParser(requirement);

        // 2. Cast the parser to the specific P requested by the caller
        // Because R is bound to F, and F is bound to IParser<P>, this is logically safe
        @SuppressWarnings("unchecked")
        IParser<P> authorizedParser = (IParser<P>) rawParser;

        // 3. Invoke the parser and return the proof
        return authorizedParser.parse();
    }
}
