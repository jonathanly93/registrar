import controllers.*;
import database.JDBC;
import models.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

// The bulk of the project. The REGGIESYSTEM class will represent the projects "front end" where
// the user can interact with the registrar through the console.
public class REGGIESystem extends Thread{

    private boolean isRunning;
    private CourseCatalog courseCatalog;
    private CourseCatalogController courseCatalogController;
    private UserSession userSession;
    private UserSessionController userSessionController;
    private RegisteredUserController registeredUserController;
    private StudentController studentController;
    private SectionController sectionController;
    private JDBC jdbc;
    private boolean admin;

    // Boots up all the controllers needed to work the REGGIE Registrar on startup
    public REGGIESystem(){
        this.isRunning = true;
        this.admin = false;
        this.courseCatalog = new CourseCatalog();
        this.courseCatalogController = new CourseCatalogController();
        this.courseCatalogController.getCourseCatalog(courseCatalog);
        this.userSession = new UserSession();
        this.userSessionController = new UserSessionController();
        this.registeredUserController = new RegisteredUserController();
        this.studentController = new StudentController();
        this.sectionController = new SectionController();
        this.jdbc = new JDBC();

    }

    // Exits the program
    public void shutdown(){
        this.isRunning = false;
        System.out.println("Shutting Down...\n" + "Thank you for using the REGGIE System");
    }

    // Sets admin access.
    public void setAdmin(boolean admin){
        this.admin = admin;
    }

    // Checks admin status
    public boolean getAdmin(){
        return admin;
    }

