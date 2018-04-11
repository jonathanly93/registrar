Please Read my proposal to see if I have completed what was intended.

Initial Setup
1) Load the sql file to start up the Registrar DB
2) compile all the files
3) java MainDriver


To Test for restriction (student cant sign up with restrictions) follow these steps
1) login
2) jon@example.com 12345
3) enrollment
4) add course
5) view sections (mark down a course id and section id pair)
6) <course_id> <section_id> (4 400 for example)
7) You should not be able to sign up

To Test for every other function use email: jeff@example.com password: 12345, or sign up a user and use that account.

I have also enabled an "admin" function. I made this feature mainly to help facilitate the program and the database. It essentially has a lot of the functions the Administrator class would have had, it I had implemented it.

You should be able to delete restrictions, delete student account, view all student account information, create a new course.

I initially wanted to implemented adding new sections and adding restrictions, but I did not have enough time to complete. Students actually sign up for sections and not coures. Courses is suppose to provide a base to create a section object, so by adding a course, you will not be able to add it on the student enrollment.


Classes Fully Implemented: Course, CourseCatalog, RegisteredUser, Restriction, RestrictionList,
Section, Student, StudentCourse, StudentCourseList, Student, UserSession

Classes Not Fully Implemented: instructor, room (these classes were not in my proposal, but I included them in so I can draw a bit more information for my main classes)

Objective: Navigate through a Registrar and allow the user to do the following:
1) Sign up for a new user.
2) Login to an existing user. (10 in the default DB but you can log in with newly created users)
3) Sign up for courses. (up to 3 courses)
4) Drop courses.
5) View courses currently enrolled or have completed (completed courses can only be seen with the provided dummy users)
6) Change user password.
7) View available sections to sign up.
8) View all courses offered (4 in the default DB)
9) View restrictions
10) View users information
11) Sign out and end a user session.

NOT THE BEST IMPLEMENTATION (due to time constraints)
1) IDs are generated uniquely from 1-1000000, but there isn't a good way of actually seperating out user, course, and section id.
2) login is done through "email" and "password". email does not check for real emails and actually takes any string as an input. The password is also not encrypted and left as a string in the DB.
3) The JDBC class is a bit messy. I just have that class to retrieve neccessary calls to the DB to extract data when needed.
4) Data retrieval from DB are not written optimally and may cause some slow downs when processing large amounts of data.
5) I wasn't sure if I was suppose to include the jUnit, but the test was only covering about 1/2 of my project so I decided to remove it.

I have tried my best at debugging and have not ran any issues following these steps.
Please feel free to navigate through my project in any other way, however, I can't gaurentee that it will be errorless.

Author Note: 

I tried to implement as many oo concepts as I could. Initially it started out well designing for class and responsibilities, however, as I started to code the project, I realized I was missing some classes and that some other classes functions were a bit repetitive.

I started off by designing the functionality of the system and how each class are suppsoe to talk to other classes to aggregate data. During this time I was trying to implement jUnit as I was working. However when I figured a decent of my class had to be changed because I could not reach my requirement, I decided to scrap the jUnit testing as it was taking me too long to update that as I started building. 

Many of my classes initially had inheritance. Student inherited RegisteredUser, CourseCatalog inherited Course. However I decided to go for composition over inheritance approach and recreated the classes to have compositions of the respected objects (this is part of why I decided to drop jUnit). 

I tried to implemented a lot of the SOLID principles especially single use and open for extension, but closed for modifiation.

As for pattern usage, I mainly used iterator as it was very useful to listing out a collection's objects.