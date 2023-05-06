package com.klotski.model;

/**
 * Represents a Position as a set of (x,y) coordinates
 */
public class Position
{
    private int x;
    private int y;

    /**
     * Constructor of a Position.
     * @param x x coordinate of the position.
     * @param y y coordinate of the position.
     */
    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x coordinate.
     */
    public int getX()
    {
        return this.x;
    }

    /**
     * @return the y coordinate.
     */
    public int getY()
    {
        return this.y;
    }

    /**
     * @param x x coordinate to set.
     */
    public void setX(int x)
    {
        this.x = x;
    }

    /**
     * @param y y coordinate to set.
     */
    public void setY(int y)
    {
        this.y  = y;
    }

    /**
     * Method to compare if two positions have the same (x,y) coordinates.
     * @param o is the Position that we want to compare to the implicit parameter.
     * @return true if the two positions have the same (x,y) coordinates, otherwise it returns false.
     */
    public boolean equals(Object o)
    {
        if(o == this)
        {
            return true;
        }
        if(!(o instanceof Position))
        {
            return false;
        }
        Position position = (Position) o;
        return (position.getX()==this.getX() && position.getY()==this.getY());
    }

    @Override
    public String toString() {
        return Integer.toString(x) + Integer.toString(y);
    }
    /**
     * Method to convert a string in a position in the format XY.
     * @param position: the string to be converted
     */
    public void convertToPosition(String position)
    {


    }
}