    // Loop used when user is not logged in
    public void loggedOutLoop(){
        System.out.println("\nIf you have a login email please sign in with <login> command"
                            +"\n<help> to view commands." );

        Scanner scanner = new Scanner(System.in);
        // Sets user input in scanner
        String input = scanner.nextLine().toUpperCase();

        // Displays commands a user can use
        if(input.equals("HELP")){
            System.out.println("<login> : sign into your account" +
                    "\n<view course catalog> : view the courses offered" +
                    "\n<sign up> : sign up a new user" +
                    "\n<shutdown> : exit the REGGIE Registrar (Prototype)" +
                    "\n<admin> : gives user admin access");
        }
        // Logs the user in. Takes in email and password as arguments for login
        else if(input.equals("LOGIN")) {
            System.out.println("Please enter your email and password.");
            System.out.println("<email> <password>");

            String loginInfo = scanner.nextLine();
            String[] loginInfoSplit = loginInfo.split("\\s+");

            // Only accepts an <email> <password> String
            if (loginInfoSplit.length == 2) {

                // Starts the user session
                userSessionController.startSession(loginInfoSplit[0], loginInfoSplit[1], userSession);
            }
            else { System.out.println("That is not a valid input."); }
        }
        // Displays Course Catalog
        else if(input.equals("VIEW COURSE CATALOG")){

            // Sets up iterator to print course list.
            Iterator itr = courseCatalog.getCourseList().iterator();
            System.out.println("Course ID\t\t\t\tCourse\t\t\t\tDepartment\t\t\t\tCredit"
                    +"\n_____________________________________________________________");
            while(itr.hasNext()){
                Course course = (Course) itr.next();

                System.out.println(course.getId()+ "\t\t\t\t" +
                        course.getCourseName() + "\t\t\t\t" +
                        course.getDepartment() + "\t\t\t\t" +
                        course.getCredit());
            }
        }
        // Signs up a new User
        else if(input.equals("SIGN UP")){
            System.out.println("Please provide a name, email and password." +
                    "\nYour email and password will be used as your login info" +
                    "\n<name> <email> <password>");

            String loginInfo = scanner.nextLine();
            String[] loginInfoSplit = loginInfo.split("\\s+");

            // Only accepts an <email> <password> String
            if (loginInfoSplit.length == 3) {
                // Starts the user session
                registeredUserController.createRegisteredUser(loginInfoSplit[0],loginInfoSplit[1],loginInfoSplit[2]);
                if(userSession.getStatus()){
                    System.out.println("Hello " + userSession.getStudent().getRegisteredUser().getName() + "!");
                }
            }
            else { System.out.println("That is not a valid input."); }
        }
        // Exits the Program
        else if (input.equals("SHUTDOWN")){
            shutdown();
        }
        // Enter admin mode
        else if(input.equals("ADMIN")) {
            setAdmin(true);
        }
        // Invalid input
        else {System.out.println("Invalid command");
        }
    }
    // Loop used when user is logged in.
    public void loggedInLoop(){
        System.out.println("\n User Login Commands" +
                "\n<user preferences> : view user information and change password" +
                "\n<enrollment> : sign up for a section. Max sections taken concurrently = 3" +
                "\n<logout> : logout user session");

        Scanner scanner = new Scanner(System.in);
        // Sets user input in scanner
        String input = scanner.nextLine().toUpperCase();
        // Initialize decision loop
        boolean decisionLoop = false;

        // Displays possible commands
        if(input.equals("USER PREFERENCES")){

            // Start a decision loop
            decisionLoop = true;
            while(decisionLoop){
                System.out.println("\nTo view student info : <student info>"
                        + "\nTo change password : <change password>"
                        + "\nTo view restrictions: <view restrictions"
                        + "\nTo exit User Preferences : <exit>");

                // Sets user input in scanner
                input = scanner.nextLine().toUpperCase();
                if(input.equals("CHANGE PASSWORD")){

                    System.out.println("To change password please provide : <old password> <new password>");
                    input = scanner.nextLine();
                    String[] inputSplit = input.split("\\s+");
                    if(inputSplit.length == 2) {
                        registeredUserController.changePassword(inputSplit[0], inputSplit[1], userSession.getRegisteredUser().getEmail());
                    } else {
                        System.out.println("Not a valid input");
                    }
                } else if(input.equals("STUDENT INFO")){
                    System.out.println("ID: " + userSession.getStudent().getRegisteredUser().getId()
                                        + "\tName: " + userSession.getStudent().getRegisteredUser().getName()
                                        + "\tEmail: " + userSession.getStudent().getRegisteredUser().getEmail());

                    if(userSession.getStudent().getStudentCourseList().getStudentCourseList().size() > 0) {
                        System.out.println("\nYou have enrolled in the following courses");
                    } else{System.out.println("\nYou have not enrolled or completed any courses.");}

                    Iterator itr = userSession.getStudent().getStudentCourseList().getStudentCourseList().iterator();
                    while(itr.hasNext()){
                        StudentCourse studentCourse = (StudentCourse) itr.next();

                        // Checks if users is currently enrolled in a course
                        String activity = "Completed";
                        if(studentCourse.getCourseActivity()){
                            activity = "In progress";
                        }

                        // Prints out every course enrolled.
                        System.out.println("Course: " + studentCourse.getSection().getCourse().getCourseName() + "\t" +
                                 "Instructor: " + studentCourse.getSection().getInstructor().getName() + "\t" +
                                "Section: " + studentCourse.getSection().getCourse().getCredit() + "\t" +
                                "Grade: " + studentCourse.getGrade() + "\t" +
                                "Current Activity: " + activity +"");
                    }
                }else if(input.equals("VIEW RESTRICTIONS")){
                    // Retrieve restriction list
                    ArrayList<Restriction> restrictionArrayList = jdbc.getRestrictionList(userSession.getRegisteredUser().getId());
                    Iterator itr = restrictionArrayList.iterator();
                    if(restrictionArrayList.size() > 0){
                        System.out.println("Here are your list of restrictions\n");
                    } else {System.out.println("You do not have any restrictions\n");}
                    while(itr.hasNext()){
                        Restriction restriction = (Restriction) itr.next();

                        System.out.println(restriction.getTitle() + ": " + restriction.getDescription());
                    }

                }else if(input.equals("EXIT")){
                    decisionLoop = false;
                } else
                 {System.out.println("Invalid Command");
                }
            }
        }
        // Used to sign up for a section
        else if(input.equals("ENROLLMENT")) {
            // Start a decision loop
            decisionLoop = true;
            while (decisionLoop) {
                System.out.println("\nTo add a course : <add course>"
                        + "\nTo drop a course : <drop course>"
                        + "\nTo exit Enrollment : <exit>");

                // Sets user input in scanner
                input = scanner.nextLine().toUpperCase();

                // Setup second loop
                boolean decisionLoop2 = true;

                // Add a course to student courselist
                if (input.equals("ADD COURSE")) {

                    while (decisionLoop2) {
                        System.out.println("\nCommands available" +
                                "\nTo sign up for a course, provide : <course_id> <section_id>" +
                                "\nTo view all course available, type : <view sections>" +
                                "\nTo go back, type : <exit>");

                        String info = scanner.nextLine().toUpperCase();
                        String[] infoSplit = info.split("\\s+");

                        if (info.equals("VIEW SECTIONS")) {
                            System.out.println("LIST OF ALL AVAILABLE SECTIONS. YOU WILL NEED THE course id and section id to register");

                            // Retreive the list
                            ArrayList<StudentCourse>studentCoursesList = jdbc.getAllSections();
                            // Retrieve missing information
                            StudentCourseListController studentCourseListController = new StudentCourseListController();
                            studentCourseListController.appendInformation(studentCoursesList);

                            Iterator itr = studentCoursesList.iterator();
                            while(itr.hasNext()) {
                                StudentCourse studentCourse = (StudentCourse) itr.next();
                                System.out.println("Course ID:  " + studentCourse.getSection().getCourse().getId()
                                                    + " SectionID:    " + studentCourse.getSection().getSectionId()
                                                    + " Course: " + studentCourse.getSection().getCourse().getCourseName()
                                                    + " Department:   " + studentCourse.getSection().getCourse().getDepartment()
                                                    + " Instructor:  " + studentCourse.getSection().getInstructor().getName()
                                                    + " Room:    " + studentCourse.getSection().getRoom().getRoomNumber()
                                                    + " Section Time:    " +studentCourse.getSection().getTime()
                                                    + " Max Capacity:    " +studentCourse.getSection().getMaxCapacity()
                                                    + " Credits: " + studentCourse.getSection().getCourse().getCredit()
                                                    + " Quarter: " + studentCourse.getSection().getQuarter());

                            }

                        } else if (infoSplit.length == 2 && infoSplit[1].matches("\\d+")) {

                            // Retrieve section information
                            Section section = sectionController.retreiveSection(Integer.parseInt(infoSplit[1]));

                            // Checks to make sure the section is actually available
                            if(section.getSectionId() != 0){
                                // Add the course to the Student's course list
                                studentController.addStudentCourse(userSession.getStudent(), section);
                            } else{System.out.println("The section does not exist");}
                        } else if (info.equals("EXIT")) {
                            decisionLoop2 = false;
                        } else {
                            System.out.println("Invalid Command");
                        }
                    }
                }
                // Drops a currently enrolled course
                else if (input.equals("DROP COURSE")) {
                    while (decisionLoop2) {

                        System.out.println("To drop a course : <course_id> <section_id>" +
                                "\n To view enrolled courses: <view schedule>" +
                                "\n To go back, type : <exit>");

                        String info = scanner.nextLine().toUpperCase();;
                        String[] infoSplit = info.split("\\s+");

                        // Displays the classes currently taking
                        if (info.equals("VIEW SCHEDULE")) {
                            System.out.println("Display current schedule");
                        } else if (infoSplit.length == 2 && infoSplit[0].matches("\\d+")) {
                            studentController.removeStudentCourse(Integer.parseInt(infoSplit[0]), userSession.getStudent());
                        } else if (info.equals("EXIT")) {
                            decisionLoop2 = false;
                        } else {
                            System.out.println("Invalid command");
                        }
                    }
                    // Exits enrollment
                } else if (input.equals("EXIT")) {
                    decisionLoop = false;
                } else {
                    System.out.println("\nInvalid Command\n");
                }
            }
        }
        // Log out of the user
        else if (input.equals("LOGOUT")) {
            userSessionController.endSession(userSession);
        }
        // Invalid argument
        else {System.out.println("Invalid option. Use <help> to view actions"); }
    }


