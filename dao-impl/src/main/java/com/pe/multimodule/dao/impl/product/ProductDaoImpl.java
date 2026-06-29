package com.pe.multimodule.dao.impl.product;

import com.pe.multimodule.dao.api.product.ProductDao;
import com.pe.multimodule.dao.api.product.ProductSearchQuery;
import com.pe.multimodule.dao.impl.SearchableDaoImpl;
import com.pe.multimodule.dao.impl.transformer.product.ProductTransformer;
import com.pe.multimodule.domain.product.Product;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.Validator;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductDaoImpl extends SearchableDaoImpl<
        UUID,
        Product,
        ProductEntity,
        ProductRepository,
        ProductSearchQuery
        > implements ProductDao {

    public ProductDaoImpl(ProductRepository repository, Validator validator) {
        super(repository, ProductTransformer.instance, validator);
    }

    @Override
    public Product loadBySku(String sku) {
        final var product = repository.loadBySku(sku);
        return transformer.createOutput(product);
    }

    @Override
    protected Specification<ProductEntity> createSpecification(ProductSearchQuery criteria) {
        return (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if(criteria.getIds() != null && !criteria.getIds().isEmpty()){
                predicates.add(root.get("id").in(criteria.getIds()));
            }

            if(criteria.getName() != null && !criteria.getName().isBlank()){
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + criteria.getName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
