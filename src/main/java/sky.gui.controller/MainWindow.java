package sky.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sky.Sky;
import sky.SkyException;

/**
 * Controller for the main application window.
 */
public class MainWindow extends AnchorPane {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Sky sky;

    private final Image userImage =
            new Image(getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image dukeImage =
            new Image(getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Sky logic engine.
     *
     * @param sky Sky instance
     */
    public void setSky(Sky sky) {
        this.sky = sky;
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        try {
            String response = sky.getResponse(input);
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(response, dukeImage)
            );
        } catch (SkyException e) {
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog("Oops! " + e.getMessage(), dukeImage)
            );
        }
        userInput.clear();
    }
}
