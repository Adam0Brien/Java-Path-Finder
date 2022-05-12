package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import main.Driver;
import model.CostOfPath;
import model.Pixel;
import utils.Algo;
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
    ComboBox<String> start, destination, waypoints,pointsOfInterest;
    @FXML
    AnchorPane anchorpane;
    @FXML
    ListView waypointView;
    @FXML
    ListView avoidView;
    @FXML
    TreeView<String> routeTreeView;
    @FXML
    AnchorPane mainPane;
    @FXML
    ComboBox<String> avoidRoom;
    @FXML
    ToggleButton startCorrdsButton, destinationCorrdsButton, breadthFirstButton;
    @FXML
    VBox breadthFirstBox;
    @FXML
    Label startCorrdsLabel, destinationCorrdsLabel;

    private GalleryAPI galleryAPI;
    private List<String> waypointsList;
    private Pixel startPixel, destinationPixel;


    /**
     * On startup loads the map of the art gallery and makes all the connections for each room
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        galleryAPI = Driver.galleryAPI;
        this.waypointsList = galleryAPI.getWaypointsList();
        view.setImage(galleryAPI.getGalleryImage());

        System.out.println(galleryAPI.getGalleryImage().getWidth() + "x" + galleryAPI.getGalleryImage().getHeight());
        System.out.println(galleryAPI.getBreadthSearchImage().getWidth() + "x" + galleryAPI.getGalleryImage().getHeight());
        System.out.println(view.getFitWidth() + "x" + view.getFitHeight());
        ToggleGroup toggleGroup = new ToggleGroup();
        startCorrdsButton.setToggleGroup(toggleGroup);
        destinationCorrdsButton.setToggleGroup(toggleGroup);

        start.getItems().addAll(galleryAPI.getNames());
        destination.getItems().addAll(galleryAPI.getNames());
        avoidRoom.getItems().addAll(galleryAPI.getNames());
        waypoints.getItems().addAll(galleryAPI.getNames());
        pointsOfInterest.getItems().addAll(galleryAPI.getPointsOfInterest());
        breadthFirstBox.setVisible(false);

    }



    public void setDeadthFirstSearchPixels(MouseEvent e) {
        if (!breadthFirstButton.isSelected()) return;
        int x = (int) ((e.getX() / view.getFitWidth()) * galleryAPI.getBreadthSearchImage().getWidth());
        int y = (int) ((e.getY() / view.getFitHeight()) * galleryAPI.getBreadthSearchImage().getHeight());
        System.out.println("E: " + e.getX() + ", " + e.getY());
        System.out.println("Calculated: " + x + ", " + y);
        if (!galleryAPI.getBreadthSearchImage().getPixelReader().getColor(x, y).equals(Color.BLACK)) {
            if (startCorrdsButton.isSelected()) {
                startPixel = new Pixel(x, y);
                startCorrdsLabel.setText("X: " + x + " Y: " + y);
                startCorrdsButton.setSelected(false);
            }
            if (destinationCorrdsButton.isSelected()) {
                destinationPixel = new Pixel(x, y);
                destinationCorrdsLabel.setText("X: " + x + " Y: " + y);
                destinationCorrdsButton.setSelected(false);
            }
//            System.out.println("True");
        }
//        System.out.println(e.getX() +  ", " + e.getY());
//        System.out.println(x + ", " +  y);
//        Color c = galleryAPI.getGalleryImage().getPixelReader().getColor(x,y);
//        System.out.println(c.getRed()*255 + ", " + c.getGreen()*255 + ", " + c.getBlue()*255);
    }


    public void addWaypoint() {
        waypointView.getItems().addAll(waypoints.getValue());
        waypointsList.add(waypoints.getValue());
    }


    public void findDepthpath(ActionEvent actionEvent) {
        List<GraphNode<?>> newPath;
        if (!waypointsList.isEmpty()) {
            newPath = galleryAPI.waypointSupport(start.getValue(), destination.getValue(), Algo.Depth);
        } else {
            CostOfPath cp = Graph.searchGraphDepthFirstCheapestPath(galleryAPI.findGraphNode(start.getValue()), null, 0, galleryAPI.findGraphNode(destination.getValue()).data);

            newPath = cp.pathList;
            System.out.println(cp.pathCost);
        }
        drawSinglePath(newPath, Color.RED);
    }

    public void findAllDepthpaths(ActionEvent actionEvent) {
        List<List<GraphNode<?>>> t;
        if (!waypointsList.isEmpty()) {
            //pathList = galleryAPI.waypointSupport(findRoom((ArrayList<Room>) galleryAPI.getRooms(), start.getValue()), findRoom((ArrayList<Room>) galleryAPI.getRooms(), destination.getValue()), waypointsList.get, galleryAPI.getRoomNodes(), galleryAPI.getRooms());
        } else {
            t = Graph.findAllPathsDepthFirst(galleryAPI.findGraphNode(start.getValue()), null, galleryAPI.findGraphNode(destination.getValue()).data);
            TreeItem<String> root = new TreeItem<>("Routes");
            for (List<GraphNode<?>> list : t) {
                //System.out.println("Route");
                TreeItem<String> item = new TreeItem<>("Route");
                for (GraphNode<?> node : list) {
                    Room room = (Room) node.data;
                    TreeItem<String> subItem = new TreeItem<>(room.getRoomName());
                    item.getChildren().add(subItem);
                    //System.out.println("\t" + room.getRoomName());
                }
                root.getChildren().add(item);
            }
            routeTreeView.setRoot(root);
            routeTreeView.setShowRoot(false);
        }


    }


    public void findbreadthpath(ActionEvent actionEvent) {
        List<GraphNode<Pixel>> pixels = (List<GraphNode<Pixel>>) galleryAPI.breadthFirstSearch(startPixel,destinationPixel);
        Image image = galleryAPI.getGalleryImage();
        WritableImage writableImage = new WritableImage(image.getPixelReader(), (int)image.getWidth(), (int)image.getHeight());
        for (GraphNode<Pixel> p : pixels){
            writableImage.getPixelWriter().setColor(p.data.getXCorrd(), p.data.getYCoord()+20, Color.ORANGE);
        }
        view.setImage(writableImage);
    }


    public void findPathDij(ActionEvent actionEvent) {

        List<GraphNode<?>> pathList = new ArrayList<>();
        if (!waypointsList.isEmpty()) {
            pathList = galleryAPI.waypointSupport(start.getValue(), destination.getValue(), Algo.Dijkstra);
        } else {
            CostOfPath cp = Graph.findCheapestPathDijkstra(galleryAPI.findGraphNode(start.getValue()), galleryAPI.findGraphNode(destination.getValue()).data);

            pathList = cp.pathList;
            //System.out.println(cp.pathCost);
        }


        drawSinglePath(pathList, Color.BLUE);
    }

    public void drawSinglePath(List<GraphNode<?>> pathList, Color c) {
        mainPane.getChildren().clear();
        for (int i = 0; i < pathList.size(); i++) {
            GraphNode<Room> node = (GraphNode<Room>) pathList.get(i);

            if (i + 1 < pathList.size()) {
                GraphNode<Room> nextNode = (GraphNode<Room>) pathList.get(i + 1);
                Line l = new Line(node.data.getXCoord(), node.data.getYCoord(), nextNode.data.getXCoord(), nextNode.data.getYCoord());
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

    public void resetAvoidedRoom() {
        galleryAPI.resetAvoidRoom();
        avoidView.getItems().clear();
        avoidRoom.getItems().clear();
        avoidRoom.getItems().addAll(galleryAPI.getNames());
        avoidRoom.setPromptText("Room");
    }

    public void resetWaypoints() {
        waypointView.getItems().clear();
        galleryAPI.getWaypointsList().clear();
    }

    public void clearMap() {
        mainPane.getChildren().clear();
    }

    public void showBreadthSearchBox() {
        if (breadthFirstButton.isSelected()) {
            breadthFirstBox.setVisible(true);
        } else {
            breadthFirstBox.setVisible(false);
        }
    }
}

