package com.klotski.Controllers;

import com.klotski.model.*;

import java.util.ArrayList;
import java.util.List;

public class GameHandler
{
    private Grid grid;                  // Klotski data & logic (model)
    private StateHandler history;       // Moves handler
    private Move lastUndoMove;          // Last undo move

    public GameHandler()
    {
        grid = generateGrid();
        history = new StateHandler("temp.hst");
    }

    /** Constructor for saved games
     * @param savedDispositionID
     * @param movesCount
     * @param isTerminated
     */
    public GameHandler(int savedDispositionID, int movesCount, boolean isTerminated)
    {
        
    }

    private Grid generateGrid()
    {
        Grid currentGrid = new Grid();
        currentGrid.setBlock(new Block(new Position(0,0),2,2));
        currentGrid.setBlock(new Block(new Position(2,0),1,1));
        currentGrid.setBlock(new Block(new Position(0,2),2,1));
        currentGrid.setBlock(new Block(new Position(3,3),1,2));
        currentGrid.setBlock(new Block(new Position(3,1),1,2));
        currentGrid.setBlock(new Block(new Position(2,3),1,2));
        currentGrid.setBlock(new Block(new Position(2,1),1,1));
        currentGrid.setBlock(new Block(new Position(0,4),1,1));
        currentGrid.setBlock(new Block(new Position(0,3),2,1));
        currentGrid.setBlock(new Block(new Position(1,4),1,1));

        currentGrid.setFreeBlock(new Block(new Position(3,0),1,1),
                new Block(new Position(2,2),1,1));

        return currentGrid;
    }

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
                return true;
            }

            // Move not valid
            return false;
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }
    }

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

    public boolean isSolved()
    {
        return grid.isSolved();
    }

    public Position getPositionOfLastMovedBlock()
    {
        if(!history.hasState())
            throw new RuntimeException("Moves not ready. View cannot request last position.");

        return history.topMove().getEnd();
    }

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

        throw new IllegalArgumentException("Block not registered in that position.");
    }

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

    public boolean undo()
    {
        // If there are no moves, exit
        if(!history.hasState())
            return false;

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
                lastUndoMove = inverted;
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

    public Move getLastUndoMove()
    {
        return lastUndoMove;
    }

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

    public int getMoveCounter()
    {
        return history.getCount();
    }

    public void saveGame()
    {
        history.flush();
    }
}
