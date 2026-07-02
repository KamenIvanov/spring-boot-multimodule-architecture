package com.pe.multimodule.dao.api.outbox;

import com.pe.multimodule.dao.api.CrudDao;
import com.pe.multimodule.dao.api.Paging;
import com.pe.multimodule.domain.outbox.OutboxEvent;

import java.util.List;
import java.util.UUID;

public interface OutboxEventDao extends CrudDao<UUID, OutboxEvent> {

    List<OutboxEvent> load(int bucketId, Paging paging);
}
