package controller;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.*;
import utils.Algo;
import utils.Graph;

import java.io.*;
import java.util.*;


public class GalleryAPI {

    private List<Room> rooms;
    private HashMap<String, GraphNode<Room>> roomsHashMap;
    private List<String> names;
    private List<GraphNode<Room>> roomNodes;
    private List<GraphNode<Pixel>> pixelNodes;
    private HashMap<String, GraphNode<Pixel>> hashMap;
    private Image galleryImage;
    private Image breadthSearchImage;
    private List<GraphNode<Room>> avoidedRooms;
    private List<String> waypointsList;

    private List<String> pointsOfInterestNames;
    private List<String> pointsOfInterstList;


    public List<Circle> rectangles;


    public GalleryAPI() {
        this.waypointsList = new LinkedList<>();
        this.hashMap = new HashMap<>();
        this.rooms = new LinkedList<>();
        this.names = new ArrayList<>();
        this.pointsOfInterstList = new ArrayList<>();
        this.pointsOfInterestNames = new ArrayList<>();
        this.roomNodes = new LinkedList<>();
        this.pixelNodes = new LinkedList<>();
        this.roomsHashMap = new HashMap<>();
        this.avoidedRooms = new LinkedList<>();
        this.galleryImage = new Image(getClass().getResourceAsStream("/images/floorplan-level-2-july-2020.jpg"));
        this.breadthSearchImage = new Image(getClass().getResourceAsStream("/images/floorplan-level-2-july-2020-breadth-search.jpg"));
        this.rectangles = new LinkedList();
        readInDatabase();
        connectRooms();
        buildPixelGraph();
    }

    public List<String> getPointsOfInterstList() {
        return pointsOfInterstList;
    }

    public void setPointsOfInterstList(List<String> pointsOfInterstList) {
        this.pointsOfInterstList = pointsOfInterstList;
    }

    public List<String> getPointsOfInterestNames() {
        return pointsOfInterestNames;
    }

    public void setPointsOfInterestNames(List<String> pointsOfInterestNames) {
        this.pointsOfInterestNames = pointsOfInterestNames;
    }

    public List<GraphNode<Pixel>> getPixelNodes() {
        return pixelNodes;
    }

    public void setPixelNodes(List<GraphNode<Pixel>> pixelNodes) {
        this.pixelNodes = pixelNodes;
    }

    public Image getBreadthSearchImage() {
        return breadthSearchImage;
    }

