package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import model.Room;
import model.GraphNode;

import java.util.LinkedList;
import java.util.List;

public class GalleryAPI {

    private List<Room> rooms;
    private ObservableList<String> names;
    private List<GraphNode<Room>> roomNodes;
    private Image galleryImage;

    public GalleryAPI(){
        this.rooms = new LinkedList<>();
        this.names = FXCollections.observableArrayList();
        this. roomNodes = new LinkedList<>();
        this.galleryImage = new Image(getClass().getResourceAsStream("/images/floorplan-level-2-july-2020.jpg"));
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
}
