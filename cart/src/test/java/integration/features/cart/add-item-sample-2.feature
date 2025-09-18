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
