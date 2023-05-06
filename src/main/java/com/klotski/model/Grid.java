package com.klotski.model;

/**
 * Represents the Grid for the Klotski game. It consists of an array of 10 real Blocks and of an array of 2 Blocks
 * that indicate the free spaces on the grid.
 */
public class Grid
{
    private Block[] occupied;
    private Block[] free;

    /**
     * WIN_POS represents the position that the main block must have in order to win the game.
     */
    private final Position WIN_POS = new Position(2,4);
    private final int GRID_WIDTH = 4;
    private final int GRID_HEIGHT = 5;

    /**
     * Constructor of a Grid. It creates the two arrays of Blocks.
     */
    public Grid()
    {
        occupied = new Block[10];
        free = new Block[2];
    }

    /**
     * Method to check whether the given position is free or not.
     * @param pos position to check.
     * @return true if the specified position is free, otherwise it returns false.
     */
    boolean isFree(Position pos)
    {
        for(Block block : free)
        {
            if(block.getPos()==pos)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check whether the puzzle has been solved or not.
     * @return true if the main block is in the winning position, otherwise it returns false.
     */
    boolean isSolved()
    {
        for(Block block : occupied)
        {
            if(block.isSpecial() && block.getPos()==WIN_POS)
            {
                return true;
            }
        }
        return false;
    }

}
