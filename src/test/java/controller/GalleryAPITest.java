package controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Graph;

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

    @Test
    void findShortestRoute() {
    }

    @Test
    void generateMultipleRoutes() {

    }
}