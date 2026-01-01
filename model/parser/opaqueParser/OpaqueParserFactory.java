package com.authservice.domain.model.parser.opaqueParser;

import com.authservice.domain.model.aggregates.parser.interfaces.IParserFactory;
import com.authservice.domain.ports.IJsonParser;
import com.authservice.domain.ports.ITokenRepository;

/**
 * Factory for creating {@link OpaqueParser} instances.
 * <p>
 * This factory implements {@link IParserFactory} and is
 * responsible for constructing parsers that handle opaque tokens.
 * It enforces a capability-guarded handshake with
 * {@link OpaqueParserRequirement},
 * ensuring that only authorized requirements can provide the necessary
 * dependencies.
 * </p>
 *
 * <p>
 * Dependencies extracted from the requirement include:
 * <ul>
 * <li>The token ID to parse</li>
 * <li>An {@link ITokenRepository} for fetching the token's JSON payload</li>
 * <li>An {@link IJsonParser} for validating the JSON payload</li>
 * </ul>
 * </p>
 *
 * <p>
 * Instances of this factory should be created via {@link #createFactory()}.
 * </p>
 */
public class OpaqueParserFactory implements IParserFactory<OpaqueParser, OpaqueParserRequirement> {

    /** Private constructor to enforce use of the factory creation method. */
    private OpaqueParserFactory() {
    }

    /**
     * Creates an {@link OpaqueParser} from the given requirement.
     * <p>
     * Performs a capability-guarded handshake to retrieve the token ID,
     * repository, and JSON parser from the requirement. Only the requirement
     * that was bound to this factory can provide these values.
     * </p>
     *
     * @param requirement the {@link OpaqueParserRequirement} containing
     *                    all necessary dependencies
     * @return a fully configured {@link OpaqueParser}
     */
    @Override
    public OpaqueParser create(OpaqueParserRequirement requirement) {
        String tokenId = requirement.getInput(this);
        ITokenRepository repo = requirement.getRepository(this);
        IJsonParser jsonParser = requirement.getJsonParser(this);

        return new OpaqueParser(tokenId, repo, jsonParser);
    }

    /**
     * Factory creation method.
     *
     * @return a new {@link OpaqueParserFactory} instance
     */
    public static OpaqueParserFactory createFactory() {
        return new OpaqueParserFactory();
    }
}
