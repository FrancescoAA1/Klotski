package com.klotski.model;

/**
 * Represents a correct move made by the player
 */
public class Move
{
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
    // override of toString in order to have the move pattern written to file
    @Override
    public String toString()
    {
        return init.toString() + " " + end.toString() + " " + direction.toString();
    }

}