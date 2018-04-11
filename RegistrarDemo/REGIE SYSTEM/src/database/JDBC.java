package database;

import models.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

// The data retrieval class. It stores all the queries and statement used in the project
public class JDBC {

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;


    // Statement used to connect to localhost Registrar Database
    public void connectDB(){

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String connectionUrl = "jdbc:mysql://localhost:3306/registrar?autoReconnect=true&useSSL=false";
          //  System.out.println(connectionUrl);
            String connectionUser = "root";
          //  System.out.println(connectionUser);
            String connectionPassword = "password";
          //  System.out.println(connectionPassword);
            conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
    } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        }
    }

    // Used to insert data into Registrar Database
    public void insertToDB(String sqlInsertStatement){

        try{
            connectDB();
            stmt = conn.createStatement();

            stmt.executeUpdate(sqlInsertStatement);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

    }

    /**
     * Adds a student course entry in the studentcoure table
     * @param courseName : the course name
     * @param department : the department
     * @param credit : the amount of credit offered
     * @param grade : the grade received. "P" means in progress
     * @param activity : the current activity. 0 = finished, 1 = currently taking
     * @param quarter : the quarter the course is offered
     * @param userId : the ID of the user taking the course
     * @param courseId : the course ID
     */
    public void addStudentCourseToDB(int courseId, int sectionId, String courseName, String department, int instructorId,
                                     int roomId, int sectionTime, int maxCapacity, int credit, char grade,
                                     int activity, String quarter, int userId) {

        String sqlInsertStatement = "INSERT INTO studentcourses VALUES"
                + "("+courseId+", "+sectionId+", '"+courseName+"', '"+department+"',"+instructorId+ ","+roomId+
                ","+sectionTime+","+maxCapacity+","+credit+"," + " '"+grade+"', "+activity+", '"+quarter+"', "+userId+")";
        insertToDB(sqlInsertStatement);

    }

    /**
     * Checks if a user is in a course.
     * @param courseId : The course ID
     * @param userId : The user ID
     * @return : returns -1 if the user is not in the course, 0 if the course is completed, and 1 if the student
     * is currently in the course.
     */
    public int checkCourseActivity(int courseId, int userId){

        // Returns -1 if the student is not in the course.
        int courseActivity = -1;
        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT activity FROM studentcourses WHERE course_id = " + courseId + " AND user_id = " + userId);

            while (rs.next()) {
                String activity = rs.getString("activity");

                courseActivity = Integer.parseInt(activity);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        // Returns true if id is unique
        return courseActivity;
    }

    /**
     * Deletes the an entry from the studentcourse table
     * @param courseId : the ID of the course
     * @param userId : the ID of the user taking the course
     */
    public void deleteStudentCourseInDB(int courseId, int userId){

        String sqlInsertStatement = "DELETE FROM studentcourses " +
                "WHERE course_id = " + courseId + " AND user_id = " + userId;
        insertToDB(sqlInsertStatement);
    }

    /**
     * Finds out if the id provided is unique
     *
     * @param i : argument provided to see if its unique
     * @return True if the id provided is a unique user ID
     */
    public boolean getUniqueRestrictionId(int i){

        boolean unique = true;
        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT restriction_id FROM restrictions");

            while (rs.next()) {
                String courseId = rs.getString("restriction_id");

                int j = Integer.parseInt(courseId);

                // Sets unique to false if i = j
                if(i == j){
                    unique = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        // Returns true if i is unique
        return unique;
    }

    /**
     * Checks to see if the restriction exist in the db
     * @param id : the ID of the restriction
     * @return true if the restriction exist, false if it doesn't
     */
    public boolean isRestrictionAvailable(int id){

        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT restriction_id FROM restrictions");

            while (rs.next()) {
                String userId = rs.getString("restriction_id");

                int tempId = Integer.parseInt(userId);

                // If id match is found, return true.
                if(id == tempId){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        // Return false if ID is now valid
        return false;
    }

    /**
     * Deletes the restriction with the provided id
     * @param id : the id of the restriction
     */
    public void deleteRestrictionInDB(int id){

        String sqlInsertStatement = "DELETE FROM restrictions WHERE restriction_id = " + id;
        insertToDB(sqlInsertStatement);
    }

    public ArrayList<Restriction> getAllRestriction(){
        ArrayList<Restriction> restrictionArrayList = new ArrayList<>();

        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM restrictions");

            while (rs.next()) {
                String restrictionId = rs.getString("restriction_id");
                String title = rs.getString("restriction");
                String description = rs.getString("description");
                String userId = rs.getString("user_id");

                Restriction restriction = new Restriction(title,description,Integer.parseInt(restrictionId));
                restriction.setUserId(Integer.parseInt(userId));

                restrictionArrayList.add(restriction);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return restrictionArrayList;
    }

    /**
     * Finds out if the id provided is unique
     *
     * @param id : argument provided to see if its unique
     * @return True if the id provided is a unique user ID
     */
    public boolean getUniqueUserId(int id){

        boolean unique = true;
        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT user_id FROM students");

            while (rs.next()) {
                String courseId = rs.getString("user_id");

                int j = Integer.parseInt(courseId);

                // Sets unique to false if i = j
                if(id == j){
                    unique = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        // Returns true if id is unique
        return unique;
    }

    /**
     * Adds a new entry in the student table in the Registrar DB
     * @param name : the name of the user
     * @param email : the email of the user
     * @param password : the password of the user
     * @param id : the unique id of the user
     */
    public void addStudentToDB(String name, String email, String password, int id){

        String sqlInsertStatement = "INSERT INTO students VALUES"
                + "("+id+", '"+name+"', '"+email+"', '"+password+"')";
        insertToDB(sqlInsertStatement);
    }

    /**
     * Finds the user password when an email is provided
     * @param email : used to find the user password
     * @return the users password if user exist and empty string
     * if the user does not exist.
     */
    public String getUserPassword(String email){

        String password = "";
        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT user_password FROM students WHERE user_email = '" + email + "'");

            while (rs.next()) {
                password = rs.getString("user_password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        // Returns password if found. Returns a blank if user does not exist
        return password;
    }

    /**
     * Retrieves a section when provided the section id
     * @param id : section_id
     * @return a section object with the section's information
     */
    public Section retrieveSection(int id){

        Section section = new Section();
        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM sections WHERE section_id = " + id);

            while (rs.next()) {
                String courseId = rs.getString("course_id");
                String courseName = rs.getString("course_name");
                String department = rs.getString("department");
                String instructorId = rs.getString("instructor_id");
                String roomId = rs.getString("room_id");
                String time = rs.getString("section_time");
                String capacity = rs.getString("max_capacity");
                String credit = rs.getString("credit");
                String quarter = rs.getString("quarter");

                // Build the section
                Instructor instructor = new Instructor(Integer.parseInt(instructorId));
                Course course = new Course(courseName,department,Integer.parseInt(courseId),Integer.parseInt(credit));
                Room room = new Room(Integer.parseInt(roomId));
                ArrayList<RegisteredUser> registeredUsersArrayList = new ArrayList<>();

                section = new Section(id,instructor,course,quarter,room,Integer.parseInt(time),Integer.parseInt(capacity),
                        registeredUsersArrayList);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        // Returns password if found. Returns a blank if user does not exist

        ArrayList<RegisteredUser> registeredUsers = getRegisteredUserFix(id);
        section.setRegisteredUserArray(registeredUsers);
        return section;
    }

    /**
     * Changes the user password.
     *
     * @param password : the new password
     * @param email : used to find the users account
     */
    public void changePassword(String password, String email) {
        String sqlInsertStatement = "UPDATE students SET user_password = '"
                + password +"' WHERE user_email = '" + email +"'";
        insertToDB(sqlInsertStatement);
    }

    /**
     * Adds an entry in the course table in the Registrar DB
     *
     * @param courseName : argument passed for the course_name column
     * @param department : argument passed for the department column
     * @param credit : argument passed for the credit column
     * @param id : argument passed for the course_id column
     */
    public void addCourseInDB(String courseName, String department, int credit, int id){

        String sqlInsertStatement = "INSERT INTO courses VALUES"
                + "("+id+", '"+courseName+"', '"+department+"', '"+credit+"')";
        insertToDB(sqlInsertStatement);
    }

    /**
     *  Edits an entry in the course table in the Registrar DB
     *
     * @param courseName : argument passed for the course_name column
     * @param department : argument passed for the department column
     * @param credit : argument passed for the credit column
     * @param id : argument passed used to search the entry to be edited
     */
    public void editCourseInDB(String courseName, String department, int credit, int id){
        String sqlInsertStatement = "UPDATE courses SET course_name = '" + courseName
                + "' department = '" + department
                + "' credit = " + credit
                + " WHERE course_id = " + id;
        insertToDB(sqlInsertStatement);
    }

    /**
     * Remove an entry in the course table in the Registrar DB
     *
     * @param id : argument passed used to sewarch the entry to be deleted
     */
    public void deleteCourseInDB(int id){
        String sqlInsertStatement = "DELETE FROM courses WHERE course_id = " + id;
        insertToDB(sqlInsertStatement);
    }


    /**
     * Finds out if the argument presented is unique
     *
     * @param i : an integer used to compare with all ID in the course table
     * @return true if a unique id is found
     */
    public boolean getUniqueCourseId(int i){

        boolean unique = true;
        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT course_id FROM courses");

            while (rs.next()) {
                String courseId = rs.getString("course_id");

                int j = Integer.parseInt(courseId);

                // Sets unique to false if i = j
                if(i == j){
                    unique = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        // Returns true if i is unique
        return unique;
    }

    /**
     * Finds out if the argument ID provided matches any of the
     * courseID in the courses table in the Registrar DB
     *
     * @param id : integer argument used to compare with the course_id column in the courses table
     * @return true if there is a match in ID
     */
    public boolean isCourseAvailable(int id){

        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT course_id FROM courses");

            while (rs.next()) {
                String userId = rs.getString("course_id");

                int tempId = Integer.parseInt(userId);

                // If id match is found, return true.
                if(id == tempId){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        // Return false if ID is now valid
        return false;
    }

    public boolean checkCourseEnrollment(int userId, int courseId){

        int count = 0;
        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM studentcourses WHERE course_id = " + courseId + " AND user_id = " + userId);

            // Increase the count if email is present
            while(rs.next()){
                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        // If count was increased, return true
        if(count > 0){
            return true;
        }
        return false;
    }

    /**
     * Checks if the email is registered in the Registrar
     * @param email : the email of the user
     * @return true if the email is present
     */
    public boolean checkEmail(String email){

        // Initialize a count to see if email is present
        int count = 0;
        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM students WHERE user_email = '" + email +"'");

            // Increase the count if email is present
            while(rs.next()){
                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        // If count was increased, return true
        if(count > 0){
            return true;
        }
        return false;
    }

    /**
     * Get registered user for Login to create a new User Session
     * @param email : email of the user
     * @return RegisteredUser object containing the user id, user name, email, and password
     */
    public RegisteredUser getRegisteredUser(String email){

        RegisteredUser registeredUser = new RegisteredUser();

        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM students WHERE user_email = '" + email +"'");

            while (rs.next()) {
                String userId = rs.getString("user_id");
                String name = rs.getString("user_name");
                String emaill = rs.getString("user_email");
                String password = rs.getString("user_password");


                // Create a registered user with the information extracted
                registeredUser = new RegisteredUser(name,email,password,Integer.parseInt(userId));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        // Returns the registered user crafted
        return registeredUser;
    }

    /**
     * Retrieves the list of courses available
     * @return ArrayList of Courses
     */
    public ArrayList<Course> getCourseCatalog(){

        Course course;
        ArrayList<Course> courseCatalog = new ArrayList<>();

        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM courses");

            while (rs.next()) {
                String courseId = rs.getString("course_id");
                String courseName = rs.getString("course_name");
                String department = rs.getString("department");
                String credit = rs.getString("credit");

                course = new Course(courseName,department,Integer.parseInt(courseId),Integer.parseInt(credit));
                courseCatalog.add(course);


            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return courseCatalog;
    }
    public Room getRoom(int id){
         Room room = new Room();
        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM rooms WHERE room_id = " + id);

            while (rs.next()) {
                String roomId = rs.getString("room_id");
                String roomName = rs.getString("room_name");

                room = new Room(Integer.parseInt(roomId));
                return room;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return room;
    }

    public Instructor getInstructor(int id){
        Instructor instructor = new Instructor();
        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM instructors WHERE user_id = " + id);

            while (rs.next()) {
                String userId = rs.getString("user_id");
                String userName = rs.getString("user_name");

                instructor = new Instructor(Integer.parseInt(userId));
                return instructor;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return instructor;
    }

    /**
     * Retrieves the list of all studentcoures belonging to a student with the given id argument
     * @param i : the id of the user
     * @return ArrayList of student courses belonging to the user with id i
     */
    public ArrayList<StudentCourse> getStudentCourseList(int i){

        StudentCourse studentCourse;
        ArrayList<StudentCourse> studentCourseList = new ArrayList<>();

        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM studentcourses WHERE user_id = " + i);

            while (rs.next()) {
                String courseId = rs.getString("course_id");
                String sectionId = rs.getString("section_id"); // new
                String courseName = rs.getString("course_name");
                String department = rs.getString("department");
                String instructorId = rs.getString("instructor_id"); // new
                String roomId = rs.getString("room_id"); // new
                String time = rs.getString("section_time"); // new
                String maxCapacity = rs.getString("max_capacity"); // new
                String credit = rs.getString("credit");
                String grade = rs.getString("grade");
                String activity = rs.getString("activity");
                String quarter = rs.getString("quarter");
                String userId = rs.getString("user_id");


                // If activity == 1 then course is in progress.
                boolean active = false;
                if(Integer.parseInt(activity) == 1){
                    active = true;
                }

                // Create course object
                Course course = new Course(courseName,department,Integer.parseInt(courseId),Integer.parseInt(credit));
                // Get instructor information from instructor_id and create object
                Instructor instructor = new Instructor(Integer.parseInt(instructorId));
                // Get room from room_id and create object
                Room room = new Room(Integer.parseInt(roomId));
                // Build student arraylist from section_id
                ArrayList<RegisteredUser> registeredUserArrayList = new ArrayList<>();
                // Use previously constructed object to create section object
                Section section = new Section(Integer.parseInt(sectionId), instructor, course, quarter, room,
                        Integer.parseInt(time), Integer.parseInt(maxCapacity), registeredUserArrayList);

                // Create student course with section object.
                studentCourse = new StudentCourse(grade.charAt(0),active,Integer.parseInt(userId),section);

                // Adds studentCourse into the studentCourseList
                studentCourseList.add(studentCourse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return studentCourseList;
    }

    /**
     * Retrieves the restriction list for the given userID
     * @param userId : the user ID of the user
     * @return ArrayList of restrictions belonging to the given user ID
     */
    public ArrayList<Restriction> getRestrictionList(int userId){

        Restriction restriction;
        ArrayList<Restriction> restrictionList = new ArrayList<>();

        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM restrictions WHERE user_id = " + userId);

            while (rs.next()) {
                String title = rs.getString("restriction");
                String description = rs.getString("description");
                String restrictionId = rs.getString("restriction_id");

                // Create restriction object
                restriction = new Restriction(title,description,Integer.parseInt(restrictionId));

                // Adds restriction object into restriction list
                restrictionList.add(restriction);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        // Returns the restriciton list for the given user with the ID provided
        return restrictionList;
    }

    public ArrayList<StudentCourse> getAllSections(){

        ArrayList<StudentCourse> studentCourseList = new ArrayList<>();

        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM sections");

            while (rs.next()) {
                String courseId = rs.getString("course_id");
                String sectionId = rs.getString("section_id");
                String courseName = rs.getString("course_name");
                String department = rs.getString("department");
                String instructorId = rs.getString("instructor_id");
                String roomId = rs.getString("room_id");
                String sectionTime = rs.getString("section_time");
                String maxCapacity = rs.getString("max_capacity");
                String credit = rs.getString("credit");
                String quarter = rs.getString("quarter");


                // Create course object
                Course course = new Course(courseName,department,Integer.parseInt(courseId),Integer.parseInt(credit));
                // Create instructor object
                Instructor instructor = new Instructor(Integer.parseInt(instructorId));
                // Create room object
                Room room = new Room(Integer.parseInt(roomId));
                // Create ArrayList of registered users
                ArrayList<RegisteredUser> registeredUsers = new ArrayList<>();
                // Create Section Object
                Section section = new Section(Integer.parseInt(sectionId),instructor,course,quarter,room,
                        Integer.parseInt(sectionTime),Integer.parseInt(maxCapacity),registeredUsers);

                StudentCourse studentCourse = new StudentCourse(true,-10,section);
                studentCourseList.add(studentCourse);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        // Returns the arraylist
        return studentCourseList;
    }

    public ArrayList<RegisteredUser> getRegisteredUserFix(int i){

        ArrayList<RegisteredUser> registeredUsersList = new ArrayList<>();

        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM studentcourses WHERE section_id = " + i);

            while (rs.next()) {
                String userId = rs.getString("user_id");
                RegisteredUser registeredUser = new RegisteredUser();
                registeredUser.setId(Integer.parseInt(userId));

                registeredUsersList.add(registeredUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return registeredUsersList;
    }

    // Minor fix to retrieve instructor name when id is provided.
    public String getInstructorName(int id){

        String name = "";
        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM instructors WHERE user_id = " + id);

            while (rs.next()) {

                name = rs.getString("user_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        // Return the instructors name
        return name;

    }

    // Minor fix to retrieve room name when given id
    public String getRoomName(int id){

        String roomName = "";
        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM rooms WHERE room_id = " + id);

            while (rs.next()) {
                roomName = rs.getString("room_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        // Return the room name
        return roomName;

    }

    // Check if a restriction id exist
    public boolean checkRestriction(int id){
        int count = 0;
        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM restrictions WHERE restriction_id = " + id);
            while (rs.next()) {
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        if(count > 0){
            return true;
        } return false;
    }

    // Check if a user id exist
    public boolean checkStudent(int id){
        int count = 0;
        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM students WHERE user_id = " + id);
            while (rs.next()) {
              count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        if(count > 0){
            return true;
        } return false;
    }
    // Delete a student when provided and id
    public void deleteStudent(int id){
        String sqlInsertStatement = "DELETE FROM students WHERE user_id = " + id;
        insertToDB(sqlInsertStatement);

    }

    public ArrayList<RegisteredUser> getAllStudents(){

        ArrayList<RegisteredUser> registeredUserArrayList = new ArrayList<>();
        try{
            connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM students");
            while (rs.next()) {
                String userno = rs.getString("user_id");
                String username = rs.getString("user_name");
                String useremail = rs.getString("user_email");
                String userpassword = rs.getString("user_password");

                RegisteredUser registeredUser = new RegisteredUser(username,useremail,userpassword,Integer.parseInt(userno));
                registeredUserArrayList.add(registeredUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Printing e: " + e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return registeredUserArrayList;
    }

}

