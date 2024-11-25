Elective Courses Management
===========================

Develop a system to manage enrollments to elective courses. These
courses can only accept a limited number of students. Students may
choose different courses (and assign a priority to each of them
according to their preference). The system then assigns students to
courses.

All classes must reside in the package named `it.polito.oop.elective`.

The class named `Example` in package `main` contains examples using
the main methods.

You are free to access the [JDK documentation](https://oop.polito.it/api).


R1: Students and Courses
------------------------

The facade class for the system is `ElectiveManager` through which all
operations are performed.

Courses are defined by calling the method `addCourse(String , int )`
that receives as parameters the (unique) course name and the number of
students it can accept (i.e., the available places). The list of the
defined courses can be obtained by calling the method `getCourses()`,
which returns the names of the defined courses, ordered alphabetically.

Students are registered in the system by calling the
`loadStudent(String , double)` method, which receives as parameters
the student ID and the grade point average. If the method is called more
than once for the same student, the student grade point average is
updated with the last passed value.

The `getStudents()` method returns the list of the IDs of all the
registered students while the getStudents(double, double) method only
returns the list of the IDs of the students whose grade point average is
in the interval specified by the two input parameters (inclusive).


R2: Enrollment requests
-----------------------

A student wanting to enroll to elective courses specifies a list of (1
up to 3) courses ordered by preference, from the most preferred one to
the least preferred one.

The request is made by calling the `requestEnroll(String,List)`
method, which receives as parameters the student ID and the list of the
desired courses. This method returns the number of courses included in
the list. An `ElectiveException` exception is raised if the number of
requested courses is not between 1 and 3 (inclusive), if the student ID
does not belong to an already registered student, or if one or more of
the requested courses have not been defined.

The `numberRequests()` method returns the number of student that have
expressed a preference for elective courses. This method returns a map
that associates each course with the number of students that have
selected it as their first, second, or third choice. The map key is the
name of the course, while values are lists of 3 elements. The first
element in the list counts the student that have selected course (i.e.,
the key) as their first, second, or third choice. The map also includes
courses that have not been chosen by any student (in such case the list
will contain 3 zeros).


R3: Making the classes
----------------------

When the deadline for selecting the elective exams expires, the student
office defines the list of students enrolled to each elective course.
The lists are defined by calling the `makeClasses()` method, that
returns the number of students not enrolled to any course.

When defining the lists, the system tries to accommodate the student
preferences starting from the students with higher grade point average
until no places remain in any of the defined courses.

A student that does not find a place in any of the requested courses is
not enrolled to any of them. The `getAssignments()` method returns
assignment to courses as a map that associates each course to the list
of student ID assigned to it. Lists are ordered in decreasing order of
the grade point average.


R4: Message Notification
------------------------

The system sends notifications regarding the procedure of the assignment
of elective courses.

The system uses notification objects to send messages. Objects must
implement the `Notifier` interface and are registered through the
`addNotifier(Notifier)` method. For instance, notifiers could send SMS
or email notifications.

Developing such objects and the corresponding classes is out of the
scope of the exam (assume they are provided by third parties).

When an enrolment request is made (through a call to the
`requestEnroll()` method) the system notifies all the registered
notifiers with the `requestReceived(String)` method. This method
receives as a parameter the student ID of the notification recipient.

After the classes have been made (trough the `makeClasses()` method),
each student enrolled in a course is notified by calling the
`assignedToCourse(String,String)` method. This method receives as
parameters the student ID and the name of the course the student has
been assigned to.

### Hints

-   The Notifier must not be implemented. The main package contains a
    sample class that may used to understand how Notifier work.
-   To fulfil this requirement, just implement the
    `addNotifier(Notifier)`. Of course, you may also need to add some
    functionality to previously described methods.


R5: Stats
---------

The `successRate(int)` method returns the percentage of students that
have been assigned to a course corresponding to a given preference. This
method receives as parameter the preference number, between 1 and 3
(inclusive) and returns the success rate, that is a floating point
number between 0 and 1. The rate is evaluated by taking into account (as
denominator) all the students, including those not assigned to any
course.

The `getNotAssigned()` method returns the ID of students that have not
been assigned to any course.
