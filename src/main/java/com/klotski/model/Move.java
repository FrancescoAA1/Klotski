package com.klotski.model;

/**
 * Represents a correct move made by the player
 */
public class Move
{

    private static final String STRING_SEPARATOR = " ";
    private static final int INIT_PATTERN_STRING_POS = 0;
    private static final int END_PATTERN_STRING_POS = 1;
    private static final int DIRECTION_PATTERN_STRING_POS = 2;

    // value indicating the starting position of the moved block
    private Position init;
    // value indicating the final position of the moved block
    private Position end;
    // indicates the direction of the move
    private Direction direction;

    public Move(Position init, Position end, Direction direction)
    {
        this.direction = direction;
        this.init = init;
        this.end = end;
    }
    /*
    * we only use get methods since the created move is supposed to be immutable
    * */
    public Direction getDirection()
    {
        return direction;
    }
    public Position getInit()
    {
        return init;
    }
    public Position getEnd()
    {
        return end;
    }
    /**
     * override of toString in order to have the move pattern written to file
     */
    @Override
    public String toString()
    {
        return init.toString() + STRING_SEPARATOR + end.toString() + STRING_SEPARATOR + direction.toString();
    }
    /**
     * Method to convert a string in a Move in the format INIT END DIRECTION.
     * @param move: the string to be converted
     * @return the object move
     */
    public static Move convertToMove(String move)
    {
        Position init = null;
        Position end = null;
        Direction dir;
        try
        {
            String[] tmp = move.split(STRING_SEPARATOR);
            init = Position.convertToPosition(tmp[INIT_PATTERN_STRING_POS]);
            end = Position.convertToPosition(tmp[END_PATTERN_STRING_POS]);
            dir = Direction.values()[Integer.parseInt(tmp[DIRECTION_PATTERN_STRING_POS])];
            return new Move(init, end, dir);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Illegal format for move");
        }
    }

}