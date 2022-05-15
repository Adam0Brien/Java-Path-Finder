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
import javafx.scene.shape.Circle;
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
    ListView waypointView , interestsView;
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
    @FXML
    ColorPicker dijkstrasColorPicker, depthColorPicker, breadthColorPicker;

    private GalleryAPI galleryAPI;
    private List<String> waypointsList , interestsList;
    private Pixel startPixel, destinationPixel;
    private Circle startCircle, endCircle;
    private Color dijkstraColor, depthColor, breathColor;

    /**
     * On startup loads the map of the art gallery and makes all the connections for each room
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        galleryAPI = Driver.galleryAPI;
        this.waypointsList = galleryAPI.getWaypointsList();
        this.interestsList = galleryAPI.getPointsOfInterest();
        view.setImage(galleryAPI.getGalleryImage());
        //view.setImage(galleryAPI.getBreadthSearchImage());
        mainPane.setPrefHeight(view.getFitHeight());

        dijkstraColor = Color.BLUE;
        dijkstrasColorPicker.setValue(Color.BLUE);
        depthColor = Color.RED;
        depthColorPicker.setValue(Color.RED);
        breathColor = Color.ORANGE;
        breadthColorPicker.setValue(Color.ORANGE);

        startCircle = new Circle();
        startCircle.setRadius(3);
        startCircle.setFill(Color.RED);
        endCircle = new Circle();
        endCircle.setRadius(3);
        endCircle.setFill(Color.RED);

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
        System.out.println("======");
        System.out.println(e.getX() + ", " + e.getY());
        System.out.println("-------");
        System.out.println(x+", " +y);

        if (!galleryAPI.getBreadthSearchImage().getPixelReader().getColor(x, y).equals(Color.BLACK)) {
            if (startCorrdsButton.isSelected()) {
                startPixel = new Pixel(x, y);
                startCorrdsLabel.setText("X: " + x + " Y: " + y);
                startCorrdsButton.setSelected(false);
                mainPane.getChildren().remove(startCircle);
                startCircle.setLayoutX(e.getX());
                startCircle.setLayoutY(e.getY());
                mainPane.getChildren().add(startCircle);
            }
            if (destinationCorrdsButton.isSelected()) {
                destinationPixel = new Pixel(x, y);
                destinationCorrdsLabel.setText("X: " + x + " Y: " + y);
                destinationCorrdsButton.setSelected(false);
                mainPane.getChildren().remove(endCircle);
                endCircle.setLayoutX(e.getX());
                endCircle.setLayoutY(e.getY());
                mainPane.getChildren().add(endCircle);
            }
        }
    }


    public void addWaypoint() {
        if (galleryAPI.getWaypointsList().contains(waypoints.getValue())) return;
        waypointView.getItems().addAll(waypoints.getValue());
        waypointsList.add(waypoints.getValue());
    }

    public void addInterest(){
        interestsView.getItems().addAll(pointsOfInterest.getValue());
        interestsList.add(pointsOfInterest.getValue());
    }

    public void findDepthpath(ActionEvent actionEvent) {
        List<GraphNode<?>> newPath;
        if (!waypointsList.isEmpty()) {
            newPath = galleryAPI.waypointSupport(start.getValue(), destination.getValue(), Algo.Depth);
        } else {
            CostOfPath cp = Graph.searchGraphDepthFirstCheapestPath(galleryAPI.findGraphNode(start.getValue()), null, 0, galleryAPI.findGraphNode(destination.getValue()).data);

            newPath = cp.pathList;
        }
        drawSinglePath(newPath, depthColor);
    }

    public void findAllDepthpaths(ActionEvent actionEvent) {
        List<List<GraphNode<?>>> t;
        if (!waypointsList.isEmpty()) {
            //pathList = galleryAPI.waypointSupport(findRoom((ArrayList<Room>) galleryAPI.getRooms(), start.getValue()), findRoom((ArrayList<Room>) galleryAPI.getRooms(), destination.getValue()), waypointsList.get, galleryAPI.getRoomNodes(), galleryAPI.getRooms());
        } else {
            t = Graph.findAllPathsDepthFirst(galleryAPI.findGraphNode(start.getValue()), null, galleryAPI.findGraphNode(destination.getValue()).data);
            TreeItem<String> root = new TreeItem<>("Routes");
            for (List<GraphNode<?>> list : t) {
                TreeItem<String> item = new TreeItem<>("Route");
                for (GraphNode<?> node : list) {
                    Room room = (Room) node.data;
                    TreeItem<String> subItem = new TreeItem<>(room.getRoomName());
                    item.getChildren().add(subItem);

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
            writableImage.getPixelWriter().setColor(p.data.getXCorrd(), p.data.getYCoord(), breathColor);
        }
        view.setImage(writableImage);
    }


    public void findPathDij(ActionEvent actionEvent) {

        List<GraphNode<?>> pathList = new ArrayList<>();
        if (!waypointsList.isEmpty()) {
            pathList = galleryAPI.waypointSupport(start.getValue(), destination.getValue(), Algo.Dijkstra);
        }
//        else if (!interestsList.isEmpty()) {
//            pathList = galleryAPI.interestsSupport(start.getValue(),destination.getValue(),Algo.Dijkstra);
//        }
        else{
            CostOfPath cp = Graph.findCheapestPathDijkstra(galleryAPI.findGraphNode(start.getValue()), galleryAPI.findGraphNode(destination.getValue()).data);

            pathList = cp.pathList;
        }


        drawSinglePath(pathList, dijkstraColor);
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
        if (avoidRoom.getValue() == null) return;
        galleryAPI.avoidRoom(avoidRoom.getValue());
        avoidView.getItems().add(avoidRoom.getValue());
    }

    public void resetAvoidedRoom() {
        galleryAPI.resetAvoidRoom();
        avoidView.getItems().clear();
        avoidRoom.getItems().clear();
        avoidRoom.getItems().addAll(galleryAPI.getNames());
        avoidRoom.setPromptText("Avoid");
    }

    public void resetWaypoints() {
        waypointView.getItems().clear();
        galleryAPI.getWaypointsList().clear();
    }

    public void clearMap() {
        mainPane.getChildren().clear();
        view.setImage(galleryAPI.getGalleryImage());
    }

    public void showBreadthSearchBox() {
        if (breadthFirstButton.isSelected()) {
            breadthFirstBox.setVisible(true);
        } else {
            breadthFirstBox.setVisible(false);
        }
    }

    public void changeDijstrasColor(ActionEvent actionEvent) {
        dijkstraColor = dijkstrasColorPicker.getValue();
    }

    public void changeDepthColor(ActionEvent actionEvent) {
        depthColor = depthColorPicker.getValue();
    }

    public void changeBreadthColor(ActionEvent actionEvent) {
        breathColor = breadthColorPicker.getValue();
    }
}

