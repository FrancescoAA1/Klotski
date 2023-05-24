package com.klotski.Model;

/**
 * Represents a Position as a set of (x,y) coordinates
 */
public class Position
{
    private static final String STRING_SEPARATOR = ";";
    private static final int X_PATTERN_STRING_POS = 0;
    private static final int Y_PATTERN_STRING_POS = 1;
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
        return Integer.toString(x) + STRING_SEPARATOR + Integer.toString(y);
    }
    /**
     * Method to convert a string in a position in the format XY.
     * @param position: the string to be converted
     * @return the object position
     */
    public static Position convertToPosition(String position)
    {
        int x;
        int y;
        try
        {
            String[] tmp = position.split(STRING_SEPARATOR);
            x = Integer.parseInt(tmp[X_PATTERN_STRING_POS]);
            y = Integer.parseInt(tmp[Y_PATTERN_STRING_POS]);
            return new Position(x,y);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Illegal format for position. The patter is X"+STRING_SEPARATOR+"Y");
        }
    }
}