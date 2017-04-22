package mainmenu;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainmenuController implements Initializable {

    // Close button pressed: terminate program.
    @FXML
    protected void handleClose(ActionEvent e) {
        ((Node) (e.getSource())).getScene().getWindow().hide();
    }

    // Single message button ressed: open new window.
    @FXML
    protected void handleSingleMessage(ActionEvent e) throws IOException {
        Parent main = FXMLLoader.load(getClass().getResource("/singlemessage/singlemessage.fxml"));
        Stage stage = new Stage();
        stage.getIcons().add(new Image(MainmenuController.class.getResourceAsStream("/resources/icon.png")));
        stage.setScene(new Scene(main));
        stage.setTitle("Send a single message");
        stage.show();
    }

    // Message list button ressed: open new window.
    @FXML
    protected void handleMessageList(ActionEvent e) throws IOException {
        Parent main = FXMLLoader.load(getClass().getResource("/messagelist/messagelist.fxml"));
        Stage stage = new Stage();
        stage.getIcons().add(new Image(MainmenuController.class.getResourceAsStream("/resources/icon.png")));
        stage.setScene(new Scene(main));
        stage.setTitle("Send a list of messages");
        stage.show();
    }
    
    // Help button ressed: open guide.
    @FXML
    protected void handleHelp(ActionEvent e) {
        try {
            Desktop.getDesktop().browse(new URI("https://giobonvi.github.io/WhatsappSpammer/"));
        } catch (IOException|URISyntaxException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to open the help page.");
            alert.setContentText(
                    "The program could not open the help page.\n"
                    + "You can try opening it manually in your browser:\n\n"
                    + "https://giobonvi.github.io/WhatsappSpammer/"
            );
            alert.showAndWait();
            // Set icon.
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(MainmenuController.class.getResourceAsStream("/resources/icon.png")));
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
