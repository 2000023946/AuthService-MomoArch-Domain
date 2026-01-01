package com.authservice.domain.model.parser.interfaces;

/**
 * Represents a requirement or contract that must be satisfied
 * in order to create and execute a parser.
 * <p>
 * An {@code IParserRequirement} encapsulates all input data and
 * contextual information required by a corresponding
 * {@link IParserFactory} to construct a parser instance.
 * </p>
 *
 * <p>
 * This interface participates in a <em>self-referential generic contract</em>
 * with {@link IParserFactory}, ensuring that a requirement is only
 * consumable by the factory it was designed for.
 * </p>
 *
 * @param <F> the concrete {@link IParserFactory} type that consumes
 *            this requirement
 */
public interface IParserRequirement<F extends IParserFactory<? extends IParser<? extends IParsedProof>, ? extends IParserRequirement<F>>> {

    /**
     * Retrieves the raw input to be parsed from the given parser factory.
     * <p>
     * Implementations define how input is extracted or derived
     * from the provided {@code receiver}. This allows requirements
     * to remain declarative while factories control parser creation.
     * </p>
     *
     * @param receiver the parser factory requesting the input
     * @return a {@link String} representing the input to be parsed
     */
    String getInput(F receiver);
}
