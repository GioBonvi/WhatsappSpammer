package whatsappspammer;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class WhatsappSpammer extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        /*
        // Show disclaimer.
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Disclaimer");
        alert.setHeaderText("Read accurately");
        alert.setContentText(
                "This application could cause unwanted behaviours (including,"
                + " but not limited to freezes, performance issues, "
                + "forced restarts and crashes) on the device running "
                + "the application and Whatsapp Web, on the mobile device "
                + "running Whatsapp and the device(s) receiving the messages.\n"
                + "The author of this software is not to be held responsible "
                + "for any damage or problems caused by the use of this software.\n\n"
                + "By clicking \"Ok\" you declare you have read, understood "
                + "and accepted this disclaimer."
        );
        // Set icon.
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(WhatsappSpammer.class.getResourceAsStream("/resources/icon.png")));
        ButtonType btnOk = new ButtonType("Ok", ButtonData.LEFT);
        ButtonType btnCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(btnOk, btnCancel);
        if (alert.showAndWait().get() != btnOk) {
            System.exit(0);
        }
*/
        // Open the main menu.
        Parent main = FXMLLoader.load(getClass().getResource("/mainmenu/mainmenu.fxml"));
        Scene scene = new Scene(main);
        stage.getIcons().add(new Image(WhatsappSpammer.class.getResourceAsStream("/resources/icon.png")));
        stage.setScene(scene);
        stage.setTitle("Main menu");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
