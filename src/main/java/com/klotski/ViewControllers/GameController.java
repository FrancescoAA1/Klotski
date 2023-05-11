package com.klotski.ViewControllers;

import com.klotski.Controllers.GameHandler;
import com.klotski.UI.Axis;
import com.klotski.model.Block;
import com.klotski.model.Direction;
import com.klotski.model.Position;
import com.klotski.model.Move;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable
{
    /* VARS */
    private GameHandler gameHandler;    // CONTROLLER object

    /* VIEW VARS */
    @FXML
    private GridPane grid;
    @FXML
    private Label lblCounterUnit;
    @FXML
    private Label lblCounterTens;
    @FXML
    private Label lblCounterHundreds;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // Initialize objects
        gameHandler = new GameHandler();
        initializeAnimations();

        // Load dynamically blocks from controller.
        loadKlotski();
    }

    private void loadKlotski()
    {
        // Get all blocks from CONTROLLER
        ArrayList<Block> blocks = gameHandler.getAllBlocks();

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
    }

    public void UndoClicked(ActionEvent actionEvent)
    {
        // Execute undo
        if(gameHandler.undo())
        {
            // Get undo move
            Move undo = gameHandler.getLastUndoMove();
            // Get moved block
            Position pos = undo.getInit();
            current = null;
            for(Node child : grid.getChildren())
            {
                if(GridPane.getColumnIndex(child) == pos.getX() && GridPane.getRowIndex(child) == pos.getY())
                    current = (Pane)child;
            }
            // Set end position
            if(current != null)
            {
                GridPane.setColumnIndex( current, undo.getEnd().getX());
                GridPane.setRowIndex( current, undo.getEnd().getY());
                startUndoAnimation(undo.getDirection(), current);
            }

            // Load counter
            updateMoveCounter();
        }
    }

    public void ResetClicked(ActionEvent actionEvent)
    {
        // Unload all existing blocks
        grid.getChildren().clear();

        // Initialize objects
        gameHandler = new GameHandler();

        // Load dynamically blocks from controller.
        loadKlotski();

        // Load counter
        updateMoveCounter();
    }

    public void DispositionListClicked(ActionEvent actionEvent)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/View/dispositions.fxml"));
        OpenWindow(fxmlLoader, "Disposition", actionEvent);
    }

    public void HomeClicked(ActionEvent actionEvent)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/View/menu.fxml"));
        OpenWindow(fxmlLoader, "Main Menu", actionEvent);
    }

    public void NextBestMoveClicked(ActionEvent actionEvent) {
    }

    private void OpenWindow(FXMLLoader fxmlLoader, String title, ActionEvent event)
    {
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    private void updateMoveCounter()
    {
        int count = gameHandler.getMoveCounter();

        lblCounterUnit.setText(String.valueOf(count % 10));
        lblCounterTens.setText(String.valueOf(count % 100  / 10));
        lblCounterHundreds.setText(String.valueOf(count % 1000  / 100));
    }

    private void CreateSquare(int column, int row)
    {
        Pane square = new Pane();
        square.getStyleClass().add("square");
        AssignBehaviour(square, column, row, 1, 1);
    }
    private void CreateBigSquare(int column, int row)
    {
        Pane square = new Pane();
        square.getStyleClass().add("big_square");
        AssignBehaviour(square, column, row, 2, 2);
    }
    private void CreateVerticalRectangle(int column, int row)
    {
        Pane square = new Pane();
        square.getStyleClass().add("vertical_rectangle");
        AssignBehaviour(square, column, row, 1, 2);
    }
    private void CreateHorizontalRectangle(int column, int row)
    {
        Pane square = new Pane();
        square.getStyleClass().add("horizontal_rectangle");
        AssignBehaviour(square, column, row, 2, 1);
    }
    private void AssignBehaviour(Pane pane, int column, int row, int columnSpan, int rowSpan)
    {
        pane.setOnMousePressed(e -> MousePressed(e));
        pane.setOnMouseDragged(e -> MouseDragged((e)));
        pane.setOnMouseReleased(e -> MouseReleased(e));
        grid.add(pane, column, row, columnSpan, rowSpan);
    }



    /* HANDLING SLIDE INPUT FUNCTION */
    private Pane current;           // Current block selected
    private double initialX;        // X of the start point
    private double initialY;        // Y of the start point
    private boolean isDirectionDefined;         // Drag direction defined
    private Axis direction;                     // Drag direction
    private static final int MAXOFFSET = 106;   // Max drag consented
    private static final int MINOFFSET = 10;    // Drag sensibility
    private boolean[] availability;             // Destinations availability
    private TranslateTransition xTranslateAnimation;        // Animation for horizontal move-error
    private TranslateTransition yTranslateAnimation;        // Animation for vertical move-error
    private TranslateTransition undoTranslateAnimation;     // Animation for undo move


    /**
     * Initialize animations object.
     */
    private void initializeAnimations()
    {
        // X translation for move error
        xTranslateAnimation = new TranslateTransition(Duration.millis(40));
        xTranslateAnimation.setFromX(-3);
        xTranslateAnimation.setToX(3);
        xTranslateAnimation.setCycleCount(4);
        xTranslateAnimation.setAutoReverse(true);
        xTranslateAnimation.setOnFinished(e -> {xTranslateAnimation.getNode().setTranslateX(0);});

        // Y translation for move error
        yTranslateAnimation = new TranslateTransition(Duration.millis(40));
        yTranslateAnimation.setFromY(-3);
        yTranslateAnimation.setToY(3);
        yTranslateAnimation.setCycleCount(4);
        yTranslateAnimation.setAutoReverse(true);
        yTranslateAnimation.setOnFinished(e -> {yTranslateAnimation.getNode().setTranslateY(0);});
    }
    private void startUndoAnimation(Direction undo, Pane control)
    {
        // UNDO translation
        undoTranslateAnimation = new TranslateTransition(Duration.millis(150));
        switch(undo)
        {
            case UP:
                undoTranslateAnimation.setFromY(MAXOFFSET);
                undoTranslateAnimation.setToY(0);
                break;
            case DOWN:
                undoTranslateAnimation.setFromY(MAXOFFSET * -1);
                undoTranslateAnimation.setToY(0);
                break;
            case LEFT:
                undoTranslateAnimation.setFromX(MAXOFFSET);
                undoTranslateAnimation.setToX(0);
                break;
            case RIGHT:
                undoTranslateAnimation.setFromX(MAXOFFSET * -1);
                undoTranslateAnimation.setToX(0);
                break;
        }
        undoTranslateAnimation.setNode(control);
        undoTranslateAnimation.playFromStart();
    }

    /**
     * Event handler: handle drag started on a klotski block.
     * Create all necessary variables to handle drag movement.
     * @param event event generated by the click of the mouse on a klotski block.
     */
    private void MousePressed(MouseEvent event)
    {
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
        // If the direction of the drag is not defined, ignore event.
        if(!isDirectionDefined) return;

        // Get current mouse position
        double finalX = event.getScreenX();
        double finalY = event.getScreenY();

        // Current position of the block selected by user
        Position currentPos = new Position(GridPane.getColumnIndex(current), GridPane.getRowIndex(current));

        // Reset block position - View update #1
        current.setTranslateY(0);
        current.setTranslateX(0);

        // Get and check move.
        switch(direction)
        {
            case VERTICAL:
            {
                // If requested movement is too long, truncate it
                double translation = finalY - initialY;
                // Check movement validity
                Direction dir = translation > 0 ? Direction.DOWN : Direction.UP;
                if (gameHandler.move(currentPos, dir))
                {
                    // Set new position of the block after the move.
                    Position destination = gameHandler.getPositionOfLastMovedBlock();
                    GridPane.setRowIndex( current, destination.getY());

                    // Move Counter
                    updateMoveCounter();
                }
                else
                {
                    yTranslateAnimation.setNode(current);
                    yTranslateAnimation.play();
                }
                break;
            }
            case HORIZONTAL:
            {
                // If requested movement is too long, truncate it
                double translation = finalX - initialX;
                // Check movement validity
                Direction dir = translation > 0 ? Direction.RIGHT : Direction.LEFT;
                if (gameHandler.move(currentPos, dir))
                {
                    // Set new position of the block after the move.
                    Position destination = gameHandler.getPositionOfLastMovedBlock();
                    GridPane.setColumnIndex( current, destination.getX());

                    // Move Counter
                    updateMoveCounter();
                }
                else
                {
                    xTranslateAnimation.setNode(current);
                    xTranslateAnimation.play();
                }
                break;
            }
        }
    }
}