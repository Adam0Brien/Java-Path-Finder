package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import model.CostOfPath;
import model.GraphLink;
import model.Room;
import model.GraphNode;
import utils.Graph;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class GalleryAPI {

    private List<Room> rooms;
    private HashMap<String, GraphNode<Room>> roomsHashMap;
    private List<String> names;
    private List<GraphNode<Room>> roomNodes;
    private Image galleryImage;
    private List<GraphNode<Room>> avoidedRooms;
    private List<String> waypointsList;


    public GalleryAPI() {
        this.waypointsList = new LinkedList<>();
        this.rooms = new LinkedList<>();
        this.names = new ArrayList<>();
        this.roomNodes = new LinkedList<>();
        this.roomsHashMap = new HashMap<>();
        this.avoidedRooms = new LinkedList<>();
        this.galleryImage = new Image(getClass().getResourceAsStream("/images/floorplan-level-2-july-2020.jpg"));
        readInDatabase();
        connectRooms();

    }

    public List<String> getWaypointsList() {
        return waypointsList;
    }

    public void setWaypointsList(List<String> waypointsList) {
        this.waypointsList = waypointsList;
    }

    public List<GraphNode<Room>> getAvoidedRooms() {
        return avoidedRooms;
    }

    public void setAvoidedRooms(List<GraphNode<Room>> avoidedRooms) {
        this.avoidedRooms = avoidedRooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<GraphNode<Room>> getRoomNodes() {
        return roomNodes;
    }

    public void setRoomNodes(List<GraphNode<Room>> roomNodes) {
        this.roomNodes = roomNodes;
    }

    public GraphNode<Room> findGraphNode(String roomName) {
        return roomsHashMap.get(roomName);
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
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
                Room r = new Room(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]), values[3], values[4]);
                //System.out.println(values[0] + ", " + values[1] + ", " + values[2] + ", " + values[3] + ", " + values[4]);
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
        GraphNode<Room> roomA = roomsHashMap.get(nodeA);
        GraphNode<Room> roomB = roomsHashMap.get(nodeB);
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
                //System.out.println(values[0] + values[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


//        System.out.println("Rooms Connected");
//        System.out.println("-----------------------");
//        for (GraphNode<Room> room : roomNodes){
//            System.out.println(room.data.getRoomName());
//            for (GraphLink adj : room.adjList){
//                System.out.println(adj.destNode.data);
//            }
//        }
    }


    public void avoidRoom(String smellyRoom) {
        System.out.println("Room to avoid: " + smellyRoom);
        for (GraphNode<Room> n : roomNodes) {
            for (GraphLink l : n.adjList) {
                GraphNode<Room> r = (GraphNode<Room>) l.destNode;
                if (n.data.getRoomName().equals(smellyRoom) || r.data.getRoomName().equals(smellyRoom)) {
                    l.cost = 1000; //makes the cost of the room not worth going through
                    avoidedRooms.add(r);
                }
            }
        }
    }


    public void resetAvoidRoom() {
        try {
            for (GraphNode<Room> n : roomNodes) {
                for (GraphLink l : n.adjList) {
                    GraphNode<Room> r = (GraphNode<Room>) l.destNode;
                    for (GraphNode<Room> room : avoidedRooms) {
                        if (n.data.getRoomName().equals(room.data.getRoomName()) || r.data.getRoomName().equals(room.data.getRoomName())) {
                            l.cost = 1; //makes the cost of the room not worth going through
                        }
                    }
                }
            }
            avoidedRooms.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<GraphNode<?>> depthWaypointSupport(String start, String destination){
        List<GraphNode<?>> pathList = new LinkedList<>();

        GraphNode<Room> startNode = findGraphNode(start);

        for (String waypoint : waypointsList){
            GraphNode<Room> waypointNode = findGraphNode(waypoint);
            CostOfPath temp = Graph.searchGraphDepthFirstCheapestPath(startNode, null, 0, waypointNode.data);
            for (GraphNode<?> room : temp.pathList){
                pathList.add(room);
            }
            startNode = waypointNode;
        }
        CostOfPath cp = Graph.searchGraphDepthFirstCheapestPath(startNode,null, 0,findGraphNode(destination).data);

        for (GraphNode<?> room : cp.pathList){
            pathList.add(room);
        }
        return pathList;
    }

}
