package com.klotski.ViewControllers;

import com.klotski.model.Direction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable
{
    private static final int COLUMNS = 4;
    private static final int ROWS = 5;

    @FXML
    private GridPane grid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        CreateBigSquare(1, 0);
        CreateSquare(3,4);
        CreateVerticalRectangle(3,0);
        CreateHorizontalRectangle(0, 3);
    }

    public void UndoClicked(ActionEvent actionEvent)
    {
    }

    public void ResetClicked(ActionEvent actionEvent)
    {
    }

    public void DispositionListClicked(ActionEvent actionEvent)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/View/dispositions.fxml"));
        OpenWindow(fxmlLoader, "Disposition", actionEvent);
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
    private boolean isDirectionDefined;     // Sliding direction defined
    private Direction direction;            // Sliding direction
    private static final int MAXOFFSET = 106;   // Max sliding
    private static final int MINOFFSET = 10;    // Sliding sensibility


    private void MouseDragged(MouseEvent event)
    {
        double xf = event.getScreenX();
        double yf = event.getScreenY();

        if(isDirectionDefined)
            switch(direction)
            {
                case UP:
                case DOWN:
                    if(Math.abs(yf - initialY) < MAXOFFSET)
                        current.setTranslateY(yf - initialY);
                    else
                        current.setTranslateY(yf - initialY > 0 ? MAXOFFSET : -MAXOFFSET);
                    break;
                case LEFT:
                case RIGHT:
                    if(Math.abs(xf - initialX) < MAXOFFSET)
                        current.setTranslateX(xf - initialX);
                    else
                        current.setTranslateX(xf - initialX > 0 ? MAXOFFSET : -MAXOFFSET);
                    break;
            }
        else
            if(Math.abs(xf- initialX) > MINOFFSET)
            {
                isDirectionDefined = true;
                direction = Direction.LEFT;
            }
            else if(Math.abs(yf- initialY) > MINOFFSET)
            {
                isDirectionDefined = true;
                direction = Direction.UP;
            }
    }

    private void MouseReleased(MouseEvent event)
    {
        if(!isDirectionDefined) return;

        double xf = event.getScreenX();
        double yf = event.getScreenY();
        current.setTranslateY(0);
        current.setTranslateX(0);

        switch(direction)
        {
            case UP:
            case DOWN:
                int row = GridPane.getRowIndex(current) + ( yf > initialY ? 1 : - 1);
                if(row >= 0 && row < ROWS - (GridPane.getRowSpan(current) - 1) )
                    GridPane.setRowIndex( current, row);
                break;
            case LEFT:
            case RIGHT:
                int column = GridPane.getColumnIndex(current) + ( xf > initialX ? 1 : - 1);
                if(column >= 0 && column < COLUMNS - (GridPane.getColumnSpan(current) - 1) )
                    GridPane.setColumnIndex( current, column);
                break;
        }

    }
    private void MousePressed(MouseEvent event)
    {
        current = (Pane)event.getSource();
        isDirectionDefined = false;
        initialX = event.getScreenX();
        initialY = event.getScreenY();
    }
}