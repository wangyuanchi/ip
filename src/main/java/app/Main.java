package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import shibe.Shibe;

/**
 * A GUI for Shibe using FXML.
 */
public class Main extends Application {

    private Shibe shibe = new Shibe("./data/itemList.txt", ",,");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setResizable(false);
            fxmlLoader.<MainWindow>getController().setShibe(shibe);
            this.shibe.run();
            fxmlLoader.<MainWindow>getController().displayShibeDialog("Hello!");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
