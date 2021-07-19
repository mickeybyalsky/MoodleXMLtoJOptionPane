/**
 * Program: Uses and implements logic for multiple choice questions
 * Name: MultipleChoiceQuestion.java
 * @author  Mickey Byalsky
 */
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;


public class MultipleChoiceQuestion extends Question {
    private ArrayList<Answer> answers;

    /**
     * Program: collects attributes for multiple choice questions
     * @param id
     * @param type
     * @param name
     */
    public MultipleChoiceQuestion(String id, String type, String name, String questionText, double defaultGrade, ArrayList<Answer> answers) {
        super(id, type, name, questionText, defaultGrade);
        this.answers = answers;
        Collections.shuffle(this.answers); // randomize order of answers
    }

    /**
     * Display the question and return the response. Also instructs jOptionPane to draw it
     * @return
     */
    public int displayQuestion() {

        String[] options = {"D.", "C.", "B.", "A."};
        String[] formatted = new String[answers.size()];
        String text, output = "";

        for (int i = 0; i < answers.size(); i++) {
            text = answers.get(i).getText();

            if(text.startsWith("<p>"))  // remove the paragraph tag since it will be added at the end.
                text = text.substring(3,text.length()-"</p>".length());

            formatted[i] = "<p>" +(char) (65 + i) + ". " + text + "</p>";
            output += formatted[i];
        }

        int response = JOptionPane.showOptionDialog(null, "<html>"+getQuestionText() + output+"</html>", "Question # " + getID() + "            Marks: " + getDefaultGrade(),
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[3]);
        response = Math.abs(3-response);
        return response;
    }

    /**
     * Program: Finds how many points were gained from the answer chosen by user
     * @param response
     * @return double points
     */

    public double returnPoints(int response) {

        double points = ((answers.get(response).getAnswer() / 100.0) * getDefaultGrade());
        JOptionPane.showMessageDialog(null, "Points Earned From Question:\n"+points);
        return points;
    }

    /**
     * Program shows available answers in the JOptionPane
     * @return
     */
    public String toString() {

        return super.toString() + "\nANSWERS = " + answers;
    }
}
