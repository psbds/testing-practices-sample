Feature: Cart - Add Item
  As a customer
  I want to add items in my cart
  So that I can place orders later

  Background:
    Given i'm a valid user

  Scenario: Add product to cart when the the product exists and is available in stock
    Given the product is valid and available in stock
    When I add a valid product to the cart
    Then the response should be sucessful
   # Then the cart should contain product '1001' with quantity '3' and price '29.99'
