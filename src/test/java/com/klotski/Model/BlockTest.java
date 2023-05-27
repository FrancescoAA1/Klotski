package com.klotski.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest
{
    private Position position;

    @BeforeEach
    void setUp()
    {
        position = new Position(0,0);

    }

    @Test
    public void testIsSpecial_MainBlock()
    {
        // Arrange
        Block block = new Block(position, 2, 2);

        // Act
        boolean result = block.isSpecial();

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsSpecial_NonMainBlock()
    {
        // Arrange
        Block block = new Block(position, 3, 3);

        // Act
        boolean result = block.isSpecial();

        // Assert
        assertFalse(result);
    }

    @Test
    public void testGetWidth()
    {
        // Arrange
        Block block = new Block(position, 3, 4);

        // Act
        int result = block.getWidth();

        // Assert
        assertEquals(3, result);
    }

    @Test
    public void testGetHeight()
    {
        // Arrange
        Block block = new Block(position, 3, 4);

        // Act
        int result = block.getHeight();

        // Assert
        assertEquals(4, result);
    }

    @Test
    public void testGetPos()
    {
        // Arrange
        Block block = new Block(position, 3, 4);

        // Act
        Position result = block.getPos();

        // Assert
        assertEquals(position, result);
    }

    @Test
    public void testSetPos()
    {
        // Arrange
        Position position2 = new Position(4, 5);
        Block block = new Block(position, 3, 4);

        // Act
        block.setPos(position2);

        // Assert
        assertEquals(position2, block.getPos());
    }
}