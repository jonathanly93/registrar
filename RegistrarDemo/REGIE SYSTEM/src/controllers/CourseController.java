package controllers;

import database.JDBC;
import models.Course;
import models.CourseCatalog;

public class CourseController {

    private RandomNumberGenerator rng;
    private JDBC jdbc;

    public CourseController(){
        rng = new RandomNumberGenerator();
        jdbc = new JDBC();
    }

    public void createCourse(String courseName, String department, int credit, CourseCatalog courseCatalog) {


        // Get first instance of unique id
        int uniqueId = rng.getRandomNumber(7);

        // Creates a new course
        Course course = new Course(courseName, department,credit, uniqueId);

        // Add the course to the course catalog
        courseCatalog.getCourseList().add(course);

        // Adds the entry into the course table in the Registrar DB
        jdbc.addCourseInDB(courseName, department, credit, uniqueId);

        System.out.println("\nNew course successfully created!");
    }
}
