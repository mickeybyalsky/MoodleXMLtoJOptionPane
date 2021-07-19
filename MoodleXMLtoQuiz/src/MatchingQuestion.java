/**
 * Program: Uses and implements logic for matching questions
 * Name: MatchingQuestion.java
 * @author  Mickey Byalsky
 */
import java.util.ArrayList;
public class MatchingQuestion extends Question {
    private ArrayList<String> subQuestions;
    private ArrayList<String> answers;

    /**
     * Program:Chooses answers for selected matching question
     * @param id
     * @param type
     * @param name
     * @param questionText
     * @param defaultGrade
     * @param subQuestions
     * @param answers
     */
    public MatchingQuestion(String id, String type, String name, String questionText, double defaultGrade, ArrayList<String> subQuestions, ArrayList<String> answers) {
        super(id, type, name, questionText, defaultGrade);
        this.subQuestions = subQuestions;
        this.answers = answers;
    }

    /**
     *Program: returns string with the possible subquestions and possible answers
     * @return string with possible subQuestions and possible answers
     */
    public String toString() {

        return super.toString() + "\nSUBQUESTIONS = " + subQuestions + "\nANSWERS = " + answers;
    }
}
