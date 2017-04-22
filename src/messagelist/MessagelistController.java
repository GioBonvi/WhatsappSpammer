package messagelist;

import java.awt.AWTException;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import whatsappspammer.Spammer;

public class MessagelistController implements Initializable {

    @FXML
    private TextField listFilePathTxt;
    @FXML
    private TextField repetitionsTxt;
    @FXML
    private ToggleGroup selectionMode;

    private final Alert errorAlert;

    public MessagelistController() {
        // Error alert setup.
        errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Some fields contain an invalid parameter.");
        // Set icon.
        ((Stage) errorAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(MessagelistController.class.getResourceAsStream("/resources/icon.png")));
    }

    // Load button pressed: prompt for file selection.
    @FXML
    protected void handleLoad(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open message list file");
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Any", "*.*"),
                new FileChooser.ExtensionFilter("WSL", "*.wsl"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"),
                new FileChooser.ExtensionFilter("LST", "*.lst")
        );
        // WSL is default.
        fc.setSelectedExtensionFilter(fc.getExtensionFilters().get(1));
        File listFile = fc.showOpenDialog(new Stage());
        if (listFile != null) {
            listFilePathTxt.setText(listFile.getAbsolutePath());
        }
    }

    // Close button pressed: close window.
    @FXML
    protected void handleClose(ActionEvent e) {
        ((Node) (e.getSource())).getScene().getWindow().hide();
    }

    // Run button pressed: execute the spamming.
    @FXML
    protected void handleRun(ActionEvent e) {
        // Validate the inputs.

        // File path.
        if (listFilePathTxt.getText().equals("")
                || !Files.exists((new File(listFilePathTxt.getText())).toPath())) {
            errorAlert.setContentText("Invalid list file!");
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

        // Mode.
        Spammer.Mode mode;
        if (((RadioButton) selectionMode.getSelectedToggle()).getText().equals("Full list")) {
            mode = Spammer.Mode.ALL;
        } else {
            mode = Spammer.Mode.RANDOM;
        }

        // Execution.
        try {
            Spammer spammer = new Spammer();

            // Load messages from the specified source.
            spammer.parseFile(listFilePathTxt.getText());

            // Show the instructions to the user.
            if (spammer.showInstructions().get() == ButtonType.OK) {
                // If the user confirms then proceed with the spamming.
                spammer.foucsPage();
                spammer.printMessages(mode, repetitions);
            }
        } catch (AWTException ex) {
            errorAlert.setContentText("Error in managing the mouse with the robot!");
            errorAlert.showAndWait();
        } catch (Exception ex) {
            errorAlert.setContentText(ex.getMessage());
            errorAlert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
