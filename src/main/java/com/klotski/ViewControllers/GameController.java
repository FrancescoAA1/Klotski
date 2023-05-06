package com.klotski.ViewControllers;

import com.klotski.UI.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    public static final int CELLSIDE = 94;
    public static final int CELLSPACING = 16;
    public static final int GRIDWIDTH = 430;
    public static final int GRIDHEIGHT = 536;
    public static final int GRIDPADDING = 6;

    @FXML
    private GridPane grid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        BigSquare bs = new BigSquare();
        grid.add(bs.GetControl(), 0, 0, 2, 2);
        LittleSquare ls = new LittleSquare();
        grid.add(ls.GetControl(), 2, 0, 1, 1);
        LittleSquare ls1 = new LittleSquare();
        grid.add(ls1.GetControl(), 0, 4, 1, 1);
        VerticalRectangle vr = new VerticalRectangle();
        HorizontalRectangle hr = new HorizontalRectangle();
        grid.add(hr.GetControl(), 2, 2, 2,1);
        grid.add(vr.GetControl(), 0, 2,1,2);
    }

    public void UndoClicked(ActionEvent actionEvent)
    {
    }

    public void ResetClicked(ActionEvent actionEvent)
    {
    }

    private void PositionBlock(Rectangle block, int column, int row)
    {
        AnchorPane.setTopAnchor(block, (double) (GRIDPADDING + (row) * CELLSPACING + row * CELLSIDE));
        AnchorPane.setLeftAnchor(block, (double) ( GRIDPADDING + (column) * CELLSPACING + column * CELLSIDE));
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
}