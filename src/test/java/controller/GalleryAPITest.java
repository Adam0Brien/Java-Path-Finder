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

    @BeforeEach
    void setUp() {
        galleryAPI = new GalleryAPI();
    }

    @AfterEach
    void tearDown() {
        galleryAPI = null;
    }

}