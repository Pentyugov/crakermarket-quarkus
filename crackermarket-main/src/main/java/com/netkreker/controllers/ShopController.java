package com.netkreker.controllers;

import com.netkreker.client.*;
import com.netkreker.model.Category;
import com.netkreker.model.order.Order;
import com.netkreker.model.order.OrderDetails;
import com.netkreker.model.user.Role;
import com.netkreker.model.user.User;
import com.netkreker.service.TokenUtil;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/shop")
//@NoCache
public class ShopController {
    private Boolean isLogged = false;
    private Boolean isAdmin = false;

    @ResourcePath("shop/shop.html")
    Template shopPage;
    @ResourcePath("admin/adminPage.html")
    Template adminPage;
    @ResourcePath("shop/categoryPage.html")
    Template categoryPage;
    @ResourcePath("shop/cart.html")
    Template cartPage;

    @Inject
    @RestClient
    CategoryClient categoryClient;

    @Inject
    @RestClient
    OrderClient orderClient;

    @Inject
    @RestClient
    UserClient userClient;

    @Inject
    @RestClient
    ProductClient productClient;

    @Inject
    @RestClient
    CartClient cartClient;

    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    SecurityIdentity identity;

    @GET()
    @Produces(MediaType.TEXT_HTML)
    @Authenticated
    public TemplateInstance shopPage() {
        isLogged = !identity.getPrincipal().getName().isEmpty();
        isAdmin = identity.hasRole(Role.ROLE_ADMIN);
        return shopPage.data("message", null)
                .data("isAdmin", isAdmin)
                .data("isLogged", isLogged);
    }

    @Path("/order/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(Order order) {
        return orderClient.create(order, jsonWebToken.getSubject());
    }

    @Path("/order/new")
    @POST
    public Response newOrder(OrderDetails orderDetails) {
        Response response = orderClient.newOrder(orderDetails);
        return response;
    }

    @Path("/admin")
    @GET
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed(Role.ROLE_ADMIN)
    public TemplateInstance adminsPage() {
        List<User> users = userClient.getAllUsers(TokenUtil.getToken(jsonWebToken));
        return adminPage.data("users", users)
                .data("isAdmin", isAdmin)
                .data("isLogged", isLogged);
    }

    @Path("/order/user")
    @GET
    @Authenticated
    public List<Order> getOrdersByUserId(@QueryParam("userId") String userId) {
        return orderClient.getOrdersByUserId(TokenUtil.getToken(jsonWebToken), userId);
    }


    @Path("/category/{category}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getCategoryPage(@PathParam("category") String category) {
        isLogged = !identity.getPrincipal().getName().isEmpty();
        isAdmin = identity.hasRole(Role.ROLE_ADMIN);

        Category cat = categoryClient.getCategoryByName(category);
        return categoryPage.data("categoryId", cat.getId())
                           .data("isAdmin", isAdmin)
                           .data("isLogged", isLogged)
                           .data("CategoryName", cat.getName());
    }

    @Path("/products")
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Authenticated
    public Response getProductsFromCategory(@QueryParam("category") String category) {
        return productClient.getProductsFromCategory(category);
    }

    @Path("/cart/addProduct")
    @GET
    public Response addProductToCart(@QueryParam("product") String product) {
        return cartClient.addProductToCart(product, jsonWebToken.getSubject());
    }

    @Path("/cart/info")
    @GET
    public Response getCartInfo() {
        return cartClient.getCartInfo(jsonWebToken.getSubject());
    }

    @Path("/cart")
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Authenticated
    public TemplateInstance getCartPage(){
        isLogged = !identity.getPrincipal().getName().isEmpty();
        isAdmin = identity.hasRole(Role.ROLE_ADMIN);
        return cartPage.data("userId", jsonWebToken.getSubject())
                           .data("isAdmin", isAdmin)
                           .data("isLogged", isLogged);
    }

}
