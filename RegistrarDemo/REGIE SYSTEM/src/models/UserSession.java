package models;

/**
 * User Session holds a user and a password. Starts a new session when logged in.
 */
public class UserSession {

    private boolean status;
    private RegisteredUser registeredUser;
    private Student student;

    public UserSession() {
        status = false;
    }

    // Gets the status. True if a session is running
    public boolean getStatus(){
        return status;
    }

    // Sets the status
    public void setStatus(boolean status){this.status = status;}

    // Gets the registered user of the session
    public RegisteredUser getRegisteredUser(){
        return registeredUser;
    }

    // Sets the registered user in the session
    public void setRegisteredUser(RegisteredUser registeredUser){this.registeredUser = registeredUser;}

    public Student getStudent(){
        return student;
    }

    public void setStudent(Student student){
        this.student = student;
    }
}
