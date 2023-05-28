package com.klotski.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {
    @Test
    public void testIsTerminated() {
        Match match = new Match();
        assertFalse(match.isTerminated());

        match.terminate();
        assertTrue(match.isTerminated());
    }
    @Test
    public void testName() {
        Match match = new Match("Test Match");
        assertEquals("Test Match", match.getName());

        Match matchWithDate = new Match();
        assertNotNull(matchWithDate.getName());
    }

    @Test
    public void testScore() {
        Match match = new Match();
        assertEquals(0, match.getScore());

        match.incrementScore();
        assertEquals(1, match.getScore());

        match.decrementScore();
        assertEquals(0, match.getScore());

        match.setScore(5);
        assertEquals(5, match.getScore());
    }
    @Test
    public void testHintsNumber() {
        Match match = new Match();
        assertEquals(0, match.getHintsNumber());

        match.setHintsNumber(5);
        assertEquals(5, match.getHintsNumber());

        match.increaseHints();
        assertEquals(6, match.getHintsNumber());

        match.decreaseHints();
        assertEquals(5, match.getHintsNumber());

        match = new Match();
        match.decreaseHints();
        assertEquals(0, match.getHintsNumber());
    }
}