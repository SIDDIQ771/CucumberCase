Feature: DemoBlaze Test

 Scenario: Login into App
    Given User is on Launch Page
    When User Login
    Then should display Home Page
    
  Scenario Outline: Adding Items to Cart
    Given User is oh Home Page
    When User Selects the "<Category>" and "<Product>"
    Then Should Add items to the Cart
    
    Examples:
       | Category | Product |
       | Laptops | MacBook air |
       | Phones | Nexus 6  |
    
    Scenario: Delete an Item from Cart
      Given User is on the Cart Page
      When User deletes an Item
      Then the Item is Deleted
    
    Scenario: Purchase Items
      When User clicks PlaceOrder
      Then Order is Purchased
    

