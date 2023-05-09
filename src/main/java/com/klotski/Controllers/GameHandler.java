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
        grid = new Grid();// Creare GRID
        status = new StateHandler("temp");
        history = new ArrayList<Move>();
    }

    public boolean move(Position pos, Direction dir)
    {
        try
        {
            // Get the moving block
            Block current = findBlock(pos);
            Move move = new Move(pos,dir);  //===AGGIUNTO===//
            // Check the validity of the move
            //if(grid.move(current, dir))  //===CAMBIATO===//
            if(grid.move(current,move))  //===AGGIUNTO===//
            {
                // Register move
                return true;
            }
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
        //return grid.isSolved();
        return false;
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
            if(current.getPos() == pos)
                return current;
        }

        throw new IllegalArgumentException("Block not registered in that position.");
    }

}
