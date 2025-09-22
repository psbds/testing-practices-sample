package com.github.psbds.backends.stock;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.github.psbds.backends.stock.model.getproduct.StockAPIGetProductResponse;

import jakarta.annotation.Nullable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class StockAPIClientWrapper {

    private StockAPIClient client;

    @Inject
    public StockAPIClientWrapper(@RestClient StockAPIClient client) {
        this.client = client;
    }

    public @Nullable StockAPIGetProductResponse getProduct(Long id) {
        try {
            return client.getProduct(id);
        } catch (WebApplicationException ex) {
            if (ex.getResponse().getStatus() == Status.NOT_FOUND.getStatusCode()) {
                return null;
            }
            throw ex;
        }
    }

}
