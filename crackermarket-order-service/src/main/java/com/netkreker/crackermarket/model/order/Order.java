package com.netkreker.crackermarket.model.order;

import com.netkreker.crackermarket.model.core.BaseEntity;
import com.netkreker.crackermarket.model.user.User;
import io.quarkus.panache.common.Parameters;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order extends BaseEntity {

    private User user;
    private String number;
    private LocalDateTime date;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public static Order create(Order order) {
        order.setId(UUID.randomUUID().toString());
        order.setDate(LocalDateTime.now());
        order.setNumber(order.getId().replaceAll("\\D+",""));
        order.persist();
        return Order.findById(order.getId());
    }

    public static Order findById(String id) {
        return find("id", id).firstResult();
    }

    public static Order findByNumber(String number) {
        return find("number", number).firstResult();
    }

    public static Order findByName(String name) {
        return find("name", name).firstResult();
    }

    public static List<Order> findByUser(String userId) {
        return find("user._id", userId).list();
    }

    public static List<Order> findByNameByUser(String name, String userId) {
        return find("name = :name and user._id = :userId",
                Parameters.with("name", name).and("userId", userId)).list();
    }
}
