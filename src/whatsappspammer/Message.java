package whatsappspammer;

import java.util.Random;

public class Message {

    public static final int DEFAULT_WAIT_TIME = 100;

    private final Random rand;

    // Text of the message.
    private String text;
    // Time to wait after sending the message before sending the next one.
    private int waitTimeMin;
    private int waitTimeMax;

    public Message(String text) throws Exception {
        this(text, Message.DEFAULT_WAIT_TIME);
    }

    public Message(String text, int waitTime) throws Exception {
        this(text, waitTime, waitTime);
    }

    public Message(String text, int waitTimeMin, int waitTimeMax) throws Exception {
        rand = new Random();
        // Insert newlines where due.
        this.text = text.replace("#NL#", "\n");
        this.setWaitTimeInterval(waitTimeMin, waitTimeMax);
    }

    /*
     * Parse a string of structure "#wait N_MS" or "#wait N_MS-N_MS" to extract
     * the range in which to choose waitTime.
     */
    public void parseWaitDirective(String directive) throws NumberFormatException, Exception {
        // Remove "#wait " from the beginning of the string.
        if (directive.startsWith("#wait ")) {
            directive = directive.replace("#wait ", "");
        }
        if (directive.matches("\\d+-\\d+")) {
            // Random in interval.
            String temp[] = directive.split("-");
            this.setWaitTimeInterval(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
        } else {
            // Fixed.
            this.setWaitTimeInterval(Integer.parseInt(directive), Integer.parseInt(directive));
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setWaitTimeInterval(int waitTimeMin, int waitTimeMax) throws Exception {
        // waitTimeMin must be less or equal to waitTimeMax and they both must
        // be greater than or equal to 0.
        if (waitTimeMin > waitTimeMax || waitTimeMax < 0) {
            throw new Exception("Invalid pause time.");
        }
        this.waitTimeMin = waitTimeMin;
        this.waitTimeMax = waitTimeMax;
    }

    public int[] getTimeInterval() {
        int temp[] = {this.waitTimeMin, this.waitTimeMax};
        return temp;
    }

    /* 
     * Returns a random time between this.waitTimeMin and this.waitTimeMax (both included).
     * If they are equal this.waitTimeMin is automatically returned.
     */
    public int getWaitTime() throws Exception {
        if (this.waitTimeMin == this.waitTimeMax) {
            return this.waitTimeMin;
        } else {
            return this.rand.nextInt(this.waitTimeMax + 1 - this.waitTimeMin) + this.waitTimeMin;
        }
    }
}
