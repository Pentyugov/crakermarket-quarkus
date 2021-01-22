package com.netkreker.crackermarket.controllers;


import com.netkreker.crackermarket.model.Role;
import com.netkreker.crackermarket.model.User;
import io.quarkus.security.Authenticated;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
@Transactional
@NoCache
public class UserController {


    @Path("/id/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User findById(@PathParam("id") String id) {
        return User.findById(id);
    }


    @Path("/name/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User findByUsername(@PathParam("username") String username) {
        return User.findByUsername(username);
    }

    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    public User save(User user) {

        if(User.findByUsername(user.getUsername()) != null) {
            return User.findByUsername(user.getUsername());
        } else user.persist();
        return User.findByUsername(user.getUsername());
    }

    @Path("/getAll")
    @GET
    @RolesAllowed(Role.ROLE_ADMIN)
    public List<User> getAll() {
        return User.findAll().list();
    }

    @Path("/status")
    @GET
    @RolesAllowed(Role.ROLE_ADMIN)
    public List<User> getUsersByStatus(@QueryParam("status") String status) {
        return User.findByStatus(status);
    }

    @Path("/params")
    @GET
    @RolesAllowed(Role.ROLE_ADMIN)
    public List<User> getUsersByStatus(@QueryParam("status") String status, @QueryParam("role") String role) {

        if(status.equals("all") && role.equals("all")) {
            return User.findAll().list();
        } else if (status.equals("all") && !role.equals("all")) {
            return User.findByRole(role);
        } else if (!status.equals("all") && role.equals("all")) {
            return User.findByStatus(status);
        } else {
            return User.findByParams(status, role);
        }
    }


}
