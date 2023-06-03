package com.klotski.Controllers;

import com.klotski.Interfaces.Observable;
import com.klotski.Interfaces.Observer;
import com.klotski.Model.*;

import java.util.ArrayList;

public class GameHandler implements Observable
{
    /* VARS */
    private Grid grid;                  // MODEL: Klotski data & logic
    private Match currentMatch;         // MODEL: Current game.
    private StateHandler history;       // CONTROLLER: Moves handler
    private String imagePath;   // Runtime data: info for savings;
    private boolean isOriginal; // Runtime data: info for savings;
    private NextMoveGateway gateway;    // Create next move gateway for hint feature

    private int originalDispositionID;  // ID of the original disposition;


    /* CONSTRUCTORS */
    /** Constructor for saved games (reloaded games)
     * @param savedDispositionID: ID of the disposition to load (saved in DB)
     * @param match: Match connected to this disposition (saved in DB)
     */
    public GameHandler(int savedDispositionID, Match match) throws IllegalArgumentException
    {
        // Initialize vars
        views = new ArrayList<Observer>();
        isOriginal = false;
        currentMatch = match;
        if(currentMatch == null)
            throw new IllegalArgumentException("This match is null!");

        // Connect to DB and get disposition
        DBConnector db = new DBConnector();
        db.connect();
        Disposition disposition = db.getDisposition(savedDispositionID);
        if(disposition != null) originalDispositionID = disposition.getOriginalNumber();
        Disposition original = db.getDisposition(originalDispositionID);
        db.close();

        // Validity checks
        if(original == null)
            throw new IllegalArgumentException("Original disposition does not exists!");
        if(!original.isOriginal())
            throw new IllegalArgumentException("Original disposition is not original!");
        if(disposition == null)
            throw new IllegalArgumentException("Saved disposition does not exists!");
        if(disposition.isOriginal())
            throw new IllegalArgumentException("Saved disposition is original!");

        // Load correct grid
        grid = disposition.convertToGrid();
        imagePath = disposition.getImagePath();

        // Load previous match history
        history = new StateHandler(match.getName() + ".hst");
        history.restoreStatus();

        // Create next move gateway for hint feature
        gateway = new NextMoveGateway();
    }

    /** Constructor for new games
     * @param newDispositionID: ID of the original disposition to load (from DB).
     */
    public GameHandler(int newDispositionID) throws IllegalArgumentException
    {
        // Initialize vars
        views = new ArrayList<Observer>();
        originalDispositionID = newDispositionID;
        isOriginal = true;
        currentMatch = new Match();

        // Connect to DB and get disposition
        DBConnector db = new DBConnector();
        db.connect();
        Disposition disposition = db.getDisposition(newDispositionID);
        db.close();

        // Validity checks
        if(disposition == null)
            throw new IllegalArgumentException("This disposition does not exists!");
        if(!disposition.isOriginal())
            throw new IllegalArgumentException("This disposition is not original!");
        imagePath = disposition.getImagePath();

        // Load correct grid
        grid = disposition.convertToGrid();
        history = new StateHandler(currentMatch.getName() + ".hst");

        // Create next move gateway for hint feature
        gateway = new NextMoveGateway();
    }




    /* OBSERVER PATTERN */
    private ArrayList<Observer> views;
    @Override
    public void add(Observer o)
    {
        views.add(o);
        o.updateAll("CONFIGURATION " + originalDispositionID, this.getAllBlocks(), currentMatch.getScore());
        o.notifyVictory(grid.isSolved());
    }
    @Override
    public void remove(Observer o)
    {
        views.remove(o);
    }




    /* MODEL COMMUNICATION */

