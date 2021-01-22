package com.netkreker.crackermarket.controller;


import com.netkreker.crackermarket.model.category.Category;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/category")
@NoCache
@Transactional
public class CategoryController {

    @Path("{category}")
    @GET
    public Category getCategoryByName(@PathParam("category") String category) {
            return Category.findByName(category);
    }

}
