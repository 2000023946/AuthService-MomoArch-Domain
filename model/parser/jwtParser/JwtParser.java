package com.authservice.domain.model.parser.jwtParser;

import com.authservice.domain.model.aggregates.parser.abstractions.AbstractParser;
import com.authservice.domain.model.valueobjects.JsonString;
import com.authservice.domain.ports.IJsonParser;

/**
 * Parser implementation for JSON Web Tokens (JWTs).
 * <p>
 * A {@code JwtParser} is responsible for converting a raw JWT string
 * into a trusted {@link JwtParserProof}, performing full validation
 * including signature verification and payload extraction.
 * </p>
 *
 * <p>
 * This class delegates cryptographic operations to a provided
 * {@link IJwtLibrary} implementation and uses a secret key
 * for signature verification.
 * </p>
 *
 * <p>
 * Instances are immutable and thread-safe, assuming the underlying
 * {@link IJwtLibrary} is also thread-safe.
 * </p>
 */
public class JwtParser extends AbstractParser<JwtParsedProof> {

    /** Secret key used to verify JWT signatures. */
    private final String secretKey;

    /** JWT library used for verification and JSON extraction. */
    private final IJwtLibrary library;

    private final IJsonParser jsonParser;

    /**
     * Constructs a new JWT parser.
     *
     * @param stringToParse the raw JWT string (untrusted input)
     * @param secretKey     secret key for signature verification
     * @param jsonParser    the json parser
     * @param library       JWT library implementation for verification
     */
    JwtParser(String stringToParse, String secretKey, IJwtLibrary library, IJsonParser jsonParser) {
        super(stringToParse);
        this.secretKey = secretKey;
        this.library = library;
        this.jsonParser = jsonParser;
    }

    /**
     * Parses and validates the provided JWT string.
     * <p>
     * This method performs the following steps:
     * <ol>
     * <li>Delegates signature verification and payload extraction
     * to the provided {@link IJwtLibrary}</li>
     * <li>Constructs a {@link JwtParserProof} from the verified JSON payload</li>
     * </ol>
     * </p>
     *
     * <p>
     * Callers must not assume the input is valid until this method
     * returns successfully. Any failure during verification or parsing
     * should throw an exception from the underlying {@link IJwtLibrary}.
     * </p>
     * 
     * @return JwtParsed proof
     * @throws SecurityException if the JWT is invalid or verification fails
     * 
     */
    public JwtParsedProof parse() {
        // Delegate cryptographic verification to the library
        String verifiedJson = library.verifyAndExtractJson(getStringToParse(), secretKey);
        JsonString validatedJsonString = JsonString.fromString(verifiedJson, jsonParser);
        return new JwtParsedProof(validatedJsonString);
    }
}
