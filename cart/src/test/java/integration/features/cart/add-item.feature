Feature: Cart - Add Item
  As a customer
  I want to add items in my cart
  So that I can place orders later

  Background:
    Given i'm the user with id '1234'

  Scenario: Add product to cart when the the product exists and is available in stock
    Given the product id '1001' exists in the product catalog with '10' items in stock and price '29.99'
    When I add product '1001' to cart with quantity '3' and price '29.99'