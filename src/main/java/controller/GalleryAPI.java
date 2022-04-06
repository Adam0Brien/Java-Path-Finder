package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Room;
import model.GraphNode;

import java.util.LinkedList;
import java.util.List;

public class GalleryAPI {

    private List<Room> rooms;
    private ObservableList<String> names;
    private List<GraphNode<Room>> roomNodes;

    public GalleryAPI(){
        this.rooms = new LinkedList<>();
        this.names = FXCollections.observableArrayList();
        this. roomNodes = new LinkedList<>();
    }

    public void findShortestRoute() {
        //using the CostOfPath class here... somewhere anyways
    }


    //We could generate ALL routes and use something like noise reduction to take away all the super long ones
    public void generateMultipleRoutes() {

    }
}
