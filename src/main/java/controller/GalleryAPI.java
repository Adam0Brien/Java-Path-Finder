package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import model.GraphLink;
import model.Room;
import model.GraphNode;

import java.io.*;
import java.security.cert.CertificateRevokedException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static utils.Graph.connectNodes;

public class GalleryAPI {

    private List<Room> rooms;
    private HashMap<String, GraphNode<Room>> hashMap;
    private ObservableList<String> names;
    private List<GraphNode<Room>> roomNodes;
    private Image galleryImage;

    public GalleryAPI() {
        this.rooms = new LinkedList<>();
        this.names = FXCollections.observableArrayList();
        this.roomNodes = new LinkedList<>();
        this.hashMap = new HashMap<>();
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
    public void generateMultipleRoutes() {
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
                hashMap.put(values[0], node);
                names.add(values[0]);
                rooms.add(r);
            }
        } catch (IOException e) {
            System.err.println("File Load Failed");
            e.printStackTrace();
        }
    }

    private void connectRooms() {
        connectNodes(1, 2, roomNodes);
        connectNodes(2, 3, roomNodes);
        connectNodes(2, 4, roomNodes);
        connectNodes(4, 5, roomNodes);
        connectNodes(4, 6, roomNodes);
        connectNodes(6, 7, roomNodes);
        connectNodes(7, 8, roomNodes);
        connectNodes(8, 9, roomNodes);
        connectNodes(9, 10, roomNodes);
        connectNodes(8, 15, roomNodes);
        connectNodes(8, 14, roomNodes);
        connectNodes(15, 16, roomNodes);
        connectNodes(16, 17, roomNodes);
        connectNodes(14, 16, roomNodes);
        connectNodes(14, 26, roomNodes);
        connectNodes(18, 26, roomNodes);
        connectNodes(38, 39, roomNodes);
        connectNodes(14, 18, roomNodes);
        connectNodes(18, 19, roomNodes);
        connectNodes(18, 21, roomNodes);
        connectNodes(18, 22, roomNodes);
        connectNodes(18, 24, roomNodes);
        connectNodes(19, 20, roomNodes);
        connectNodes(20, 21, roomNodes);
        connectNodes(22, 23, roomNodes);
        connectNodes(23, 24, roomNodes);
        connectNodes(24, 25, roomNodes);
        connectNodes(25, 28, roomNodes);
        connectNodes(27, 28, roomNodes);
        connectNodes(26, 27, roomNodes);
        connectNodes(29, 28, roomNodes);
        connectNodes(10, 11, roomNodes);
        connectNodes(11, 12, roomNodes);
        connectNodes(10, 13, roomNodes);
        connectNodes(13, 29, roomNodes);
        connectNodes(29, 14, roomNodes);
        connectNodes(29, 30, roomNodes);
        connectNodes(30, 31, roomNodes);
        connectNodes(30, 32, roomNodes);
        connectNodes(32, 33, roomNodes);
        connectNodes(33, 34, roomNodes);
        connectNodes(34, 35, roomNodes);
        connectNodes(34, 41, roomNodes);
        connectNodes(36, 35, roomNodes);
        connectNodes(36, 37, roomNodes);
        connectNodes(37, 32, roomNodes);
        connectNodes(36, 40, roomNodes);
        connectNodes(36, 38, roomNodes);
        connectNodes(39, 12, roomNodes);
        connectNodes(40, 44, roomNodes);
        connectNodes(41, 42, roomNodes);
        connectNodes(42, 43, roomNodes);
        connectNodes(43, 44, roomNodes);
        connectNodes(44, 45, roomNodes);
        connectNodes(45, 46, roomNodes);

//        System.out.println("The following rooms are connected");
//        for (GraphNode<Room> r : roomNodes) {
//            System.out.println(r.data.getRoomName());
//            for (GraphLink l : r.adjList) {
//                System.out.println(l.destNode.data);
//            }
//        }
    }
}
