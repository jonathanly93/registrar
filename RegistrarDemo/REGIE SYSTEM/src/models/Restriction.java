package models;


/**
 * Restrictions prevent student enrollment
 */
public class Restriction {

    private String title;
    private String description;
    private int restrictionId;
    private int userId;


    /**
     * Constructor to build a restriction object
     *
     * @param title : the restriciton's title
     * @param description : the description for the restriction
     * @param restrictionId : the restriction unique id
     */
    public Restriction(String title, String description, int restrictionId){
        this.title = title;
        this.description = description;
        this.restrictionId = restrictionId;
    }

    // Returns the title of the restriction
    public String getTitle(){
        return title;
    }

    // Sets the title of the restriction
    public void setTitle(String title){
        this.title = title;
    }

    // Returns the restriction description
    public String getDescription(){
        return description;
    }

    // Sets the restriction description
    public void setDescription(String description){
        this.description = description;
    }

    // Returns the restriction's id
    public int getRestrictionId(){ return restrictionId; }

    // Sets the restriction's id
    public void setRestrictionId(int restrictionId){ this.restrictionId = restrictionId; }


    // returns the user id
    public int getUserId(){return userId;}

    // sets the user id
    public void setUserId(int userId){this.userId = userId;}


}
