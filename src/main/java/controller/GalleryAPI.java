package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import model.GraphLink;
import model.Room;
import model.GraphNode;
import utils.Graph;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class GalleryAPI {

    private List<Room> rooms;
    private HashMap<String, GraphNode<Room>> roomsHashMap;
    private ObservableList<String> names;
    private List<GraphNode<Room>> roomNodes;
    private Image galleryImage;

    public GalleryAPI() {
        this.rooms = new LinkedList<>();
        this.names = FXCollections.observableArrayList();
        this.roomNodes = new LinkedList<>();
        this.roomsHashMap = new HashMap<>();
        this.galleryImage = new Image(getClass().getResourceAsStream("/images/floorplan-level-2-july-2020.jpg"));
        readInDatabase();
        connectRooms();
    }

    public Image getGalleryImage() {
        return galleryImage;
    }

    public void findShortestRoute() {
        //using the CostOfPath class here... somewhere anyways
    }


    //We could generate ALL routes and use something like noise reduction to take away all the super long ones
    public List<List<GraphNode<?>>> generateMultipleRoutes(String start, String destination) {
        GraphNode<Room> startNode = roomsHashMap.get(start);
        GraphNode<Room> destNode = roomsHashMap.get(destination);
        return Graph.findAllPathsDepthFirst(startNode, null, destNode);
    }

    private void readInDatabase() {
        String line = "";
        try {
            File file = new File("src/main/resources/csv/mappings.csv");
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Room r = new Room(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                GraphNode<Room> node = new GraphNode<>(r);
                roomNodes.add(node);
                roomsHashMap.put(values[0], node);
                names.add(values[0]);
                rooms.add(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectNodes(String nodeA, String nodeB) {
        //roomNodes.get(nodeA).connectToNodeUndirected(roomNodes.get(nodeB), getCost(nodeA, nodeB, roomNodes));
        GraphNode<Room> roomA = roomsHashMap.get(nodeA);
        //System.out.println(roomA);
        GraphNode<Room> roomB = roomsHashMap.get(nodeB);
        //System.out.println(roomB);
        roomA.connectToNodeUndirected(roomB, 1);
    }

    private void connectRooms() {

        String line = "";
        try {
            File file = new File("src/main/resources/csv/RoomConnection.csv");
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                connectNodes(values[0], values[1]);
                System.out.println(values[0] + values[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        connectNodes(names.get(0), "Room 2"); // Room 1 + Room 2
//        connectNodes("Room 4", "Room 2");
//        connectNodes("Room 6", "Room 4");
//        connectNodes("Room 5", "Room 4");
//        connectNodes("Room 6", "Room 7");
//        connectNodes("Room 7", "Room 8");
//        connectNodes("Room 8", "Room 9");
//        connectNodes("Room 9", "Room 10");
//        connectNodes("Room 9", "Room 51");
//        connectNodes("Room 9", "Room 15");

        System.out.println("Rooms Connected");
        System.out.println("-----------------------");
        for (GraphNode<Room> room : roomNodes){
            System.out.println(room.data.getRoomName());
            for (GraphLink adj : room.adjList){
                System.out.println(adj.destNode.data);
            }
        }
    }
}
