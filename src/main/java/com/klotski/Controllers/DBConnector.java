package com.klotski.Controllers;

import com.klotski.model.Disposition;
import com.klotski.model.Match;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;

/**
 * Represents the object used to interact with the DB
 */
public class DBConnector {

    // The relative path where the DB is located
    private static final String URL = "/target/classes/com/klotski/Data/DB/Klotski.db";
    //JDBC connector
    private static final String PREFIX_CONNECTOR = "jdbc:sqlite:";
    // the current path to obtain the absolute path to DB
    private String currentPath;
    // the object that keep the DB connection alive
    private Connection connector;

    /**
     * Constructor
     * I need to obtain the current working directory
     */
    public DBConnector()
    {
        // get the current directory
        try
        {
            currentPath = new java.io.File(".").getCanonicalPath();
        }
        catch(IOException e)
        {
            throw new IllegalStateException("Unable to get the current directory");
        }
    }
    /** Connect to local DB if the file dedicated exists
     */
    public void connect()
    {
        // check if DB file exists...otherwise throw an error
        // create a Path to check in filesystem the existence

        Path path = Paths.get(currentPath + URL);
        
        if(!Files.exists(path))
            throw new IllegalStateException("The DB file does not exist");

        // here the file exist so I am able to connect
        try{
            //Try to establish the connection
            connector = DriverManager.getConnection(PREFIX_CONNECTOR+currentPath+URL);
        }
        catch (Exception e )
        {
            System.out.println("Unable to connect to teh local Klotski.db");
        }
    }
    /** Close the DB connection if it was opened
     */
    public void close()
    {
        try
        {
            if(connector!=null && !connector.isClosed())
                connector.close();
        }
        catch (SQLException e){} // if the connection is not open
    }
    /**
     * This method is reserved to insert a new match in the DB
     * @param match: the match to be saved
     * @return TRUE: if the DB INSERTION is correct, otherwise FALSE
     */
    public boolean saveMatch(Match match, Disposition disposition)
    {
        // if is not already connected to DB
        if(connector == null)
            connect();
        // I want to create the Query structure to insert into DB a new match
        // the match_id field is not necessary because is auto-calculated by DB
        String querysql = "INSERT INTO MATCHES(name, disposition, score, terminated) VALUES(?,?,?,?)";
        // Now I want to set all field of parametrized query
        try
        {
            PreparedStatement statement = connector.prepareStatement(querysql);
            statement.setString(1, match.getName());
            statement.setString(2, disposition.toString());
            statement.setInt(3, match.getScore());
            statement.setInt(4, match.isTerminated()?1:0);
            // run query
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            return false;
        }
        // continuare qui !
        return true;
    }
    /**
     * This method is reserved to obtain all pair Match-LastDisposition saved in DB
     * ordered by match_id that is autoincrement -> this is also a temporal order
     * @return the list of Pair (Match, LastDisposition_ID) saved
     * The ArrayList is null if something went wrong
     */
    public ArrayList<Pair<Match,Integer>> listAllRecordedMatches()
    {
        ArrayList<Pair<Match,Integer>> matches = null;

        // if is not already connected to DB
        if(connector == null)
            connect();
        // I want to create the Query structure to select alla matches from DB
        String querysql = "SELECT name, disposition, score, terminated FROM MATCHES ORDER BY match_id";

        try
        {
            Statement statement = connector.createStatement();
            // executes the DB SELECT and the result is saved in result record collection
            ResultSet result = statement.executeQuery(querysql);
            matches = new ArrayList<Pair<Match,Integer>>();
            Match tmp = null;

            while(result.next()) {
                // create a new Match from data recovered from DB
                tmp = new Match(result.getString("name"));
                tmp.setScore(result.getInt("score"));

                if (result.getInt("terminated") == 1)
                    tmp.terminate();

                matches.add(new Pair<Match, Integer>(tmp, result.getInt("disposition_id")));
            }
        }
        catch (SQLException e)
        {
            return null;
        }
        return matches;
    }
    public String getDisposition(Integer disposition_id)
    {
        return null;
    }

}
