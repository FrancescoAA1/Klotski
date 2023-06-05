package com.klotski.View;

import com.klotski.Controllers.GameHandler;
import com.klotski.Controllers.NextMoveGateway;
import com.klotski.Interfaces.Observer;
import com.klotski.UI.Axis;
import com.klotski.Model.*;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class GameView implements Observer
{
    /* VARS */
    private GameHandler gameHandler;    // CONTROLLER object
    private boolean hintFlag;

    private boolean fullHintGame;

    /* VIEW VARS */
    @FXML
    private GridPane grid;      // Game grid
    @FXML
    private Label lblCounterUnit, lblCounterTens, lblCounterHundreds, lblCounterThousands;  // Move counter labels
    @FXML
    private Label lblTitle, lblVictoryMovesCounter, lblVictoryHintsCounter, lblError;     // Other labels
    @FXML
    private AnchorPane pnOverlay, pnVictoryPane;      // Popups


    /* OBSERVER PATTERN */

    /**
     * Updates the view resetting the previous move in the grid
     * @param move previous move
     * @param movesCounter move counter
     */
    @Override
    public void updateMove(Move move, int movesCounter)
    {
        // Get moved block
        Position pos = move.getInit();
        current = null;
        for(Node child : grid.getChildren())
        {
            if(GridPane.getColumnIndex(child) == pos.getX() && GridPane.getRowIndex(child) == pos.getY())
                current = (Pane)child;
        }

        // Set end position
        if(current != null)
        {
            GridPane.setColumnIndex( current, move.getEnd().getX());
            GridPane.setRowIndex( current, move.getEnd().getY());
            startMoveAnimation(move.getDirection(), current);
        }

        // Load counter
        updateMoveCounter(movesCounter);
    }

    /** Dynamically create all the blocks of the gamegrid, based on the current match
     * registered on the Controller.
     */
    @Override
    public void updateAll(String matchName, ArrayList<Block> blocks, int movesCounter)
    {
        // Load all blocks in VIEW
        for (Block current: blocks)
        {
            if(current.isSpecial())
                CreateBigSquare(current.getPos().getX(), current.getPos().getY());
            else if (current.getWidth() == 2 && current.getHeight() == 1)
                CreateHorizontalRectangle(current.getPos().getX(), current.getPos().getY());
            else if (current.getWidth() == 1 && current.getHeight() == 2)
                CreateVerticalRectangle(current.getPos().getX(), current.getPos().getY());
            else
                CreateSquare(current.getPos().getX(), current.getPos().getY());
        }

        // Load move counter
        updateMoveCounter(movesCounter);

        // Load title
        lblTitle.setText(matchName);
    }

    /**
     * If game is solver: start victory animation
     * At animation end, victory window opens automatically
     * @param isSolved
     */
    @Override
    public void notifyVictory(boolean isSolved)
    {
        if(isSolved)
            // see: initializeAnimations();
            victoryTranslateAnimation.playFromStart();
    }

    /**
     * Show the error launched by Controller-
     * @param error Error to show.
     */
    @Override
    public void notifyError(String error)
    {
        lblError.setText(error);
        errorFadeAnimation.playFromStart();
    }



    /* COMMUNICATION FUNCTIONS */
    /**
     * Set Controller for MVC pattern.
     * Load game grid (generates klotski blocks dynamically);
     * Initialize dynamic animations (for blocks);
     * @param game: GameHandler object created by previous view:
     *            - DispositionList -> new game created;
     *            - SavedGameList -> load saved game;
     */
    public void setController(GameHandler game)
    {
        // Get GameHandler (Controller)
        gameHandler = game;

        // Initialize UI
        initializeUI();

        // Initialize objects
        initializeKlotskiAnimations();

        // Register observer
        game.add(this);
    }
    /** Open another window.
     * @param fxmlLoader window loader.
     * @param title title of the new window.
     */
    private void OpenWindow(FXMLLoader fxmlLoader, String title)
    {
        // Get current window;
        Parent root = null;
        try { root = fxmlLoader.load(); }
        catch (IOException e) { throw new RuntimeException(e); }

        // Set scene and parameters
        Scene scene = new Scene(root);
        Stage stage = (Stage) grid.getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);

        // Calculate the center position of the screen
        Rectangle2D screenBound = Screen.getPrimary().getVisualBounds();
        double centerX = (screenBound.getWidth() - scene.getWidth())/2;
        double centerY = (screenBound.getHeight() - scene.getHeight())/2;

        // Set the scene position to the center of the screen
        stage.setX(centerX);
        stage.setY(centerY);

        // Show current stage
        stage.show();
    }




    /* UTILITIES */

    /** Undo last move.
     *  Handle the communication with the controller;
     *  Handle the UI effects;
     * @param actionEvent: contains the source of the event (button)
     * @return TRUE if undo is executed
     */
    private boolean undo(ActionEvent actionEvent)
    {
        // Execute undo
        if(gameHandler.undo())
            return true;
        else
        {
            // Show error
            xErrorTranslateAnimation.setNode((Node)actionEvent.getSource());
            xErrorTranslateAnimation.playFromStart();
            return false;
        }
    }
    /** Dynamically create a 1x1 square;
     * @param column column index in grid;
     * @param row row index in grid;
     */
    private void CreateSquare(int column, int row)
    {
        Pane square = new Pane();
        square.getStyleClass().add("square");
        AssignBehaviour(square, column, row, 1, 1);
    }
    /** Dynamically create a 2x2 square;
     * @param column column index in grid;
     * @param row row index in grid;
     */
    private void CreateBigSquare(int column, int row)
    {
        Pane square = new Pane();
        square.getStyleClass().add("big_square");
        AssignBehaviour(square, column, row, 2, 2);
        // Set victory animation
        victoryTranslateAnimation.setNode(square);
        reverseVictoryTranslateAnimation.setNode(square);
    }
    /** Dynamically create a vertical rectangle;
     * @param column column index in grid;
     * @param row row index in grid;
     */
    private void CreateVerticalRectangle(int column, int row)
    {
        Pane square = new Pane();
        square.getStyleClass().add("vertical_rectangle");
        AssignBehaviour(square, column, row, 1, 2);
    }
    /** Dynamically create a horizontal rectangle;
     * @param column column index in grid;
     * @param row row index in grid;
     */
    private void CreateHorizontalRectangle(int column, int row)
    {
        Pane square = new Pane();
        square.getStyleClass().add("horizontal_rectangle");
        AssignBehaviour(square, column, row, 2, 1);
    }
    /** Set events handler for dynamic sliding;
     * Add the block in the game grid.
     */
    private void AssignBehaviour(Pane pane, int column, int row, int columnSpan, int rowSpan)
    {
        pane.setOnMousePressed(e -> MousePressed(e));
        pane.setOnMouseDragged(e -> MouseDragged((e)));
        pane.setOnMouseReleased(e -> MouseReleased(e));
        grid.add(pane, column, row, columnSpan, rowSpan);
    }




    /* BUTTON EVENT HANDLER */
    public void UndoClicked(ActionEvent actionEvent)
    {
        // Execute undo
        undo(actionEvent);
    }
    public void ResetClicked(ActionEvent actionEvent)
    {
        // Undo all operations
        while(gameHandler.getMoveCounter() > 0)
        {
            // Execute undo: if history-file is not found, exit reset
            if(!undo(actionEvent))
                break;
        }
    }
    public void SaveClicked(ActionEvent actionEvent)
    {
        gameHandler.saveGame();
    }
    public void DispositionListClicked(ActionEvent actionEvent)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/GUI/dispositions.fxml"));
        OpenWindow(fxmlLoader, "Disposition");
    }
    public void HomeClicked(ActionEvent actionEvent)
    {
        if(gameHandler.isSolved())
            gameHandler.saveGame();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/GUI/menu.fxml"));
        OpenWindow(fxmlLoader, "Main Menu");
    }
    public void NextBestMoveClicked(ActionEvent actionEvent)
    {
        if(gameHandler.isSolved())
            return;
        if(hintFlag)
            return;
        hintFlag = gameHandler.hint();
    }
    public void FullHintGame(ContextMenuEvent e)
    {
        fullHintGame = !fullHintGame;
        if(fullHintGame)
        {
            if(gameHandler.isSolved())
                return;
            if(hintFlag)
                return;
            hintFlag = gameHandler.hint();
        }
    }
    public void ContinueGameClicked(ActionEvent actionEvent)
    {
        hideVictoryPanel();
        // Disable full-hint game mode, if enabled.
        fullHintGame = false;
    }




    /* BLOCK SLIDING HANDLERS */
    private Pane current;           // Current block selected
    private double initialX, initialY;        // Position (row and column indexes) of the start point
    private boolean isDirectionDefined;         // Drag direction defined
    private Axis direction;                     // Drag direction
    private boolean[] availability;             // Destinations availability

    /**
     * Event handler: handle drag started on a klotski block.
     * Set all necessary variables to handle drag movement.
     * @param event event generated by the click of the mouse on a klotski block.
     */
    private void MousePressed(MouseEvent event)
    {
        // If game is already end, block any move.
        if(gameHandler.isSolved())
            return;

        // Get current block selected by user
        current = (Pane)event.getSource();

        // Reset mouse drag variables
        isDirectionDefined = false;
        initialX = event.getScreenX();
        initialY = event.getScreenY();

        // Current position of the block selected by user
        Position currentPos = new Position(GridPane.getColumnIndex(current), GridPane.getRowIndex(current));

        // Create availability "map"
        availability = new boolean[4];
        // Check valid moves
        availability[Direction.UP.ordinal()] = gameHandler.checkMove(currentPos, Direction.UP);
        availability[Direction.DOWN.ordinal()] = gameHandler.checkMove(currentPos, Direction.DOWN);
        availability[Direction.LEFT.ordinal()] = gameHandler.checkMove(currentPos, Direction.LEFT);
        availability[Direction.RIGHT.ordinal()] = gameHandler.checkMove(currentPos, Direction.RIGHT);
    }
    /**
     * Event handler: handle dragging on a klotski block.
     * Show block movement for better usability.
     * @param event event generated by the drag of the mouse on a klotski block.
     */
    private void MouseDragged(MouseEvent event)
    {
        // If game is already end, block any move.
        if(gameHandler.isSolved())
            return;

        // Get current mouse position
        double finalX = event.getScreenX();
        double finalY = event.getScreenY();

        // If the direction of the drag is already known.
        if(isDirectionDefined)
            // Based on direction, show block movement in real time.
            switch(direction)
            {
                case VERTICAL:
                {
                    // If requested movement is too long, truncate it
                    double translation = finalY - initialY;
                    if (Math.abs(translation) >= MAXOFFSET)
                        translation = translation > 0 ? MAXOFFSET : -MAXOFFSET;
                    // Check movement validity
                    Direction dir = translation > 0 ? Direction.DOWN : Direction.UP;
                    boolean valid = availability[dir.ordinal()];
                    // If valid, show movement
                    if (valid)
                        current.setTranslateY(translation);
                    break;
                }
                case HORIZONTAL:
                {
                    // If requested movement is too long, truncate it
                    double translation = finalX - initialX;
                    if (Math.abs(translation) >= MAXOFFSET)
                        translation = translation > 0 ? MAXOFFSET : -MAXOFFSET;
                    // Check movement validity
                    Direction dir = translation > 0 ? Direction.RIGHT : Direction.LEFT;
                    boolean valid = availability[dir.ordinal()];
                    // If valid, show movement
                    if (valid)
                        current.setTranslateX(translation);
                    break;
                }
            }
        else
            // If mouse is dragged for enough pixel, set direction.
            if(Math.abs(finalX- initialX) > MINOFFSET)
            {
                // Set generic horizontal direction.
                isDirectionDefined = true;
                direction = Axis.HORIZONTAL;
            }
            else if(Math.abs(finalY- initialY) > MINOFFSET)
            {
                // Set generic vertical direction.
                isDirectionDefined = true;
                direction = Axis.VERTICAL;
            }
    }
    /**
     * Event handler: handle drop of a klotski block.
     * Register movement, check validity with Controller and update View.
     * @param event event generated by the drop of a klotski block.
     */
    private void MouseReleased(MouseEvent event)
    {
        // If game is already end, block any move.
        if(gameHandler.isSolved())
            return;

        // If the direction of the drag is not defined, ignore event.
        if(!isDirectionDefined) return;

        // Get current mouse position
        double finalX = event.getScreenX();
        double finalY = event.getScreenY();

        // Current position of the block selected by user
        Position currentPos = new Position(GridPane.getColumnIndex(current), GridPane.getRowIndex(current));

        // Get and check move.
        switch(direction)
        {
            case VERTICAL:
            {
                // Check movement validity
                double translation = finalY - initialY;
                Direction dir = translation > 0 ? Direction.DOWN : Direction.UP;
                if (!gameHandler.move(currentPos, dir))
                {
                    yErrorTranslateAnimation.setNode(current);
                    yErrorTranslateAnimation.play();
                }
                break;
            }
            case HORIZONTAL:
            {
                // Check movement validity
                double translation = finalX - initialX;
                Direction dir = translation > 0 ? Direction.RIGHT : Direction.LEFT;
                if (!gameHandler.move(currentPos, dir))
                {
                    xErrorTranslateAnimation.setNode(current);
                    xErrorTranslateAnimation.play();
                }
                break;
            }
        }
    }




    /* BLOCK SLIDING UI */
    private static final int MAXOFFSET = 106;   // Max drag consented
    private static final int MINOFFSET = 10;    // Drag sensibility
    private TranslateTransition xErrorTranslateAnimation;        // Animation for horizontal move-error
    private TranslateTransition yErrorTranslateAnimation;        // Animation for vertical move-error
    private TranslateTransition moveTranslateAnimation;     // Animation for moves (user-move, undo, hints)
    private TranslateTransition victoryTranslateAnimation;     // Animation for victory
    private TranslateTransition reverseVictoryTranslateAnimation;     // Animation for "Continue Game" after victory

    /**
     * Initialize animations object for klotski blocks.
     */
    private void initializeKlotskiAnimations()
    {
        // X translation for move error
        xErrorTranslateAnimation = new TranslateTransition(Duration.millis(40));
        xErrorTranslateAnimation.setFromX(-3);
        xErrorTranslateAnimation.setToX(3);
        xErrorTranslateAnimation.setCycleCount(4);
        xErrorTranslateAnimation.setAutoReverse(true);
        xErrorTranslateAnimation.setOnFinished(e -> {xErrorTranslateAnimation.getNode().setTranslateX(0);});

        // Y translation for move error
        yErrorTranslateAnimation = new TranslateTransition(Duration.millis(40));
        yErrorTranslateAnimation.setFromY(-3);
        yErrorTranslateAnimation.setToY(3);
        yErrorTranslateAnimation.setCycleCount(4);
        yErrorTranslateAnimation.setAutoReverse(true);
        yErrorTranslateAnimation.setOnFinished(e -> {yErrorTranslateAnimation.getNode().setTranslateY(0);});

        // Victory translation
        victoryTranslateAnimation = new TranslateTransition(Duration.millis(700));
        victoryTranslateAnimation.setFromY(0);
        victoryTranslateAnimation.setToY(450);
        victoryTranslateAnimation.setDelay(Duration.millis(300));
        // Set animation acceleration
        victoryTranslateAnimation.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double t) {
                return (t==0.0) ? 0.0 : Math.pow(1.6, 7*(t-1));
            }
        });
        // At the end of that animation, show victory pane.
        victoryTranslateAnimation.setOnFinished(e -> showVictoryPanel());

        // Reverse victory translation
        reverseVictoryTranslateAnimation = new TranslateTransition(Duration.millis(700));
        reverseVictoryTranslateAnimation.setFromY(450);
        reverseVictoryTranslateAnimation.setToY(0);
        // At the end of that animation, undo last move.
        reverseVictoryTranslateAnimation.setOnFinished(e -> UndoClicked(e));
    }
    /** Configure and start undo animation for klotski blocks;
     * @param dir direction of the animation;
     * @param control klotski block;
     */
    private void startMoveAnimation(Direction dir, Pane control)
    {
        // MOVE translation
        moveTranslateAnimation = new TranslateTransition(Duration.millis(150));
        switch(dir)
        {
            case UP:
                moveTranslateAnimation.setFromY(control.getTranslateY() == 0 ? MAXOFFSET : MAXOFFSET + control.getTranslateY());
                moveTranslateAnimation.setToY(0);
                break;
            case DOWN:
                moveTranslateAnimation.setFromY(control.getTranslateY() == 0 ? MAXOFFSET * -1 : MAXOFFSET * -1 + control.getTranslateY());
                moveTranslateAnimation.setToY(0);
                break;
            case LEFT:
                moveTranslateAnimation.setFromX(control.getTranslateX() == 0 ? MAXOFFSET : MAXOFFSET + control.getTranslateX());
                moveTranslateAnimation.setToX(0);
                break;
            case RIGHT:
                moveTranslateAnimation.setFromX(control.getTranslateX() == 0 ? MAXOFFSET * -1 : MAXOFFSET * -1 + control.getTranslateX());
                moveTranslateAnimation.setToX(0);
                break;
        }
        moveTranslateAnimation.setNode(control);

        // If full hint game mode is enabled, limit animations... (no flickering caused by thread overloaded).
        if(fullHintGame)
        {
            moveTranslateAnimation.setFromX(0);
            moveTranslateAnimation.setFromY(0);
            moveTranslateAnimation.setDuration(Duration.millis(40));
        }

        moveTranslateAnimation.playFromStart();
        moveTranslateAnimation.setOnFinished(e -> {hintFlag = false; if(fullHintGame) NextBestMoveClicked(e);});
    }



    /* UI FUNCTIONS */
    private FadeTransition victoryFadeAnimation;    // Animation to show or hide victory pane.
    private FadeTransition errorFadeAnimation;      // Animation to show or hide file-error pane.

    /** Set title and default styles;
     *  Initialize animations;
     */
    private void initializeUI()
    {
        // Set styles
        pnVictoryPane.setOpacity(0);
        lblError.setOpacity(0);

        // Victory pane show
        victoryFadeAnimation = new FadeTransition(Duration.millis(1200));
        victoryFadeAnimation.setFromValue(0.1);
        victoryFadeAnimation.setToValue(1);
        victoryFadeAnimation.setNode(pnVictoryPane);

        // Error pane show
        errorFadeAnimation = new FadeTransition(Duration.millis(2500));
        errorFadeAnimation.setFromValue(1);
        errorFadeAnimation.setToValue(0);
        errorFadeAnimation.setNode(lblError);
    }
    /** Format string for move counter in the victory pane.
     * @param num: move count;
     * @param digitCount: number of digit showed.
     * @return text for the counter;
     */
    private String formatCounters(int num, int digitCount)
    {
        // reach the correct number of digits
        String temp = String.valueOf(num);
        while(temp.length() < digitCount) {
            temp = '0' + temp;
        }

        // Format the output string: " n n n n "
        String res = " ";
        for(int i = 0; i < temp.length(); i++)
        {
            res += temp.charAt(i) + " ";
        }

        return res;
    }
    /**
     * Set the value of the move counter.
     */
    private void updateMoveCounter(int movesCounter)
    {
        lblCounterUnit.setText(String.valueOf(movesCounter % 10));
        lblCounterTens.setText(String.valueOf(movesCounter % 100  / 10));
        lblCounterHundreds.setText(String.valueOf(movesCounter % 1000  / 100));
        lblCounterThousands.setText(String.valueOf(movesCounter % 10000  / 1000));
    }
    /**
     * Show victory pane.
     */
    private void showVictoryPanel()
    {
        // Moves counter
        lblVictoryMovesCounter.setText(formatCounters(gameHandler.getMoveCounter(), 4));

        // Hints counter
        lblVictoryHintsCounter.setText(formatCounters(gameHandler.getHintCounter(), 4));

        // Show victory pane
        victoryFadeAnimation.playFromStart();
        pnOverlay.setVisible(true);
        pnVictoryPane.setVisible(true);
    }
    private void hideVictoryPanel()
    {
        // Hide victory pane
        pnOverlay.setVisible(false);
        pnVictoryPane.setVisible(false);
        pnVictoryPane.setOpacity(0);

        // Undo victory animations
        reverseVictoryTranslateAnimation.playFromStart();
    }
}