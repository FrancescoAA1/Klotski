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
        validMatch = new Match("2024-05-27_20-59-08");
        invalidMatch = null;

        //Creating Valid and Invalid Disposition
        validDisposition = new Disposition("2-1-0;0#2-2-1;0#2-1-3;0#2-1-0;2#1-2-1;2#2-1-3;2#1-1-1;3#1-1-2;3#1-1-0;4#1-1-3;4#1-1-1;4#1-1-2;4", false);
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


        //Checking match gets saved with valid match and disposition

        boolean saveResult = dbConnector.saveMatch(validMatch, validDisposition);

        assertTrue(saveResult);

        //Deleting the match just saved and check the deletion worked out right
        boolean deleteResult = dbConnector.deleteMatch(validMatch);

        assertTrue(deleteResult);
    }

    @Test
    void saveMatch_InvalidMatchOrDisposition() {

        //Checking match gets saved with invalid match and disposition

        boolean result = dbConnector.saveMatch(invalidMatch, invalidDisposition);

        assertFalse(result);

    }

    @Test
    void saveMatch_InvalidDisposition()
    {

        //Checking match gets saved with valid match and invalid disposition

        boolean result = dbConnector.saveMatch(validMatch, invalidDisposition);

        assertFalse(result);
    }

    @Test
    void saveMatch_InvalidMatch()
    {
        //Checking match gets saved with invalid match and valid disposition

        boolean result = dbConnector.saveMatch(invalidMatch, validDisposition);

        assertFalse(result);
    }

    @Test
    void listAllRecordedMatches_MatchesSaved()
    {
        //Checking the method does not return null
        ArrayList<Pair<Match, Integer>> matches = dbConnector.listAllRecordedMatches();
        assertNotNull(matches);
    }

    @Test
    void getDisposition_ValidDisposition()
    {

        //Saving a match
        dbConnector.saveMatch(validMatch, validDisposition);

        //Getting the disposition just saved
        int dispositionID = dbConnector.lastSavedDispositionID();
        Disposition disposition = dbConnector.getDisposition(dispositionID);

        //Checking it is not null
        assertNotNull(disposition);

        //Checking the saved disposition values are equal to the one passed as a parameter to saveMatch()
        assertEquals(validDisposition.getTextDisposition(), disposition.getTextDisposition());
        assertEquals(validDisposition.isOriginal(), disposition.isOriginal());
        assertEquals(validDisposition.getImagePath(), disposition.getImagePath());
        assertEquals(validDisposition.getOriginalNumber(), disposition.getOriginalNumber());
        dbConnector.deleteMatch(validMatch);
    }

    @Test
    void getDisposition_InvalidDisposition()
    {
        //Checking getDisposition()'s response to an Invalid Disposition
        int dispositionID = -10;

        Disposition disposition = dbConnector.getDisposition(dispositionID);

        assertNull(disposition);
    }

    @Test
    void listAllOriginalDispositions_EmptyList()
    {
        //Checking the method does not return null
        ArrayList<Disposition> originalDispositions = dbConnector.listAllOriginalDispositions();

        assertNotNull(originalDispositions);
    }

    @Test
    void getMatchID_ExistingMatch()
    {

        //Checking matchID of the lastSaved match
        int matchID = dbConnector.getMatchID(validMatch);
        int matchLastId = dbConnector.lastSavedMatchID();

        assertEquals(matchLastId, matchID);
    }

    @Test
    void getMatchID_InvalidMatch()
    {
        //Checking matchID of invalid Match
        Match match = new Match("");

        int matchID = dbConnector.getMatchID(match);

        assertEquals(0, matchID);
    }

    @Test
    void updateMatch_InvalidDisposition()
    {
        //Updating match with Invalid Disposition
        dbConnector.saveMatch(validMatch, validDisposition);
        boolean result = dbConnector.updateMatch(validMatch, invalidDisposition);
        dbConnector.deleteMatch(validMatch);
        assertFalse(result);
    }

    @Test
    void updateMatch_ValidDisposition()
    {
        //Updating match with Valid Disposition
        dbConnector.saveMatch(validMatch, validDisposition);
        boolean result = dbConnector.updateMatch(validMatch, validDisposition);
        dbConnector.deleteMatch(validMatch);
        assertTrue(result);
    }
}