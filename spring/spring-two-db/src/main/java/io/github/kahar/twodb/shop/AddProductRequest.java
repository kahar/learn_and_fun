package io.github.kahar.twodb.shop;

import java.math.BigDecimal;

record AddProductRequest(String name, BigDecimal price, String description) {
}
