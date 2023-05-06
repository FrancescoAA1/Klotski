package com.klotski.ViewControllers;

import com.klotski.UI.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
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



    private Pane current;
    private double x;
    private double y;
    private boolean isDirectionDefined;
    private boolean isVerticalDirection;

    public void MouseDragged(MouseEvent event) {
        double xf = event.getX();
        double yf = event.getY();
        System.out.println("D " + xf + " " + yf);

        if(isDirectionDefined)
            if(isVerticalDirection)
                if(Math.abs(yf - y) < 100)
                    current.setTranslateY(yf - y);
                else
                    current.setTranslateY(yf - y > 0 ? 100 : -100);
            else
                if(Math.abs(xf - x) < 100)
                    current.setTranslateX(xf - x);
                else
                    current.setTranslateX(xf - x > 0 ? 100 : -100);
        else
            if(Math.abs(xf-x) > 10)
            {
                isDirectionDefined = true;
                isVerticalDirection = false;
            }
            else if(Math.abs(yf-y) > 10)
            {
                isDirectionDefined = true;
                isVerticalDirection = true;
            }
    }

    public void MouseReleased(MouseEvent event)
    {
        if(!isDirectionDefined) return;

        double xf = event.getX();
        double yf = event.getY();
        current.setTranslateY(0);
        current.setTranslateX(0);

        if(!isVerticalDirection)
            GridPane.setColumnIndex( current,xf > x ? GridPane.getColumnIndex(current) + 1 : GridPane.getColumnIndex(current) - 1);
        else
            GridPane.setRowIndex( current,yf > y ? GridPane.getRowIndex(current) + 1 : GridPane.getRowIndex(current) - 1);

    }

    public void MousePressed(MouseEvent event)
    {
        current = (Pane)event.getSource();
        isDirectionDefined = false;
        x = event.getX();
        y = event.getY();
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
        grid.add(pane, row, column, rowSpan, columnSpan);
    }
}