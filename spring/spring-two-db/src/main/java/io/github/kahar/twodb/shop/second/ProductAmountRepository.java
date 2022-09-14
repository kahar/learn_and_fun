package io.github.kahar.twodb.shop.second;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductAmountRepository extends JpaRepository<ProductAmount, Long> {

    Optional<ProductAmount> findByProductId(Long productId);
}
