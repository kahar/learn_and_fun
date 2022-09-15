package io.github.kahar.twodb.shop;

import io.github.kahar.twodb.shop.third.Admin;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
class ShopController {

    private final ShopService shopService;

    ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping("/product")
    void addProduct(@RequestBody AddProductRequest request) {
        shopService.addProduct(request.name(), request.price(), request.description());
    }

    @GetMapping("/product")
    List<Object> getProducts() {
        return shopService.getProducts();
    }

    @PostMapping("/product/increase-amount")
    void increaseAmountByOne(@RequestBody IncreaseProductAmountRequest request) {
        shopService.increaseAmountByOne(request.productId());
    }

    @PostMapping("/admin")
    void addAdmin(@RequestBody AddAdmin request) {
        shopService.addAdmin(request.firstName(), request.lastName());
    }

    @GetMapping("/admin")
    List<Admin> getAdmin() {
        return shopService.getAdmin();
    }
}