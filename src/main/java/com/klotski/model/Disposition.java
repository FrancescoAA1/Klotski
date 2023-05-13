package com.klotski.model;

import java.util.ArrayList;

/**
 * This class represents the snapshot of a grid configuration.
 * Its textual representation is as follows: a row is created for each block of the grid where
 * they are saved, in order:
 * height-width-position
 * the character separating the line of one block from that of another is '|'
 * the last two lines represents the free blocks
 */
public class Disposition {

    private final String LINE_SEPARATOR = "|";
    private final String FIELD_SEPARATOR = "-";

    private final int NUM_FIELDS = 3;
    private String textDisposition;
    private String imagePath;
    private boolean isOriginal;

    /**
     * This is the "default constructor"
     *
     * @param isOriginal: specify if this is an initial game disposition or not
     */
    public Disposition(boolean isOriginal)
    {
        this.isOriginal = isOriginal;
    }

    /**
     * This is the constructor to use when you want to freeze the state of a grid
     *
     * @param isOriginal: specify if this is an initial game disposition or not
     * @param snapGrid:   the grid that you want to freeze saving his configuration
     */
    public Disposition(Grid snapGrid, boolean isOriginal)
    {
        this.isOriginal = isOriginal;
        takeSnapshot(snapGrid);
    }

    /**
     * This constructor is used when you want to get the grid from a saved layout snapshot
     *
     * @param isOriginal:  specify if this is an initial game disposition or not
     * @param txtSnapGrid: the string that identify the saved grid disposition
     */
    public Disposition(String txtSnapGrid, boolean isOriginal)
    {
        this.textDisposition = txtSnapGrid;
    }

    public boolean isOriginal()
    {
        return isOriginal;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

    public String getTextDisposition()
    {
        return textDisposition;
    }

    public void setTextDisposition(String textDisposition)
    {
        this.textDisposition = textDisposition;
    }

    /**
     * this method is used to convert the grid disposition into a string to be serialized
     *
     * @param grid: the grid to be snap
     */
    public void takeSnapshot(Grid grid)
    {
        // forall block I want to write his attributes in the txt string
        grid.makeEmpty();
        textDisposition = ""; // delete all previous savings

        // the format is: height-width-position

        while (grid.hasNext()) {
            Block block = grid.next();
            textDisposition = textDisposition +
                    block.getHeight() + FIELD_SEPARATOR +
                    block.getWidth() + FIELD_SEPARATOR +
                    block.getPos() ;

            if (grid.hasNext()) // if is the last I can't add the line separator
                textDisposition += LINE_SEPARATOR;
        }
        // Now I have to obtain the free block and I write them on the snap
        ArrayList<Block> free = grid.getFree();

        int count = 0;

        while (!free.isEmpty()) {

            Block block = free.get(count);

            textDisposition = textDisposition +
                    block.getHeight() + FIELD_SEPARATOR +
                    block.getWidth() + FIELD_SEPARATOR +
                    block.getPos() ;

            if (!grid.isEmpty()) // if is the last I can't add the line separator
                textDisposition += LINE_SEPARATOR;

            count++;
        }
    }

    /**
     * this method is used to obtain the grid described by the txt format saved in teh textDisposition
     *
     * @return the grid that the string  describe or null if the format is incorrect
     */
    public Grid convertToGrid()
    {
        Grid grid = new Grid();
        if (textDisposition == null) throw new IllegalStateException("Cannot convert a null string into a grid");
        // Start by dividing the textual representation into the lines that compose it
        // (separated by the line separator) to get all the blocks
        try
        {

            String[] stringBlocks = textDisposition.split(LINE_SEPARATOR);

            Block tmp;
            Block free1 = null;
            Block free2 = null;

            // first check: the # of lines must be the sum of normal block and fre block
            if(stringBlocks.length != Grid.BLOCK_NUMBER + Grid.FREE_BLOCK_NUMBER)
                return null;

            int countNormal = 0;
            // I have to check that there is a special block otherwise
            // I risk a rambling grid
            boolean specialFound = false;

            for (String txtBlock:stringBlocks)
            {
                // get all field of a block in the string
                String[] blockFields = txtBlock.split(FIELD_SEPARATOR);

                // the textual representation must necessarily have 3 fields
                // otherwise something went wrong
                if(blockFields.length != NUM_FIELDS)
                    return null;

                // the format is: height-width-position-freeBlock

                tmp = new Block(
                        Position.convertToPosition(blockFields[2]), // position in the last in txt
                        Integer.parseInt(blockFields[1]), // width is the second in txt
                        Integer.parseInt(blockFields[0])  // height is the first in txt
                        );

                if(tmp.isSpecial())
                    specialFound = true;


                if(countNormal < Grid.BLOCK_NUMBER)
                {
                    grid.setBlock(tmp);
                    countNormal++;
                }
                else if(free1==null)
                    free1 = tmp;
                else if(free2==null)
                    free2 = tmp;
                else return null; //something went wrong
            }
            // check if I have found the spacial block
            if(!specialFound) return null; 
            // it remains only to set the two free blocks
            grid.setFreeBlock(free1, free2);
            return grid;
        }
        catch(Exception e)
        {
            return null;
        }
    }
}
