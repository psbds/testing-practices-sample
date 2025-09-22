package com.github.psbds.backends.stock;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.github.psbds.backends.stock.model.getproduct.StockAPIGetProductResponse;

import io.quarkus.oidc.client.filter.OidcClientFilter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "stock-api")
@OidcClientFilter("default-oidc-filter")
public interface StockAPIClient {

    @GET
    @Path("/product/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public StockAPIGetProductResponse getProduct(@PathParam("id") Long id);

}
