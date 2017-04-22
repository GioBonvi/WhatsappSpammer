package mainmenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        stage.minWidthProperty().set(450);
        stage.minHeightProperty().set(410);
        stage.setScene(new Scene(main));
        stage.setTitle("Send a single message");
        stage.show();
    }

    // Message list button ressed: open new window.
    @FXML
    protected void handleMessageList(ActionEvent e) throws IOException {
        Parent main = FXMLLoader.load(getClass().getResource("/messagelist/messagelist.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(main));
        stage.setTitle("Send a list of messages");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
