package com.klotski.Controllers;

import com.klotski.Model.Disposition;
import com.klotski.Model.Match;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;

/**
 * Represents the object used to interact with the DB
 */
public class DBConnector {

    // The relative path where the DB is located
    private static final String URL = "/com/klotski/Data/DB/Klotski.db";
    //JDBC connector
    private static final String PREFIX_CONNECTOR = "jdbc:sqlite:";
    //JDBC connector
    private static final String JAR_PREFIX_CONNECTOR = "jdbc:sqlite::resource:";
    // the object that keep the DB connection alive
    private Connection connector;

    /**
     * Connect to local DB if the file dedicated exists
     */
    public void connect()
    {
        try
        {
            //Try to establish the connection (DEBUG BUILD)
            connector = DriverManager.getConnection(PREFIX_CONNECTOR + getClass().getResource(URL));
        }
        catch (Exception e)
        {
            try
            {
                //Try to establish the connection (JAR FILE)
                connector = DriverManager.getConnection(JAR_PREFIX_CONNECTOR + getClass().getResource(URL));
            } catch (Exception e1) {
                System.out.println("Unable to connect to the local Klotski.db");
            }
        }
    }

    /**
     * Close the DB connection if it was opened
     */
    public void close()
    {
        try {
            if (connector != null && !connector.isClosed())
                connector.close();
        } catch (SQLException e) {
        } // if the connection is not open
    }

