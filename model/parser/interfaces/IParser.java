package com.authservice.domain.model.parser.interfaces;

/**
 * Generic parser interface for converting a raw, untrusted string
 * (typically a token) into a validated parsing proof.
 * <p>
 * Implementations of this interface act as a <strong>security
 * boundary</strong>:
 * they are responsible for fully validating the input before producing
 * a trusted result that may be consumed by downstream domain logic.
 * </p>
 *
 * <p>
 * Example implementations include:
 * <ul>
 * <li><strong>JWT parsers</strong> — decode, verify signature and claims,
 * and extract validated payload JSON</li>
 * <li><strong>Opaque token parsers</strong> — resolve tokens via storage
 * systems such as databases or caches</li>
 * </ul>
 * </p>
 *
 * <p>
 * Callers must assume that all input provided to this interface is
 * <em>untrusted</em>. Only the returned {@link IParsedProof} may be
 * considered safe to use.
 * </p>
 */
public interface IParser<P extends IParsedProof> {

    /**
     * Parses and validates the provided raw input string.
     * <p>
     * Implementations must perform all required validation steps
     * (such as structural checks, signature verification, expiration
     * checks, and format validation) before returning a parsing proof.
     * </p>
     *
     * <p>
     * If validation fails at any stage, an exception must be thrown
     * and no proof may be produced.
     * </p>
     *
     * @return a validated {@link IParsedProof} representing the trusted
     *         result of the parsing operation
     * @throws InvalidTokenException if the input is malformed, expired,
     *                               fails validation, or cannot be parsed
     */
    P parse();

    /**
     * Returns the raw input string currently associated with this parser.
     * <p>
     * This method exists primarily for introspection, diagnostics,
     * or tracing purposes and should not be used as a substitute
     * for {@link #parse(String)}.
     * </p>
     *
     * <p>
     * The returned value must be treated as <em>untrusted</em>.
     * </p>
     *
     * @return the raw input string to be parsed
     */
    String getStringToParse();
}
