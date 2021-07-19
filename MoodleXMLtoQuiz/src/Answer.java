/**
 * Program: Checks how correct the answer given was
 * Name: Answer.java
 * @author  Mickey Byalsky
 */
public class Answer {

    private int fraction;
    private String text;
    private String feedback;

    /**
     * Program: Answer sets attributes to particular questions
     * @param fraction
     * @param text
     * @param feedback
     */
    public Answer(int fraction, String text, String feedback) {
        this.fraction = fraction;
        this.text = text;
        this.feedback = feedback;
    }

    /**
     *
     * @return text
     */
    public String getText() {

        return text;
    }

    /**
     *
     * @return
     */
    public int getAnswer(){

        return fraction;
    }

    /**
     * Program: Returns correct/incorrect program attributes into JOptionPane
     * @return question attributes to string
     */
    public String toString() {

        return "FRACTION = " + fraction + "\nTEXT = " + text + "\nFEEDBACK = " + feedback;
    }

}
