package com.authservice.domain.ports;

import java.util.Optional;

/**
 * Repository interface for accessing token data.
 * <p>
 * This interface defines the contract for retrieving tokens from
 * a storage mechanism (e.g., database, cache). Implementations
 * may vary depending on the persistence layer.
 * </p>
 */
public interface ITokenRepository {

    /**
     * Finds the token associated with the given token ID.
     * <p>
     * The returned token is typically a raw string representation of
     * an access token, opaque token, or JWT, depending on the system.
     *
     * @param tokenId the unique identifier of the token
     * @return the token string associated with the given ID, or {@code null}
     *         if no token is found
     */
    Optional<String> findByTokenId(String tokenId);
}
