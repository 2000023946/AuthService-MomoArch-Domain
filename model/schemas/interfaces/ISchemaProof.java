package com.authservice.domain.model.schemas.interfaces;

import com.authservice.domain.model.aggregates.mapping.interfaces.IMap;

/**
 * Represents a proof that a raw {@link IMap} has been successfully validated
 * and transformed into a stable {@link ISchema}.
 * <p>
 * A schema proof serves as a <strong>domain-level validation artifact</strong>
 * that guarantees:
 * <ul>
 * <li>The original mapping contained all required keys</li>
 * <li>All structural and type constraints were enforced</li>
 * <li>The data has been normalized into an immutable field schema</li>
 * </ul>
 * </p>
 *
 * <p>
 * By exposing a schema rather than the original mapping, this interface
 * ensures that downstream consumers (such as aggregate factories) interact
 * only with <em>stable, validated, and intention-revealing</em> data.
 * </p>
 *
 * <p>
 * Schema proofs decouple validation from aggregate reconstitution and
 * provide a clear boundary between untrusted external data and the
 * domain model.
 * </p>
 */
public interface ISchemaProof {

    /**
     * Returns the validated schema produced from the original mapping.
     * <p>
     * The returned schema is guaranteed to satisfy all structural
     * requirements necessary for aggregate reconstitution.
     * </p>
     *
     * @return an immutable, validated schema
     */
    ISchema getSchema();
}
