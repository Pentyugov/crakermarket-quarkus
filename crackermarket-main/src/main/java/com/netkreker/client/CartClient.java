package com.netkreker.client;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Singleton
@Path("/cart")
@RegisterRestClient(configKey="CartClient")
//@NoCache
public interface CartClient {

    @Path("/addProduct")
    @GET
    Response addProductToCart(@QueryParam("product") String productId, @QueryParam("user") String userId);

    @Path("/info")
    @GET
    Response getCartInfo(@QueryParam("user") String userId);
}
