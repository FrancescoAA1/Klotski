package com.klotski.Controllers;

import com.klotski.model.Move;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StateHandler {

    private final String DEFAULT_PATH= "/com/Data/MatchLogs/";
    private final String REGEX_FILE_FORMAT = "^[^<>:\"/\\\\|?*]*$";

    private  String fileName;
    private Stack<Move> recordings;
    private int count;

    /** Constructor
     * @param file_name : string that identifies the name of the file to write to or from which to retrieve the state
     **/
    public StateHandler(String file_name)
    {
        recordings = new Stack<Move>();
        count = 0;

        if(checkFileName(file_name))
            fileName = file_name;
        else throw new IllegalArgumentException("Illegal file name");
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
    /** Add a Move in the stack recordings
     * @param move: the move
     */
    public void pushMove(Move move)
    {
        recordings.push(move);
        count++;
    }
    /** Remove the last move from recordings
     * @return the last move removed
     */
    public Move popMove()
    {
        count--;
        return recordings.pop();
    }
    /** Store all recordings in file using this pattern:
     * 1 LINE : round counter
     * OTHER LINE : list of move
     * The move pattern is provide by toString() of Move in the following format
     * INIT_POSITION END_POSITION DIRECTION
     */
    public void flush()
    {
        PrintWriter filewriter = null;
        try
        {
            //try to open file in writing mode
            filewriter = new PrintWriter(new FileWriter(DEFAULT_PATH + fileName));
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
            filewriter.close();
        }
    }
    /** Retrieves the state from the file whose name is the string supplied during object creation.
     * @return FALSE : if the file does not exist or the format is incorrect otherwise
     * TRUE : it loads the data into the stack in memory
     */
    public boolean restoreStatus()
    {
        Scanner filereader = null;

        try
        {
            // if file does not exists return false
            filereader = new Scanner(new FileReader(DEFAULT_PATH + fileName));
        }
        catch(IOException e)
        { return false; }

        // here the file is open and exists
        // start reading file in the established format
        try
        {
            count = Integer.parseInt(filereader.nextLine());


        }
        catch (Exception e)
        { return false; }

        return true;
    }
}
