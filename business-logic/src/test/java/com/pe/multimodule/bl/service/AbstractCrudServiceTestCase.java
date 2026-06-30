package com.pe.multimodule.bl.service;

import com.pe.multimodule.api.rest.secured.CrudRestService;
import com.pe.multimodule.dao.api.CrudDao;
import com.pe.multimodule.domain.AbstractDomain;
import com.pe.multimodule.domain.exceptions.AuthorizationException;
import com.pe.multimodule.domain.exceptions.NotFoundException;
import com.pe.multimodule.dto.AbstractDto;
import com.pe.multimodule.dto.AbstractEntityDto;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public abstract class AbstractCrudServiceTestCase<
        Domain extends AbstractDomain<UUID>,
        NewEntityDto extends AbstractDto,
        Dto extends AbstractEntityDto,
        Dao extends CrudDao<UUID, Domain>,
        Service extends CrudRestService<NewEntityDto, Dto>
        > extends AbstractServiceTestCase {

    @Test
    void create_WhenRequesterIdIsNull_ShouldThrowAuthorizationException() {
        final NewEntityDto dto = createValidNewEntityDto();
        assertThrows(AuthorizationException.class, () -> getService().create(dto, null));
        verify(getMockDao(), never()).save(any());
    }

    @Test
    void create_WhenRequesterIdIsPresent_ShouldPersistAndReturnDto() {
        final NewEntityDto dto = createValidNewEntityDto();
        final UUID entityId = UUID.randomUUID();
        final AtomicReference<Domain> savedEntity = new AtomicReference<>();

        when(getMockDao().save(any())).thenAnswer(invocation -> {
            final Domain entity = invocation.getArgument(0);
            entity.setId(entityId);
            entity.setCreatedById(requesterId);
            savedEntity.set(entity);
            return entity;
        });

        final Dto result = getService().create(dto, requesterId);

        assertNotNull(savedEntity.get());
        assertEquals(entityId, result.getId());
        assertEquals(savedEntity.get().getId(), result.getId());
        verify(getMockDao(), times(1)).save(any());
    }

    @Test
    void update_WhenRequesterIdIsNull_ShouldThrowAuthorizationException() {
        final Dto dto = createValidDto();
        assertThrows(AuthorizationException.class, () -> getService().update(dto, null));
        verify(getMockDao(), never()).save(any());
    }

    @Test
    void update_WhenRequesterIsOwner_ShouldPersistUpdatedEntity() {
        final Domain existingEntity = createPersistedEntity(requesterId);
        final Dto dto = createValidDto();
        dto.setId(existingEntity.getId());

        when(getMockDao().loadById(existingEntity.getId())).thenReturn(existingEntity);

        final Dto result = getService().update(dto, requesterId);

        assertEquals(existingEntity.getId(), result.getId());
        verify(getMockDao(), times(1)).loadById(existingEntity.getId());
    }

    @Test
    void update_WhenRequesterIsNotOwner_ShouldThrowAuthorizationException() {
        final Domain existingEntity = createPersistedEntity(alternativeRequesterId);
        final Dto dto = createValidDto();
        dto.setId(existingEntity.getId());

        when(getMockDao().loadById(existingEntity.getId())).thenReturn(existingEntity);
        assertThrows(AuthorizationException.class, () -> getService().update(dto, requesterId));
    }

    @Test
    void update_WhenEntityIdIsNull_ShouldThrowNotFoundException() {
        final Dto dto = createValidDto();
        dto.setId(null);
        assertThrows(NotFoundException.class, () -> getService().update(dto, requesterId));
        verify(getMockDao(), never()).save(any());
    }

    @Test
    void loadById_WhenEntityDoesNotExist_ShouldThrowNotFoundException() {
        final UUID randomId = UUID.randomUUID();
        when(getMockDao().loadById(randomId)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> getService().loadById(randomId, requesterId));
    }

    @Test
    void loadById_WhenRequesterIsOwner_ShouldReturnDto() {
        final Domain existingEntity = createPersistedEntity(requesterId);

        when(getMockDao().loadById(existingEntity.getId())).thenReturn(existingEntity);

        final Dto result = getService().loadById(existingEntity.getId(), requesterId);

        assertEquals(existingEntity.getId(), result.getId());
        verify(getMockDao(), times(1)).loadById(existingEntity.getId());
    }

    @Test
    void loadById_WhenRequesterIsNotOwner_ShouldThrowAuthorizationException() {
        final Domain existingEntity = createPersistedEntity(alternativeRequesterId);
        when(getMockDao().loadById(existingEntity.getId())).thenReturn(existingEntity);
        assertThrows(AuthorizationException.class, () -> getService().loadById(existingEntity.getId(), requesterId));
    }

    @Test
    void delete_WhenRequesterIsNull_ShouldThrowAuthorizationException() {
        assertThrows(AuthorizationException.class, () -> getService().delete(UUID.randomUUID(), null));
        verify(getMockDao(), never()).delete(any());
    }

    @Test
    void delete_WhenEntityDoesNotExist_ShouldDoNothing() {
        final UUID randomId = UUID.randomUUID();
        when(getMockDao().loadById(randomId)).thenReturn(null);

        getService().delete(randomId, requesterId);

        verify(getMockDao(), times(1)).loadById(randomId);
        verify(getMockDao(), never()).delete(any());
    }

    @Test
    void delete_WhenRequesterIsOwner_ShouldDeleteEntity() {
        final Domain existingEntity = createPersistedEntity(requesterId);

        when(getMockDao().loadById(existingEntity.getId())).thenReturn(existingEntity);

        getService().delete(existingEntity.getId(), requesterId);

        verify(getMockDao(), times(1)).loadById(existingEntity.getId());
        verify(getMockDao(), times(1)).delete(existingEntity);
    }

    @Test
    void delete_WhenRequesterIsNotOwner_ShouldThrowAuthorizationException() {
        final Domain existingEntity = createPersistedEntity(alternativeRequesterId);

        when(getMockDao().loadById(existingEntity.getId())).thenReturn(existingEntity);

        assertThrows(AuthorizationException.class, () -> getService().delete(existingEntity.getId(), requesterId));
        verify(getMockDao(), never()).delete(any());
    }

    protected abstract Service getService();

    protected abstract Dao getMockDao();

    protected abstract NewEntityDto createValidNewEntityDto();

    protected abstract Dto createValidDto();

    private Domain createPersistedEntity(UUID ownerId) {
        final UUID entityId = UUID.randomUUID();
        final AtomicReference<Domain> savedEntity = new AtomicReference<>();

        when(getMockDao().save(any())).thenAnswer(invocation -> {
            final Domain entity = invocation.getArgument(0);
            entity.setId(entityId);
            entity.setCreatedById(ownerId);
            savedEntity.set(entity);
            return entity;
        });

        getService().create(createValidNewEntityDto(), requesterId);

        final Domain entity = savedEntity.get();
        assertNotNull(entity);
        assertEquals(entityId, entity.getId());
        assertEquals(ownerId, entity.getCreatedById());
        return entity;
    }
}
