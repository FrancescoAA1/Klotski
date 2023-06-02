package com.klotski.Model;

import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class DispositionTest {

    @Test
    public void testSetAndGetOriginalNumber() {
        Disposition disposition = new Disposition("", false);
        assertEquals(0, disposition.getOriginalNumber());

        disposition.setOriginalNumber(5);
        assertEquals(5, disposition.getOriginalNumber());
    }

    @Test
    public void testIsOriginal() {
        Disposition disposition1 = new Disposition("", true);
        assertTrue(disposition1.isOriginal());

        Disposition disposition2 = new Disposition("", false);
        assertFalse(disposition2.isOriginal());
    }

    @Test
    public void testSetAndGetImagePath() {
        Disposition disposition = new Disposition("", false);
        assertNull(disposition.getImagePath());

        disposition.setImagePath("/path/to/image.png");
        assertEquals("/path/to/image.png", disposition.getImagePath());
    }

    @Test
    public void testSetAndGetTextDisposition() {
        Disposition disposition = new Disposition("", false);
        assertEquals("", disposition.getTextDisposition());

        disposition.setTextDisposition("block1-block2-block3");
        assertEquals("block1-block2-block3", disposition.getTextDisposition());
    }

    @Test
    void testTakeSnapshot() {
        // test error parsing parameters in constructor and directly calling the method
        Disposition disp = new Disposition("", false);
        assertThrows(IllegalArgumentException.class, () -> {new Disposition(null, false, 0);});
        assertThrows(IllegalArgumentException.class, () -> {disp.takeSnapshot(null,true);}, "Cannot take snapshot of null grid");
        Grid grid = new Grid();
        assertThrows(InvalidParameterException.class, () -> {new Disposition(grid, false, 0);}, "Cannot take a snap of the grid set. Problem found in grid.");
        assertThrows(InvalidParameterException.class, () -> {disp.takeSnapshot(grid, false);}, "Cannot take a snap of the grid set. Problem found in grid.");
        disp.setTextDisposition("2-1-0;0#2-2-1;0#2-1-3;0#2-1-0;2#1-2-1;2#2-1-3;2#1-1-1;3#1-1-2;3#1-1-0;4#1-1-3;4#1-1-1;4#1-1-2;4");
        Grid grid2 = disp.convertToGrid();
        assertEquals("2-1-0;0#2-2-1;0#2-1-3;0#2-1-0;2#1-2-1;2#2-1-3;2#1-1-1;3#1-1-2;3#1-1-0;4#1-1-3;4#1-1-1;4#1-1-2;4", disp.getTextDisposition());
    }

    @Test
    void testConvertToGrid() {
        Disposition disposition = new Disposition("1-1-0;0#2-2-1;0#1-1-3;2#2-1-0;3#2-1-3;0#1-2-1;2#1-1-0;1#1-2-2;4#1-1-1;3#1-2-2;3#1-1-0;2#1-1-1;4", false);

    }
}