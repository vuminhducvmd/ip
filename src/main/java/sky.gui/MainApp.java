package sky.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sky.Sky;

/**
 * JavaFX application entry point for Sky.
 */
public class MainApp extends Application {

    private final Sky sky = new Sky("data/sky.txt");

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/MainWindow.fxml")
        );
        stage.setScene(new Scene(loader.load()));

        // inject Sky into controller
        loader.<sky.gui.controller.MainWindow>getController().setSky(sky);

        stage.setTitle("Sky");
        
        stage.show();
    }
}
