package models;

import java.util.ArrayList;

/**
 * Restriction List holds an list of Restriction objects.
 */
public class RestrictionList {

    private ArrayList<Restriction> restrictionList;
    private int userId;

    /**
     * Constructor for the RestrictionList
     * All RestrictionList contains an array list
     * of  Restrictions
     */
    public RestrictionList(int userId){
        restrictionList = new ArrayList<>();
        this.userId = userId;
    }

    public void setRestrictionList(ArrayList<Restriction> restrictionList){
        this.restrictionList = restrictionList;
    }
    // Returns the restrictionList
    public ArrayList<Restriction> getRestrictionList(){
        return restrictionList;
    }

    // Sets the userId
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Returns the userId
    public int getUserId(){
        return userId;
    }

}
