package models;

import java.util.ArrayList;

/**
 * Student course has a composition of the Course Class.
 * StudentCourse creates objects that represents courses belonging to a student.
 * In other words, these are the courses that a student will see once they
 * register for a course.
 *
 * It has extra parameters including: grade, courseActivity, quarter, and studentId
 */
public class StudentCourse{

    private char grade;
    private boolean courseActivity;
    private int studentId;
    private Section section;

    /**
     * StudentCourse Constructor.
     *
     * Grade is left blank because grade assignment is done periodically
     * and is not initially assigned.
     *
     * @param courseActivity : True if the course is currently in progress, False if the course is completed
     * @param studentId : The student ID of the student attending the course
     * @param section : The section that the student is taking
     */
    public StudentCourse(boolean courseActivity, int studentId, Section section){

        this.courseActivity = courseActivity;
        this.studentId = studentId;
        this.section = section;

    }

    public StudentCourse(char grade, boolean courseActivity, int studentId, Section section){
        this.grade = grade;
        this.courseActivity = courseActivity;
        this.studentId = studentId;
        this.section = section;
    }

    public StudentCourse(){}

    // Returns the letter grade received in the course
    public char getGrade(){
        return grade;
    }

    // Sets the letter grade received in the course
    public void setGrade(char grade){
        this.grade = grade;
    }

    // If true, the student is currently enrolled in the course.
    public boolean getCourseActivity(){
        return courseActivity;
    }

    // Sets the current activity of the course
    public void setCourseActivity(boolean courseActivity){
        this.courseActivity = courseActivity;
    }

    // Return the studentId for the student enrolled in the course
    public int getStudentId(){
        return studentId;
    }

    // Sets the student Id for hte student enrolled in the course
    public void setStudentId(int studentId){
        this.studentId = studentId;
    }

    // Returns the section object
    public Section getSection(){
        return section;
    }

    // Sets the section object
    public void setSection(Section section){
        this.section = section;
    }
}
