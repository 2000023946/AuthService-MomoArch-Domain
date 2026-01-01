package com.authservice.domain.model.parser.jwtParser.buildStages;

/**
 * Second stage in the staged builder for creating a JWT parser.
 * <p>
 * This stage supplies the secret key that will be used to verify
 * JWT signatures. Only after the secret key is provided can the
 * library be supplied and the parser built.
 * </p>
 *
 * <p>
 * The secret key is treated as sensitive information and must
 * only be used within the parser lifecycle.
 * </p>
 */
public interface SecretKeyStage {

    /**
     * Supplies the secret key used for verifying JWT signatures.
     *
     * @param secretKey the secret key as a string
     * @return the next stage of the builder which requires the JWT library
     */
    JwtLibraryStage withSecret(String secretKey);
}
