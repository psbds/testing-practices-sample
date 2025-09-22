Feature: Cart - Add Item
  As a customer
  I want to add items in my cart
  So that I can place orders later

  Background:
    Given i'm the user with id '1234'

  Scenario: Add a valid product to cart when the the product exists is available in stock
    Given the stock API will return the following product information for id '1001':
    """
    {
      "name": "T-Shirt",
      "price": 29.99,
      "stock": 10,
      "metadata": [
        {
          "name": "size",
          "values": ["S", "M", "L"]
        }
      ]
    }
    """
    When I send a POST request to the item endpoint with body:
    """
    {
      "product_id": "1001",
      "quantity": 3,
      "price": 29.99,
      "metadata": [
        {
          "key": "size",
          "value": "M"
        }
      ]
    }
    """
    Then the response status code should be '200' 
    And the following items should be present in the Items table in the database:
    | user_id | product_id | quantity | price |
    | 1234    | 1001       | 3        | 29.99 |
    And the following metadata should be present in the ItemMetadata table in the database:
    | [key] | [value] |
    |  size | M       |
    And the response should contain the same item id inserted in the database
