package duck.application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * A GUI for Duck using FXML.
 */
public class Main extends Application {
    private Duck duck = new Duck();

    /**
     * Starts the Duck application.
     *
     * @param stage Stage object to run Duck
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            fxmlLoader.setRoot(stage);
            fxmlLoader.load();

            fxmlLoader.<MainWindow>getController().setDuck(duck);
            fxmlLoader.<MainWindow>getController().displayGreetings();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}