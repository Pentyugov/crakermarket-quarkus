package com.netkreker.controllers;


import com.netkreker.client.CategoryClient;
import com.netkreker.client.OrderClient;
import com.netkreker.client.UserClient;
import com.netkreker.model.Category;
import com.netkreker.model.order.Order;
import com.netkreker.model.user.Role;
import com.netkreker.model.user.User;
import com.netkreker.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.quarkus.oidc.runtime.OidcJsonWebTokenProducer;
import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/")
//@NoCache
public class MainController {

    private Boolean isLogged = false;
    private Boolean isAdmin = false;

    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    Template index;

    @Inject
    @RestClient
    UserClient userClient;

    @Inject
    SecurityIdentity identity;

    @Inject
    @RestClient
    OrderClient orderClient;

    @ResourcePath(value = "user/personal.html")
    Template personalPage;

    @ResourcePath("shop/categoryPage.html")
    Template categoryPage;

    @Inject
    @RestClient
    CategoryClient categoryClient;

    @GET
    public TemplateInstance mainPage() {
        isLogged = !identity.getPrincipal().getName().isEmpty();
        isAdmin = identity.hasRole(Role.ROLE_ADMIN);
        String username = null;
        String roleMessage = null;
        Set roles = null;
        if(identity.getPrincipal().getName() != null) {
            username = identity.getPrincipal().getName();
        }
        if(!identity.getRoles().isEmpty()) {
            roleMessage = "You are in role: ";
            roles = identity.getRoles().stream().filter(a -> a.equals(Role.ROLE_ADMIN) || a.equals(Role.ROLE_SIMPLE_USER)).collect(Collectors.toSet());
        }
        return index.data("username", username, "roleMessage", roleMessage, "role", roles)
                .data("isAdmin", isAdmin)
                .data("isLogged", isLogged);
    }

    @Path("/personal")
    @GET
    @Authenticated
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance personal() {
        isLogged = !identity.getPrincipal().getName().isEmpty();
        isAdmin = identity.hasRole(Role.ROLE_ADMIN);
        String token = "Bearer " + jsonWebToken.getRawToken();
        User user = userClient.registerUser(token, UserService.createUserFromToken(jsonWebToken, identity));
        return personalPage.data("username", user.getUsername())
                           .data("isAdmin", isAdmin)
                           .data("isLogged", isLogged);
    }

    @Path("/login")
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Authenticated
    public TemplateInstance login() {
        isLogged = !identity.getPrincipal().getName().isEmpty();
        isAdmin = identity.hasRole(Role.ROLE_ADMIN);
        String username = null;
        String roleMessage = null;
        Set roles = null;
        String token = "Bearer " + jsonWebToken.getRawToken();
        userClient.registerUser(token, UserService.createUserFromToken(jsonWebToken, identity));
        if(identity.getPrincipal().getName() != null) {
            username = identity.getPrincipal().getName();
        }
        if(!identity.getRoles().isEmpty()) {
            roleMessage = "You are in role: ";
            roles = identity.getRoles().stream().filter(a -> a.equals(Role.ROLE_ADMIN) || a.equals(Role.ROLE_SIMPLE_USER)).collect(Collectors.toSet());
        }
        return index.data("username", username, "roleMessage", roleMessage, "role", roles)
                .data("isAdmin", isAdmin)
                .data("isLogged", isLogged);
    }

//    @Path("/logout")
//    @GET
//    @Produces(MediaType.TEXT_HTML)
//    public Response logout() throws URISyntaxException {
//        return Response.status(Response.Status.MOVED_PERMANENTLY).location(new URI("http://localhost:8080")).build();
//    }


}
