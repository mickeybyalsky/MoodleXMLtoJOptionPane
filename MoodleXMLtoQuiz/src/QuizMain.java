/**
 * Program: Runs main method, initiates JOptionPane, calls classes/methods
 * Name: QuizMain.java
 * @author  Mickey Byalsky
 */
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class QuizMain {
    private static int numberOfQuestions;//number of questions the user wants to do
    private static double totalPoints;//number of points scored by the user
    private static int response;//response variable from MultipleChoiceQuestion class
    private static Category category;
    private static ArrayList<MultipleChoiceQuestion> multiChoiceQuestionList;//ArrayList of multiple choice questions
    private static MultipleChoiceQuestion mc; //multiple choice question object

    /**
     * Program instructs reader to read file and choose fitting category
     */
    public static void main(String[] args) {
        ReadFromFileMoodleXML reader = new ReadFromFileMoodleXML();
        reader.readFile();
        category = reader.getCategory();
        multiChoiceQuestionList = reader.getMultipleChoiceQuestions();

        //initializing random number
        Random random = new Random();
        int maxNumber = multiChoiceQuestionList.size() - 1;//max number would be length of array minus 1
        int randomNumber = random.nextInt(maxNumber);//random number is generated

        //next methods are called
        displayFirstScreen();
        displayQuestion(randomNumber);
        displayScore();
    }

    /**
     * Program displays an intro screen that tells them what the category is.
     * This is followed by another screen that asks the user how many questions they would like.
     * This is done so that the user can decide if they want a long quiz for thorough practice or a short quiz if they are in a time crunch.
     * @return the number of questions that the user wants to do
     */
    private static int displayFirstScreen() {
        //program landing screen
        JOptionPane.showMessageDialog(null, "Welcome to the Formative quiz for " + category.toString() + ". Press OK to begin.");

        //program asks user how many questions they want to do
        numberOfQuestions = Integer.parseInt(JOptionPane.showInputDialog(null, "How many Questions would you like?"));

        //returns number of questions that user wants
        return numberOfQuestions;
    }

    /**
     * Program displays a randomly selected question and it's answers.
     * pre: randomNumber - the number generated picks a question from the ArrayList of questions
     * post: displays the question and delete the question's index from the ArrayList so that it does not appear twice.
     */
    public static void displayQuestion (int randomNumber){
        for (int i = 0; i < numberOfQuestions; i++) {
            mc = multiChoiceQuestionList.get(randomNumber);
            response = mc.displayQuestion();
            totalPoints += mc.returnPoints(response);
            multiChoiceQuestionList.remove(randomNumber);
        }
    }

    /**
     * Program displays a JOptionPane depending on the user's score.
     * pre: totalPoints - points scored by the user, numberOfQuestions - number of questions that the user opted to complete
     * post: A JOptionPane message dialog depending on the user's score
     */
    public static void displayScore (){
        double percentage = (double) (totalPoints/numberOfQuestions)*100;//calculates percentage scored
        int percentageAsInt = (int) percentage;
        System.out.println("total points:" +totalPoints);
        System.out.println("num of q's: " + numberOfQuestions);
        System.out.println("percentage: "+percentage);

        if (percentage>=70)//if user scores 70 or above
            JOptionPane.showMessageDialog(null, "All Done!\nYou scored " +percentageAsInt+"% on this quiz.\nNice work, you killed it!");//JOptionPane

        else//if lower than 70
            JOptionPane.showMessageDialog(null,"All Done!\nYou scored " +percentageAsInt+"% on this quiz.\nTry this quiz again to get a higher score!");//JOptionPane
    }

}
