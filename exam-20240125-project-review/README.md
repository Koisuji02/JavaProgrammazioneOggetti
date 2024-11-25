Project Reviews
===============

Write a program to support planning the review meetings for student projects.
Classes are located in the `it.polito.project` package; the main class is `ReviewServer`. The `TestApp` class in the `example` package shows usage examples for the main methods and examples of the requested checks. 
Only implement the requested checks. 
Exceptions in the methods described below are of `ReviewException` type.

The [JDK documentation](https://oop.polito.it/api/) is located on the local server.


R1: Groups and Reviews
----------------------

The `addGroups()` method allows adding student groups (e.g. *Group1*, *The best*) to the list of groups. It accepts the groups and adds them to the list.

The `getGroups()` method retrieves the list of available groups.

The `addReview()` method adds a new review with a specified group. It takes the title, topic, and groups for the review as input and returns a unique ID for the review meeting. If the specified group does not exist, it throws a `ReviewException`.

The `getReviews()` method retrieves the list of review meetings for a given group. It takes the group as input and returns a collection of review IDs.

The `getReviewTitle()` method retrieves the title of a review based on its ID. It takes the review ID as input and returns the corresponding review's title.

The `getReviewTopic()` method retrieves the subject of a review based on its ID. It takes the review ID as input and returns the corresponding review's topic.


R2: Review time slots
---------------------

The `ReviewServer` class provides additional methods to manage reviews time slot information.

To add a new option slot for a review, you can use the `addOption()` method. This method requires the meeting ID, date, start time, and end time as parameters. It ensures that the new slot does not overlap with any existing slots for the same review. If the provided meeting ID is invalid, a `ReviewException` is thrown. The method calculates the length of the slot in hours and returns this value.

If you need to retrieve information about the available slots for a specific review, you can use the `showSlots()` method. The returned value is a map where each date is a key, and the corresponding value is a list of slots described in the format "hh:mm-hh:mm". This format indicates the start and end times of each slot, allowing you to view the available time options for the meeting.



R3: Preferences
---------------

The `ReviewServer` class provides a set of methods to manage student preferences for review meetings.

To declare a meeting open for collecting preferences, you can use the `openPoll()` method, that given the review ID, enables the collection of preferences for that review.

When students want to enter their preferences for a specific slot or option of a review, they can use the `selectPreference()` method. This method requires the student's email, name, surname, review ID, date of the selected slot, and the time range of the slot. Preferences can only be recorded for reviews that have an opened poll. The method adds the preference and returns the total number of preferences recorded for that slot. If an invalid review ID or slot is provided, a `ReviewException` is thrown.

Finally, if you need to retrieve the list of preferences expressed for a specific meeting, you can use the `listPreferences()` method. By providing the meeting ID, the method returns a collection of preferences expressed for that meeting. Each preference is represented as a string in the format "YYYY-MM-DDThh:mm-hh:mm=EMAIL", where the date, time interval, and email of the participant are separated by "T" and "=".


R4: Close poll
--------------

To close the poll associated with a review and determine the most preferred options, you can use the `closePoll()` method. By providing the review ID, the method retrieves the best options and disables the poll, indicating that no further preferences can be recorded for that review.

The method returns a collection of strings representing the most preferred options. Each option is formatted as "YYYY-MM-DDThh:mm-hh:mm=##", where the date, time interval, and preference count (`##`) are separated by "T" and "=". This format allows you to easily identify the date, time range, and the number of preferences received for each option.



R5: Stats
---------

Further methods provide useful information for analyzing review preferences.

The `reviewPreferences()` method retrieves the preference count for each slot of a review. It takes the review ID as input and returns a map where each key represents a date associated with the review, and the corresponding value is a list of slots with their preferences. Each slot is described as a string in the format "hh:mm-hh:mm=###", indicating the time interval and the number of preferences for that slot, such as "14:00-15:30=2". This method provides an overview of the preferences expressed for each slot within the review.

The `preferenceCount()` method calculates the total number of preferences collected for each review. It does not require any input parameters and returns a map where each key represents a review ID, and the corresponding value represents the count of preferences expressed for that review. This method allows for assessing the level of interest or popularity of each review based on the number of preferences received.

