package controller;

import javafx.scene.image.Image;
import model.GraphLink;
import model.GraphNode;
import model.Pixel;
import model.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Graph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GalleryAPITest {

    GalleryAPI galleryAPI;
    Room room1;
    GraphNode<Room> roomNode1;

    @BeforeEach
    void setUp() {
        galleryAPI = new GalleryAPI();
        room1 = galleryAPI.getRooms().get(0);
        roomNode1 = galleryAPI.getRoomNodes().get(0);
    }

    @AfterEach
    void tearDown() {
        galleryAPI = null;
        room1 = null;
    }

    @Test
    void testFindPixel(){
        assertEquals(galleryAPI.getPixelNodes().get(0), galleryAPI.findPixel(galleryAPI.getPixelNodes().get(0).data));
        assertEquals(galleryAPI.getPixelNodes().get(1), galleryAPI.findPixel(galleryAPI.getPixelNodes().get(1).data));
        assertEquals(galleryAPI.getPixelNodes().get(2), galleryAPI.findPixel(galleryAPI.getPixelNodes().get(2).data));
        assertEquals(galleryAPI.getPixelNodes().get(3), galleryAPI.findPixel(galleryAPI.getPixelNodes().get(3).data));
    }

    @Test
    void findGraphNodeByInterest(){
        assertEquals(galleryAPI.findGraphNode("Shop"), galleryAPI.findGraphNodeByInterest("Shop"));
    }
}