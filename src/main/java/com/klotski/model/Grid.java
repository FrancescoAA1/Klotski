package com.klotski.model;

/**
 * Represents the Grid for the Klotski game. It consists of an array of 10 real Blocks and of an array of 2 Blocks
 * that indicate the free spaces on the grid.
 */
public class Grid
{
    private Block[] occupied;
    private Block[] free;
    private static int counter;
    private static int size;

    /**
     * WIN_POS represents the position that the main block must have in order to win the game.
     */
    private static final Position WIN_POS = new Position(1,2);
    private static final int GRID_WIDTH = 4;
    private static final int GRID_HEIGHT = 5;
    public static final int BLOCK_NUMBER = 10;
    public static final int FREE_BLOCK_NUMBER = 2;
    private static final int MIN_X = 0;
    private static final int MIN_Y = 0;

    /**
     * Constructor of a Grid. It creates the two arrays of Blocks.
     */
    public Grid()
    {
        occupied = new Block[BLOCK_NUMBER];
        free = new Block[FREE_BLOCK_NUMBER];
        counter = 0;
        size = 0;
    }

    /**
     * Method that moves a block in the specified direction if the movement is valid.
     * @param block block that is moving.
     * @param move move that the block is doing.
     * @return true if the move is done, otherwise it returns false.
     */
    public boolean move(Block block, Move move)
    {
        Direction direction = move.getDirection();

        //check if the target is inside the grid
        Position[] crit = getCritical(block,direction);
        if(!isValid(crit[0]) || !isValid(crit[1]))
        {
            return false;
        }

        if(checkCollision(block,direction) || !isValid(move.getEnd()))
        {
            return false;
        }
        moveFree(block,direction);
        block.setPos(move.getEnd());

        return true;
    }

    /**
     * Method to move the free blocks as soon as a valid block is moved
     * @param block block that is moving
     * @param direction direction of the block that is moving
     */
    private void moveFree(Block block, Direction direction)
    {
        int block_width = block.getWidth();
        int block_height = block.getHeight();
        Position block_pos = block.getPos();
        int block_x = block_pos.getX();
        int block_y = block_pos.getY();

        Position[] crit = getCritical(block,direction);

        //target coordinates of the free block
        int free_x1;
        int free_y1;
        //if the block is not 1x1 there might be more free blocks to move
        int free_x2;
        int free_y2;

        int dx1 = 0;
        int dy1 = 0;

        int dx2 = 0;
        int dy2 = 0;

        switch (direction)
        {
            case UP:
                //if height==1 the y coord of the free blocks is the same of the current y coord of the block

                if(block_height != 1)
                {
                    dy1 = +1;
                    dy2 = +1;
                }
                //need to move 2 free blocks
                if(block_width != 1)
                {
                    dx2++;
                }
                break;
            case DOWN:
                //the y coord of the blocks is the same of the current y coord of the block

                //need to move 2 free blocks
                if(block_width != 1)
                {
                    dx2++;
                }
                break;
            case LEFT:
                //if width==1 the x coord of the free blocks is the same of the current x coord of the block

                if(block_width != 1)
                {
                    dx1 = 1;
                    dx2 = 1;
                }
                if(block_height != 1)
                {
                    dy2++;
                }
                break;
            case RIGHT:
                //the x coord of the blocks is the same of the current x coord of the block

                if(block_height != 1)
                {
                    dy2++;
                }
                break;
        }
        free_x1 = block_x + dx1;
        free_y1 = block_y + dy1;
        Position free1_pos = new Position(free_x1,free_y1);

        free_x2 = block_x + dx2;
        free_y2 = block_y + dy2;
        Position free2_pos = new Position(free_x2,free_y2);


        if(crit[0].equals(crit[1]))
        {
            for(Block free : free)
            {
                if(free.getPos().equals(crit[0]))
                {
                    free.setPos(free1_pos);
                }
            }
        } else
        {
            free[0].setPos(free1_pos);
            free[1].setPos(free2_pos);
        }
    }

