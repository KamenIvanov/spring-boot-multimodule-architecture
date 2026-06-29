package com.pe.multimodule.domain.asserter.products;

import com.pe.multimodule.domain.asserter.AbstractDeepEqualsAsserter;
import com.pe.multimodule.domain.product.Product;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductAsserter extends AbstractDeepEqualsAsserter<Product> {

    public static final ProductAsserter instance = new ProductAsserter();

    private ProductAsserter() {
        // Singleton
    }

    @Override
    public void assertDeepEquals(Product expected, Product actual) {
        super.assertDeepEquals(expected, actual);

        Assertions.assertEquals(expected.getSku(), actual.getSku());// Сравнението ще върне 0, ако стойностите са математически еднакви
        assertEquals(0, expected.getPrice().compareTo(actual.getPrice()));
    }
}
