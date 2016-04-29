Scenario: Empty Cart
!-- The total amount of empty cart is exactly 0.
Given a cart
Then the total amount is 0

Scenario: Adding and Removing Items
!-- Adding the same item is updating the quantity of the stored item.
Given a cart
When I add an item (code:A00001, price:1230, quantity:2)
Then the total amount is 2460
When I add an item (code:A00002, price:340, quantity:2)
Then the total amount is 3140
When I remove the item (code:A00001)
Then the total amount is 680

Scenario: Adding the Same Item
!-- Adding the same item is updating the quantity of the stored item.
Given a cart
When I add an item (code:A00001, price:1230, quantity:2)
Then the total amount is 2460
When I add an item (code:A00002, price:340, quantity:1)
Then the total amount is 2800
When I add an item (code:A00001, price:1230, quantity:1)
Then the total amount is 1570
