package com.authservice.domain.model.parser.jwtParser;

import com.authservice.domain.model.aggregates.parser.interfaces.IParserFactory;
import com.authservice.domain.ports.IJsonParser;

/**
 * Factory class for creating {@link JwtParser} instances.
 * <p>
 * A {@code JwtParserFactory} serves as the authorized consumer
 * of {@link JwtParserRequirement} objects. It enforces a
 * capability-based construction pattern, ensuring that only
 * requirements bound to this factory can be used to create parsers.
 * </p>
 *
 * <p>
 * This factory encapsulates all knowledge required to instantiate
 * a {@link JwtParser} from a validated requirement and provides
 * a single entry point for parser creation.
 * </p>
 *
 * <p>
 * The class uses a private constructor and a static factory
 * method to control instantiation.
 * </p>
 */
public class JwtParserFactory implements IParserFactory<JwtParser, JwtParserRequirement> {

    /**
     * Private constructor to enforce controlled creation via
     * {@link #createFactory()}.
     */
    private JwtParserFactory() {
    }

    /**
     * Creates a {@link JwtParser} from the given {@link JwtParserRequirement}.
     * <p>
     * This method performs the <strong>capability check</strong> by
     * passing itself to the requirement. Only a requirement bound
     * to this factory will succeed; otherwise, an exception is thrown.
     * </p>
     *
     * @param requirement the capability-guarded JWT parser requirement
     * @return a new {@link JwtParser} instance initialized with the
     *         input string from the requirement
     * @throws IllegalArgumentException if the requirement is bound to
     *                                  a different factory
     */
    @Override
    public JwtParser create(JwtParserRequirement requirement) {
        String stringToParse = requirement.getInput(this);
        String secretKey = requirement.getInput(this);
        IJwtLibrary jwtLibrary = requirement.getJwtLibrary(this);
        IJsonParser jsonParser = requirement.getJsonParser(this);
        return new JwtParser(stringToParse, secretKey, jwtLibrary, jsonParser);
    }

    /**
     * Creates a new instance of {@link JwtParserFactory}.
     * <p>
     * This static factory method ensures controlled instantiation
     * and enforces any future lifecycle or singleton policies.
     * </p>
     *
     * @return a new {@link JwtParserFactory} instance
     */
    public static JwtParserFactory createFactory() {
        return new JwtParserFactory();
    }
}
