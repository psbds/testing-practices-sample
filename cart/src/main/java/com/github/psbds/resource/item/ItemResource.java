package com.github.psbds.resource.item;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestResponse;

import com.github.psbds.resource.BaseAuthenticatedResource;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemRequest;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemResponse;
import com.github.psbds.resource.item.model.getitems.ItemResourceGetItemsResponse;
import com.github.psbds.service.item.CreateItemService;
import com.github.psbds.service.item.ListItemsService;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class ItemResource extends BaseAuthenticatedResource {

    private CreateItemService createItemService;
    private ListItemsService listItemsService;

    @Inject
    public ItemResource(CreateItemService createItemService, ListItemsService listItemsService, JsonWebToken jwt) {
        super(jwt);
        this.createItemService = createItemService;
        this.listItemsService = listItemsService;
    }

    @POST
    @Transactional
    public RestResponse<ItemResourcePostItemResponse> createItem(@Valid ItemResourcePostItemRequest request) {
        var userId = getUserId();
        var response = createItemService.create(userId, request);

        return RestResponse.ok(response);
    }

    @GET
    public RestResponse<ItemResourceGetItemsResponse> getItems() {
        var userId = getUserId();
        var response = listItemsService.listByUserId(userId);

        return RestResponse.ok(response);
    }
}
