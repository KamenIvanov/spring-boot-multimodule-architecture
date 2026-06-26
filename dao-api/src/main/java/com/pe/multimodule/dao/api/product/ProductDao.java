package com.pe.multimodule.dao.api.product;

import com.pe.multimodule.dao.api.SearchableDao;
import com.pe.multimodule.domain.product.Product;

import java.util.UUID;

public interface ProductDao extends SearchableDao<UUID, Product, ProductSearchQuery> {
}
