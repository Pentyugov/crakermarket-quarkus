package com.netkreker.crackermarket.controller;


import com.netkreker.crackermarket.model.product.Product;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/products")
//@NoCache
@Transactional
public class ProductController {


    @Path("/")
    @GET
    public Response getProductsFromCategory(@QueryParam("category") String category) {
        return Response.ok(Product.findByCategory(category)).build();
    }
}
