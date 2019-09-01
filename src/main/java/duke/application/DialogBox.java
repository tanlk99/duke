package duke.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * A dialog box to display chat messages. Consists of an image element
 * and the chat message.
 */
class DialogBox extends HBox {
    private Label text;
    private ImageView displayPicture;

    /**
     * Creates a new DialogBox.
     * The image is aligned to the right by default.
     *
     * @param l     Label to place in the dialog box
     * @param iv    ImageView to place in the dialog box
     */
    private DialogBox(Label l, ImageView iv) {
        text = l;
        displayPicture = iv;

        text.setWrapText(true);
        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);

        this.setAlignment(Pos.TOP_RIGHT);
        this.getChildren().addAll(text, displayPicture);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    /**
     * Creates a dialog box for messages from the user.
     * The image is aligned to the right of the window.
     *
     * @param l     Label to place in the dialog box
     * @param iv    ImageView to place in the dialog box
     * @return      A DialogBox object for the message
     */
    static DialogBox getUserDialog(Label l, ImageView iv) {
        return new DialogBox(l, iv);
    }

    /**
     * Creates a dialog box for messages from Duke.
     * The image is aligned to the left of the window.
     *
     * @param l     Label to place in the dialog box
     * @param iv    ImageView to place in the dialog box
     * @return      A DialogBox object for the message
     */
    static DialogBox getDukeDialog(Label l, ImageView iv) {
        var db = new DialogBox(l, iv);
        db.flip();
        return db;
    }
}