    /**
     * Execute a move requested by view (user).
     * @param pos: position of the block to move
     * @param dir: direction of the move
     * @return TRUE if move is executed
     */
    public boolean move(Position pos, Direction dir)
    {
        try
        {
            // Get the moving block
            Block current = findBlock(pos);

            // Create current move
            Move move = new Move(pos,dir);

            // Check the validity of the move
            if(grid.move(current,move))
            {
                // Register move
                history.pushMove(move);

                // Increment match score
                currentMatch.incrementScore();

                // Check if klotski is solved: in that case, terminate match
                this.isSolved();

                // Update views
                for(Observer view : views)
                    view.updateMove(move, currentMatch.getScore());

                // Move valid
                return true;
            }
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }

        // Move not valid
        return false;
    }
    /**
     * Execute an hint.
     * @return TRUE if move is executed
     */
    public boolean hint()
    {
        try
        {
            // Create current disposition
            Disposition disp = new Disposition(grid, false,  originalDispositionID);
            disp.takeSnapshot(grid, false);

            // Get hint
            Move move = gateway.GetNextMove(originalDispositionID, disp);
            if(move == null)
            {
                // Generate alternative current disposition (inverted free blocks)
                disp.takeSnapshot(grid, true);

                // Get hint
                move = gateway.GetNextMove(originalDispositionID, disp);
            }

            if(move != null)
            {
                // Execute as a normal move
                move(move.getInit(), move.getDirection());
                currentMatch.increaseHints();
            }
            else
                // No hint: Execute undo
                return undo();

            return true;
        }
        catch(RuntimeException e)
        {
            for(Observer view : views)
                view.notifyError("No internet: cannot get next move.");
            return false;
        }
    }
    /**
     * Check if a move requested by view (user) is valid.
     * @param pos: position of the block to move
     * @param dir: direction of the move
     * @return TRUE if move is valid
     */
    public boolean checkMove(Position pos, Direction dir)
    {
        try
        {
            // Get the moving block
            Block current = findBlock(pos);
            // Check the validity of the move
            return !grid.checkCollision(current, dir);
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }
    }
    /**
     * FInd the block (model) stored in a specific position
     * @param pos: position of the block;
     * @return: block in that position;
     * @throws IllegalArgumentException: block not found;
     */
    private Block findBlock(Position pos) throws IllegalArgumentException
    {
        // Reset iterator
        grid.makeEmpty();

        // Start search
        while(grid.hasNext())
        {
            Block current = grid.next();
            if(current.getPos().equals(pos))
                return current;
        }

        // Block not found.
        throw new IllegalArgumentException("Block not registered in that position.");
    }
    /**
     * Check if game is solved (model logic).
     * @return TRUE if game is solved.
     */
    public boolean isSolved()
    {
        // Notify to views
        for(Observer view : views)
            view.notifyVictory(grid.isSolved());
        if(!grid.isSolved())
            return false;

        // Set match end.
        currentMatch.terminate();
        return true;
    }
    /**
     * Get the whole list of blocks in this klotski match.
     * @return list of blocks;
     */
    public ArrayList<Block> getAllBlocks()
    {
        ArrayList<Block> currentBlocks = new ArrayList<Block>();

        // Reset iterator
        grid.makeEmpty();

        // Get all blocks
        while(grid.hasNext())
        {
            Block current = grid.next();
            if(current != null)
                currentBlocks.add(current);
        }

        return currentBlocks;
    }

    /**
     * Get the score ot the current match: score = #moves
     * @return the count of moves executed by start.
     */
    public int getMoveCounter() { return currentMatch.getScore(); }

    /**
     * Get the hint count ot the current match
     * @return the count of hints executed by start.
     */
    public int getHintCounter() { return currentMatch.getHintsNumber(); }



    /* CONTROLLERS COMMUNICATION */
    /**
     * Get the position of the last moved block: no moves = no block
     * @return position of the last moved block
     */
    public Position getPositionOfLastMovedBlock()
    {
        if(!history.hasState())
            throw new RuntimeException("Moves not ready. View cannot request last position.");

        return history.topMove().getEnd();
    }
    /**
     * Undo the last move executed:
     * get the last move by State Controller;
     * get the opposite of that move;
     * execute that move with Model;
     * @return TRUE if undo is executed
     */
    public boolean undo()
    {
        // If there are no moves, exit
        if(!history.hasState())
        {
            // But if move counter in Model has moves = moves-file not found...
            if(currentMatch.getScore() > 0)
                for(Observer view : views)
                    view.notifyError("Cannot load previous saved moves: file not found.");
            return false;
        }

        // Get last move
        Move last = history.topMove();
        // Create new inverted move.
        Move inverted = new Move(last.getEnd(), calculateOpposite(last.getDirection()));

        try
        {
            // Get the moving block
            Block current = findBlock(inverted.getInit());
            // Execute move
            if(grid.move(current,inverted))
            {
                // Remove canceled move
                history.popMove();

                // Decrement match score
                currentMatch.decrementScore();

                // Updating views
                for(Observer view : views)
                    view.updateMove(inverted, getMoveCounter());

                return true;
            }

            // Cannot undo
            return false;
        }
        catch (IllegalArgumentException e)
        {
            // Cannot undo
            return false;
        }
    }



    /* COMMUNICATION WITH DB CONTROLLER */
    /**
     * Save the current match (and disposition) in the DB.
     */
    public boolean saveGame()
    {
        boolean result = true;

        // Convert current disposition (model).
        Disposition current = new Disposition(grid, isOriginal, originalDispositionID);
        current.setImagePath(imagePath);

        // Connect to DB.
        DBConnector db = new DBConnector();
        db.connect();

        // If this is a new game, save as new.
        // If this is a reloaded game, overwrite it;
        if(isOriginal)
        {
            result = db.saveMatch(currentMatch, current);
            isOriginal = false;
        }
        else
            result = db.updateMatch(currentMatch, current);

        // Close DB.
        db.close();

        // Save moves
        result = result && history.flush();

        return result;
    }




    /* UTILITIES */
    /**
     * Calculate the opposite direction of the given one.
     * @param dir: original direction.
     * @return opposite direction.
     */
    private Direction calculateOpposite(Direction dir)
    {
        // Return opposite direction
        switch(dir)
        {
            case UP: return Direction.DOWN;
            case DOWN : return Direction.UP;
            case LEFT: return Direction.RIGHT;
            default: return Direction.LEFT;
        }
    }
}