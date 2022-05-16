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

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getExhibit() {
        return exhibit;
    }

    public void setExhibit(String exhibit) {
        this.exhibit = exhibit;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
