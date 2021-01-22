package com.netkreker.controllers;

import com.netkreker.client.UserClient;
import com.netkreker.model.user.Role;
import com.netkreker.model.user.User;
import com.netkreker.service.TokenUtil;
import com.netkreker.service.UserService;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.util.List;

@Path("/user")
@NoCache
public class UserController {

    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    @RestClient
    UserClient userClient;

    @Inject
    SecurityIdentity identity;

    @Path("/getAllUsers")
    @GET
    @RolesAllowed(Role.ROLE_ADMIN)
    public List<User> getAllUsers() {
        return userClient.getAllUsers(TokenUtil.getToken(jsonWebToken));
    }

    @Path("/status")
    @GET
    public List<User> getAllActiveUsers(@QueryParam("status") String status) {
        return userClient.getActiveUsers(TokenUtil.getToken(jsonWebToken), status);
    }

    @Path("/params")
    @GET
    public List<User> getUsersByParams(@QueryParam("status") String status, @QueryParam("role") String role) {
        return userClient.getUsersByParams(TokenUtil.getToken(jsonWebToken), status, role);
    }

    @Path("/name/{username}")
    @GET
    @RolesAllowed(Role.ROLE_ADMIN)
    public User getUserByUsername(@PathParam("username") String username) {
        return userClient.findUserByUsername(TokenUtil.getToken(jsonWebToken), username);
    }

    @Path("/current")
    @GET
    public User getCurrentUser() {
        return UserService.createUserFromToken(jsonWebToken, identity);
    }
}
