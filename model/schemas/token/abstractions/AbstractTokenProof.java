
package com.authservice.domain.model.schemas.token.abstractions;

import java.util.Objects;

import com.authservice.domain.model.schemas.interfaces.ISchemaProof;

/**
 * Base class for all token-related schema proofs.
 * <p>
 * This abstraction ensures that every token schema (Access, Refresh, MFA)
 * can be wrapped in a protective "Proof" that certifies its structural
 * integrity after factory processing.
 * </p>
 *
 * @param <S> The specific type of token schema being certified.
 */
public abstract class AbstractTokenProof<S extends AbstractTokenSchema> implements ISchemaProof {

    private final S schema;

    /**
     * Initializes the proof with the validated schema.
     * Protected access ensures only specialized proof subclasses can
     * instantiate this via their internal static factories.
     *
     * @param schema the validated token schema
     */
    protected AbstractTokenProof(S schema) {
        this.schema = Objects.requireNonNull(schema, "Schema cannot be null in a Proof");
    }

    /**
     * Retrieves the sealed schema.
     * <p>
     * Aggregate roots use this method during reconstitution to extract
     * the raw data needed for domain object construction.
     * </p>
     *
     * @return the certified token schema
     */
    @Override
    public S getSchema() {
        return schema;
    }
}