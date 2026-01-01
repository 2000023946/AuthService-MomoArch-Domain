package com.authservice.domain.model.parser.jwtParser;

/**
 * Abstraction over a JSON Web Token (JWT) library.
 * <p>
 * This interface decouples the domain layer from specific JWT library
 * implementations, allowing different underlying libraries or
 * algorithms to be swapped without affecting domain logic.
 * </p>
 *
 * <p>
 * Implementations of this interface are responsible for verifying
 * the integrity and authenticity of JWTs and extracting their
 * payload as a raw JSON string.
 * </p>
 */
public interface IJwtLibrary {

    /**
     * Verifies the signature of a JWT and extracts the raw JSON payload.
     * <p>
     * The provided {@code token} must be a properly structured JWT,
     * and the {@code secret} must match the signing key or secret
     * used to create the token. Implementations must validate:
     * <ul>
     * <li>Token structure (header, payload, signature)</li>
     * <li>Cryptographic signature</li>
     * <li>Optional claims (e.g., expiration, issuer) if required</li>
     * </ul>
     * Only if verification succeeds should the JSON payload be returned.
     * </p>
     *
     * @param token  the JWT string to verify
     * @param secret the signing key or secret used for verification
     * @return the raw JSON payload contained in the JWT
     * @throws SecurityException if verification fails due to invalid
     *                           signature, malformed token, or other
     *                           security checks
     */
    String verifyAndExtractJson(String token, String secret);
}
