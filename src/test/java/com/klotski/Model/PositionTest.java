package com.klotski.Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest
{

    private Position position1;
    private Position position2;
    private Position position3;

    @BeforeEach
    void setUp()
    {
        position1 = new Position(10,20);
        position2 = new Position(10,20);
        position3 = new Position(5,15);
    }

    @Test
    void getX()
    {
        //Assert
        assertEquals(10,position1.getX());
    }

    @Test
    void getY()
    {
        //Assert
        assertEquals(20,position1.getY());
    }

    @Test
    public void testSetX()
    {
        // Arrange
        Position position = new Position(10, 20);

        // Act
        position.setX(30);

        // Assert
        assertEquals(30, position.getX());
    }

    @Test
    public void testSetY()
    {
        // Arrange
        Position position = new Position(10, 20);

        // Act
        position.setY(40);

        // Assert
        assertEquals(40, position.getY());
    }

    @Test
    public void testEquals_SameCoordinates()
    {
        //Act
        boolean result = position1.equals(position2);

        //Assert
        assertTrue(result);
    }

    @Test
    public void testEquals_DifferentCoordinates()
    {
        // Act
        boolean result = position1.equals(position3);

        //Assert
        assertFalse(result);
    }

    @Test
    public void testConvertToPosition_ValidPosition()
    {
        // Arrange
        String positionString = "10;20";

        // Act
        Position result = Position.convertToPosition(positionString);

        // Assert
        assertEquals(10, result.getX());
        assertEquals(20, result.getY());
    }

    @Test
    public void testConvertToPosition_InvalidPosition()
    {
        // Arrange
        String positionString = "10,20"; // Using invalid separator

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> Position.convertToPosition(positionString));
    }

    @Test
    public void testToString()
    {

        // Act
        String result = position1.toString();

        // Assert
        assertEquals("10;20", result);
    }
}