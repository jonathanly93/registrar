package controllers;

import models.StudentCourse;

import java.util.ArrayList;

public class StudentCourseListController {

    // Method to append missing data in student course.
    public void appendInformation(ArrayList<StudentCourse> studentCourseArrayList){

        StudentCourseController studentCourseController = new StudentCourseController();
        for(int i = 0 ; i < studentCourseArrayList.size(); i++){
            studentCourseController.appendInformation(studentCourseArrayList.get(i));
        }

    }


}