    public void setBreadthSearchImage(Image breadthSearchImage) {
        this.breadthSearchImage = breadthSearchImage;
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

    public GraphNode<Room> findGraphNodeByInterest(String interest) {
        for (Room r : rooms) {
            //System.out.println(r.getRoomName() + ", " + r.getExhibit());
            if (r.getExhibit().equals(interest)) {
                return findGraphNode(r.getRoomName());
            }
        }
        return null;
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


                Circle c = new Circle(Integer.parseInt(values[1]), Integer.parseInt(values[2]), 6);

                c.setFill(Color.TRANSPARENT);
                rectangles.add(c);
                Tooltip t = new Tooltip("Room Number: " + r.getRoomName() + "\nExibit : " + r.getExhibit() + "\nDate: " + r.getDate());
                Tooltip.install(c,t);


                if (!pointsOfInterestNames.contains(values[3])) pointsOfInterestNames.add(values[3]);
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
    }


    public void avoidRoom(String smellyRoom) {
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

    public List<GraphNode<?>> waypointSupport(String start, String destination, Algo type) {
        List<GraphNode<?>> pathList = new LinkedList<>();
        waypointsList.add(destination);
        GraphNode<Room> startNode = findGraphNode(start);

        for (String waypoint : waypointsList) {
            GraphNode<Room> waypointNode = findGraphNode(waypoint);
            CostOfPath temp = (type.equals(Algo.Depth)) ? Graph.searchGraphDepthFirstCheapestPath(startNode, null, 0, waypointNode.data) : Graph.findCheapestPathDijkstra(startNode, waypointNode.data);
            assert temp != null;
            pathList.addAll(temp.pathList);
            startNode = waypointNode;
        }
        waypointsList.remove(destination);
        return pathList;
    }

    public List<GraphNode<?>> interestsSupport(String start, String destination, Algo type) {
        List<GraphNode<?>> pathList = new LinkedList<>();
        GraphNode<Room> startNode = findGraphNode(start);
        for (String interest : pointsOfInterstList) {
            GraphNode<Room> interestNode = findGraphNodeByInterest(interest);
            CostOfPath temp = (type.equals(Algo.Depth)) ? Graph.searchGraphDepthFirstCheapestPath(startNode, null, 0, interestNode.data) : Graph.findCheapestPathDijkstra(startNode, interestNode.data);
            assert temp != null;
            pathList.addAll(temp.pathList);
            startNode = interestNode;
        }

        pathList.addAll((type.equals(Algo.Depth)) ? Graph.searchGraphDepthFirstCheapestPath(startNode, null, 0, findGraphNode(destination).data).pathList : Graph.findCheapestPathDijkstra(startNode, findGraphNode(destination).data).pathList);
        return pathList;
    }

    public List<?> breadthFirstSearch(Pixel startPixel, Pixel destination) {
        return Graph.findBreadPath(findPixel(startPixel), findPixel(destination).data);
    }

    public void buildPixelGraph() {
        int cost = 1;
        for (int x = 0; x < breadthSearchImage.getWidth(); x++) {
            for (int y = 0; y < breadthSearchImage.getHeight(); y++) {
                if (breadthSearchImage.getPixelReader().getColor(x, y).equals(Color.WHITE)) {
                    GraphNode<Pixel> node = new GraphNode<>(new Pixel(x, y));
                    pixelNodes.add(node);
                    hashMap.put(node.data.toString(), node);
                }
            }
        }
        for (int x = 0; x < breadthSearchImage.getWidth(); x++) {
            for (int y = 0; y < breadthSearchImage.getHeight(); y++) {
                if (breadthSearchImage.getPixelReader().getColor(x, y).equals(Color.WHITE)) {

                    GraphNode<Pixel> current = findPixel(new Pixel(x, y));
                    //Below current pixel
                    int belowX = x;
                    int belowY = y + 1;
                    if (belowY < breadthSearchImage.getHeight()) {
                        if (breadthSearchImage.getPixelReader().getColor(belowX, belowY).equals(Color.WHITE)) {
                            GraphNode<Pixel> pixel = findPixel(new Pixel(belowX, belowY));
                            current.connectToNodeUndirected(pixel, cost);
                        }
                    }
                    //Right of current pixel
                    int rightX = x + 1;
                    int rightY = y;
                    if (rightX < breadthSearchImage.getWidth()) {
                        if (breadthSearchImage.getPixelReader().getColor(rightX, rightY).equals(Color.WHITE)) {
                            GraphNode<Pixel> pixel = findPixel(new Pixel(rightX, rightY));
                            current.connectToNodeUndirected(pixel, cost);
                        }
                    }
                    //diagonal down right to pixel
                    int diagX = x + 1;
                    int diagY = y + 1;
                    if (diagX < breadthSearchImage.getWidth() && diagY < breadthSearchImage.getHeight()) {
                        if (breadthSearchImage.getPixelReader().getColor(diagX, diagY).equals(Color.WHITE)) {
                            GraphNode<Pixel> pixel = findPixel(new Pixel(diagX, diagY));
                            current.connectToNodeUndirected(pixel, cost);
                        }
                    }
                }
            }
        }
    }

    public void buildPixelGraph(Image image) {
        int cost = 1;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getPixelReader().getColor(x, y).equals(Color.BLACK)) continue;
                pixelNodes.add(new GraphNode<>(new Pixel(x, y)));

            }
        }

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getPixelReader().getColor(x, y).equals(Color.BLACK)) continue;

                GraphNode<Pixel> current = findPixel(new Pixel(x, y));
                //System.out.println(current.data);
                //Below current pixel
                int belowX = x;
                int belowY = y + 1;
                if (belowY < image.getHeight()) {
                    if (!image.getPixelReader().getColor(belowX, belowY).equals(Color.BLACK)) {
                        GraphNode<Pixel> pixel = findPixel(new Pixel(belowX, belowY));
                        current.connectToNodeUndirected(pixel, cost);
                    }
                }
                //Right of current pixel
                int rightX = x + 1;
                int rightY = y;
                if (rightX < image.getWidth()) {
                    if (!image.getPixelReader().getColor(rightX, rightY).equals(Color.BLACK)) {
                        GraphNode<Pixel> pixel = findPixel(new Pixel(rightX, rightY));
                        current.connectToNodeUndirected(pixel, cost);
                    }
                }
                //diagonal down right to pixel
                int diagX = x + 1;
                int diagY = y + 1;
                if (diagX < image.getWidth() && diagY < image.getHeight()) {
                    if (!image.getPixelReader().getColor(diagX, diagY).equals(Color.BLACK)) {
                        GraphNode<Pixel> pixel = findPixel(new Pixel(diagX, diagY));
                        current.connectToNodeUndirected(pixel, cost);
                    }
                }

            }
        }
        //System.out.println(pixelNodes.size());
    }

    public GraphNode<Pixel> findPixel(Pixel lookingFor) {
        return hashMap.get(lookingFor.toString());
    }

    public boolean containsPixel(Pixel pixel) {
        for (GraphNode<Pixel> node : pixelNodes) {
            Pixel p = node.data;
            if (p.equals(pixel)) return true;
        }
        return false;
    }
}
