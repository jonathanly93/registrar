package models;

import java.util.ArrayList;

/**
 *  Contains the list of courses available for students to take.
 *  Contains Array List of Course parameter to carry a list of courses.
 */
public class CourseCatalog {

    private ArrayList<Course> courseCatalog;

    /**
     * Constructor for Course Catalog.
     * All Course Catalog holds an array of courses.
     */
    public CourseCatalog() {

        courseCatalog = new ArrayList<>();
    }

    // Returns the courseCatalog
    public ArrayList<Course> getCourseList() {
        return courseCatalog;
    }

    // Sets the Course Catalog
    public void setCourseCatalog(ArrayList<Course> courseCatalog) {
        this.courseCatalog = courseCatalog;
    }
}


