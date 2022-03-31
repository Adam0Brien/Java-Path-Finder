package teamproject.ca2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    /**
     * Variables
     */

    public List<Room> rooms = new LinkedList<>();
    public ObservableList<String> names = FXCollections.observableArrayList();
    public List<GraphNode<Room>> roomNodes = new LinkedList<>();



    @FXML
    ImageView view;
    @FXML
    ImageView processedView;
    @FXML
    ImageView finalView;
    @FXML
    Label fileName;
    @FXML
    Label fileSize;
    @FXML
    Label fileName1;
    @FXML
    Label fileSize1;

    /**
     * On startup loads the map of the art gallery and makes all the connections for each room
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("C:\\CA2\\src\\main\\resources\\teamproject\\ca2\\floorplan-level-2-july-2020.jpg");
        Image image = new Image(file.toURI().toString());
        view.setImage(image);
        processedView.setImage(image);
        finalView.setImage(image);


        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\CA2\\src\\main\\resources\\teamproject\\ca2\\mappings.csv"));

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Room r = new Room(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                GraphNode<Room> node = new GraphNode<>(r);
                roomNodes.add(node);
                names.add(values[0]);
                rooms.add(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        connectNodes(1,2,roomNodes);
        connectNodes(2,3,roomNodes);
        connectNodes(2,4,roomNodes);
        connectNodes(4,5,roomNodes);
        connectNodes(4,6,roomNodes);
        connectNodes(6,7,roomNodes);
        connectNodes(7,8,roomNodes);
        connectNodes(8,9,roomNodes);
        connectNodes(9,10,roomNodes);
        connectNodes(8,15,roomNodes);
        connectNodes(8,14,roomNodes);
        connectNodes(15,16,roomNodes);
        connectNodes(16,17,roomNodes);
        connectNodes(14,16,roomNodes);
        connectNodes(14,26,roomNodes);
        connectNodes(18,26,roomNodes);
        connectNodes(38,39,roomNodes);
        connectNodes(14,18,roomNodes);
        connectNodes(18,19,roomNodes);
        connectNodes(18,21,roomNodes);
        connectNodes(18,22,roomNodes);
        connectNodes(18,24,roomNodes);
        connectNodes(19,20,roomNodes);
        connectNodes(20,21,roomNodes);
        connectNodes(22,23,roomNodes);
        connectNodes(23,24,roomNodes);
        connectNodes(24,25,roomNodes);
        connectNodes(25,28,roomNodes);
        connectNodes(27,28,roomNodes);
        connectNodes(26,27,roomNodes);
        connectNodes(29,28,roomNodes);
        connectNodes(10,11,roomNodes);
        connectNodes(11,12,roomNodes);
        connectNodes(10,13,roomNodes);
        connectNodes(13,29,roomNodes);
        connectNodes(29,14,roomNodes);
        connectNodes(29,30,roomNodes);
        connectNodes(30,31,roomNodes);
        connectNodes(30,32,roomNodes);
        connectNodes(32,33,roomNodes);
        connectNodes(33,34,roomNodes);
        connectNodes(34,35,roomNodes);
        connectNodes(34,41,roomNodes);
        connectNodes(36,35,roomNodes);
        connectNodes(36,37,roomNodes);
        connectNodes(37,32,roomNodes);
        connectNodes(36,40,roomNodes);
        connectNodes(36,38,roomNodes);
        connectNodes(39,12,roomNodes);
        connectNodes(40,44,roomNodes);
        connectNodes(41,42,roomNodes);
        connectNodes(42,43,roomNodes);
        connectNodes(43,44,roomNodes);
        connectNodes(44,45,roomNodes);
        connectNodes(45,46,roomNodes);


        System.out.println("These Rooms are Connected");
        for(GraphNode<Room> r: roomNodes){
            System.out.println(r.data.getRoomName());
           for (GraphLink l : r.adjList){
               System.out.println(l.destNode.data);
           }
        }

    }


    /**
     *
     * Key Methods
     *
     */

    public void findCoords(ActionEvent event) throws IOException {

        view.setOnMousePressed(e -> {
            view.getImage().getPixelReader().getColor((int) e.getX(), (int) e.getY());
            System.out.println("\nX Cord: " + e.getX());
            System.out.println("\nY Cord: " + e.getY());
        });
    }


//https://www.khanacademy.org/math/geometry/hs-geo-analytic-geometry/hs-geo-distance-and-midpoints/v/distance-formula#:~:text=Learn%20how%20to%20find%20the,distance%20between%20any%20two%20points.
    //distance between two points formula

    /**
     *
     * @param nodeA  Starting Node
     * @param nodeB  End Node
     * @param roomNodes
     * @return the distance between both nodes
     */
    public int getCost(int nodeA,int nodeB,List<GraphNode<Room>> roomNodes){
        int nodeAX = roomNodes.get(nodeA).data.getXCoord();
        int nodeAY = roomNodes.get(nodeA).data.getYCoord();
        int nodeBX = roomNodes.get(nodeB).data.getXCoord();
        int nodeBY = roomNodes.get(nodeB).data.getYCoord();

        return (int) Math.sqrt(nodeBX-nodeAX)*(nodeBX - nodeAX) + (nodeBY - nodeAY) * (nodeBY - nodeAY);
    }

    /**
     *
     * @param nodeA Starting node
     * @param nodeB End node
     * @param roomNodes
     */
    public void connectNodes(int nodeA,int nodeB, List<GraphNode<Room>> roomNodes){
        roomNodes.get(nodeA).connectToNodeUndirected(roomNodes.get(nodeB), getCost(nodeA,nodeB,roomNodes));
    }








}