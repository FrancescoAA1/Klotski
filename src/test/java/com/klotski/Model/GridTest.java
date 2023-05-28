package com.klotski.Model;

import com.sun.nio.sctp.SctpSocketOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GridTest
{
    private Grid grid;
    private Position position;

    @BeforeEach
    void setUp()
    {
        grid = new Grid();
        position = new Position(0,0);

        grid.setBlock(new Block(new Position(0,0),1,2));
        grid.setBlock(new Block(new Position(1,0),2,2));
        grid.setBlock(new Block(new Position(3,0),1,2));
        grid.setBlock(new Block(new Position(0,2),1,2));
        grid.setBlock(new Block(new Position(1,2),2,1));
        grid.setBlock(new Block(new Position(3,2),1,2));
        grid.setBlock(new Block(new Position(1,3),1,1));
        grid.setBlock(new Block(new Position(2,3),1,1));
        grid.setBlock(new Block(new Position(0,4),1,1));
        grid.setBlock(new Block(new Position(3,4),1,2));

        grid.setFreeBlock(new Block(new Position(1,4),1,1),new Block(new Position(2,4),1,1));
    }

    @Test
    void testMove_invalidMove()
    {
        Block block = grid.next();
        // Test moving a block up (invalid move)
        assertFalse(grid.move(block, new Move(position,Direction.UP)));
        //Check whether the final position of the block is the same of the initial position
        assertEquals(position, block.getPos());

    }

    @Test
    void testMove_validMove()
    {
        Block block = grid.next();
        //get the block in position (1;3)
        for(int i=0;i<6;i++)
        {
            block = grid.next();
        }

        // Test moving a block down (valid move)
        assertTrue(grid.move(block, new Move(block.getPos(),Direction.DOWN)));
        //Check the final position of the block, the block moved downwards
        assertEquals(new Position(1,4), block.getPos());
    }

    @Test
    void testMoveFree_invalidMove()
    {
        Block block = grid.next();
        // Test moving a block up (invalid move)
        grid.move(block, new Move(position,Direction.UP));

        //get the free block list
        ArrayList<Block> free_list = grid.getFree();

        //test whether the free blocks are in the same position
        assertEquals(new Position(1,4), free_list.get(0).getPos());
        assertEquals(new Position(2,4), free_list.get(1).getPos());

    }

    @Test
    void testMoveFree_validMove()
    {
        Block block = grid.next();
        //get the block in position (1;3)
        for(int i=0;i<6;i++)
        {
            block = grid.next();
        }

        //move the block downwards
        grid.move(block, new Move(block.getPos(),Direction.DOWN));

        //get the free block list
        ArrayList<Block> free_list = grid.getFree();

        //test whether the first free block moved upwards and the second free block is in the same position
        assertEquals(new Position(1,3), free_list.get(0).getPos());
        assertEquals(new Position(2,4), free_list.get(1).getPos());

    }

    @Test
    void testCheckCollision_invalidMove()
    {
        Block block = grid.next();

        //move the block up (invalid move)
        boolean res = grid.checkCollision(block, Direction.UP);

        //Assert
        assertTrue(res);
    }

    @Test
    void testCheckCollision_validMove()
    {
        Block block = grid.next();
        //get the block in position (1;3)
        for(int i=0;i<6;i++)
        {
            block = grid.next();
        }

        //move the block downwards (valid move)
        boolean res = grid.checkCollision(block, Direction.DOWN);

        //Assert
        assertFalse(res);
    }


    @Test
    void testIsSolved_unsolved()
    {
        //Act
        boolean res = grid.isSolved();

        //Assert
        assertFalse(res);
    }

    @Test
    void testHasNext_present()
    {
        //Act
        boolean res = grid.hasNext();

        //Assert
        assertTrue(res);
    }

    @Test
    void testHasNext_not_present()
    {
        //reach the end of the blocks list
        for(int i=0;i<11;i++)
        {
            grid.next();
        }

        //Act
        boolean res = grid.hasNext();

        //Assert
        assertFalse(res);
    }

    @Test
    void testNext_present()
    {
        Block block = grid.next();
        Block expected = new Block(new Position(0,0),1,2);

        //Assert
        assertEquals(expected.getPos(),block.getPos());
        assertEquals(expected.getWidth(),block.getWidth());
        assertEquals(expected.getHeight(),block.getHeight());
    }

    @Test
    void testNext_not_present()
    {
        //reach the end of the blocks list
        for(int i=0;i<11;i++)
        {
            grid.next();
        }
        //Assert
        assertNull(grid.next());
    }


    @Test
    void testSetBlock_full_list()
    {
        //Act
        boolean res = grid.setBlock(new Block(new Position(0,0),1,2));

        //Assert
        assertFalse(res);
    }


    @Test
    void testGetFree()
    {
        //Act
        ArrayList<Block> free_list = grid.getFree();

        //Assert
        //check first free block
        assertEquals(new Position(1,4), free_list.get(0).getPos());
        assertEquals(1, free_list.get(0).getWidth());
        assertEquals(1, free_list.get(0).getHeight());

        //check second free block
        assertEquals(new Position(2,4), free_list.get(1).getPos());
        assertEquals(1, free_list.get(1).getWidth());
        assertEquals(1, free_list.get(1).getHeight());

    }
}