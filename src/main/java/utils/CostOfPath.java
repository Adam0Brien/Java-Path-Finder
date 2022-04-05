package utils;


//taken from notes


import java.util.ArrayList;
import java.util.List;

public class CostOfPath {
    //New class to hold a CostedPath object i.e. a list of GraphNodeAL2 objects and a total cost attribute
    public int pathCost = 0;
    public List<GraphNode<?>> pathList = new ArrayList<>();


}


//TODO need to fix
//
//    //Retrieve cheapest path by expanding all paths recursively depth-first
//    public static <T> CostOfPath searchGraphDepthFirstCheapestPath(GraphNode<?> from, List<GraphNode<?>> encountered,
//                                                                   int totalCost, T lookingfor) {
//        if (from.data.equals(lookingfor)) { //Found it - end of path
//            CostOfPath cp = new CostOfPath(); //Create a new CostedPath object
//            cp.pathList.add(from); //Add the current node to it - only (end of path) element
//            cp.pathCost = totalCost; //Use the current total cost
//            return cp; //Return the CostedPath object
//        }
//        if (encountered == null) encountered = new ArrayList<>(); //First node so create new (empty) encountered list
//        encountered.add(from);
//        List<CostOfPath> allPaths = new ArrayList<>(); //Collection for all candidate costed paths from this node
//        for (GraphNode adjLink : from.adjList) //For every adjacent node
//            if (!encountered.contains(adjLink.destNode)) //That has not yet been encountered
//            {
////Create a new CostedPath from this node to the searched for item (if a valid path exists)
//                CostOfPath temp = searchGraphDepthFirstCheapestPath(adjLink.destNode, new ArrayList<>(encountered),
//                        totalCost + adjLink.cost, lookingfor);
//                if (temp == null) continue; //No path was found, so continue to the next iteration
//                temp.pathList.add(0, from); //Add the current node to the front of the path list
//                allPaths.add(temp); //Add the new candidate path to the list of all costed paths
//            }
////If no paths were found then return null. Otherwise, return the cheapest path (i.e. the one with min pathCost)
//        return allPaths.isEmpty() ? null : Collections.min(allPaths, (p1, p2) -> p1.pathCost - p2.pathCost);
//
//    }
//}
