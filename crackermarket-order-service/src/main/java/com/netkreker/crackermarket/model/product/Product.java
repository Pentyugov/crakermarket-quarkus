package com.netkreker.crackermarket.model.product;

import com.netkreker.crackermarket.model.category.Category;
import com.netkreker.crackermarket.model.core.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

public class Product extends BaseEntity {
    private Category category;
    private String description;
    private int amount;
    private String imageLink;
    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public static Product findByProductId(String id) {
        return find("_id", id).firstResult();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static List<Product> findByCategory(String categoryId) {
        return find("category._id", categoryId).list();
    }
}
