package com.authservice.domain.model.parser.abstractions;

import com.authservice.domain.model.aggregates.parser.interfaces.IParsedProof;
import com.authservice.domain.model.aggregates.parser.interfaces.IParser;

/**
 * Abstract base class for parser implementations that operate on
 * a raw, untrusted input string.
 * <p>
 * This class provides common infrastructure for {@link IParser}
 * implementations by storing the input string and exposing it
 * via {@link #getStringToParse()}.
 * </p>
 *
 * <p>
 * Concrete subclasses are responsible for implementing the
 * {@link #parse(String)} method and enforcing all required
 * validation and security checks before producing a trusted
 * parsing proof.
 * </p>
 *
 * <p>
 * Instances of this class are immutable and thread-safe,
 * assuming subclasses do not introduce mutable state.
 * </p>
 */
public abstract class AbstractParser<P extends IParsedProof> implements IParser<P> {

    /**
     * The raw, untrusted input string to be parsed.
     */
    private final String stringToParse;

    /**
     * Constructs a parser bound to a specific raw input string.
     * <p>
     * The provided string is treated as <em>untrusted</em> and must
     * be fully validated by subclasses during parsing.
     * </p>
     *
     * @param stringToParse the raw input string to be parsed
     */
    protected AbstractParser(String stringToParse) {
        this.stringToParse = stringToParse;
    }

    /**
     * Returns the raw input string associated with this parser.
     * <p>
     * This method is intended for diagnostic, tracing, or
     * introspection purposes only. Callers must not assume
     * that the returned value is valid or safe.
     * </p>
     *
     * @return the raw, untrusted input string
     */
    @Override
    public String getStringToParse() {
        return stringToParse;
    }
}
