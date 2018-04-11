package models;

import java.util.ArrayList;

// A section has many compositions. It represents a section course which a student participates
public class Section {

    private int sectionId;
    private Instructor instructor;
    private Course course;
    private String quarter;
    private Room room;
    private int time;
    private int maxCapacity;
    private ArrayList<RegisteredUser> registeredUserArrayList;


    /**
     *
     * @param sectionId : The section ID
     * @param instructor : The instructor who is teaching the course
     * @param course : The course information
     * @param quarter : The quarter at which it is taught
     * @param room : The room at which it is taught in
     * @param time : The time at which the class is taking place
     * @param maxCapacity : The number of students that can enroll
     * @param registeredUsersArrayList : A list of students in the course
     */
    public Section(int sectionId, Instructor instructor, Course course, String quarter,
                   Room room, int time, int maxCapacity, ArrayList<RegisteredUser>registeredUsersArrayList){

        this.sectionId = sectionId;
        this.instructor = instructor;
        this.course = course;
        this.quarter = quarter;
        this.room = room;
        this.time = time;
        this.maxCapacity = maxCapacity;
        this.registeredUserArrayList = registeredUsersArrayList;

    }

    public Section(){}

    public int getSectionId(){ return sectionId; }

    public void setSectionId(int section_id){ this.sectionId = section_id; }

    public Instructor getInstructor(){ return instructor;}

    public void setInstructor(Instructor instructor){ this.instructor = instructor;}

    public Course getCourse(){ return  course;}

    public void setCourse(Course course){ this.course = course;}

    public String getQuarter(){return quarter;}

    public void setQuarter(String quarter){this.quarter = quarter;}

    public Room getRoom(){ return room;}

    public void setRoom(Room room){ this.room = room;}

    public int getTime(){ return time;}

    public void setTime(int time){ this.time = time;}

    public int getMaxCapacity(){ return maxCapacity;}

    public void setMaxCappacity(int maxCapacity){ this.maxCapacity = maxCapacity;}

    public ArrayList<RegisteredUser> getRegisteredUserArrayList(){ return registeredUserArrayList;}

    public void setRegisteredUserArray(ArrayList<RegisteredUser> registeredUserArrayList){ this.registeredUserArrayList = registeredUserArrayList;}

}
