package com.netkreker.client;

import com.netkreker.model.user.User;
import org.apache.http.HttpHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Singleton
@Path("/user")
@RegisterRestClient(configKey="UserClient")
public interface UserClient {

    @Path("/id/{id}")
    @GET
    User findCurrentUser(@PathParam("id") UUID id);

    @Path("/save")
    @POST
    User registerUser(@HeaderParam(HttpHeaders.AUTHORIZATION) String Authorization, User user);

    @Path("/getAll")
    @GET
    List<User> getAllUsers(@HeaderParam(HttpHeaders.AUTHORIZATION) String Authorization);

    @Path("/status")
    @GET
    List<User> getActiveUsers(@HeaderParam(HttpHeaders.AUTHORIZATION) String Authorization, @QueryParam("status") String status);

    @Path("/params")
    @GET
    List<User> getUsersByParams(@HeaderParam(HttpHeaders.AUTHORIZATION) String Authorization, @QueryParam("status") String status, @QueryParam("role") String role);

    @Path("/name/{username}")
    @GET
    User findUserByUsername(@HeaderParam(HttpHeaders.AUTHORIZATION) String Authorization, @PathParam("username") String username);

    @Path("/info")
    @GET
    Response getUserInfo(@QueryParam("userId") String userId);

}