    public void adminLoop(){
        System.out.println( "\n Commands available for Admins" +
                            "\n<course menu> : expand the course catalog by adding new courses" +
                            "\n<student menu> : view and remove all students" +
                            "\n<restriction menu> : add or remove restrictions from students" +
                            "\n<exit> go back to sign up page");
        Scanner scanner = new Scanner(System.in);
        String info = scanner.nextLine().toUpperCase();
        boolean decisionLoop = true;
        if(info.equals("EXIT")){
            setAdmin(false);
        } else if(info.equals("COURSE MENU")) {
            while (decisionLoop) {
                System.out.println( "\n Commands available" +
                        "\n<add course> : add a new course" +
                        "\n<delete course> : delete a course" +
                        "\n<view course> : view the current course list" +
                        "\n<exit> go back to previous section");
                info = scanner.nextLine().toUpperCase();

                if (info.equals("ADD COURSE")) {
                    System.out.println("Provide the following : <course name> <department> <credit>");
                    info = scanner.nextLine();
                    String[] infoSplit = info.split("\\s+");
                    if(infoSplit.length == 3){
                        CourseController courseController = new CourseController();
                        courseController.createCourse(infoSplit[0],infoSplit[1],Integer.parseInt(infoSplit[2]),courseCatalog);
                    }

                } else if (info.equals("DELETE COURSE")) {
                    System.out.println("Provide the following : <course id>");
                    info = scanner.nextLine().toUpperCase();
                    if(info.matches("\\d+")){
                        CourseCatalogController courseCatalogController = new CourseCatalogController();
                        courseCatalogController.deleteCourse(Integer.parseInt(info),courseCatalog);
                    } else{System.out.println("Invalid input");}

                } else if (info.equals("VIEW COURSE")) {
                    // Sets up iterator to print course list.
                    Iterator itr = courseCatalog.getCourseList().iterator();
                    System.out.println("Course ID\t\t\t\tCourse\t\t\t\tDepartment\t\t\t\tCredit"
                            +"\n_____________________________________________________________");
                    while(itr.hasNext()){
                        Course course = (Course) itr.next();

                        System.out.println(course.getId()+ "\t\t\t\t" +
                                course.getCourseName() + "\t\t\t\t" +
                                course.getDepartment() + "\t\t\t\t" +
                                course.getCredit());
                    }
                } else if (info.equals("EXIT")) {
                    decisionLoop = false;
                } else {
                    System.out.println("Invalid Command");

                }
            }
        }
        else if(info.equals("STUDENT MENU")) {
                while (decisionLoop) {
                    System.out.println( "\n Commands available" +
                            "\n<delete student> : delete a student" +
                            "\n<view student> : view all student currently registered" +
                            "\n<exit> go back to previous section");
                info = scanner.nextLine().toUpperCase();
                if (info.equals("DELETE STUDENT")) {
                    System.out.println("Provide the following: <student id>");
                    info = scanner.nextLine().toUpperCase();
                    if(info.matches("\\d+")) {
                        if (jdbc.checkStudent(Integer.parseInt(info))) {
                            jdbc.deleteStudent(Integer.parseInt(info));
                            System.out.println("\nThe student has been deleted");
                        } else {
                            System.out.println("The student does not exist");
                        }
                    }
                } else if (info.equals("VIEW STUDENT")) {

                        System.out.println("Student loop");
                        // Retrieve the list of students
                        ArrayList<RegisteredUser> studentArrayList = jdbc.getAllStudents();

                        // Sets up iterator to print student list.
                        Iterator itr = studentArrayList.iterator();
                        System.out.println("ID\t\t\t\tName\t\t\t\tEmail\t\t\t\tPassword"
                                +"\n_________________________________________________________");
                        while(itr.hasNext()){
                            RegisteredUser registeredUser = (RegisteredUser) itr.next();

                            System.out.println(registeredUser.getId() + "\t\t\t\t" +
                                                registeredUser.getName() + "\t\t\t\t" +
                                                registeredUser.getEmail() +"\t\t\t\t" +
                                                registeredUser.getPassword());
                        }
                } else if (info.equals("EXIT")) {
                    decisionLoop = false;
                } else {
                    System.out.println("Invalid Command");
                    }
                }
        } else if(info.equals("RESTRICTION MENU")) {
            while (decisionLoop) {
                System.out.println("\n Commands available" +
                        "\n<add restriction> : add a restriction" +
                        "\n<delete restriction> : delete a restriction" +
                        "\n<view restriction> : view all restrictions" +
                        "\n<exit> go back to previous section");
                info = scanner.nextLine().toUpperCase();
                if (info.equals("ADD RESTRICTION")) {
                    System.out.println("Sorry function is not available at the moment");
                } else if (info.equals("DELETE RESTRICTION")) {
                    System.out.println("Provide the following: <restriction id>");
                    info = scanner.nextLine().toUpperCase();
                    if (info.matches("\\d+")) {
                        if (jdbc.checkRestriction(Integer.parseInt(info))) {
                            jdbc.deleteRestrictionInDB(Integer.parseInt(info));
                            System.out.println("\nThe restriction has been deleted");
                        } else {
                            System.out.println("The restriction does not exist");
                        }
                    }
                } else if (info.equals("VIEW RESTRICTION")) {
                    // Retrieve the list of students
                    ArrayList<Restriction> restrictionArrayList = jdbc.getAllRestriction();

                    // Sets up iterator to print student list.
                    Iterator itr = restrictionArrayList.iterator();
                    System.out.println("Restriction ID\t\t\t\tTitle\t\t\t\tDescription\t\t\t\tUser ID"
                            + "\n___________________________________________________________________________________________");
                    while (itr.hasNext()) {
                        Restriction restriction = (Restriction) itr.next();

                        System.out.println(restriction.getRestrictionId() + "\t\t\t\t" +
                                restriction.getTitle() + "\t\t\t\t" +
                                restriction.getDescription() + "\t\t\t\t" +
                                restriction.getUserId());
                    }
                } else if (info.equals("EXIT")) {
                    decisionLoop = false;
                } else {
                    System.out.println("Invalid Command");
                }
            }
        } else{System.out.println("Invalid Command");}
    }


    public void run(){
        System.out.println("Welcome to the REGGIE System");

        // Run program loop
        while(isRunning){

            // Run the session for non-logged in users.
            // Can only access the commands: View Course Catalog and Login
            while(!userSession.getStatus() && isRunning){
               loggedOutLoop();
               // Admin loop if admin is enabled
               while(admin){
                   adminLoop();
               }
            }

            // Access to more commands once logged in
            while(userSession.getStatus()){
                loggedInLoop();
            }
        }
    }
}

