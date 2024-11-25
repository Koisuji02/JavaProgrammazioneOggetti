Delivero
========

The program implements the back-end of a food delivery system.

All classes are contained in the package `delivero`. The main class is
`Delivery`. The class TestApp `TestApp` in the package `example`
contains an example of use of the Delivery class. Exceptions are
generated through the class `DeliveryException`.

It is possible to access the [Java API Documentation](https://oop.polito.it/api).

R1 Restaurants
--------------

The restaurants that use the Delivero service are labelled with a
category (e.g., traditional, fast food, ethnic, Chinese, etc.). The
method `addCategory(String category)` enables to add one category to
the list of categories managed by the service. The method throws an
exception if the category is already available.

The method `getCategories()` returns the list of defined categories.

The method `addRestaurant(String name, String category)` registers a
new restaurant (whose name is unique) to the service with a related
category. The method throws an exception if the category is not defined.

The method `getRestaurantsForCategory(String category)` returns an
ordered list by name of the restaurants of a given category. It returns
an empty list in there are no restaurants in the selected category or
the category does not exist.

R2 Dishes
---------

The method `addDish(String name, String restaurantName, float price)`
adds a dish to the list of dishes of a restaurant. Every dish has a
given price. The method throws an exception if the dish name already
exists in the same restaurant.

The method `getDishesByPrice(float minprice, float maxprice)` returns
a map associating the name of each restaurant with the list of the name
of the dishes whose price is in the provided range of price (limits
included). If the restaurant has no dishes in the range, it does not
have to appear in the map.

The method `getDishesForRestaurant(String restaurantName)` returns the
ordered list of dishes sold by a given restaurant. If the restaurant
does not exist or does not sell dishes the method must return an empty
list.

The method `getDishesByCategory(String category)` returns the list of
all dish names sold by all restaurants belonging to the given category.
If the category is not defined or there are no dishes in the category
the method must return and empty list.

R3 Orders
---------

The method `addOrder(String dishName[], int quantity[], String customerName, String restaurantName, int deliveryTime, int deliveryDistance)` creates a delivery order. Each order may contain
more than one product with the related quantity. The delivery time is
indicated with a number in the range `8` to `23`. The delivery distance
is expressed in kilometers. The parameters of the method can be assumed
correct. The method must record the requests arrival order.

The method `addOrderd()` return a progressive order number, the first
order has number 1.

The method `scheduleDelivery(int deliveryTime, int maxDistance, int maxOrders)` enables to schedule the deliveries. 
Delivero has several
bellboys, some using bicycle, others motorbikes. To optimize the
delivery based on the transportation this method returns the order
numbers of the first `maxOrders` (following the orders arrival time)
scheduled to be delivered at `deliveryTime` whose `deliveryDistance` is
lower or equal that `maxDistance`. Once returned by the method the
orders must be marked as assigned so that they will not be considered if
the method is called again. The method returns an empty list if there
are no orders (not yet assigned) that meet the requirements.

The method `getPendingOrders()` returns the number of orders that still need to be assigned.

R4 Ratings
----------

The method `setRatingForRestaurant(String restaurantName, int rating)`
records a rating (a number between 0 and 5, boundaries included) of a
restaurant. Ratings outside the valid range are discarded.

The method `restaurantsAverageRating()` returns the ordered list of
restaurant. The restaurant must be ordered by decreasing average rating.
The average rating of a restaurant is the sum of all rating divided by
the number of ratings. If a restaurant received no rating, it does not
appear in the list.

R5 Stats
--------

The method `ordersPerCategory()` returns a map associating each
category to the number of orders placed to restaurants in that category.
Also categories that have not received orders must be included in the
result.

The method `bestRestaurant` returns the name of the restaurant that
has received the higher average rating.
