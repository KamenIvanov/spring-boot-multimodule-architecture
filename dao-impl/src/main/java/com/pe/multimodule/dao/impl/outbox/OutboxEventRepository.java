package com.pe.multimodule.dao.impl.outbox;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface OutboxEventRepository extends CrudRepository<OutboxEventEntity, UUID>, JpaSpecificationExecutor<OutboxEventEntity> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(name = "jakarta.persistence.lock.timeout", value = "-2") // -2 means SKIP LOCKED in Hibernate
    })
    @Query("""
                SELECT event
                FROM OutboxEventEntity event
                WHERE event.bucketId = :bucketId
                ORDER BY event.id ASC
            """)
    Slice<OutboxEventEntity> loadByBucketId(@Param("bucketId") int bucketId, Pageable pageable);
}
