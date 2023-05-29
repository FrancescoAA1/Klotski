package com.klotski.Model;

import org.junit.jupiter.api.Test;

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
        Disposition disp = null;
        assertThrows(IllegalArgumentException.class, () -> {new Disposition(null, false)};

    }

    @Test
    void convertToGrid() {
    }
}