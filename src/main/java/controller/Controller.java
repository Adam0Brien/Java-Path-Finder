package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import main.Driver;
import model.CostOfPath;
import utils.Graph;
import model.GraphNode;
import model.Room;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    ImageView view;
    @FXML
    ImageView finalView;
    @FXML
    ComboBox<String> start, destination,waypoints;
    @FXML
    AnchorPane anchorpane;
    @FXML
    ListView waypointView;
    @FXML
    ListView avoidView;

    @FXML
    AnchorPane mainPane;
    @FXML
    ComboBox<String> avoidRoom;

    private GalleryAPI galleryAPI;
    private List<String> waypointsList;



    /**
     * On startup loads the map of the art gallery and makes all the connections for each room
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.waypointsList = new LinkedList<>();
        galleryAPI = Driver.galleryAPI;
        view.setImage(galleryAPI.getGalleryImage());

        start.getItems().addAll(galleryAPI.getNames());
        destination.getItems().addAll(galleryAPI.getNames());
        avoidRoom.getItems().addAll(galleryAPI.getNames());
        waypoints.getItems().addAll(galleryAPI.getNames());


    }


    /**
     * Key Methods
     */

    public void findCoords(ActionEvent event) throws IOException {

        view.setOnMousePressed(e -> {
            view.getImage().getPixelReader().getColor((int) e.getX(), (int) e.getY());
            System.out.println("\nX Cord: " + e.getX());
            System.out.println("\nY Cord: " + e.getY());
        });
    }

    public void breadthFirstSearch(ActionEvent actionEvent) {

    }

    public void addWaypoint() {
        waypointView.getItems().addAll(waypoints.getValue());
        waypointsList.add(waypoints.getValue());
        //System.out.println(waypointsList.toString());
    }


    public int findRoom(ArrayList<Room> array, String name) {
        for (Room node : array) {
            if (node.getRoomName().equals(name)) {
                return array.indexOf(node);
            }
        }
        return -1;
    }
    public void findPathDij(ActionEvent actionEvent) {

        List<GraphNode<?>> pathList = new ArrayList<>();
        if (!waypointsList.isEmpty()) {
            //pathList = galleryAPI.waypointSupport(findRoom((ArrayList<Room>) galleryAPI.getRooms(), start.getValue()), findRoom((ArrayList<Room>) galleryAPI.getRooms(), destination.getValue()), waypointsList.get, galleryAPI.getRoomNodes(), galleryAPI.getRooms());
        } else {
            CostOfPath cp = Graph.findCheapestPathDijkstra(galleryAPI.findGraphNode(start.getValue()), galleryAPI.findGraphNode(destination.getValue()).data);

            pathList = cp.pathList;
            System.out.println(cp.pathCost);
        }



        //
        // Option of doing a hash set however the hash set doesn't keep the path in order
        //
        //HashSet<GraphNodeDw<?>> hs = new HashSet<>(pathList);
        drawSinglePath(pathList,Color.BLUE);
        for (GraphNode<?> n : pathList) {
            GraphNode<Room> l = (GraphNode<Room>) n;

        }
    }

    public void drawSinglePath(List<GraphNode<?>> pathList,Color c) {
        mainPane.getChildren().clear();
        for (int i = 0; i < pathList.size(); i++) {
            GraphNode<Room> node = (GraphNode<Room>) pathList.get(i);

            if (i + 1 < pathList.size()) {
                GraphNode<Room> nextNode = (GraphNode<Room>) pathList.get(i + 1);
                Line l = new Line(node.data.getXCoord(), node.data.getYCoord() + 50, nextNode.data.getXCoord(), nextNode.data.getYCoord() + 50);
                l.setFill(c);
                l.setStroke(c);
                l.setStrokeWidth(5);
                mainPane.getChildren().add(l);
            }

        }
    }


    public void avoidThisRoom() {
        if (galleryAPI.getAvoidedRooms().contains(galleryAPI.findGraphNode(avoidRoom.getValue()))) return;
            galleryAPI.avoidRoom(avoidRoom.getValue());
            avoidView.getItems().add(avoidRoom.getValue());
            avoidRoom.getItems().remove(avoidRoom.getValue());

    }


    public void updateSelectionsStart(){

    }

    public void updateSelectionsAvoid(){

    }


    public void updateSelectionsDestination(){

    }



//
//    /**
//     * Draws a line between two nodes
//     * @param nodeA starting node
//     * @param nodeB destination node
//     */
//    public void drawLine(int nodeA, int nodeB) {
//        int nodeAX = roomNodes.get(nodeA).data.getXCoord();
//        int nodeAY = roomNodes.get(nodeA).data.getYCoord();
//        int nodeBX = roomNodes.get(nodeB).data.getXCoord();
//        int nodeBY = roomNodes.get(nodeB).data.getYCoord();
//
//        Line line = new Line(nodeAX, nodeAY, nodeBX, nodeBY);
//        line.setStartX(nodeAX);
//        line.setStartY(nodeAY);
//        line.setEndX(nodeBX);
//        line.setEndY(nodeBY);
//        line.setFill(Color.LIMEGREEN);
//        line.setStrokeWidth(2);
//        line.setLayoutX(view.getLayoutX());
//        line.setLayoutY(view.getLayoutY());
//        ((AnchorPane) view.getParent()).getChildren().add(line);
//    }

}

