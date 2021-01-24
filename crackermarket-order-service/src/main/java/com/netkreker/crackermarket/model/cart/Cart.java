package com.netkreker.crackermarket.model.cart;

import com.netkreker.crackermarket.model.core.BaseEntity;
import com.netkreker.crackermarket.model.core.Status;
import com.netkreker.crackermarket.model.product.Product;
import com.netkreker.crackermarket.model.user.User;
import io.quarkus.panache.common.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cart extends BaseEntity {
    private List<Product> products = new ArrayList<>();
    private User user;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public static Cart findByUserId(String userId) {
        return find("user._id = :userId and status =:status",
                Parameters.with("userId", userId).and("status", Status.ACTIVE)).firstResult();
    }

    public static Cart findActiveById(String cartId) {
        return find("_id = :cartId and status = :status",
                Parameters.with("cartId", cartId).and("status", Status.ACTIVE)).firstResult();
    }
}
