package models;

// Instructor class. In reality, the instructor should also have a composition of registered user, however
// I simplified for demonstration purposes
public class Instructor {

    private int id;
    private String name;

    public Instructor(int id){
        this.id = id;
    }

    public Instructor(){}

    public void setId(int id){
        this.id = id;
    }

    public int getId(){return id;}

    public void setName(String name){this.name = name;}

    public String getName(){return name;}
}
