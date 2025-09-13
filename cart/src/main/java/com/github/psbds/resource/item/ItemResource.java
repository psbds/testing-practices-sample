package com.github.psbds.resource.item;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestResponse;

import com.github.psbds.resource.BaseAuthenticatedResource;
import com.github.psbds.resource.item.model.PostItemRequest;
import com.github.psbds.resource.item.model.PostItemResponse;
import com.github.psbds.service.item.CreateItemService;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
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

    @Inject
    public ItemResource(CreateItemService createItemService) {
        this.createItemService = createItemService;
    }

    @POST
    @Transactional
    public RestResponse<PostItemResponse> createItem(@Valid PostItemRequest request) {
        var userId = getUserId();
        var response = createItemService.create(userId, request);

        return RestResponse.ok(response);
    }
}
