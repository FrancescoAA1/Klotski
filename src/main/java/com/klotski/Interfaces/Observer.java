package com.klotski.Interfaces;

import com.klotski.Model.Block;
import com.klotski.Model.Move;

import java.util.ArrayList;

public interface Observer
{
    public void updateMove(Move move, int movesCounter);
    public void updateAll(String matchName, ArrayList<Block> blocks, int movesCounter);
    public void notifyVictory(boolean isSolved);
}