    /**
     * This method is reserved to insert a new match in the DB
     *
     * @param match: the match to be saved
     * @param disposition: the disposition of this match
     * @return TRUE: if the DB INSERTION is correct, otherwise FALSE
     */
    public boolean saveMatch(Match match, Disposition disposition)
    {
        // if is not already connected to DB
        if (connector == null)
            connect();
        // the steps are:
        // (1) Insert into DB the disposition
        // (2) Obtain the disposition ID that is auto-calculated from DB
        // (3) Insert into DB the match wid the ID disposition from (2)


        // I want to create the Query structure to insert into DB a new disposition
        // the disposition_id field is not necessary because is auto-calculated by DB
        String querysql1 = "INSERT INTO DISPOSITIONS (schema, original, disposition_image, original_number) VALUES(?,?,?,?)";
        // Now I want to set all field of parametrized query
        try {
            PreparedStatement statement = connector.prepareStatement(querysql1);
            statement.setString(1, disposition.getTextDisposition());
            statement.setInt(2, 0); // this is not an original disposition
            statement.setString(3, disposition.getImagePath());
            statement.setInt(4, disposition.getOriginalNumber());
            // run query
            statement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        // In this case the insertion has been done
        // I sort the table in descending order by ID so
        // the value with the largest ID is the first (and it's the last inserted).
        // Then, I extract only the first row from the SELECT
        int lastDispositionID = lastSavedDispositionID();
        // if it is equals to zero something went wrong
        if(lastDispositionID == 0) return false;

        // Now I have to insert the ID  in the match

        // I want to create the Query structure to insert into DB a new match
        // the match_id field is not necessary because is auto-calculated by DB
        String querysql3 = "INSERT INTO MATCHES(name, disposition_id, score, terminated, hints_number) VALUES(?, ?, ?, ?, ?)";
        // Now I want to set all fields of parametrized query
        try {
            PreparedStatement statement = connector.prepareStatement(querysql3);
            statement.setString(1, match.getName());
            statement.setInt(2, lastDispositionID);
            statement.setInt(3, match.getScore());
            statement.setInt(4, match.isTerminated() ? 1 : 0);
            statement.setInt(5, match.getHintsNumber());
            // run query
            statement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    /**
     * This method is reserved to obtain all pair Match-LastDisposition saved in DB
     * ordered by match_id that is autoincrement -> this is also a temporal order
     *
     * @return the list of Pair (Match, LastDisposition_ID) saved
     * The ArrayList is null if something goes wrong
     */
    public ArrayList<Pair<Match, Integer>> listAllRecordedMatches()
    {
        ArrayList<Pair<Match, Integer>> matches = null;

        // if is not already connected to DB
        if (connector == null)
            connect();
        // I want to create the Query structure to select alla matches from DB
        String querysql = "SELECT name, disposition_id, score, terminated, hints_number FROM MATCHES ORDER BY match_id";

        try {
            Statement statement = connector.createStatement();
            // executes the DB SELECT and the result is saved in result record collection
            ResultSet result = statement.executeQuery(querysql);
            matches = new ArrayList<Pair<Match, Integer>>();
            Match tmp = null;

            while (result.next()) {
                // create a new Match from data recovered from DB
                tmp = new Match(result.getString("name"));
                tmp.setScore(result.getInt("score"));

                if (result.getInt("terminated") == 1)
                    tmp.terminate();
                tmp.setHintsNumber(result.getInt("hints_number"));

                matches.add(new Pair<Match, Integer>(tmp, result.getInt("disposition_id")));
            }
        } catch (SQLException e) {
            return null;
        }
        return matches;
    }

    /**
     * This method is reserved to obtain a single and specific disposition saved in DB
     *
     * @param disposition_id: the ID of the disposition that you want to get
     * @return the disposition with the specified ID in DB, null if there is not a disposition
     * with the specified ID
     */
    public Disposition getDisposition(Integer disposition_id)
    {
        Disposition disposition = null;
        // if is not already connected to DB
        if (connector == null)
            connect();
        // I want to create the Query structure to select the specific disposition
        String querysql = "SELECT schema, original, disposition_image, original_number FROM DISPOSITIONS WHERE disposition_id = ?";

        try {
            PreparedStatement statement = connector.prepareStatement(querysql);
            //insert the param for ID
            statement.setInt(1, disposition_id);
            // executes the DB SELECT and the result is saved in result record collection
            ResultSet result = statement.executeQuery();
            // I get only one row
            String image = result.getString("disposition_image");
            String schema = result.getString("schema");
            int original = result.getInt("original");
            int origin_number = result.getInt("original_number");

            if (original == 1)
                disposition = new Disposition(schema, true);
            else disposition = new Disposition(schema, false);

            disposition.setImagePath(image);
            disposition.setOriginalNumber(origin_number);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return disposition;
    }

    /**
     * This method is reserved to obtain all original dispositions in DB
     *
     * @return the list of all original dispositions
     * The ArrayList is null if something goes wrong
     */
    public ArrayList<Disposition> listAllOriginalDispositions()
    {
        ArrayList<Disposition> originalDisposition = null;

        // if it is not already connected to DB
        if (connector == null)
            connect();
        // I want to create the Query structure to select all original dispositions
        String querysql = "SELECT schema, disposition_image FROM DISPOSITIONS WHERE original=1";

        try {
            Statement statement = connector.createStatement();
            // executes the DB SELECT and the result is saved in result record collection
            ResultSet result = statement.executeQuery(querysql);
            originalDisposition = new ArrayList<Disposition>();

            Disposition tmp = null;

            while (result.next()) {
                // create a new Match from data recovered from DB
                tmp = new Disposition(result.getString("schema"), true);
                tmp.setImagePath(result.getString("disposition_image"));

                originalDisposition.add(tmp);
            }
        } catch (SQLException e) {
            return null;
        }
        return originalDisposition;
    }

    /**
     * This method is reserved to return the ID of the last match saved in DB
     *
     * @return the last match's ID saved on DB
     * 0 if something goes wrong
     */
    public int lastSavedMatchID()
    {
        // if it is not already connected to DB
        if (connector == null)
            connect();
        // I sort the MATCHES table in descending order by ID so
        // the value with the largest ID is the first (and it's the last inserted).
        // Then, I extract only the first row from the SELECT
        String querysql = "SELECT match_id FROM MATCHES ORDER BY match_id DESC LIMIT 1";
        int lastMatchID;
        try
        {
            Statement statement = connector.createStatement();
            // executes the DB SELECT and the result is saved in result record collection
            ResultSet result = statement.executeQuery(querysql);

            lastMatchID = result.getInt("match_id");

            return lastMatchID;

        } catch (SQLException e) {
            return 0;
        }
    }
    /**
     * This method is reserved to return the ID of the last disposition saved in DB
     *
     * @return the last disposition's ID saved on DB
     * 0 if something goes wrong
     */
    public int lastSavedDispositionID()
    {
        // if it is not already connected to DB
        if (connector == null)
            connect();
        String querysql = "SELECT disposition_id FROM DISPOSITIONS ORDER BY disposition_id DESC LIMIT 1";
        int lastDispositionID;
        try {
            Statement statement = connector.createStatement();
            // executes the DB SELECT and the result is saved in result record collection
            ResultSet result = statement.executeQuery(querysql);

            lastDispositionID = result.getInt("disposition_id");

            return lastDispositionID;

        } catch (SQLException e) {
            return 0;
        }
    }

    /**
     * This method is reserved to return the ID of the match specified in params
     * if it is saved in DB
     * @param match: the game whose ID you want to get
     * @return the match ID if it is saved in DB
     * 0 if something goes wrong or match is not in DB
     */
    public int getMatchID(Match match)
    {
        // if it is not already connected to DB
        if (connector == null)
            connect();
        // I want to search with a SELECTION QUERY the id associated to the match with this name
        String querysql = "SELECT match_id FROM MATCHES WHERE name = ?";

        try
        {
            PreparedStatement statement = connector.prepareStatement(querysql);
            //insert the param from match (name)
            statement.setString(1, match.getName());
            // executes the DB SELECT and the result is saved in result record collection
            ResultSet result = statement.executeQuery();
            // It returns only one object (the ID if is present)
            return result.getInt("match_id");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    /**
     * This method is reserved to update an already existing match in the DB
     *
     * @param match: the match to be updated
     * @param disposition: the disposition to update associated to the match to update
     * @return TRUE: if the DB UPDATE is correct, otherwise FALSE
     */
    public boolean updateMatch(Match match, Disposition disposition)
    {
        // if is not already connected to DB
        if (connector == null)
            connect();
        // the steps are:
        // (1) Get the id of the match to update
        // (2) Update the match with new data
        // (3) Get the ID of the disposition associated to the match
        // (4) Update the disposition

        // step 1 - Get the id of the match to update
        int matchID = getMatchID(match);
        // if it is equals to zero something went wrong
        if(matchID == 0) return false;

        // step 2 - update the match row

        // the field ID , name and disposition are not changed
        // so I only need to update score and terminated status
        String querysql1 = "UPDATE MATCHES SET score = ?, terminated = ?, hints_number = ? WHERE match_id = ?";

        try {
            PreparedStatement statement = connector.prepareStatement(querysql1);
            statement.setInt(1, match.getScore());
            statement.setInt(2, match.isTerminated()?1:0);
            statement.setInt(3, match.getHintsNumber());
            statement.setInt(4, matchID);
            // run query
            int rowsAffected = statement.executeUpdate();
            // if no rows where affected by the update => something went wrong
            if(rowsAffected==0) return false;

        } catch (SQLException e) {
            return false;
        }

        // step 3 - Get the ID of the disposition associated to the match
        int dispositionID = getDispositionAssociated(matchID);
        // if it is equals to zero something went wrong
        if(dispositionID == 0) return false;

        // step 4 - Update the disposition

        // the field ID, originals, original_number and disposition_image are not changed
        // so I only need to update the schema
        String querysql2 = "UPDATE DISPOSITIONS SET schema = ? WHERE disposition_id = ?";

        try {
            PreparedStatement statement = connector.prepareStatement(querysql2);
            statement.setString(1, disposition.getTextDisposition());
            statement.setInt(2, dispositionID);
            // run query
            int rowsAffected = statement.executeUpdate();
            // if no rows where affected by the update => something went wrong
            if(rowsAffected==0) return false;

        } catch (SQLException e) {
            return false;
        }

        return true;
    }
    /**
     * This method is reserved to return the ID of disposition associated
     * to the match with the ID specified in params
     * if th eID is correct
     * @param match_id: the ID of the match whose disposition ID you want to get
     * @return the disposition ID associated to the match if it is saved in DB
     * 0 if something goes wrong or match_id is not correct
     */
    private int getDispositionAssociated(int match_id)
    {
        // if it is not already connected to DB
        if (connector == null)
            connect();
        // I want to search with a SELECTION QUERY the id associated to the match with this name
        String querysql = "SELECT disposition_id FROM MATCHES WHERE match_id = ?";

        try
        {
            PreparedStatement statement = connector.prepareStatement(querysql);
            //insert the param from match (name)
            statement.setInt(1, match_id);
            // executes the DB SELECT and the result is saved in result record collection
            ResultSet result = statement.executeQuery();
            // It returns only one object (the ID if is present)
            return result.getInt("disposition_id");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
}