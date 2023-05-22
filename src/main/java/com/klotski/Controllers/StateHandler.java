package com.klotski.Controllers;

import com.klotski.model.Disposition;
import com.klotski.model.Move;
import javafx.util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Represents the object used to save the moves made by the user to file
 */

public class StateHandler {

    private final String DEFAULT_PATH= "/target/classes/com/klotski/Data/MatchLogs/";
    private final String REGEX_FILE_FORMAT = "^[^<>:\"/\\\\|?*]*$";

    private String currentPath;

    private  String fileName;
    private Stack<Move> recordings;
    //da rimuovere
    private Stack<Pair<Move, Disposition>> rectemp;
    private int count;

    /** Constructor
     * @param file_name : string that identifies the name of the file to write to or from which to retrieve the state
     **/
    public StateHandler(String file_name)
    {
        recordings = new Stack<Move>();
        // da rimuovere
        rectemp = new Stack<Pair<Move, Disposition>>();
        count = 0;
        setFileName(file_name);
        // get the current directory
        try
        {
            currentPath = new java.io.File(".").getCanonicalPath();
            // Check if the MatchLocks folder exists otherwise a save has never been made then
            // I create it
            Path path = Paths.get(currentPath + DEFAULT_PATH);
            if(!Files.exists(path)) // In this case I need to create the folder
            {
                try
                {
                    Files.createDirectories(path);
                }
                catch (Exception e)
                {
                    System.out.println("Error while creating MatchLogs folder" + e.getMessage());
                }
            }

        }
        catch(IOException e)
        {
            throw new IllegalStateException("Unable to get the current directory");
        }
    }
    /** Check the correctness of the file name which must be in the format file_name.extension
     * @param file_name: string to check
     * @return TRUE if the format is correct, otherwise FALSE
     */
    private boolean checkFileName(String file_name)
    {
        Pattern pattern = Pattern.compile(REGEX_FILE_FORMAT);
        Matcher matcher = pattern.matcher(file_name);

        return matcher.matches();
    }

    /** Set the filename save location. Format: file_name.extension
     * @param file_name: filename where to save
     */
    public void setFileName(String file_name)
    {
        if(checkFileName(file_name))
            fileName = file_name;
        else throw new IllegalArgumentException("Illegal file name");
    }

    public int getCount() {
        return count;
    }

    /** Check if there is a state saved
     * @return FALSE if the collection is empty, otherwise TRUE
     */
    public boolean hasState()
    {
        return !recordings.isEmpty();
    }
    /** Add a Move in the stack recordings
     * @param move: the move
     */
    public void pushMove(Move move)
    {
        recordings.push(move);
        count++;
    }
    public void pushMovetmp(Move move, Disposition disp)
    {
        rectemp.push(new Pair<Move, Disposition>(move, disp));
        count++;
    }
    /** Remove the last move from recordings
     * @return the last move removed or NULL if the collection is empty
     */
    public Move popMove()
    {
        if(hasState())
        {
            count--;
            return recordings.pop();
        }
        else return null;
    }
    /** Return the last move from recordings without removing
     * @return the last move removed or NULL if the collection is empty
     */
    public Move topMove()
    {
        if(hasState())
        {
            return recordings.peek();
        }
        else return null;
    }
    /** Store all recordings in file using this pattern:
     * 1 LINE : round counter
     * OTHER LINE : list of move
     * The move pattern is provide by toString() of Move in the following format
     * INIT_POSITION END_POSITION DIRECTION
     * @return TRUE if the stack was written to file successfully, FALSE otherwise
     */
    public boolean flush()
    {
        PrintWriter filewriter = null;
        try
        {
            //Try to open file in writing mode
            //The second parameter set to false allows you to overwrite the file.
            //So if I want to save a game that was already saved,
            // just pass the same filename and it gets overwritten
            filewriter = new PrintWriter(new FileWriter(currentPath + DEFAULT_PATH + fileName, false));
            // the first line is the # of rounds
            filewriter.println(count);
            // write all stack of recordings
            for ( Move record:recordings)
            {
                // automatically invoking toString provided by Move to write the line format
                filewriter.println(record);
            }
        }
        catch (IOException e)
        {
            if(filewriter != null)
                filewriter.close();
            return false;
        }
        filewriter.close();
        return true;
    }
    public boolean flush2()
    {
        PrintWriter filewriter = null;
        try
        {
            //Try to open file in writing mode
            //The second parameter set to false allows you to overwrite the file.
            //So if I want to save a game that was already saved,
            // just pass the same filename and it gets overwritten
            filewriter = new PrintWriter(new FileWriter(currentPath + DEFAULT_PATH + fileName, false));
            // write all stack of recordings
            for ( Pair<Move, Disposition> pair:rectemp)
            {
                // automatically invoking toString provided by Move to write the line format
                filewriter.println(pair.getKey().toString() + "?" + pair.getValue().toString());
            }
        }
        catch (IOException e)
        {
            if(filewriter != null)
                filewriter.close();
            return false;
        }
        filewriter.close();
        return true;
    }
    /** Retrieves the state from the file whose name is the string supplied during object creation.
     * @return FALSE : if the file does not exist or the format is incorrect otherwise
     * TRUE : it loads the data into the stack in memory
     */
    public boolean restoreStatus()
    {
        Scanner filereader = null;
        recordings = new Stack<Move>();

        try
        {
            // if file does not exist return false
            filereader = new Scanner(new FileReader(currentPath+DEFAULT_PATH + fileName));
        }
        catch(IOException e)
        { return false; }

        // here the file is open and exists
        // start reading file in the established format
        try
        {
            count = Integer.parseInt(filereader.nextLine());

            while(filereader.hasNextLine())
                recordings.push(Move.convertToMove(filereader.nextLine()));

            // check if the number of moves is equals to counter
            // if not, something went wrong (some lines have been lost)
            if (count != recordings.size())
                throw new IllegalArgumentException();
        }
        catch (Exception e)
        { return false; }

        return true;
    }
}
