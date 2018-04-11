package controllers;


import database.JDBC;
import models.*;


// Controls the UserSession
public class UserSessionController {

   private JDBC jdbc;

   public UserSessionController(){
       jdbc = new JDBC();
   }
    // Starts a new login session
    public void startSession(String email, String password, UserSession userSession){

        // Checks to see if email exist in Registrar DB
        if(jdbc.checkEmail(email)) {

            // Checks if password matches
            if(password.equals(jdbc.getUserPassword(email))) {

                // Sets the user session status to true
                userSession.setStatus(true);

                // Finds and sets the student information
                userSession.setRegisteredUser(jdbc.getRegisteredUser(email));

                System.out.println("Login Successful!");
                startStudentSession(userSession);
            }
            else{ System.out.println("Incorrect Password");}
        }
        else{ System.out.println("Email does not exist"); }
    }

    public void endSession(UserSession userSession){
        if(userSession.getStatus()) {
            userSession.setStatus(false);
            System.out.println("Ending User Session");
        } else{ System.out.println("User session has not started"); }
    }

    public void startStudentSession(UserSession userSession){


        RestrictionList restrictionList = new RestrictionList(userSession.getRegisteredUser().getId());
        StudentCourseList studentCourseList = new StudentCourseList(userSession.getRegisteredUser().getId());
        restrictionList.setRestrictionList(jdbc.getRestrictionList(userSession.getRegisteredUser().getId()));
        studentCourseList.setStudentCourseList(jdbc.getStudentCourseList(userSession.getRegisteredUser().getId()));
        Student student = new Student(restrictionList,studentCourseList,userSession.getRegisteredUser());

        // Append missing information
        StudentCourseListController studentCourseListController = new StudentCourseListController();
        studentCourseListController.appendInformation(studentCourseList.getStudentCourseList());

        userSession.setStudent(student);
    }

}
