Feature: Cart - Get Items
  As a customer
  I want to get items in my cart
  So that I can review them before placing an order

  Scenario: Get items in the cart when the cart is empty
    Given i'm the user with id '111222333'
    When I get all items from my cart
    Then the response status code should be '200'
    And the response should contain an empty list of items

  Scenario: Get items in the cart when the cart has one item
    Given i'm the user with id '333444555'
    Given I have the following item in my cart:
      | product_id | quantity | price | metadata_key | metadata_value |
      | 1001       | 2        | 29.99 | size         | M              |
    When I get all items from my cart
    Then the response status code should be '200'
    And the response should contain 1 item
    And the response should contain an item with product_id '1001', quantity '2', and price '29.99'
    And the item should contain metadata with key 'size' and value 'M'

  Scenario: Get items in the cart when the cart has multiple items
    Given i'm the user with id '555666777'
    Given I have the following items in my cart:
      | product_id | quantity | price | metadata_key | metadata_value |
      | 1001       | 2        | 29.99 | size         | M              |
      | 2002       | 1        | 49.99 | color        | blue           |
      | 3003       | 3        | 19.99 | brand        | Nike           |
    When I get all items from my cart
    Then the response status code should be '200'
    And the response should contain 3 items
    And the response should contain an item with product_id '1001', quantity '2', and price '29.99'
    And the response should contain an item with product_id '2002', quantity '1', and price '49.99'
    And the response should contain an item with product_id '3003', quantity '3', and price '19.99'

  Scenario: Get items when user is not authenticated
    Given I am not authenticated
    When I try to get all items from my cart without authentication
    Then the response status code should be '401'

  Scenario: Get items for a specific user only returns their items
    Given i'm the user with id '888777999'
    Given I have the following item in my cart:
      | product_id | quantity | price | metadata_key | metadata_value |
      | 1001       | 2        | 29.99 | size         | M              |
    And another user 'other-user-123' has the following item in their cart:
      | product_id | quantity | price | metadata_key | metadata_value |
      | 2002       | 1        | 49.99 | color        | red            |
    When I get all items from my cart
    Then the response status code should be '200'
    And the response should contain 1 item
    And the response should contain an item with product_id '1001', quantity '2', and price '29.99'
    And the response should not contain an item with product_id '2002'

