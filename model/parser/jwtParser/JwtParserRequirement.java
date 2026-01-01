package com.authservice.domain.model.parser.jwtParser;

import com.authservice.domain.model.aggregates.parser.abstractions.AbstractParserRequirement;
import com.authservice.domain.model.aggregates.parser.jwtParser.buildStages.*;
import com.authservice.domain.ports.IJsonParser;

import java.util.Objects;

/**
 * Represents an immutable, capability-guarded requirement for parsing a JWT.
 * <p>
 * A {@code JwtParserRequirement} encapsulates all data needed to safely
 * construct a {@link JwtParser} and ensures that only the authorized
 * {@link JwtParserFactory} can access it. It contains:
 * <ul>
 * <li>A raw, untrusted JWT string</li>
 * <li>The authorized parser factory that can consume this requirement</li>
 * <li>The secret key used to verify JWT signatures</li>
 * <li>A JWT library implementation to perform verification</li>
 * <li>An {@link IJsonParser} to validate and parse JSON payloads</li>
 * </ul>
 *
 * <p>
 * This class participates in a <strong>two-phase handshake</strong>:
 * <ol>
 * <li>Constructed via a staged builder, binding it to a concrete factory</li>
 * <li>The authorized factory retrieves the components to parse and produce
 * a trusted {@link JwtParserProof}</li>
 * </ol>
 * </p>
 *
 * <p>
 * Access to all fields is capability-guarded: only the authorized factory
 * declared during construction may retrieve the string, secret, library,
 * or JSON parser. Any attempt by another factory will throw an exception.
 * </p>
 *
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 */
public class JwtParserRequirement extends AbstractParserRequirement<JwtParserFactory> {

    /** Secret key used for verifying JWT signatures. */
    private final String secretKey;

    /** JWT library implementation used for parsing and verification. */
    private final IJwtLibrary library;

    /** JSON parser used to validate and parse JWT payloads. */
    private final IJsonParser jsonParser;

    /**
     * Private constructor for the staged builder.
     *
     * @param stringToParse the raw JWT string
     * @param target        the authorized parser factory
     * @param secretKey     secret key for JWT verification
     * @param library       JWT library for parsing/verification
     * @param jsonParser    JSON parser for payload validation
     */
    private JwtParserRequirement(String stringToParse,
            JwtParserFactory target,
            String secretKey,
            IJwtLibrary library,
            IJsonParser jsonParser) {
        super(stringToParse, target);
        this.secretKey = secretKey;
        this.library = library;
        this.jsonParser = jsonParser;
    }

    /**
     * Returns the secret key for JWT verification.
     *
     * @param receiver the parser factory requesting the secret key
     * @return the secret key
     * @throws IllegalArgumentException if the receiver is not the authorized
     *                                  factory
     */
    String getSecretKey(JwtParserFactory receiver) {
        validateReceiver(receiver);
        return secretKey;
    }

    /**
     * Returns the JWT library used for parsing and verification.
     *
     * @param receiver the parser factory requesting the library
     * @return the JWT library instance
     * @throws IllegalArgumentException if the receiver is not the authorized
     *                                  factory
     */
    IJwtLibrary getJwtLibrary(JwtParserFactory receiver) {
        validateReceiver(receiver);
        return library;
    }

    /**
     * Returns the JSON parser used to validate and parse the payload.
     *
     * @param receiver the parser factory requesting the parser
     * @return the JSON parser instance
     * @throws IllegalArgumentException if the receiver is not the authorized
     *                                  factory
     */
    IJsonParser getJsonParser(JwtParserFactory receiver) {
        validateReceiver(receiver);
        return jsonParser;
    }

    /**
     * Entry point for building a {@link JwtParserRequirement}.
     *
     * <p>
     * The staged builder enforces the following construction order:
     * </p>
     * <ol>
     * <li>Declare the target parser factory</li>
     * <li>Provide the JWT string</li>
     * <li>Provide the secret key</li>
     * <li>Provide the JWT library</li>
     * <li>Provide the JSON parser</li>
     * <li>Build the immutable requirement</li>
     * </ol>
     *
     * @return the first stage of the builder
     */
    public static TargetStage builder() {
        return new Builder();
    }

    // ---------------------------------------------------------------------
    // Staged Builder Implementation
    // ---------------------------------------------------------------------

    /**
     * Staged builder for {@link JwtParserRequirement}.
     * <p>
     * Enforces correct construction order at compile time and prevents
     * incomplete or unauthorized configurations.
     * </p>
     */
    private static class Builder implements
            TargetStage,
            StringToParseStage,
            SecretKeyStage,
            JwtLibraryStage,
            JsonParserStage,
            FinalStage {

        private String stringToParse;
        private JwtParserFactory target;
        private String secretKey;
        private IJwtLibrary library;
        private IJsonParser jsonParser;

        @Override
        public StringToParseStage withTarget(JwtParserFactory target) {
            this.target = Objects.requireNonNull(target, "Target factory cannot be null");
            return this;
        }

        @Override
        public SecretKeyStage withStringToParse(String stringToParse) {
            this.stringToParse = Objects.requireNonNull(stringToParse, "Token string cannot be null");
            return this;
        }

        @Override
        public JwtLibraryStage withSecret(String secretKey) {
            this.secretKey = Objects.requireNonNull(secretKey, "Secret key cannot be null");
            return this;
        }

        @Override
        public JsonParserStage withLibrary(IJwtLibrary library) {
            this.library = Objects.requireNonNull(library, "JWT library cannot be null");
            return this;
        }

        @Override
        public FinalStage withJsonParser(IJsonParser parser) {
            this.jsonParser = Objects.requireNonNull(parser, "JSON parser cannot be null");
            return this;
        }

        @Override
        public JwtParserRequirement build() {
            return new JwtParserRequirement(stringToParse, target, secretKey, library, jsonParser);
        }
    }
}
