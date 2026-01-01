package com.authservice.domain.model.parser.interfaces;

import com.authservice.domain.model.valueobjects.JsonString;

/**
 * Represents a generic proof that some content has been successfully parsed and
 * validated.
 * <p>
 * This interface defines a <strong>contractual guarantee</strong> that the
 * underlying content has been validated and is safe to consume.
 * </p>
 * 
 * <p>
 * Implementations act as a <em>trust boundary</em> marker. Any component
 * receiving
 * an {@code IParsedProof} may assume that no further validation or parsing
 * checks are required.
 * </p>
 */
public interface IParsedProof {

    /**
     * Returns the validated content.
     * 
     * @return a {@link JsonString} representing a validated and trusted content
     *         payload
     */
    JsonString getJsonString();
}
