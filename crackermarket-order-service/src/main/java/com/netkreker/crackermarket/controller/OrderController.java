package com.netkreker.crackermarket.controller;

import com.netkreker.crackermarket.client.UserClient;
import com.netkreker.crackermarket.model.cart.Cart;
import com.netkreker.crackermarket.model.core.Status;
import com.netkreker.crackermarket.model.order.Order;
import com.netkreker.crackermarket.model.order.OrderDetails;
import com.netkreker.crackermarket.model.product.Product;
import com.netkreker.crackermarket.model.user.User;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/order")
@Transactional
public class OrderController {

    @Inject
    @RestClient
    UserClient userClient;

    @Path("/create")
    @POST
    public Response create(Order order, @QueryParam("userId") String userId) {

        if (Order.findByNameByUser(order.getName(), userId).isEmpty()) {
            order.setUser(userClient.findById(userId));
            Order.create(order);

            return Response.ok(order).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();

        }
    }

    @Path("/findByUserId")
    @GET
    public List<Order> getOrdersByUserId(@QueryParam("userId") String userId) {
        return Order.findByUser(userId);
    }

    @Path("/new")
    @POST
    public Response newOrder(OrderDetails orderDetails) {
        User user = userClient.findById(orderDetails.getUserId());
        Cart cart = Cart.findActiveById(orderDetails.getCartId());
        for (Product p : cart.getProducts()) {
            p.setAmount(p.getAmount() - 1);
            p.update();
        }
        cart.setStatus(Status.ORDERED);
        cart.update();
        Order order = Order.create(user, cart, orderDetails.getAddress(), orderDetails.getPaymentMethod());
        return Response.ok(order).build();
    }
}
