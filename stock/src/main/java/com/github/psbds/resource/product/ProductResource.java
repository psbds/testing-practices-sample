package com.github.psbds.resource.product;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestResponse;

import com.github.psbds.resource.BaseAuthenticatedResource;
import com.github.psbds.resource.product.model.getproduct.ProductResourceGetProductResponse;
import com.github.psbds.resource.product.model.postproduct.ProductResourcePostProductRequest;
import com.github.psbds.resource.product.model.postproduct.ProductResourcePostProductResponse;
import com.github.psbds.service.product.GetProductService;
import com.github.psbds.service.product.CreateProductService;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class ProductResource extends BaseAuthenticatedResource {

    private GetProductService getProductService;
    private CreateProductService postProductService;

    @Inject
    public ProductResource(GetProductService getProductService, CreateProductService postProductService, JsonWebToken jwt) {
        super(jwt);
        this.getProductService = getProductService;
        this.postProductService = postProductService;
    }

    @GET
    @Path("/{id}")
    public RestResponse<ProductResourceGetProductResponse> getItem(@PathParam("id") Long id) {
        var response = getProductService.get(id);

        return RestResponse.ok(response);
    }

    @POST
    public RestResponse<ProductResourcePostProductResponse> createItem(@Valid ProductResourcePostProductRequest request) {
        var response = postProductService.create(request);

        return RestResponse.status(RestResponse.Status.CREATED, response);
    }
}
