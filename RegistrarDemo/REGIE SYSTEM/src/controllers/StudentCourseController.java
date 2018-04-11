package controllers;

import database.JDBC;
import models.Instructor;
import models.Room;
import models.StudentCourse;

public class StudentCourseController{

    private JDBC jdbc;

    public StudentCourseController(){
        jdbc = new JDBC();
    }

    // Side fix for not properly setting student arraylist, room, and instructor
    // due to 1-limit connection at a time in DB
    public void appendInformation(StudentCourse studentCourse){

        // sets the student array list previously empty.
        // Sets the student ID first.
        studentCourse.getSection().setRegisteredUserArray(jdbc.getRegisteredUserFix(studentCourse.getSection().getSectionId()));

        // Sets the name of the professor, previously only containing id
        Instructor instructor = new Instructor();
        instructor.setId(studentCourse.getSection().getInstructor().getId());
        instructor.setName(jdbc.getInstructorName(studentCourse.getSection().getInstructor().getId()));
        studentCourse.getSection().setInstructor(instructor);

        // Sets the name of the room, previously only containing id
        Room room = new Room();
        room.setRoomId(studentCourse.getSection().getRoom().getRoomId());
        room.setRoomName(jdbc.getRoomName(studentCourse.getSection().getRoom().getRoomId()));
        studentCourse.getSection().setRoom(room);

    }

}
