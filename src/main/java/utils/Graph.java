package utils;

import model.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph {

    /**
     * @param nodeA     Starting Node
     * @param nodeB     End Node
     * @param roomNodes
     * @return the distance between both nodes
     */
    //https://www.khanacademy.org/math/geometry/hs-geo-analytic-geometry/hs-geo-distance-and-midpoints/v/distance-formula#:~:text=Learn%20how%20to%20find%20the,distance%20between%20any%20two%20points.
    //distance between two points formula
    public static int getCost(int nodeA, int nodeB, List<GraphNode<Room>> roomNodes) {
        int nodeAX = roomNodes.get(nodeA).data.getXCoord();
        int nodeAY = roomNodes.get(nodeA).data.getYCoord();
        int nodeBX = roomNodes.get(nodeB).data.getXCoord();
        int nodeBY = roomNodes.get(nodeB).data.getYCoord();

        return (int) Math.sqrt(nodeBX - nodeAX) * (nodeBX - nodeAX) + (nodeBY - nodeAY) * (nodeBY - nodeAY);
    }

    /**
     * @param nodeA     Starting node
     * @param nodeB     End node
     * @param roomNodes
     */
    public static void connectNodes(int nodeA, int nodeB, List<GraphNode<Room>> roomNodes) {
        roomNodes.get(nodeA).connectToNodeUndirected(roomNodes.get(nodeB), getCost(nodeA, nodeB, roomNodes));
    }

    /**
     * BreadthFirstSearch taken from notes
     *
     * @param startNode
     * @param lookingfor
     * @param <T>
     * @return
     */
    //Interface method to allow just the starting node and the goal node data to match to be specified
    public static <T> List<GraphNode<?>> findPathBreadthFirstInterface(GraphNode<?> startNode, T lookingfor) {
        List<List<GraphNode<?>>> agenda = new ArrayList<>(); //Agenda comprised of path lists here!
        List<GraphNode<?>> firstAgendaPath = new ArrayList<>(), resultPath;
        firstAgendaPath.add(startNode);
        agenda.add(firstAgendaPath);
        resultPath = findPathBreadthFirst(agenda, null, lookingfor); //Get single BFS path (will be shortest)
        Collections.reverse(resultPath); //Reverse path (currently has the goal node as the first item)
        return resultPath;
    }

    //Agenda list based breadth-first graph search returning a single reversed path (tail recursive)
    public static <T> List<GraphNode<?>> findPathBreadthFirst(List<List<GraphNode<?>>> agenda,
                                                              List<GraphNode<?>> encountered, T lookingfor) {
        if (agenda.isEmpty()) return null; //Search failed
        List<GraphNode<?>> nextPath = agenda.remove(0); //Get first item (next path to consider) off agenda
        GraphNode<?> currentNode = nextPath.get(0); //The first item in the next path is the current node
        if (currentNode.data.equals(lookingfor))
            return nextPath; //If that's the goal, we've found our path (so return it)
        if (encountered == null)
            encountered = new ArrayList<>(); //First node considered in search so create new (empty)
        //encountered list
        encountered.add(currentNode); //Record current node as encountered so it isn't revisited again
        for (GraphLink adjNode : currentNode.adjList) //For each adjacent node
            if (!encountered.contains(adjNode)) { //If it hasn't already been encountered
                List<GraphNode<?>> newPath = new ArrayList<>(nextPath); //Create a new path list as a copy of
//the current/next path
                newPath.add(0, adjNode.destNode); //And add the adjacent node to the front of the new copy
                agenda.add(newPath); //Add the new path to the end of agenda (end->BFS!)
            }
        return findPathBreadthFirst(agenda, encountered, lookingfor); //Tail call
    }
}
