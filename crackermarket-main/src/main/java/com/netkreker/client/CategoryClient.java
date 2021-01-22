package com.netkreker.client;

import com.netkreker.model.Category;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Singleton
@Path("/category")
@RegisterRestClient(configKey="OrderClient")
@NoCache
public interface CategoryClient {

    @Path("{category}")
    @GET
    Category getCategoryByName(@PathParam("category") String category);
}
