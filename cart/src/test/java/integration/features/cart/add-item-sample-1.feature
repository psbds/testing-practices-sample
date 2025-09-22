Feature: Cart - Add Item
  As a customer
  I want to add items in my cart
  So that I can place orders later

  Background:
    Given i'm a valid user

  Scenario: Add a valid product to cart when the the product exists is available in stock
    Given the product is valid and available in stock
    When I add a valid product to the cart
    Then the response should be successful
    And the item should have been added to the cart
    And the response should contain the item id

