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

        Scene scene = new Scene(loader.load());

        stage.setScene(scene);

        loader.<sky.gui.controller.MainWindow>getController().setSky(sky);

        stage.setTitle("Sky");

        // prevent UI breaking when resized too small
        stage.setMinWidth(500);
        stage.setMinHeight(400);

        stage.show();
    }
}
