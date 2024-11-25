Discounts
=========

The program simulates the management of discounts in a large supermarket. All the classes are in the package `it.polito.oop.discounts`. 
The main class is `Discounts`. 
Class `TestApp` in package `example` contains simple test. 
All exceptions thrown belong to class `DiscountsException`.

The JDK documentation is available at URL
[https://oop.polito.it/api/index.html](https://oop.polito.it/api/index.html).

R1: Cards
---------

To get a discount a customer must own a card. A card can be obtained
with the method `issueCard()` that accepts the name of the client and
returns a progressive number starting from 1 (included).

To know the name of the owner of the card the method `cardHolder()` is
used, it accepts the number of the card and returns the name of the
client.

To get the total amount of cards issued we can use the method `nOfCards()`.

R2: Products
------------

To register a product type in the supermarket we can use method
`addProduct(String, String, double)`. The first argument is the
category code (e.g. Drinks), the second is the product code and the
latter is the price. The method raises and exception if the product code
is already defined.

The method `getPrice(String)` returns the product for a given product
code; if the code is undefined it raises and exception.

The method `getAveragePrice(String)` returns the rounded average
price, of all products in the given category; if the category is
undefined (i.e. it has no products) it raises and exception.

R3: Discounts
-------------

Discounts can be defined at the category level and apply to all products
belonging to the category. Initially all categories have a discount
equal to 0%.

The method `setDiscount(String, int)` assigns a discount percentage
(integer value between 0 and 50, limits included) to the category passed
as first argument; it raises and exception if the category is undefined
or the discount is not in the valid range).

The method `getDiscount(String)` returns the discount assigned to a given category.

R4: Purchases
-------------

Purchases can be performed with or without a card. In the former case
the method `addPurchase(int, String..)` is used, which has the card
code as first argument. In the latter case the method to be used is
`addPurchase(String..)`.

The argument(s) `String...` represents the purchased items. Every item
is a string containing the product code and the number of units,
separated by character `":"` without any additional spacing.

Both methods return a progressive purchase code (starting from 1).

The above two methods record the total amount of the purchase that is
equal to the sum of all item prices; the price of an item is given by
the product price multiplied by the number of units. When a card is used
for the purchase the method applies a discount based on the product
categories: for each item the discount is the one for the category of
the product.

Both methods raise ans exception if when an undefined
product code is found.

The method `getAmount(int)`, given the purchase code, returns the
total of the purchase after applying the possible discount.

The method `getDiscount(int)` provides the discount applied for a
given purchase code.

The method `getNofUnits(int)` returns the number of units present in a
given purchase.

R5: Statistics
--------------

The method `productIdsPerNofUnits()` returns the list of product codes
per number of units purchased. Only products with at least one purchased
unit are considered. The key of the map (number of items) are sorted in
ascending order and the product code lists are sorted alphabetically.

The method `totalPurchasePerCard()` returns the total amount purchased
per card. Only cards that performed at least one purchase are
considered. The keys (card numbers) are sorted in ascending order.

The method `totalPurchaseWithoutCard()` returns the total amount of
purchases performed without cards.

The method `totalDiscountPerCard()` returns the total discount per
card. Only cards that performed at least one purchase with discount
greater than zero are considered. Keys are sorted in ascending order.
