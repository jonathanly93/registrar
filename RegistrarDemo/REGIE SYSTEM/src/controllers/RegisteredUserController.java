package controllers;

import database.JDBC;
import models.RegisteredUser;

// All logic classes will be moved under the rightful class after submission.
// This class is used for the JUNIT testing.
public class RegisteredUserController {

    private JDBC jdbc;
    private RandomNumberGenerator rng;
    private RegisteredUser registeredUser;

    /**
     * Creates a new user and adds entry to the Student table
     * in the Registrar DB
     *
     * @param name : name of the student
     * @param email : email of the student
     * @param password : password for the account
     */
    public void createRegisteredUser(String name, String email, String password){

        rng = new RandomNumberGenerator();
        jdbc = new JDBC();

        // Get first instance of unique id
        int uniqueId = rng.getRandomNumber(7);

        // Loops until a unique id is found
        while(!jdbc.getUniqueUserId(uniqueId)){
            uniqueId = rng.getRandomNumber(7);
        }

        // Creates a new Registered Uesr
        registeredUser = new RegisteredUser(name,email,password,uniqueId);

        // Adds the entry into the student table in the registrar DB
        jdbc.addStudentToDB(name,email,password,uniqueId);

        System.out.println("New user successfully created!");
    }


    /**
     * Changes the Registered User's password
     *
     * @param oldPassword : the user's old password
     * @param newPassword : the password to replace the old password
     * @param email : the email of the user
     */
    public void changePassword(String oldPassword, String newPassword, String email){

        jdbc = new JDBC();

        if(jdbc.getUserPassword(email).equals(oldPassword)) {

            jdbc.changePassword(newPassword,email);
            System.out.println("Password successfully changed!");
        }else{
            System.out.println("You did not enter the previous password correctly");
        }
    }
}
