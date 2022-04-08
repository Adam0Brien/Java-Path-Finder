package utils;

import model.CostOfPath;
import model.GraphLink;
import model.GraphNode;
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

    //Recursive depth-first search of graph (all paths identified returned)
    public static <T> List<List<GraphNode<?>>> findAllPathsDepthFirst(GraphNode<?> from, List<GraphNode<?>> encountered, T lookingfor){
        List<List<GraphNode<?>>> result=null, temp2;
        if(from.data.equals(lookingfor)) { //Found it
            List<GraphNode<?>> temp=new ArrayList<>(); //Create new single solution path list
            temp.add(from); //Add current node to the new single path list
            result=new ArrayList<>(); //Create new "list of lists" to store path permutations
            result.add(temp); //Add the new single path list to the path permutations list
            return result; //Return the path permutations list
        }
        if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(from); //Add current node to encountered list
        for(GraphLink adjNode : from.adjList){
            if(!encountered.contains(adjNode)) {
                temp2=findAllPathsDepthFirst(adjNode.destNode,new ArrayList<>(encountered),lookingfor); //Use clone of encountered list
//for recursive call!
                if(temp2!=null) { //Result of the recursive call contains one or more paths to the solution node
                    for(List<GraphNode<?>> x : temp2) //For each partial path list returned
                        x.add(0,from); //Add the current node to the front of each path list
                    if(result==null) result=temp2; //If this is the first set of solution paths found use it as the result
                    else result.addAll(temp2); //Otherwise append them to the previously found paths
                }
            }
        }
        return result;
    }

    //Retrieve cheapest path by expanding all paths recursively depth-first
    public static <T> CostOfPath searchGraphDepthFirstCheapestPath(GraphNode<?> from, List<GraphNode<?>> encountered,
                                                                   int totalCost, T lookingfor) {
        if (from.data.equals(lookingfor)) { //Found it - end of path
            CostOfPath cp = new CostOfPath(); //Create a new CostedPath object
            cp.pathList.add(from); //Add the current node to it - only (end of path) element
            cp.pathCost = totalCost; //Use the current total cost
            return cp; //Return the CostedPath object
        }
        if (encountered == null) encountered = new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(from);
        List<CostOfPath> allPaths = new ArrayList<>(); //Collection for all candidate costed paths from this node
        for (GraphLink adjLink : from.adjList) //For every adjacent node
            if (!encountered.contains(adjLink.destNode)) //That has not yet been encountered
            {
                //Create a new CostedPath from this node to the searched for item (if a valid path exists)
                CostOfPath temp = searchGraphDepthFirstCheapestPath(adjLink.destNode, new ArrayList<>(encountered),
                        totalCost + adjLink.cost, lookingfor);
                if (temp == null) continue; //No path was found, so continue to the next iteration
                temp.pathList.add(0, from); //Add the current node to the front of the path list
                allPaths.add(temp); //Add the new candidate path to the list of all costed paths
            }
        //If no paths were found then return null. Otherwise, return the cheapest path (i.e. the one with min pathCost)
        return allPaths.isEmpty() ? null : Collections.min(allPaths, (p1, p2) -> p1.pathCost - p2.pathCost);

    }
}
