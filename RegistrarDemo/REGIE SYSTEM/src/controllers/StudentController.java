package controllers;

import database.JDBC;
import models.*;

public class StudentController {

    private JDBC jdbc;

    public StudentController(){
        jdbc = new JDBC();
    }

    /**
     * Adds a course into the Student's StudentCourseList and create an entry for the studentcourse table in the
     * Registrar DB
     * @param section : the section to add
     * @param student : The person who is taking the course
     */
    public void addStudentCourse(Student student, Section section){

        // Checks that the student has no restrictions and
        // is enrolled in less than the maximum number of courses allowed
        if(student.getMaxEnrolledCourse() > student.getStudentCourseList().getStudentCourseList().size() &&
                student.getRestrictionList().getRestrictionList().size() == 0) {

            // Checks to see if the student is alreaedy enrolled
            boolean enrollment = jdbc.checkCourseEnrollment(student.getRegisteredUser().getId(), section.getCourse().getId());

            if (!enrollment) {
                // Creates new student course if not enrolled
                StudentCourse studentCourse = new StudentCourse(true, student.getRegisteredUser().getId(), section);
                // Append missing student course information
                StudentCourseController studentCourseController = new StudentCourseController();
                studentCourseController.appendInformation(studentCourse);


                // Add the StudentCourse into the student's StudentCourseList
                student.getStudentCourseList().getStudentCourseList().add(studentCourse);

                // Add an entry into the student course table in the Registrar database. Grade is set to "P" for in progress
                jdbc.addStudentCourseToDB(section.getCourse().getId(), section.getSectionId(),
                        section.getCourse().getCourseName(), section.getCourse().getDepartment(),
                        section.getInstructor().getId(), section.getRoom().getRoomId(),
                        section.getTime(), section.getMaxCapacity(), section.getCourse().getCredit(),
                        'P', 1, section.getQuarter(), student.getRegisteredUser().getId());

                System.out.println("The course was added");

            } else {
                System.out.println("You are already enrolled in that course!");
            }
        } else if(student.getRestrictionList().getRestrictionList().size() > 0){
            System.out.println("You have a restriction. Please handle them before registering.");
        } else{System.out.println("You are at max capacity. Please drop a course before enrolling.");}
    }

    /**
     * Removes the studentcourse from the Student's StudentCourseList
     * Removes the entry in the studentcourse table in the Registrar DB
     * with the given courseID and userID
     * @param courseId : The course ID
     * @param student : The student who is taking the course.
     */
    public void removeStudentCourse(int courseId, Student student) {

        // Can only remove a course if the student is currently enrolled
        int progressStatus = jdbc.checkCourseActivity(courseId, student.getRegisteredUser().getId());

        // 1 signifies that the course is currently in progress.
        if (progressStatus == 1) {
            // Find and remove the studentcourse from the studentcourselist
            for (int i = 0; i < student.getStudentCourseList().getStudentCourseList().size(); i++) {
                if (courseId == student.getStudentCourseList().getStudentCourseList().get(i).getSection().getCourse().getId()) {
                    // Removes the course from the list.
                    System.out.println("The course has been removed");
                    student.getStudentCourseList().getStudentCourseList().remove(i);
                }
            }

            // Remove the entry from the studentcourse table in the Registrar database
            jdbc.deleteStudentCourseInDB(courseId, student.getRegisteredUser().getId());

        } else if (progressStatus == 0) {
            System.out.println("Cannot remove a course that has been completed");
        } else{ System.out.println("The course does not exist");}
    }
}
