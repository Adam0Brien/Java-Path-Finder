package controller;

import javafx.scene.image.Image;
import model.GraphLink;
import model.GraphNode;
import model.Pixel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Graph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GalleryAPITest {

    GalleryAPI galleryAPI;
    Image testImage;

    @BeforeEach
    void setUp() {
        galleryAPI = new GalleryAPI();
        testImage = new Image(getClass().getResourceAsStream("/images/test.png"));
    }

    @AfterEach
    void tearDown() {
        galleryAPI = null;
    }

    @Test
    void testBuildGraph(){
        galleryAPI.buildPixelGraph(testImage);

//        for (GraphNode<Pixel> p : galleryAPI.getPixelNodes()){
//            System.out.println("----------------");
//            System.out.println(p.data);
//            for (GraphLink link : p.adjList){
//                System.out.println("\t" + link.destNode.data);
//            }
//        }

        List<GraphNode<?>> p = Graph.findPathBreadthFirstInterface(galleryAPI.getPixelNodes().get(0), galleryAPI.getPixelNodes().get(10).data);

        for (GraphNode<?> n : p){
            System.out.println(n.data);
        }
    }

    @Test
    void testContains(){
        //assertTrue(galleryAPI.containsPixel(new Pixel(1,1)));
        galleryAPI.buildPixelGraph(testImage);
        for (GraphNode<Pixel> node : galleryAPI.getPixelNodes()){
            System.out.println(node.data);
            Pixel p = node.data;
            if (p.equals(new Pixel(1,1))) System.out.println("found");
        }
    }
}