package controllers;

import database.JDBC;
import models.Section;

public class SectionController {

    private JDBC jdbc;
    public SectionController(){
        jdbc = new JDBC();
    }

    // Pulls out section from section table in DB when given the id of the section
    public Section retreiveSection(int sectionId){
        return jdbc.retrieveSection(sectionId);
    }

}
