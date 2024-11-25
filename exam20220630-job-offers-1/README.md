Job Offers
==========

(the Italian version is available in file [README_it.md](README_it.md)).


The program simulates the management of job vacancies by a company. All
classes are found in the **jobOffers** package. The main class is
**JobOffers**. The **TestApp** class in the example package contains
examples and presents the main test cases but not all. Exceptions are
thrown using the **JOException** class; only the specified checks must
be carried out and not all possible ones. If a method throws an
exception there is no change in the data present in the main class.
Suggestion: every time you have implemented a method, run *TestApp* to
check the result.

The JDK documentation is accessible at URL <https://oop.polito.it/api/>.

R1: Skills, positions
---------------------

Method **int addSkills (String \... skills)** inserts a number of
skills, such as java, python, etc. Duplicate skills are ignored. The
result of the method is the number of skills entered so far.

Method **int addPosition (String role, String \... skillLevels)**
inserts a position, such as *juniorProgrammer*, with the required skills
along with the desired levels of knowledge (look at the examples in
*TestApp*). A skill is followed by *\":\"* and the level of knowledge
between 4 and 8 inclusive. The method throws an exception if: the
position has already been entered, one or more skills have not been
entered or a level is not between 4 and 8. The result of the method is
the average of the levels.

R2: Candidates, applications
----------------------------

Method **int addCandidate (String name, String \... skills)** inserts
the name of a candidate with the list of his/her skills. The method
throws an exception if the candidate has already been entered or a skill
has not been entered. The result is the number of skills.

Method **addApplications (String name, String \... positions)** allows a
candidate to apply for one or more positions. The method throws an
exception if: the candidate has not been entered, not all the positions
have been entered, the candidate does not have all the skills required
by the positions. The method gives the list of applications; an
application is a string consisting of the name of the candidate followed
by *\":\"* and the name of the position. Strings are sorted by
candidates and by positions.

Method **getCandidatesForPositions()** gives a map whose keys are the
names of the positions in alphabetical order; the values are the ordered
lists of the candidates that applied to each position. Positions without
candidates are ignored.

R3: Consultants, ratings
------------------------

Method **int addConsultant (String name, String \... skills)** inserts
the name of a consultant with the list of his/her skills. The method
throws an exception if the consultant has already been entered or a
skill has not been entered. The result is the number of skills.

Method **Integer addRatings (String consultant, String candidate,
String\... skillRatings)** allows a consultant to assign a rating to
each of the skills indicated by the candidate. An example of skillRating
is as follows:*\[\"java:8\", \"databases:6\"\]*. Each string is made up
of the skill followed by a colon and the rating. The rating is between 4
and 10 inclusive. The method throws an exception if the consultant or
the candidate has not been entered, the consultant\'s skills do not
include all those declared by the candidate, there are skills with
out-of-range ratings (e.g. *java:11*). The result of the method is the
average rating.

R4: Discarding applications, establishing eligible
--------------------------------------------------

Method **discardApplications()** discards the applications of ineligible
candidates. A candidate is ineligible for a position if one or more of
his/her skills have ratings below the levels required by the position.
The skill ratings are those added through method *addRatings()*. The
method gives the list of discarded applications; an application is
indicated by the name of the candidate followed by *\":\"* and the name
of the position. The list is sorted alphabetically by candidates and by
positions. The list can be empty.

Method **getEligibleCandidates(String position)** defines the eligible
candidates for a given position. The candidates must have applied for
the indicated position, their skills must include those related to the
position; furthermore the ratings of the skills (provided by
consultants) must be no lower than the levels defined with method
*addPosition()*. The result is a list of eligible candidates in
alphabetical order; the list may be empty.
