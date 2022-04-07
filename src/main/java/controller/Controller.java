package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import main.Driver;
import utils.Graph;
import model.GraphNode;
import model.Room;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    ImageView view;
    @FXML
    ImageView finalView;
    @FXML
    ComboBox<String> start, destination;
    @FXML
    AnchorPane anchorpane;

    private GalleryAPI galleryAPI;

    /**
     * On startup loads the map of the art gallery and makes all the connections for each room
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        galleryAPI = Driver.galleryAPI;
        view.setImage(galleryAPI.getGalleryImage());


//        String line = "";
//        try {
//            BufferedReader br = new BufferedReader(new FileReader("C:\\CA2\\src\\main\\resources\\teamproject\\ca2\\mappings.csv"));
//
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",");
//                Room r = new Room(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]));
//                GraphNode<Room> node = new GraphNode<>(r);
//                roomNodes.add(node);
//                names.add(values[0]);
//                rooms.add(r);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        start.setItems(names);
//        destination.setItems(names);
//
//
//        connectNodes(1, 2, roomNodes);
//        connectNodes(2, 3, roomNodes);
//        connectNodes(2, 4, roomNodes);
//        connectNodes(4, 5, roomNodes);
//        connectNodes(4, 6, roomNodes);
//        connectNodes(6, 7, roomNodes);
//        connectNodes(7, 8, roomNodes);
//        connectNodes(8, 9, roomNodes);
//        connectNodes(9, 10, roomNodes);
//        connectNodes(8, 15, roomNodes);
//        connectNodes(8, 14, roomNodes);
//        connectNodes(15, 16, roomNodes);
//        connectNodes(16, 17, roomNodes);
//        connectNodes(14, 16, roomNodes);
//        connectNodes(14, 26, roomNodes);
//        connectNodes(18, 26, roomNodes);
//        connectNodes(38, 39, roomNodes);
//        connectNodes(14, 18, roomNodes);
//        connectNodes(18, 19, roomNodes);
//        connectNodes(18, 21, roomNodes);
//        connectNodes(18, 22, roomNodes);
//        connectNodes(18, 24, roomNodes);
//        connectNodes(19, 20, roomNodes);
//        connectNodes(20, 21, roomNodes);
//        connectNodes(22, 23, roomNodes);
//        connectNodes(23, 24, roomNodes);
//        connectNodes(24, 25, roomNodes);
//        connectNodes(25, 28, roomNodes);
//        connectNodes(27, 28, roomNodes);
//        connectNodes(26, 27, roomNodes);
//        connectNodes(29, 28, roomNodes);
//        connectNodes(10, 11, roomNodes);
//        connectNodes(11, 12, roomNodes);
//        connectNodes(10, 13, roomNodes);
//        connectNodes(13, 29, roomNodes);
//        connectNodes(29, 14, roomNodes);
//        connectNodes(29, 30, roomNodes);
//        connectNodes(30, 31, roomNodes);
//        connectNodes(30, 32, roomNodes);
//        connectNodes(32, 33, roomNodes);
//        connectNodes(33, 34, roomNodes);
//        connectNodes(34, 35, roomNodes);
//        connectNodes(34, 41, roomNodes);
//        connectNodes(36, 35, roomNodes);
//        connectNodes(36, 37, roomNodes);
//        connectNodes(37, 32, roomNodes);
//        connectNodes(36, 40, roomNodes);
//        connectNodes(36, 38, roomNodes);
//        connectNodes(39, 12, roomNodes);
//        connectNodes(40, 44, roomNodes);
//        connectNodes(41, 42, roomNodes);
//        connectNodes(42, 43, roomNodes);
//        connectNodes(43, 44, roomNodes);
//        connectNodes(44, 45, roomNodes);
//        connectNodes(45, 46, roomNodes);
//
//
//        //still need to do the sainsbury wing
//
//
//        drawLine(0, 1); //testing drawLine it works lol
//
//
//        System.out.println("The following rooms are connected");
//        for (GraphNode<Room> r : roomNodes) {
//            System.out.println(r.data.getRoomName());
//            for (GraphLink l : r.adjList) {
//                System.out.println(l.destNode.data);
//            }
//        }

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

//    public void breadthFirstSearch(ActionEvent event) {
//        System.out.println(Graph.findPathBreadthFirstInterface(roomNodes.get(0),roomNodes.get(9)));
//    }





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

