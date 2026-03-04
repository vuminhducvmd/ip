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
    private final Image skyImage =
            new Image(getClass().getResourceAsStream("/images/DaSky.png"));

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

        dialogContainer.getChildren().add(
            DialogBox.getSkyDialog(
                "Hello! I'm Sky\n" +
                "How can I help you today?",
                skyImage
            )
        );
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        try {
            String response = sky.getResponse(input);

            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getSkyDialog(response, skyImage)
            );

        } catch (SkyException e) {
            DialogBox errorBox =
                    DialogBox.getSkyDialog("Oops! " + e.getMessage(), skyImage);

            errorBox.setStyle("-fx-background-color: #FFDDDD;");

            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    errorBox
            );

        } catch (Exception e) {
            DialogBox errorBox =
                    DialogBox.getSkyDialog(
                            "Oops! Something went wrong. Please check the command format.",
                            skyImage
                    );

            errorBox.setStyle("-fx-background-color: #FFDDDD;");

            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    errorBox
            );
        }
        userInput.clear();
    }
}
