package model;

public class Room {

    private String roomName;
    private int xCoord;
    private int yCoord;


    public Room(String roomName, int xCoord, int yCoord) {
        this.roomName = roomName;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
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
