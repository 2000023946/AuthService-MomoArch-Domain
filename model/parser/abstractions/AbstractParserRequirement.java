package com.authservice.domain.model.parser.abstractions;

import com.authservice.domain.model.aggregates.parser.interfaces.IParsedProof;
import com.authservice.domain.model.aggregates.parser.interfaces.IParser;
import com.authservice.domain.model.aggregates.parser.interfaces.IParserFactory;
import com.authservice.domain.model.aggregates.parser.interfaces.IParserRequirement;

/**
 * Base class for parser requirements that enforces
 * factory-specific consumption.
 * <p>
 * This abstract class guarantees that a requirement instance
 * can only be consumed by the {@link IParserFactory} it was
 * created for. Subclasses are responsible only for providing
 * the actual input data.
 * </p>
 *
 * <p>
 * This class implements {@link IParserRequirement} and
 * centralizes receiver validation to prevent duplication
 * and accidental misuse.
 * </p>
 *
 * @param <F> the concrete parser factory authorized to consume
 *            this requirement
 */
public abstract class AbstractParserRequirement<F extends IParserFactory<? extends IParser<? extends IParsedProof>, ? extends IParserRequirement<F>>>
        implements IParserRequirement<F> {

    private final F target;
    private final String stringToParse;

    /**
     * Creates a requirement bound to a specific parser factory.
     * 
     * @param stringToParse the string to parse
     * @param target        the only factory authorized to consume this requirement
     */
    protected AbstractParserRequirement(String stringToParse, F target) {
        this.stringToParse = stringToParse;
        this.target = target;
    }

    /**
     * Validates that the given receiver is the factory this
     * requirement was created for.
     *
     * @param receiver the factory attempting to consume the requirement
     * @throws IllegalArgumentException if the receiver is not authorized
     */
    protected final void validateReceiver(F receiver) {
        if (receiver != target) {
            throw new IllegalArgumentException(
                    "Requirement consumed by an unauthorized parser factory");
        }
    }

    /**
     * Template method that enforces receiver validation
     * before delegating input extraction to subclasses.
     *
     * @param receiver the parser factory requesting input
     * @return the input to be parsed
     */
    @Override
    public final String getInput(F receiver) {
        validateReceiver(receiver);
        return stringToParse;
    }

}
