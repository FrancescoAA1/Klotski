package com.klotski.ViewControllers;

import com.klotski.Controllers.DBConnector;
import com.klotski.UI.DispositionCard;
import com.klotski.model.Disposition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DispositionsListController implements Initializable {
    /*@FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }*/

    @FXML
    private GridPane grid;

    private DBConnector db;
    public DispositionsListController()
    {
        System.out.println("OK");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        db = new DBConnector();
        ArrayList<Disposition> disp = db.listAllOriginalDispositions();
        ArrayList<DispositionCard> cards = new ArrayList<>();

        String path = "";
        int disposition_index = 1;

        for(Disposition d: disp)
        {
            cards.add(new DispositionCard(getClass().getResource(d.getImagePath()).getPath(), disposition_index++));
        }

        int x = 0;
        int y = 0;
        for(int i = 0; i < cards.size(); i++)
        {
            grid.add(cards.get(i).GetControl(), x++,y);
            GridPane.setMargin(cards.get(i).GetControl(), new Insets(16,6,6,6));
            if(x >= 3)
            {
                x = 0;
                y++;
            }
        }
    }

    public void CloseClicked(ActionEvent actionEvent)
    {
        Stage stage = (Stage) grid.getScene().getWindow();
        stage.close();
    }

    public void MenuClicked(ActionEvent actionEvent)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/View/menu.fxml"));
        OpenWindow(fxmlLoader, "Main Menu", actionEvent);
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