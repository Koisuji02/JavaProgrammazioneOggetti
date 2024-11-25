# Futsal fields

(the Italian version is available in file [README_it.md](README_it.md)).

Develop a system for managing the reservations of a sport facility for playing *futsal*, a version of five-a-side football.

All classes must be in the package named `it.polito.oop.futsal`. The *facade* class through which all operations are performed is `Fields`.

The class named `TestApp` in the default package contains a synthetic test for the application.

JDK documentation is accessible from the URL <http://softeng.polito.it/courses/docs/api/index.html>.

## R1: Fields

The facility contains different football pitches (fields) with different characteristics, which can be initialized using the method `defineFields (Integer ...)`, which accepts several field descriptors.

Each descriptor is an object of the class `Features`, which includes three `Boolean` attributes:

* `indoor`: `True` whether the field is covered;
* `heating`: `True` whether the field has a heating system, by default the attribute is `False`;
* `ac`: `True` whether the field has an air conditioning system, by default the attribute is not present;

Heating and air conditioning may only be available in covered area (indoor fields). If this condition is not met, an exception `FutsalException` is thrown.

The fields are given a number starting from 1, according to the order in which they were defined.

It is possible to get the total number of fields using the method `countFields()`, and, the total number of indoor fields with `countIndoor()`.

The sports facility has an opening and a closing time which are accessible via getter and setter methods: `getOpeningTime()`, `setOpeningTime()`, `getClosingTime()`, `setClosingTime()`. The times are represented by strings with the format `"hh:mm"`, where `hh` represents the hours and `mm`, the minutes. Unless specified, assume that the closing time is by midnight.


## R2: Associates

The associates of the facility are registered using the method `newAssociate(String, String, String)`, which gets the name, surname and telephone number, and returns a unique code (`int`).

Given the unique code of an associate, it is possible to query for the name, surname and telephone using the methods `getFirst()`, `getLast()` and `getPhone()`. Whether the code does not correspond to any associate, the method throws an exception `FutsalException`.

The method `countAssociates ()` returns the number of registered associates.


## R3: Field Reservation

The different fields can be reserved in slot of exactly one hour, starting from the opening time up to the closing time.

Reserving a field is performed using the method `bookField()`, which gets the field number, the unique code of an associate, and the starting time.

The start time of a booking must be aligned to the opening time (i.e., the difference in minutes between the booking and the opening must be a multiple of 60).

> For example, if the opening time is `"14:30"`, it is only possible to book starting at half hours. Thus, `"18:30"` is valid, `"20:15"` is not .

If the field number or the associate code or the booking time is not valid, an exception `FutsalException` is thrown.

Checking if a field is already booked at a given time is possible using the method `isBooked()`, which gets the field number and the time, and returns a Boolean value.

## R4: Availability and occupation of fields

It is possible to get what is the occupation level of a specific field (i.e., the number of reservations) using the method `getOccupation()`, which gets a field number, and returns the number of reservations made.

Before making a reservation it is possible to check the availability of the fields that have certain characteristics.

The method `findOptions()` gets a schedule and an object `Features`, and returns the list of options available in the fields that have the required characteristics and are free at the specified time.

The method returns a list of alternatives represented by objects that implement the `FieldOption` interface.

The interface specifies the getter methods `getField()`, and `getOccupation()`, which return the field number and the occupation.

<!--
The method `toString ()` must return a string with the format `"HH: MM, T = OO%"` where:

- `HH:MM` indicates the time when the table will be free,
- `T` is the table number,
- `OO%` is the percentage of table occupancy.
-->

The list is sorted by decreasing occupation and then by increasing field number.

## R5: Statistics

The following methods are used to collect statistics:

* `countServedAssociates()` returns the total number of associates who have made at least one reservation.
* `occupation()` returns the occupation level of the facility in terms of percentage. It is calculated as the ratio of the number of reservations in all fields and the number of blocks from one hour available between opening and closing times. 
* `fieldTurnover()` returns a map with keys the numbers of the fields, and values ​​the number of reservations at these fields.