    /**
     * Method to get the positions in which the specified block wants to move.
     * @param block block that is moving.
     * @param direction direction of the block that is moving
     * @return an array of Positions that may be occupied by the moving block.
     */
    private Position[] getCritical(Block block, Direction direction)
    {
        int block_width = block.getWidth();
        int block_height = block.getHeight();
        Position block_pos = block.getPos();
        int block_x = block_pos.getX();
        int block_y = block_pos.getY();

        //coordinates of the block to check.
        int crit_x1;
        int crit_y1;
        //if the block is not 1x1 there might be more blocks to check. This block is adjacent to the first crit position.
        int crit_x2;
        int crit_y2;

        int dx1 = 0;
        int dy1 = 0;

        int dx2 = 0;
        int dy2 = 0;

        switch (direction)
        {
            case UP:
                dy1 = -1;
                dy2 = -1;
                //need to check 2 blocks
                if(block_width != 1)
                {
                    dx2++;
                }
                break;
            case DOWN:
                dy1 = block_height;
                dy2 = block_height;
                //need to check 2 blocks
                if(block_width != 1)
                {
                    dx2++;
                }
                break;
            case LEFT:
                dx1 = -1;
                dx2 = -1;
                if(block_height != 1)
                {
                    dy2++;
                }
                break;
            case RIGHT:
                dx1 = block_width;
                dx2 = block_width;
                if(block_height != 1)
                {
                    dy2++;
                }
                break;
        }
        crit_x1 = block_x + dx1;
        crit_y1 = block_y + dy1;
        Position crit1_pos = new Position(crit_x1,crit_y1);

        crit_x2 = block_x + dx2;
        crit_y2 = block_y + dy2;
        Position crit2_pos = new Position(crit_x2,crit_y2);

        //note that if crit1_pos == crit2_pos the block that is moving has only one critical position
        Position[] crit = new Position[] {crit1_pos,crit2_pos};
        return crit;

    }

    /**
     * Method to check if there are collisions with other blocks
     * @param block block that we want to check the movement of.
     * @param direction direction of the block that we want to check the movement of.
     * @return true if there are collisions, otherwise it returns false.
     */
    public boolean checkCollision(Block block, Direction direction)
    {
        Position[] crit_pos = getCritical(block,direction);
        return !(isFree(crit_pos[0]) && isFree(crit_pos[1]));
    }

    /**
     * Method to check whether the given position is free or not.
     * @param pos position to check.
     * @return true if the specified position is free, otherwise it returns false.
     */
    public boolean isFree(Position pos)
    {
        for(Block block : free)
        {
            if(block.getPos().equals(pos))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check whether the specified position is inside the Grid.
     * @param pos position to check.
     * @return true if the position is valid, otherwise it returns false.
     */
    private boolean isValid(Position pos)
    {
        return (pos.getX()>=MIN_X && pos.getX()<GRID_WIDTH && pos.getY()>=MIN_Y && pos.getY()<GRID_HEIGHT);
    }
    /**
     * Method to check whether the puzzle has been solved or not.
     * @return true if the main block is in the winning position, otherwise it returns false.
     */
    public boolean isSolved()
    {
        for(Block block : occupied)
        {
            if(block.isSpecial() && block.getPos().equals(WIN_POS))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check if there are other elements in the occupied array.
     * @return true if there is another element in the occupied array, otherwise it returns false.
     */
    public boolean hasNext()
    {
        return (counter < size);
    }

    /**
     * Method to scroll through the occupied array as a list.
     * @return the next Block in the occupied array.
     */
    public Block next()
    {
        Block res =  occupied[counter];
        counter++;
        return res;
    }

    /**
     *  Method to check whether the occupied array is empty.
     * @return true if the array is empty.
     */
    public boolean isEmpty()
    {
        return (counter == 0);
    }

    /**
     * Method to set the counter of elements to zero.
     */
    public void makeEmpty()
    {
        counter = 0;
    }

    /**
     * Method that adds a block in the occupied array.
     * @param block block that is being added to the grid.
     */
    public void setBlock(Block block)
    {
        occupied[size] = block;
        size++;
    }

    /**
     * Method that adds free blocks to the grid.
     * @param block1 first block to add.
     * @param block2 second block to add.
     */
    public void setFreeBlock(Block block1, Block block2)
    {
        free[0] = block1;
        free[1] = block2;
    }


}
