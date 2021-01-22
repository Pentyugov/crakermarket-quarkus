package com.netkreker.client;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Singleton
@Path("/products")
@RegisterRestClient(configKey="ProductClient")
@NoCache
public interface ProductClient {

    @Path("/")
    @GET
    Response getProductsFromCategory(@QueryParam("category") String category);
}
