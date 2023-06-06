package com.klotski.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    @Test
    void testConstructorWithThreeParameters() {
        Position init = new Position(1, 2);
        Position end = new Position(3, 4);
        Direction direction = Direction.UP;

        Move move = new Move(init, end, direction);

        assertEquals(init, move.getInit());
        assertEquals(end, move.getEnd());
        assertEquals(direction, move.getDirection());
    }

    @Test
    void testConstructorWithTwoParameters() {
        // test for right direction
        Position init = new Position(1, 2);
        Direction direction = Direction.RIGHT;
        Position expectedEnd = new Position(2, 2);

        Move move = new Move(init, direction);

        assertEquals(init, move.getInit());
        assertEquals(expectedEnd, move.getEnd());
        assertEquals(direction, move.getDirection());
        // test for left direction
        init = new Position(1, 2);
        direction = Direction.LEFT;
        expectedEnd = new Position(0, 2);

        move = new Move(init, direction);

        assertEquals(init, move.getInit());
        assertEquals(expectedEnd, move.getEnd());
        assertEquals(direction, move.getDirection());

        // test for up direction
        init = new Position(1, 2);
        direction = Direction.UP;
        expectedEnd = new Position(1, 1);

        move = new Move(init, direction);

        assertEquals(init, move.getInit());
        assertEquals(expectedEnd, move.getEnd());
        assertEquals(direction, move.getDirection());

        // test for down direction
        init = new Position(1, 2);
        direction = Direction.DOWN;
        expectedEnd = new Position(1, 3);

        move = new Move(init, direction);

        assertEquals(init, move.getInit());
        assertEquals(expectedEnd, move.getEnd());
        assertEquals(direction, move.getDirection());
    }

    @Test
    void testConvertToMove() {
        // correct string
        String moveString = "1;0 2;3 UP";
        Position expectedInit = new Position(1, 0);
        Position expectedEnd = new Position(2, 3);
        Direction expectedDirection = Direction.valueOf("UP");

        Move move = Move.convertToMove(moveString);

        assertEquals(expectedInit, move.getInit());
        assertEquals(expectedEnd, move.getEnd());
        assertEquals(expectedDirection, move.getDirection());
        // some Bad Formatted strings to test
        String bad1 = " ";
        assertThrowsExactly(IllegalArgumentException.class, () -> {Move.convertToMove(bad1);}, "Illegal format for move");
        String bad2 = "22 33 DOWN";
        assertThrowsExactly(IllegalArgumentException.class, () -> {Move.convertToMove(bad2);}, "Illegal format for move");
        String bad3 = "2;2 33 DOWN";
        assertThrowsExactly(IllegalArgumentException.class, () -> {Move.convertToMove(bad3);}, "Illegal format for move");
        String bad4 = "22 3;3 DOWN";
        assertThrowsExactly(IllegalArgumentException.class, () -> {Move.convertToMove(bad4);}, "Illegal format for move");
        String bad5 = "22 33DOWN";
        assertThrowsExactly(IllegalArgumentException.class, () -> {Move.convertToMove(bad5);}, "Illegal format for move");
        String bad6 = "2233";
        assertThrowsExactly(IllegalArgumentException.class, () -> {Move.convertToMove(bad6);}, "Illegal format for move");
        String bad7 = "RIGHT 2:2 3:3";
        assertThrowsExactly(IllegalArgumentException.class, () -> {Move.convertToMove(bad7);}, "Illegal format for move");
        String bad8 = "22 33 LEFT ";
        assertThrowsExactly(IllegalArgumentException.class, () -> {Move.convertToMove(bad8);}, "Illegal format for move");
        String bad9 = "2;2 3;3 DOW";
        assertThrowsExactly(IllegalArgumentException.class, () -> {Move.convertToMove(bad9);}, "Illegal format for move");
        String bad10 = "10000;0 33;10000- DOWN";
        assertThrowsExactly(IllegalArgumentException.class, () -> {Move.convertToMove(bad10);}, "Illegal format for move");
    }

    @Test
    void testEquals() {
        Position init1 = new Position(1, 2);
        Position end1 = new Position(3, 4);
        Direction direction1 = Direction.UP;
        Move move1 = new Move(init1, end1, direction1);

        Position init2 = new Position(1, 2);
        Position end2 = new Position(3, 4);
        Direction direction2 = Direction.UP;
        Move move2 = new Move(init2, end2, direction2);

        assertEquals(move1, move2);
        // for not equality
        init1.setX(3);
        assertNotEquals(move1, move2);
    }
}