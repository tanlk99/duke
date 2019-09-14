package duke.application;

import java.io.IOException;
import java.util.Collections;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * A dialog box to display chat messages. Consists of an image element
 * and the chat message.
 */
class DialogBox extends HBox {
    private static final String DUKE_DIALOG_BOX_ID = "duke-dialog-box";
    private static final String USER_DIALOG_BOX_ID = "user-dialog-box";

    private static final double TRANSITION_DURATION = 0.5;
    private static final double TRANSITION_OPACITY_FROM = 0.5;
    private static final double TRANSITION_OPACITY_TO = 1.0;

    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;
    private FadeTransition fadeTransition;

    /**
     * Creates a new DialogBox.
     * The image is aligned to the right by default.
     *
     * @param text  Text to place in the dialog box
     * @param img   Image to place in the dialog box
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        double radius = displayPicture.getFitHeight() / 2;
        displayPicture.setClip(new Circle(radius, radius, radius));

        prepareAnimation();
    }

    /**
     * Prepares the fade-in transition of the dialog box.
     */
    private void prepareAnimation() {
        fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.seconds(TRANSITION_DURATION));
        fadeTransition.setFromValue(TRANSITION_OPACITY_FROM);
        fadeTransition.setToValue(TRANSITION_OPACITY_TO);

        fadeTransition.setNode(this);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialog box for messages from the user.
     * The image is aligned to the right of the window.
     *
     * @param text  Text to place in the dialog box
     * @param img   Image to place in the dialog box
     * @return      A DialogBox object for the message
     */
    static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.setId(USER_DIALOG_BOX_ID);
        db.fadeTransition.play();
        return db;
    }

    /**
     * Creates a dialog box for messages from Duke.
     * The image is aligned to the left of the window.
     *
     * @param text  Text to place in the dialog box
     * @param img   Image to place in the dialog box
     * @return      A DialogBox object for the message
     */
    static DialogBox getDukeDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.setId(DUKE_DIALOG_BOX_ID);
        db.flip();

        db.fadeTransition.play();
        return db;
    }
}