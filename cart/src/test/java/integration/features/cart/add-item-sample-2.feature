Feature: Cart - Add Item
  As a customer
  I want to add items in my cart
  So that I can place orders later

  Background:
    Given i'm the user with id '1234'

  Scenario: Add a valid product to cart when the the product exists is available in stock
    Given the product id '1001' exists in the product catalog with '10' items in stock and price '29.99' and allows the metadata 'size' with values 'S, M, L'
    When I add product '1001' to cart with quantity '3' and price '29.99' and metadata 'size' with value 'M'
    Then the response status code should be '200' 
    And the cart should contain product '1001' with quantity '3' and price '29.99'
    And the response should contain the item id inserted in the database

  Scenario: Add a valid product to cart when the the product exists but is not available in stock
    Given the product id '9999' exists in the product catalog with '1' items in stock and price '29.99' and allows the metadata 'size' with values 'S, M, L'
    When I add product '9999' to cart with quantity '3' and price '29.99' and metadata 'size' with value 'M'
    Then the response status code should be '500' 
    And the cart should not contain product '9999'

  Scenario: Add a product to cart when the price doesn't match the current product price
    Given the product id '2002' exists in the product catalog with '10' items in stock and price '49.99' and allows the metadata 'size' with values 'S, M, L'
    When I add product '2002' to cart with quantity '2' and price '39.99' and metadata 'size' with value 'L'
    Then the response status code should be '500' 
    And the cart should not contain product '2002'

  Scenario: Add a product to cart when the user is not authenticated
    Given the product id '3003' exists in the product catalog with '10' items in stock and price '19.99' and allows the metadata 'color' with values 'red, blue, green'
    And I am not authenticated
    When I add product '3003' to cart without authentication with quantity '1' and price '19.99' and metadata 'color' with value 'red'
    Then the response status code should be '401'
    And the cart should not contain product '3003'

