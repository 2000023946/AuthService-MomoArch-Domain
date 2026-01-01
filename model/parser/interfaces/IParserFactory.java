package com.authservice.domain.model.parser.interfaces;

/**
 * Factory interface responsible for creating {@link IParser} instances.
 * <p>
 * An {@code IParserFactory} consumes a corresponding
 * {@link IParserRequirement} and produces a parser that is
 * correctly configured to handle the provided input.
 * </p>
 *
 * <p>
 * The recursive generic relationship between factories and requirements
 * enforces <strong>compile-time correctness</strong>, ensuring that
 * a factory can only accept requirements explicitly designed for it.
 * </p>
 *
 * @param <P> the concrete {@link IParser} type produced by this factory
 * @param <R> the {@link IParserRequirement} type required to create the parser
 */
public interface IParserFactory<P extends IParser<? extends IParsedProof>, R extends IParserRequirement<? extends IParserFactory<P, R>>> {

    /**
     * Creates a new parser instance using the supplied requirement.
     * <p>
     * The provided {@code requirement} contains all information
     * necessary for constructing and configuring the parser.
     * </p>
     *
     * @param requirement the requirement used to create the parser
     * @return a fully configured {@link IParser} instance
     */
    P create(R requirement);
}
