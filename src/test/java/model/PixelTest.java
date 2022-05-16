package model;

import controller.GalleryAPI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PixelTest {

    Pixel pixel1;
    Pixel pixel2;
    List<Pixel> pixels;

    @BeforeEach
    void setUp() {
        pixel1 = new Pixel(129,45);
        pixel2 = new Pixel(129,44);

        pixels = new ArrayList<>();
        pixels.add(pixel1);
        pixels.add(pixel2);
    }

    @AfterEach
    void tearDown() {
        pixel2 = null;
        pixel1 = null;
        pixels = null;
    }

    @Test
    void testEquals() {
        assertFalse(pixel1.equals(pixel2));
        assertFalse(pixel2.equals(pixel1));
        pixel2.setYCoord(45);
        assertTrue(pixel1.equals(pixel2));
        assertTrue(pixel2.equals(pixel1));
    }

    @Test
    void contains(){
        assertTrue(pixels.contains(pixel1));
        assertTrue(pixels.contains(pixel2));
        pixels.remove(pixel2);
        pixel2.setYCoord(45);
        assertTrue(pixels.contains(pixel1));
        assertTrue(pixels.contains(pixel2));
    }

    @Test
    void testGettersAndSetters(){
        assertEquals(129, pixel1.getXCorrd());
        assertEquals(45, pixel1.getYCoord());
        pixel1.setXCorrd(90);
        pixel1.setYCoord(10);
        assertEquals(90, pixel1.getXCorrd());
        assertEquals(10, pixel1.getYCoord());
    }
}