package whatsappspammer;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Spammer {

    // Send a single messages, all messages or a random message.
    public enum Mode {
        SINGLE, ALL, RANDOM
    }

    private final List<Message> messages;
    private final Robot robot;
    private final Message baseMessage;
    private final Alert alert;

    public Spammer() throws AWTException, Exception {
        this.messages = new ArrayList<>();
        this.robot = new Robot();
        this.baseMessage = new Message("");
        this.alert = new Alert(Alert.AlertType.NONE);
        // Set icon.
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Spammer.class.getResourceAsStream("/images/icon.png")));
    }

    /*
     * Prompts the user with a message explaining how he should open the Whatsapp
     * Web page so that the program can send the messages correctly.
     */
    public Optional<ButtonType> showInstructions() {
        // Display instructions.
        this.alert.setAlertType(Alert.AlertType.CONFIRMATION);
        this.alert.setTitle("Instructions");
        this.alert.setHeaderText("Follow this instructions accurately.");
        this.alert.setContentText(
                "!! Leave this box open while you do this!!\n\n"
                + "Open up https://web.whataspp.com\n"
                + "Make it full screen\n"
                + "Open the contact or group you want to send the messages to\n"
                + "Click on the send message box\n\n"
                + "When everything is ready click OK to send the messages or Cancel to abort."
        );
        // Keep instruction dialog on top.
        ((Stage) this.alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        // Show instructions and wait for confirmation.
        return this.alert.showAndWait();
    }

    /*
     * Click on the window opened by the user to give it focus.
     */
    public void foucsPage() {
        // Move the mouse on the Whatsapp window (which the user was asked to maximize).
        this.robot.mouseMove((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, 0);
        // Click to get the focus to the Whatsapp window.
        this.robot.mousePress(InputEvent.BUTTON1_MASK);
        this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    /*
     * Print the loaded messages (SINGLE, ALL or RANDOM).
     */
    public void printMessages(Mode mode, int repetitions) throws Exception {
        // Repeat.
        for (int i = 0; i < repetitions; i++) {
            switch (mode) {
                case SINGLE:
                    this.printMessage(0);
                    break;
                case ALL:
                    for (int j = 0; j < this.messages.size(); j++) {
                        this.printMessage(j);
                    }
                    break;
                case RANDOM:
                    Random r = new Random();
                    this.printMessage(r.nextInt(this.messages.size()));
                    break;
            }
        }
        this.alert.setAlertType(Alert.AlertType.INFORMATION);
        this.alert.setTitle("Spamming complete");
        this.alert.setHeaderText(null);
        this.alert.setContentText("All messages have been sent.");
        this.alert.showAndWait();
    }

    /*
     * Send the message with specified index to the Whatsapp Web page.
     */
    public void printMessage(int index) throws Exception {
        if (index >= this.messages.size()) {
            throw new Exception("Wrong message index!");
        }
        Message m = this.messages.get(index);

        // Copy-paste the message text.
        StringSelection stringSelection = new StringSelection(m.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        // Send message with ENTER key.
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(m.getWaitTime());
    }

    public void loadSingleMessage(Message m) {
        // Delete old messages.
        this.messages.clear();

        this.messages.add(m);
    }

    /*
     * Parse the messages from a .wsl (Whatsapp Spammer List) file.
     * The file contains one message for each line and additional commands, like
     * "#wait random MIN_MS MAX_MS" or "#wait TIME_MS".
     */
    public void parseFile(String fileURL) throws IOException, NumberFormatException, Exception {
        File sourceFile = new File(fileURL);
        List<String> lines = Files.readAllLines(sourceFile.toPath());

        // Delete old messages.
        this.messages.clear();

        for (String line : lines) {
            if (line.startsWith("#wait ")) {
                // Lines starting with "#wait " are directives which change the
                // time between one message and the next.
                this.baseMessage.parseWaitDirective(line);
            } else {
                // Normal text is just the text of anew message.
                this.messages.add(new Message(
                        line,
                        baseMessage.getTimeInterval()[0],
                        baseMessage.getTimeInterval()[1]
                ));
            }
        }
    }
}
