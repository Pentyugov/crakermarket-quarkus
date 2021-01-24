package com.netkreker.crackermarket.model.order;

import com.netkreker.crackermarket.model.cart.Cart;
import com.netkreker.crackermarket.model.core.BaseEntity;
import com.netkreker.crackermarket.model.core.Status;
import com.netkreker.crackermarket.model.user.User;
import io.quarkus.panache.common.Parameters;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order extends BaseEntity {

    private User user;
    private String number;
    private LocalDateTime date;
    private Cart cart;
    private String paymentMethod;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

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

    public static Order create(User user, Cart cart, String address, String paymentMethod) {
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setDate(LocalDateTime.now());
        order.setNumber(order.getId().replaceAll("\\D+",""));
        order.setUser(user);
        order.setCart(cart);
        order.setAddress(address);
        order.setPaymentMethod(paymentMethod.toUpperCase());
        order.setStatus(Status.ACTIVE);
        order.persist();
        return Order.findById(order.getId());
    }

    public static Order findById(String id) {
        return find("_id", id).firstResult();
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
