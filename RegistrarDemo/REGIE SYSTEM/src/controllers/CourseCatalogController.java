package controllers;

import database.JDBC;
import models.Course;
import models.CourseCatalog;

/**
 * Carries the functions for Course Catalog.
 *
 * A Course Catalog should be able to perform the
 * following mutations: Add, Edit, Delete
 */
public class CourseCatalogController {

    private CourseCatalog courseCatalog;
    private RandomNumberGenerator rng;
    private JDBC jdbc;

    public CourseCatalogController(){
        jdbc = new JDBC();
    }

    /**
     * Creates a course and stores in the Course Catalog
     * as well as creating an entry in the course table
     * in the registrar database.
     *
     * @param courseName : Course name
     * @param department : Department of the course
     * @param credit : Credit offered for the course
     * @param courseCatalog : Carries the collection that holds all the courses offered
     */
    public void addCourse(String courseName, String department, int credit, CourseCatalog courseCatalog){

        rng = new RandomNumberGenerator();

        // Get first instance of random ID
        int uniqueId = rng.getRandomNumber(7);

        // Loops until a unique ID is found
        while(!jdbc.getUniqueCourseId(uniqueId)) {
            uniqueId = rng.getRandomNumber(7);
        }

        // Create a new Course
        Course course = new Course(courseName, department, uniqueId, credit);

        // Add course to the course catalog
        courseCatalog.getCourseList().add(course);

        // Create entry in course table in Registrar database
        jdbc.addCourseInDB(courseName,department,credit,uniqueId);

        System.out.println("The course has been added");
    }

    /**
     *  Edits a currently available course.
     *  If a course is not present, the method
     *  does not do anything.
     *
     * @param id : Course ID of the oourse that is to be edited
     * @param courseName : New name for the course
     * @param department : New department for the course
     * @param credit : New credit amount for the course
     */
    public void editCourse(int id, String courseName, String department, int credit, CourseCatalog courseCatalog){

        // Finds out if the id provided exist
        if(jdbc.isCourseAvailable(id)){

            // Matches id with the course ID and replace the parameters with the arguments given
            for(int i = 0; i < courseCatalog.getCourseList().size(); i ++){

                // If match is found, replace the information
                if( id == courseCatalog.getCourseList().get(i).getId()){
                    courseCatalog.getCourseList().get(i).setCourseName(courseName);
                    courseCatalog.getCourseList().get(i).setDepartment(department);
                    courseCatalog.getCourseList().get(i).setCredit(credit);

                    // Edits the entry in the DB
                    jdbc.editCourseInDB(courseName,department,credit,id);
                }
            }
            System.out.println("The Course has been edited");
        } else{
            System.out.println("The Course ID provided does not exist");
        }
    }

    /**
     * Deletes the course with the provided ID if the
     * course is present.
     *
     * Warns the user if the course is the ID does not
     * match
     *
     * @param id : Course ID of the course to be removed.
     */
    public void deleteCourse(int id, CourseCatalog courseCatalog) {

        // Finds out if the id provided exist
        if(jdbc.isCourseAvailable(id)){

            // Matches id with the course ID and remove the course
            for(int i = 0; i < courseCatalog.getCourseList().size(); i ++){

                // If match is found, remove the course
                if( id == courseCatalog.getCourseList().get(i).getId()){
                    courseCatalog.getCourseList().remove(i);

                    // Deletes the entry in the DB
                    jdbc.deleteCourseInDB(id);
                }
            }
            System.out.println("The Course has been removed");
        } else{
            System.out.println("The Course ID provided does not exist");
        }
    }

    public void getCourseCatalog(CourseCatalog courseCatalog){
        courseCatalog.setCourseCatalog(jdbc.getCourseCatalog());
    }
}

