package com.netkreker.crackermarket.client;

import com.netkreker.crackermarket.model.user.User;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Singleton
@Path("/user")
@RegisterRestClient(configKey="UserClient")
//@NoCache
public interface UserClient {

    @Path("/id/{id}")
    @GET
    User findById(@PathParam("id") String id);

}
