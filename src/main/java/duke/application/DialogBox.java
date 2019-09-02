package duke.application;

import java.io.IOException;
import java.util.Collections;
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

/**
 * A dialog box to display chat messages. Consists of an image element
 * and the chat message.
 */
class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

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

        dialog.setWrapText(true);
        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);
        displayPicture.setClip(new Circle(50, 50, 50));

        this.setAlignment(Pos.TOP_RIGHT);
        this.setSpacing(10.0);
        this.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
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

        return db;
    }
}