package com.klotski.Controllers;

import com.klotski.model.*;

import java.util.ArrayList;
import java.util.List;

public class GameHandler
{
    StateHandler status;
    Grid grid;

    ArrayList<Move> history;

    public GameHandler()
    {
        grid = generateGrid();
        status = new StateHandler("temp");
        history = new ArrayList<Move>();
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
                history.add(move);
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
        if(history.isEmpty())
            throw new RuntimeException("Moves not ready. View cannot request last position.");

        return history.get(history.size() - 1).getEnd();
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

}
