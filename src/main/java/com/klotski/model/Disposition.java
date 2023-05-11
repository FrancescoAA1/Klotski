package com.klotski.model;

/**
 * This class represents the snapshot of a grid configuration.
 * Its textual representation is as follows: a row is created for each block of the grid where
 * they are saved, in order:
 * height-width-position-freeBlock
 * the character separating the line of one block from that of another is '|'
 * */
public class Disposition {

    private static final String LINE_SEPARATOR = "|";
    private static final String FIELD_SEPARATOR = "-";
    private String textDisposition;
    private String imagePath;
    private boolean isOriginal;

    public Disposition(boolean isOriginal)
    {
        this.isOriginal = isOriginal;
    }
    public Disposition(Grid snapGrid, boolean isOriginal)
    {
        this.isOriginal = isOriginal;
        takeSnapshot(snapGrid);
    }
    public Disposition(String txtSnapGrid, boolean isOriginal)
    {
        this.textDisposition = txtSnapGrid;
    }
    public boolean isOriginal() {
        return isOriginal;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTextDisposition() {
        return textDisposition;
    }

    public void setTextDisposition(String textDisposition) {
        this.textDisposition = textDisposition;
    }

    /**
     * this method is used to convert the grid disposition into a string to be serialized
     * @param grid: the grid to be snap
     * */
    public void takeSnapshot(Grid grid)
    {
        // forall block I want to write his attributes in te txt string
        grid.makeEmpty();
        while(grid.hasNext())
        {
            Block block = grid.next();
            textDisposition = textDisposition +
            block.getHeight() + FIELD_SEPARATOR +
            block.getWidth() + FIELD_SEPARATOR +
            block.getPos() + FIELD_SEPARATOR +
            block.isSpecial();
            if(grid.hasNext()) // if is the last i can't add the line separator
                textDisposition+=LINE_SEPARATOR;
        }
        // MANCANO I BLOCCHI FREE DA INSERIRE:::CHIEDERE A TOMMASO SE HA UN METODO PER OTTENERLI
    }
    /**
     * this method is used to obtain the grid described by the txt format saved in teh textDisposition
     * @return the grid that the string  describe or null if the format is incorrect
     * */
    public Grid convertToGrid()
    {
        if(textDisposition == null) throw new IllegalStateException("Cannot convert a null string into a grid");
        //TOMMASO MI DEVE FAR SAPER COME POSSO IMPOSTARE I BLOCCHI DELLA GRID
        // IL SUO METODO MI SEMBRA PERICOLOSO
        return null;
    }
}
