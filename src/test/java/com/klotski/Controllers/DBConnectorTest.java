package com.klotski.Controllers;

import com.klotski.Model.Disposition;
import com.klotski.Model.Match;
import javafx.util.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectorTest {

    private static DBConnector dbConnector;
    private static Match validMatch;
    private static Match invalidMatch;
    private static Disposition validDisposition;
    private static Disposition invalidDisposition;

    @BeforeEach
    void setUp() {
        //Opening a new DBConnection
        dbConnector = new DBConnector();
        dbConnector.connect();

        //Creating Valid and Invalid Match
        validMatch = new Match("2023-05-27_20-59-08");
        invalidMatch = null;

        //Creating Valid and Invalid Disposition
        validDisposition = new Disposition("", true);
        validDisposition.setImagePath("/com/klotski/Images/m1.png");
        validDisposition.setOriginalNumber(1);

        invalidDisposition = null;
    }

    @AfterEach
    void tearDown() {
        //Closing the existent connection
        dbConnector.close();
    }


    @Test
    void saveMatch_ValidMatchAndDisposition() {


        //Check match gets saved with valid match and disposition

        boolean result = dbConnector.saveMatch(validMatch, validDisposition);

        //this test will give false if the match is already existing, which it is you run the class twice
        assertTrue(result);
    }

    @Test
    void saveMatch_InvalidMatchOrDisposition() {

        //Check match gets saved with invalid match and disposition

        boolean result = dbConnector.saveMatch(invalidMatch, invalidDisposition);

        assertFalse(result);
    }

    @Test
    void saveMatch_InvalidDisposition()
    {

        //Check match gets saved with valid match and invalid disposition

        boolean result = dbConnector.saveMatch(validMatch, invalidDisposition);

        assertFalse(result);
    }

    @Test
    void saveMatch_InvalidMatch()
    {
        //Check match gets saved with invalid match and valid disposition

        boolean result = dbConnector.saveMatch(invalidMatch, validDisposition);

        assertFalse(result);
    }

    @Test
    void connect()
    {
        //Nothing to Assert: evaluate if the test has to be removed
    }

    @Test
    void saveMatch()
    {
        //Previously Assert: evaluate if the test has to be removed
    }

    @Test
    void listAllRecordedMatches_NoMatchesSaved_ReturnsEmptyList()
    {
        ArrayList<Pair<Match, Integer>> matches = dbConnector.listAllRecordedMatches();

        assertNotNull(matches);
        assertTrue(matches.isEmpty());
    }

    @Test
    void getDisposition_ValidDisposition()
    {

        int dispositionID = dbConnector.lastSavedDispositionID();

        Disposition disposition = dbConnector.getDisposition(dispositionID);

        assertNotNull(disposition);
        assertEquals(validDisposition.getTextDisposition(), disposition.getTextDisposition());
        assertEquals(validDisposition.isOriginal(), disposition.isOriginal());
        assertEquals(validDisposition.getImagePath(), disposition.getImagePath());
        assertEquals(validDisposition.getOriginalNumber(), disposition.getOriginalNumber());
    }

    @Test
    void getDisposition_InvalidDisposition()
    {
        int dispositionID = -10;

        Disposition disposition = dbConnector.getDisposition(dispositionID);

        assertNull(disposition);
    }

    @Test
    void listAllOriginalDispositions_EmptyList()
    {
        ArrayList<Disposition> originalDispositions = dbConnector.listAllOriginalDispositions();

        assertNotNull(originalDispositions);
    }

    @Test
    void lastSavedMatchID_NoMatch()
    {
        int lastMatchID = dbConnector.lastSavedMatchID();

        assertEquals(0, lastMatchID);
    }

    @Test
    void lastSavedDispositionID_NoDisposition()
    {
        int lastDispositionID = dbConnector.lastSavedDispositionID();

        assertEquals(0, lastDispositionID);
    }

    @Test
    void getMatchID_ExistingMatch()
    {

        int matchID = dbConnector.getMatchID(validMatch);

        assertNotEquals(0, matchID);
    }

    @Test
    void getMatchID_InvalidMatch()
    {

        Match match = new Match("");

        int matchID = dbConnector.getMatchID(match);

        assertEquals(0, matchID);
    }

    @Test
    void getMatchID()
    {
        int matchID = dbConnector.getMatchID(validMatch);

        assertEquals(12, matchID);
    }

    @Test
    void updateMatch()
    {
        //to be checked
        boolean result = dbConnector.updateMatch(validMatch, validDisposition);
        assertTrue(result);
    }
}