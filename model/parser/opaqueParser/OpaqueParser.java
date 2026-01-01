package com.authservice.domain.model.parser.opaqueParser;

import com.authservice.domain.model.aggregates.parser.abstractions.AbstractParser;
import com.authservice.domain.model.valueobjects.JsonString;
import com.authservice.domain.ports.IJsonParser;
import com.authservice.domain.ports.ITokenRepository;

/**
 * Parser for opaque tokens that retrieves a JSON payload from a repository
 * and validates it.
 * <p>
 * An {@code OpaqueParser} converts a raw opaque token string into a trusted
 * {@link OpaqueParsedProof}. The parser ensures that:
 * <ul>
 * <li>The token exists in the {@link ITokenRepository}</li>
 * <li>The retrieved value is valid JSON, verified via {@link IJsonParser}</li>
 * </ul>
 *
 * <p>
 * This class extends {@link AbstractParser}, so the raw token string is
 * immutable and accessible via {@link #getStringToParse()}.
 * </p>
 *
 * <p>
 * Instances are immutable and thread-safe; all state is provided at
 * construction.
 * </p>
 */
public class OpaqueParser extends AbstractParser<OpaqueParsedProof> {

    /** Repository used to retrieve the token's JSON payload. */
    private final ITokenRepository repository;

    /** JSON parser used to validate the retrieved payload. */
    private final IJsonParser jsonParser;

    /**
     * Constructs an {@code OpaqueParser}.
     *
     * @param stringToParse the opaque token string to parse
     * @param repository    the repository containing token payloads
     * @param jsonParser    the JSON parser for validation
     */
    OpaqueParser(String stringToParse, ITokenRepository repository, IJsonParser jsonParser) {
        super(stringToParse);
        this.repository = repository;
        this.jsonParser = jsonParser;
    }

    /**
     * Parses the opaque token string into a validated JSON proof.
     * <p>
     * Steps performed by this method:
     * <ol>
     * <li>Retrieve the token's JSON payload from {@link ITokenRepository}.
     * Throws an exception if not found.</li>
     * <li>Validate that the retrieved payload is valid JSON using
     * {@link IJsonParser}.</li>
     * <li>Return an {@link OpaqueParsedProof} containing the validated JSON.</li>
     * </ol>
     *
     * @return an {@link OpaqueParsedProof} containing the validated JSON payload
     * @throws RuntimeException if the token does not exist or is revoked
     */
    @Override
    public OpaqueParsedProof parse() {
        String jsonFromDb = repository.findByTokenId(getStringToParse())
                .orElseThrow(() -> new RuntimeException("Opaque token not found or revoked"));

        JsonString validatedJson = JsonString.fromString(jsonFromDb, jsonParser);

        return new OpaqueParsedProof(validatedJson);
    }
}
