package models;

// A very simplified blueprint for a room. We only need the name and ID for demonstration purposes
// Each section of a course will have a room object
public class Room {

    private int roomId;
    private String roomName;

    public Room(int roomId){
        this.roomId = roomId;

    }
    public Room(){}

    public void setRoomId(int roomId){this.roomId = roomId;}
    public int getRoomId(){return roomId;}
    public void setRoomName(String roomName){this.roomName = roomName;}
    public String getRoomNumber(){return roomName;}
}
