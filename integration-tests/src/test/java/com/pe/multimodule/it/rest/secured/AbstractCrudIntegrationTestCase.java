package com.pe.multimodule.it.rest.secured;

import com.pe.multimodule.domain.asserter.DeepEqualsAsserter;
import com.pe.multimodule.dto.AbstractDto;
import com.pe.multimodule.dto.AbstractEntityDto;
import com.pe.multimodule.it.env.assured.client.AbstractCrudRestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class AbstractCrudIntegrationTestCase<NewEntityVo extends AbstractDto, EntityVo extends AbstractEntityDto> extends AbstractSecuredIntegrationTestCase {

    @Test
    void testCreate() {
        final var savedVo = getCrudClient().create(createEntityDto());
        EntityVo actual = getCrudClient().loadById(savedVo.getId());
        assertNotNull(actual);
        getAsserter().assertDeepEquals(savedVo, actual);
    }

    @Test
    void testCreateInvalidEntity() {
        final var errors = getCrudClient().createBadRequest(createInvalidEntityDto());
        Assertions.assertEquals(1, errors.size());
        Assertions.assertNotNull(errors.getFirst());
    }

    @Test
    void testCreateNullRequester() {
        clearCredentials();
        final var errors = getCrudClient().createUnauthorized(createEntityDto());
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("User not authenticated!", errors.getFirst());
    }

    @Test
    void testUpdate() {
        final var expected = getCrudClient().create(createEntityDto());
        //
        updateEntityVo(expected);
        final var updatedEntityVo = getCrudClient().update(expected);
        //
        getAsserter().assertDeepEquals(expected, updatedEntityVo);
    }

    @Test
    void testUpdateNonExistentEntity() {
        final var entity = getCrudClient().create(createEntityDto());
        entity.setId(null);
        final var errors = getCrudClient().updateNotFound(entity);
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("No such entity.", errors.getFirst());
    }

    @Test
    void testUpdateNonExistentEntityWithId() {
        final var entity = getCrudClient().create(createEntityDto());
        entity.setId(UUID.randomUUID());
        final var errors = getCrudClient().updateNotFound(entity);
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("No such entity.", errors.getFirst());
    }

    @Test
    void testUpdateUnauthorized() {
        final var expected = getCrudClient().create(createEntityDto());
        setRequesterId(ACCOUNT_ID_2);
        final var errors = getCrudClient().updateUnauthorized(expected);
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Client is not authorized for this operation.", errors.getFirst());
    }

    @Test
    void testUpdateNullRequester() {
        final var entity = getCrudClient().create(createEntityDto());
        clearCredentials();
        final var errors = getCrudClient().updateUnauthorized(entity);
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("User not authenticated!", errors.getFirst());
    }

    @Test
    void testLoad() {
        final var expected = getCrudClient().create(createEntityDto());
        final var updatedEntityVo = getCrudClient().loadById(expected.getId());
        getAsserter().assertDeepEquals(expected, updatedEntityVo);
    }

    @Test
    void testLoadNonExistent() {
        final var errors = getCrudClient().loadByIdNotFound(UUID.randomUUID());
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("No such entity.", errors.getFirst());
    }

    @Test
    void testLoadNullRequesterId() {
        final var expected = getCrudClient().create(createEntityDto());
        clearCredentials();
        final var errors = getCrudClient().loadByIdUnauthorized(expected.getId());
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("User not authenticated!", errors.getFirst());
    }

    @Test
    void testDelete() {
        final var expected = getCrudClient().create(createEntityDto());
        getCrudClient().delete(expected.getId());
        //
        final var errors = getCrudClient().loadByIdNotFound(expected.getId());
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("No such entity.", errors.getFirst());
    }

    @Test
    void testDeleteUnauthorized() {
        final var expected = getCrudClient().create(createEntityDto());
        setRequesterId(ACCOUNT_ID_2);
        final var errors = getCrudClient().deleteUnauthorized(expected.getId());
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Client is not authorized for this operation.", errors.getFirst());
    }

    @Test
    void testDeleteNullRequesterId() {
        final var expected = getCrudClient().create(createEntityDto());
        clearCredentials();
        final var errors = getCrudClient().deleteUnauthorized(expected.getId());
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("User not authenticated!", errors.getFirst());
    }

    protected abstract AbstractCrudRestClient<NewEntityVo, EntityVo> getCrudClient();

    protected abstract NewEntityVo createEntityDto();

    protected abstract NewEntityVo createInvalidEntityDto();

    protected abstract void updateEntityVo(EntityVo entityVo);

    protected abstract DeepEqualsAsserter<EntityVo> getAsserter();
}