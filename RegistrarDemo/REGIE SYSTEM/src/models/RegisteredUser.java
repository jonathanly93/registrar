package models;

/**
 * Basic information for any user of the REGGIE Registrar Project
 *
 * All users must have a name, email, password, and unique ID
 */
public class RegisteredUser {

    private String name;
    private String email;
    private String password;
    private int id;

    /**
     * Constructor for Registered User
     * @param name : sets the name of the user
     * @param email : sets the email for the user
     * @param password : sets the password for the user
     * @param id : sets the id for the user
     */
    public RegisteredUser(String name, String email, String password, int id){

        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    // Empty constructor
    public RegisteredUser(){}

    // Sets the name for the Registered User
    public void setName(String name){
    }

    // Returns the name of the Registered User
    public String getName(){
        return name;
    }

    // Sets the email for the registered User
    public void setEmail(String email){
        this.email = email;
    }

    // Gets the email for the registered user
    public String getEmail(){
        return email;
    }

    // Sets the password for the registered user
    public void setPassword(String password){
        this.password = password;
    }

    // Gets the password for the registered user
    public String getPassword(){
        return password;
    }

    // Sets the ID for the registered user
    public void setId(int id){
        this.id = id;
    }

    // Gets the ID for the registered user
    public int getId(){
        return id;
    }


}
