package com.netkreker.client;

import com.netkreker.model.order.Order;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.List;

@Singleton
@Path("/order")
@RegisterRestClient(configKey="OrderClient")
@NoCache
public interface OrderClient {

    @Path("/create")
    @POST
    Response create(Order order, @QueryParam("userId") String userId);

    @Path("/findByUserId")
    @GET
    List<Order> getOrdersByUserId(@HeaderParam(HttpHeaders.AUTHORIZATION) String Authorization, @QueryParam("userId") String userId);
}
