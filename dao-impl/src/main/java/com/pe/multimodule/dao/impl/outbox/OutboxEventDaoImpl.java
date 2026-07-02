package com.pe.multimodule.dao.impl.outbox;

import com.pe.multimodule.dao.api.Paging;
import com.pe.multimodule.dao.api.outbox.OutboxEventDao;
import com.pe.multimodule.dao.impl.CrudDaoImpl;
import com.pe.multimodule.dao.impl.transformer.outbox.OutboxEventTransformer;
import com.pe.multimodule.domain.outbox.OutboxEvent;
import jakarta.validation.Validator;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public class OutboxEventDaoImpl extends CrudDaoImpl<
        UUID,
        OutboxEvent,
        OutboxEventEntity,
        OutboxEventRepository
        > implements OutboxEventDao {

    public OutboxEventDaoImpl(OutboxEventRepository repository, Validator validator) {
        super(repository, OutboxEventTransformer.instance, validator);
    }

    @Override
    public List<OutboxEvent> load(int bucketId, Paging paging) {
        final var pageable = PageRequest.of(paging.getPage(), paging.getSize());
        final var slice = repository.loadByBucketId(bucketId, pageable);
        return OutboxEventTransformer.instance.createOutput(slice.getContent());
    }
}
