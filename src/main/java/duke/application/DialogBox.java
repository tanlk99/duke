package duke.application;

import java.io.IOException;
import java.util.Collections;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * A dialog box to display chat messages. Consists of an image element
 * and the chat message.
 */
class DialogBox extends HBox {
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
        displayPicture.setClip(new Circle(50, 50, 50));

        prepareAnimation();
    }

    /**
     * Prepares the fade-in transition of the dialog box.
     */
    private void prepareAnimation() {
        fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.seconds(0.5));
        fadeTransition.setFromValue(0.4);
        fadeTransition.setToValue(1.0);

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
        db.setBackground(new Background(
                new BackgroundFill(Color.gray(0.9), CornerRadii.EMPTY, Insets.EMPTY)
        ));

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
        db.setBackground(new Background(
                new BackgroundFill(Color.gray(0.8), CornerRadii.EMPTY, Insets.EMPTY)
        ));
        db.flip();

        db.fadeTransition.play();
        return db;
    }
}