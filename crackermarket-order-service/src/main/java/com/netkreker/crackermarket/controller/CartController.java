package com.netkreker.crackermarket.controller;


import com.netkreker.crackermarket.client.UserClient;
import com.netkreker.crackermarket.model.cart.Cart;
import com.netkreker.crackermarket.model.core.Status;
import com.netkreker.crackermarket.model.product.Product;
import com.netkreker.crackermarket.model.user.User;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/cart")
//@NoCache
@Transactional
public class CartController {

    @Inject
    @RestClient
    UserClient userClient;

    @Path("/addProduct")
    @GET
    public Response addProductToCart(@QueryParam("product") String productId, @QueryParam("user") String userId) {
        Cart cart = null;
        if(Cart.findByUserId(userId) != null) {
            cart = Cart.findByUserId(userId);
            cart.addProduct(Product.findByProductId(productId));
            cart.update();
        } else {
            User user = userClient.findById(userId);
            cart = new Cart();
            cart.setId(UUID.randomUUID().toString());
            cart.setStatus(Status.ACTIVE);
            cart.setUser(user);
            cart.addProduct(Product.findByProductId(productId));
            cart.persist();
        }
        return Response.ok(cart).build();
    }

    @Path("/info")
    @GET
    public Response getCartInfo(@QueryParam("user") String user) {
        return Response.ok(Cart.findByUserId(user)).build();
    }
}
