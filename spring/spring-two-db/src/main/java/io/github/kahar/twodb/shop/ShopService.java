package io.github.kahar.twodb.shop;

import io.github.kahar.twodb.shop.first.Product;
import io.github.kahar.twodb.shop.first.ProductRepository;
import io.github.kahar.twodb.shop.second.ProductAmount;
import io.github.kahar.twodb.shop.second.ProductAmountRepository;
import io.github.kahar.twodb.shop.third.Admin;
import io.github.kahar.twodb.shop.third.AdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService {

    private final ProductRepository productRepository;
    private final ProductAmountRepository productAmountRepository;

    private final AdminRepository adminRepository;

    public ShopService(ProductRepository productRepository,
                       ProductAmountRepository productAmountRepository,
                       AdminRepository adminRepository) {
        this.productRepository = productRepository;
        this.productAmountRepository = productAmountRepository;
        this.adminRepository = adminRepository;
    }

    public List<Object> getProducts() {
        List<Object> result = new ArrayList<>();
        result.addAll(productRepository.findAll());
        result.addAll(productAmountRepository.findAll());
        return result;
    }

    @Transactional
    public void addProduct(String name, BigDecimal price, String description) {
        Product savedProduct = productRepository.save(
                new Product(name, price, description));
        productAmountRepository.save(
                new ProductAmount(savedProduct.getId(), 1L));
    }

    @Transactional("secondTransactionManager")
    public void increaseAmountByOne(Long productId) {
        productAmountRepository.findByProductId(productId)
                .ifPresent(ProductAmount::increaseByOne);
    }

    public List<Admin> getAdmin() {
        return adminRepository.findAll();

    }

    public void addAdmin(String firstName, String lastName) {
        adminRepository.save(Admin.builder().firstName(firstName).lastName(lastName).build());
    }
}
