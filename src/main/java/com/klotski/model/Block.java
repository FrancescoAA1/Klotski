package com.klotski.model;

/**
 * Represents a Block of the Klotski game. Each block is represented by the position of its top-left corner, by
 * his width and by his height.
 */
public class Block
{
    private Position pos;
    private int width;
    private int height;

    /**
     * Constructor of a Block.
     * @param pos initial position of the block.
     * @param width width of the block.
     * @param height height of the block.
     */
    public Block(Position pos, int width, int height) {
        this.pos = pos;
        this.width = width;
        this.height = height;
    }

    /**
     * @return true if it is the 2*2 main block, otherwise it returns false.
     */
    public boolean isSpecial()
    {
        return (this.width==2 && this.height ==2);
    }

    /**
     * @return the width of the block.
     */
    public int getWidth()
    {
        return this.width;
    }

    /**
     * @return the height of the block.
     */
    public int getHeight()
    {
        return this.height;
    }

    /**
     * @return the position of the top-left corner of the block.
     */
    public Position getPos()
    {
        return this.pos;
    }

}