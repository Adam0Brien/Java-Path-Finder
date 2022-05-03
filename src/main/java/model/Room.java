package model;

public class Room {

    private String roomName;
    private String exhibit;
    private int xCoord;
    private int yCoord;
    private String date;

    public Room(String roomName, int xCoord, int yCoord,String exhibit,String date) {
        this.roomName = roomName;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.exhibit = exhibit;
        this.date = date;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord(){
        return yCoord;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomName='" + roomName + '\'' +
                ", xCoord=" + xCoord +
                ", yCoord=" + yCoord +
                '}';
    }
}
