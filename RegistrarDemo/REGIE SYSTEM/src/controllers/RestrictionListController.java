package controllers;

import database.JDBC;
import models.Restriction;
import models.RestrictionList;

public class RestrictionListController {

    private Restriction restriction;
    private JDBC jdbc;
    private RandomNumberGenerator rng;


    public RestrictionListController(){
        jdbc = new JDBC();
        rng = new RandomNumberGenerator();
    }

    /**
     * NOTE NOT FINISHED
     *
     * Adds a new restriction to the restrictionList as well as an entry into the database
     * @param title : the title of the restriction
     * @param description : the restriction's description
     * @param restrictionList : the restrictionlist that is to be appended
     */
    public void addRestriction(String title, String description, RestrictionList restrictionList){

        //Generates random unique ID for the restriction
        int uniqueId = rng.getRandomNumber(7);
        while(!jdbc.getUniqueRestrictionId(uniqueId)){
            uniqueId = rng.getRandomNumber(7);
        }
        // Creates a new restriction
        restriction = new Restriction(title, description, uniqueId);

        // Adds the restriction to the restriction list.
        restrictionList.getRestrictionList().add(restriction);

        // Adds the restriction to the restriction table in the Registrar DB
       // jdbc.addRestrictionToDB(title, description, uniqueId);
    }

    /**
     * Deletes a restriction when provided the restriction id
     * @param id : the ID of the restriction
     * @param restrictionList : the list from where the restriction is removed
     */
    public void deleteRestriction(int id ,RestrictionList restrictionList){

        // Finds out if the id provided exist
        if(jdbc.isRestrictionAvailable(id)){

            // Matches id with the course ID and remove the course
            for(int i = 0; i < restrictionList.getRestrictionList().size(); i ++){

                // If match is found, remove the course
                if( id == restrictionList.getRestrictionList().get(i).getRestrictionId()){
                    restrictionList.getRestrictionList().remove(i);

                    // Deletes the entry in the DB
                    jdbc.deleteRestrictionInDB(id);
                }
            }
            System.out.println("The Course has been removed");
        } else{
            System.out.println("The Course ID provided does not exist");
        }


    }
}
