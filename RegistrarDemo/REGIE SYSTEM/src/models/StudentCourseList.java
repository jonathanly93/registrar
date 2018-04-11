package models;

import java.util.ArrayList;

/**
 * StudentCourseList holds a list of StudentCourses
 */
public class StudentCourseList {

    private ArrayList<StudentCourse> studentCourseList;
    private int userId;

    /**
     * Constructor for StudentCourseList
     *
     * Contains a list of Student Course and an ID to identify to whom the list belongs to
     * @param userId : The ID of the user the StudentCourseList object belongs to
     */
    public StudentCourseList(int userId){
        studentCourseList = new ArrayList<>();
        this.userId = userId;
    }

    // Returns the student course list
    public ArrayList<StudentCourse> getStudentCourseList(){
        return studentCourseList;
    }

    // Sets the StudentCourseList
    public void setStudentCourseList(ArrayList<StudentCourse> scl){
        this.studentCourseList = scl;
    }

    // Returns the userID
    public int getUserId(){ return userId; }

    // Sets the userId
    public void setUserId(int userId){this.userId = userId;}

}
