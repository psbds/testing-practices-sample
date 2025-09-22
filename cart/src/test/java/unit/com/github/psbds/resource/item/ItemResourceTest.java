package unit.com.github.psbds.resource.item;

import com.github.psbds.resource.item.ItemResource;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemRequest;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemResponse;
import com.github.psbds.resource.item.model.getitems.ItemResourceGetItemsItemResponse;
import com.github.psbds.resource.item.model.getitems.ItemResourceGetItemsResponse;
import com.github.psbds.service.item.CreateItemService;
import com.github.psbds.service.item.ListItemsService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;

import io.quarkus.test.component.QuarkusComponentTest;
import io.quarkus.test.InjectMock;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@QuarkusComponentTest
class ItemResourceTest {

    private static final String TEST_USER_ID = "test-user-123";
    private static final Long TEST_ITEM_ID = 12345L;

    @Inject
    ItemResource itemResource;

    @InjectMock
    CreateItemService createItemService;

    @InjectMock
    ListItemsService listItemsService;

    @InjectMock
    JsonWebToken jwt;

    @Test
    void createItem_when_validRequestIsProvided_should_returnCreatedItemResponse() {
        // Arrange
        ItemResourcePostItemRequest request = new ItemResourcePostItemRequest();
        ItemResourcePostItemResponse expectedResponse = new ItemResourcePostItemResponse(TEST_ITEM_ID);
        
        when(jwt.getSubject()).thenReturn(TEST_USER_ID);
        when(createItemService.create(TEST_USER_ID, request)).thenReturn(expectedResponse);

        // Act
        RestResponse<ItemResourcePostItemResponse> result = itemResource.createItem(request);

        // Assert
        assertNotNull(result, "Response should not be null");
        assertEquals(200, result.getStatus(), 
            "Response status should be 200 OK");
        assertNotNull(result.getEntity(), "Response entity should not be null");
        assertEquals(TEST_ITEM_ID, result.getEntity().getId(), 
            "Response should contain the expected item ID");
    }

    @Test
    void getItems_when_userHasItems_should_returnItemsList() {
        // Arrange
        ItemResourceGetItemsItemResponse item1 = new ItemResourceGetItemsItemResponse(1L, 101L, 2, new BigDecimal(29.99), Arrays.asList());
        ItemResourceGetItemsItemResponse item2 = new ItemResourceGetItemsItemResponse(2L, 102L, 1, new BigDecimal(49.99), Arrays.asList());
        List<ItemResourceGetItemsItemResponse> items = Arrays.asList(item1, item2);
        ItemResourceGetItemsResponse expectedResponse = new ItemResourceGetItemsResponse(items);
        
        when(jwt.getSubject()).thenReturn(TEST_USER_ID);
        when(listItemsService.listByUserId(TEST_USER_ID)).thenReturn(expectedResponse);

        // Act
        RestResponse<ItemResourceGetItemsResponse> result = itemResource.getItems();

        // Assert
        assertNotNull(result, "Response should not be null");
        assertEquals(200, result.getStatus(), 
            "Response status should be 200 OK");
        assertNotNull(result.getEntity(), "Response entity should not be null");
        assertEquals(2, result.getEntity().getItems().size(), 
            "Response should contain the expected number of items");
    }

    @Test
    void getItems_when_userHasNoItems_should_returnEmptyList() {
        // Arrange
        ItemResourceGetItemsResponse expectedResponse = new ItemResourceGetItemsResponse(Arrays.asList());
        
        when(jwt.getSubject()).thenReturn(TEST_USER_ID);
        when(listItemsService.listByUserId(TEST_USER_ID)).thenReturn(expectedResponse);

        // Act
        RestResponse<ItemResourceGetItemsResponse> result = itemResource.getItems();

        // Assert
        assertNotNull(result, "Response should not be null");
        assertEquals(200, result.getStatus(), 
            "Response status should be 200 OK");
        assertNotNull(result.getEntity(), "Response entity should not be null");
        assertEquals(0, result.getEntity().getItems().size(), 
            "Response should contain an empty list");
    }

    @Test
    void createItem_should_haveCorrectSecurityAnnotations() throws NoSuchMethodException {
        // Arrange
        Method createItemMethod = ItemResource.class.getMethod("createItem", ItemResourcePostItemRequest.class);

        // Act & Assert
        assertTrue(createItemMethod.isAnnotationPresent(POST.class), 
            "createItem method should have @POST annotation");
        assertTrue(createItemMethod.isAnnotationPresent(RolesAllowed.class), 
            "createItem method should have @RolesAllowed annotation");
        
        RolesAllowed rolesAllowed = createItemMethod.getAnnotation(RolesAllowed.class);
        String[] allowedRoles = rolesAllowed.value();
        
        assertEquals(2, allowedRoles.length, 
            "createItem should allow exactly 2 roles");
        assertTrue(Arrays.asList(allowedRoles).contains("user"), 
            "createItem should allow 'user' role");
        assertTrue(Arrays.asList(allowedRoles).contains("admin"), 
            "createItem should allow 'admin' role");
    }

    @Test
    void getItems_should_haveCorrectSecurityAnnotations() throws NoSuchMethodException {
        // Arrange
        Method getItemsMethod = ItemResource.class.getMethod("getItems");

        // Act & Assert
        assertTrue(getItemsMethod.isAnnotationPresent(GET.class), 
            "getItems method should have @GET annotation");
        assertTrue(getItemsMethod.isAnnotationPresent(RolesAllowed.class), 
            "getItems method should have @RolesAllowed annotation");
        
        RolesAllowed rolesAllowed = getItemsMethod.getAnnotation(RolesAllowed.class);
        String[] allowedRoles = rolesAllowed.value();
        
        assertEquals(2, allowedRoles.length, 
            "getItems should allow exactly 2 roles");
        assertTrue(Arrays.asList(allowedRoles).contains("user"), 
            "getItems should allow 'user' role");
        assertTrue(Arrays.asList(allowedRoles).contains("admin"), 
            "getItems should allow 'admin' role");
    }
}