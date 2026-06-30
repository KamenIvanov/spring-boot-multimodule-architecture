package com.pe.multimodule.it.env.assured.client;

import com.pe.multimodule.dto.AbstractDto;
import com.pe.multimodule.dto.AbstractEntityDto;
import com.pe.multimodule.it.HeadersProvider;
import com.pe.multimodule.it.env.assured.AbstractRestClient;

import java.util.List;
import java.util.UUID;

public abstract class AbstractCrudRestClient<NewEntityVo extends AbstractDto, EntityVo extends AbstractEntityDto> extends AbstractRestClient {

    public AbstractCrudRestClient(HeadersProvider headerUtils, String endpointUrl) {
        super(headerUtils, endpointUrl);
    }

    public EntityVo create(NewEntityVo entityVo) {
        final var response = postForSuccess("", getHttpHeaders(), entityVo);
        return getResponseDto(response);
    }

    public List<String> createBadRequest(NewEntityVo entityVo) {
        return postForBadRequest("", getHttpHeaders(), entityVo);
    }

    public List<String> createConflict(NewEntityVo entityVo) {
        return postForConflict("", getHttpHeaders(), entityVo);
    }

    public List<String> createUnauthorized(NewEntityVo entityVo) {
        return postForUnauthorized("", getHttpHeaders(), entityVo);
    }

    public EntityVo update(EntityVo entityVo) {
        final var response = putForSuccess("", getHttpHeaders(), entityVo);
        return getResponseDto(response);
    }

    public List<String> updateNotFound(EntityVo entityVo) {
        return putForNotFound("", getHttpHeaders(), entityVo);
    }

    public List<String> updateUnauthorized(EntityVo entityVo) {
        return putForUnauthorized("", getHttpHeaders(), entityVo);
    }

    public EntityVo loadById(UUID id) {
        final var response = getForSuccess("/" + id, getHttpHeaders());
        return getResponseDto(response);
    }

    public List<String> loadByIdNotFound(UUID id) {
        return getForNotFound("/" + id, getHttpHeaders());
    }

    public List<String> loadByIdUnauthorized(UUID id) {
        return getForUnauthorized("/" + id, getHttpHeaders());
    }

    public void delete(UUID id) {
        deleteForSuccess("/" + id, getHttpHeaders());
    }

    public List<String> deleteUnauthorized(UUID id) {
        return deleteForUnauthorized("/" + id, getHttpHeaders());
    }
}
