package io.github.kahar.twodb.shop.second;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT_AMOUNT")
public class ProductAmount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    private Long amount;

    protected ProductAmount() {
    }

    public ProductAmount(Long productId, Long amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public void increaseByOne() {
        amount++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
