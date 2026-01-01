package com.authservice.domain.model.schemas.abstractions;

import com.authservice.domain.model.schemas.interfaces.ISchema;
import com.authservice.domain.model.schemas.interfaces.ISchemaProof;

/**
 * Base implementation of {@link ISchemaProof} that seals a validated schema.
 * <p>
 * This abstraction represents a <strong>domain certificate of
 * validity</strong>.
 * Once a schema is wrapped by this proof, it is guaranteed to have passed all
 * structural, semantic, and key-level validation required for aggregate
 * reconstitution.
 * </p>
 *
 * <p>
 * Schema proofs mark the <em>final transition point</em> between validation
 * and domain object construction. Downstream consumers may rely on the
 * presence and correctness of the schema without re-validating it.
 * </p>
 *
 * @param <S> the concrete {@link ISchema} type carried by this proof
 */
public abstract class AbstractSchemaProof<S extends ISchema> implements ISchemaProof {

    private final S schema;

    /**
     * Constructs a schema proof with a validated schema.
     *
     * @param schema the validated, immutable schema representation
     * @throws IllegalArgumentException if the schema is {@code null}
     */
    protected AbstractSchemaProof(S schema) {
        if (schema == null) {
            throw new IllegalArgumentException(
                    "Schema proof cannot be created with a null schema.");
        }
        this.schema = schema;
    }

    /**
     * Returns the validated schema sealed by this proof.
     * <p>
     * The returned schema is guaranteed to satisfy all invariants required
     * for safe domain aggregate reconstitution.
     * </p>
     *
     * @return the validated schema instance
     */
    @Override
    public final S getSchema() {
        return schema;
    }
}
