package com.netkreker.crackermarket.controller;

import com.netkreker.crackermarket.client.UserClient;
import com.netkreker.crackermarket.model.order.Order;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
@Transactional
@NoCache
public class OrderController {

    @Inject
    @RestClient
    UserClient userClient;

    @Path("/order/create")
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

    @Path("/order/findByUserId")
    @GET
    public List<Order> getOrdersByUserId(@QueryParam("userId") String userId) {
        return Order.findByUser(userId);
    }
}
