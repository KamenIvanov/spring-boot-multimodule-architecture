package com.pe.multimodule.bl.service;

import com.pe.multimodule.domain.AbstractDomain;
import com.pe.multimodule.domain.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author kamen on 1.06.22 г.
 */
public class AbstractService {

    protected static final String MISSING_ENTITY = "No such entity.";
    protected static final String UNAUTHORIZED   = "Client is not authorized for this operation.";


    protected static final Logger logger = LoggerFactory.getLogger(AbstractService.class);

    protected <Domain extends AbstractDomain<UUID>> Domain loadOrThrowNotFound(Supplier<Domain> domainSupplier) {
        final Domain entity = domainSupplier.get();
        if (entity == null) {
            throw new NotFoundException(MISSING_ENTITY);
        }
        return entity;
    }
}
