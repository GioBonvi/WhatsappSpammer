package singlemessage;

import java.awt.AWTException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import whatsappspammer.Spammer;
import whatsappspammer.Message;

public class SinglemessageController implements Initializable {

    @FXML
    private TextField messageTextTxt;
    @FXML
    private TextField messageWaitTimeTxt;
    @FXML
    private TextField repetitionsTxt;

    private final Alert errorAlert;

    public SinglemessageController() {
        // Error alert setup.
        errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Some fields contain an invalid parameter.");
    }

    // Close button pressed: close window.
    @FXML
    protected void handleClose(ActionEvent e) {
        ((Node) (e.getSource())).getScene().getWindow().hide();
    }

    // Run button pressed: execute the spamming.
    @FXML
    protected void handleRun(ActionEvent e) throws NumberFormatException {
        // Validate the inputs.

        // Text of the message.
        if (messageTextTxt.getText().length() == 0) {
            errorAlert.setContentText("You have to specify the text of the message!");
            errorAlert.showAndWait();
            return;
        }
        
        // Wait time.
        Message msg;
        try {
            msg = new Message(messageTextTxt.getText());
            msg.parseWaitDirective(messageWaitTimeTxt.getText());
        } catch (Exception ex) {
            errorAlert.setContentText("Invalid pause time!");
            errorAlert.showAndWait();
            return;
        }

        // Repetitions.
        int repetitions;
        try {
            if (repetitionsTxt.getText().equals("")) {
                throw new NumberFormatException();
            }
            repetitions = Integer.parseInt(repetitionsTxt.getText());
            if (repetitions <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            errorAlert.setContentText("Invalid number of repetitions!");
            errorAlert.showAndWait();
            return;
        }

        // Execution.
        try {
            Spammer spammer = new Spammer();
            spammer.loadSingleMessage(msg);

            // Show the instructions to the user.
            if (spammer.showInstructions().get() == ButtonType.OK) {
                // If the user confirms then proceed with the spamming.
                spammer.foucsPage();
                spammer.printMessages(Spammer.Mode.SINGLE, repetitions);
            }
        } catch (AWTException ex) {
            errorAlert.setContentText("Error managing the mouse with the robot!");
            errorAlert.showAndWait();
        } catch (Exception ex) {
            errorAlert.setContentText(ex.getMessage());
            errorAlert.showAndWait();
        }
        ((Node) (e.getSource())).getScene().getWindow().requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
