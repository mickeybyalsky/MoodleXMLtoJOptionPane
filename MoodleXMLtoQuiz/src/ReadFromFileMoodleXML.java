/**
 * Program: Reads all questions and attributes from XML file
 * Name: ReadFromFileMoodleXML.java
 * @author  Mickey Byalsky
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFromFileMoodleXML {
    private Category category;
    private ArrayList<MultipleChoiceQuestion> multiChoiceQuestionList = new ArrayList<MultipleChoiceQuestion>();

    public Category getCategory() {
        return category;
    }

    public ArrayList<MultipleChoiceQuestion> getMultipleChoiceQuestions(){
        return multiChoiceQuestionList;
    }

    /**
     * Program reads MoodleQuiz xml file in & Scans type of question
     * pre: randomNumber - the number generated picks a question from the ArrayList of questions
     * post: displays the question and delete the question's index from the ArrayList so that it does not appear twice.
     */
    public void readFile() {
        final String QUESTION_START = "<!-- question:";
        final String TYPE_START = "<question type=\"";
        final String FORMAT_START = "<questiontext format=\"";

        File textFile = new File("MoodleQuiz.xml"); // from the default location
        Scanner input = null;
        try {
            input = new Scanner(textFile);
        } catch (FileNotFoundException e){}

        category = new Category();
        String line, tag, id, type, name, questionText, text, format, categoryName;
        double defaultGrade;
        ArrayList<String> subQuestionList = new ArrayList<String>();
        ArrayList<String> subAnswerList = new ArrayList<String>();

        // read lines from the text file
        while (input.hasNextLine()) {
            line = input.nextLine();

            if (line.contains(QUESTION_START)) {
                String[] tokens = line.substring(QUESTION_START.length()).split(" ");
                id = tokens[1]; // get ID
                // <question type="????"> comes next at index 16
                type = input.nextLine().trim();
                type = type.substring(TYPE_START.length(), type.length() - 2);
                // Determine if it is <question type="category">

                if (type.equals("category")) {
                    // Get the name of the category
                    tag = extractTag(input.nextLine().trim());
                    text = extractInnerText(tag, input).trim();
                    text = removeHtmlTags(text);
                    categoryName = text;
                    category.setName(categoryName);
                    System.out.println(category);
                    continue;
                }

                // Get the name of the question
                tag = extractTag(input.nextLine().trim());
                text = extractInnerText(tag, input).trim();
                name = removeHtmlTags(text);

                // <questiontext format="????"> extract the format
                format = input.nextLine().trim();
                format = format.substring(FORMAT_START.length(), format.length() - 2);

                // get questiontext
                questionText = input.nextLine().trim();

                //questionText = removeHtmlTags(questionText);
                questionText = removeTextCDATA(questionText);
                //questionText = questionText.substring("<![CDATA[".length(), questionText.length() - 3);

                // loop until you encounter <defaultgrade>
                do {
                    line = input.nextLine().trim();
                } while (!line.startsWith("<defaultgrade>"));

                defaultGrade = Double.parseDouble(removeHtmlTags(line));
                System.out.println(defaultGrade);

                if (type.equals("matching")) {
                    // loop until subquestion opens
                    do {
                        line = input.nextLine().trim();
                    } while (!line.startsWith("<subquestion"));

                    do {
                        text = input.nextLine().trim();
                        subQuestionList.add(removeTextCDATA(text));
                        input.nextLine(); // remove <answer> tag
                        text = input.nextLine().trim();
                        subAnswerList.add(removeHtmlTags(text));
                        input.nextLine(); // remove </answer> tag
                        input.nextLine(); // remove </subquestion> tag
                    } while (!input.nextLine().trim().equals("</question>"));

                    MatchingQuestion matchingQuestion = new MatchingQuestion(id, type, name, questionText, defaultGrade, subQuestionList, subAnswerList);
                    category.addQuestion(matchingQuestion);
                    System.out.println(matchingQuestion);
                    continue;
                }

                else if(type.equals("multichoice")) {
                    ArrayList<Answer> answerList = new ArrayList<Answer>();

                    // loop until <answer tag opens
                    do {
                        line = input.nextLine().trim();
                    } while (!line.startsWith("<answer "));

                    do {
                        String feedback = "";
                        // Extract the fraction amount (fraction="0")
                        String[] attributeValuePair = parseTagAttributes(line);
                        String fraction = attributeValuePair[1];
                        // get the answer text
                        line = input.nextLine();
                        text = removeTextCDATA(line); // get rid of <text> and </text>
                        input.nextLine().trim(); // <feedback format="html">
                        line = input.nextLine().trim();
                        feedback = removeTextCDATA(line);
                        answerList.add(new Answer(Integer.parseInt(fraction),text, feedback));

                        // loop until </answer closing tag
                        do {
                            line = input.nextLine().trim();
                        } while (!line.contains("</answer>"));

                        line = input.nextLine().trim();

                    } while(!line.contains("</question>"));

                    MultipleChoiceQuestion multichoiceQuestion = new MultipleChoiceQuestion(id, type, name, questionText, defaultGrade, answerList);
                    multiChoiceQuestionList.add(multichoiceQuestion);
                    category.addQuestion(multichoiceQuestion);
                    System.out.println(multichoiceQuestion);
                    continue;
                }

                // loop while not end of question
                line = input.nextLine();

                while (!line.trim().equals("</question>")) {
                    if (openTag(line.trim()))
                        System.out.println(extractTag(line.trim()));

                    line = input.nextLine();
                }

                Question q = new Question(id, type, name, questionText, defaultGrade);
                System.out.println("====================");
            }
        }
    }

    /**
     *Program: Checks if input string has opening tags
     * @param text
     * @return boolean
     * */
    public static boolean openTag(String text) {
        return text.startsWith("<") && text.charAt(1) != '/' && text.endsWith(">");
    }
    /**
     *Program: Checks if input string has closing tags
     * @param text
     * @return boolean (t/f)
     */
    public static boolean closeTag(String text) {
        return text.startsWith("</") && text.endsWith(">");
    }
    /**
     *Program: Extracts the tags associated with the string and returns it without them
     * @param text
     * @return String
     */
    public static String extractTag(String text) {
        if (openTag(text))
            return text.substring(1, text.length() - 1);

        else if (closeTag(text))
            return text.substring(2, text.length() - 1);


        return text;
    }

    /**
     * Method looks for proper HTML tags and outputs text inside
     * @param str
     * @return string without html tags
     */
    public static String removeHtmlTags(String str) {
        int pos0 = str.indexOf('<');
        int pos1 = str.indexOf('>');
        int pos2 = str.lastIndexOf("</");
        int pos3 = str.lastIndexOf('>');

        if (pos0 == 0 && pos1 > 0 && pos2 > pos1 && pos3 == str.length() - 1) {
            String tag1 = str.substring(pos0 + 1, pos1);
            String tag2 = str.substring(pos2 + 2, pos3);

            if (tag1.equals(tag2) && tag1.indexOf('<') < 0 && tag2.indexOf('>') < 0)
                str = str.substring(pos1 + 1, pos2);
        }

        return str;
    }

    /**
     *Program takes two input and returns teh text inside the tags specified
     * @param tag   - outer tag, for example <name>
     * @param input - reference to the Scanner reading from the file
     * @return a string containing just the inner text between <text> and </text>
     */
    public static String extractInnerText(String tag, Scanner input) {
        StringBuffer output = new StringBuffer();
        String line = input.nextLine();

        while (!line.trim().equals("</" + tag + ">")) {
            output.append(line);
            line = input.nextLine();
        }

        return output.toString();
    }

    /**
     *Program takes old text and makes sure there are no Cdata tags
     * @param str line of text starting with <text><![CDATA[ and ending with
     *            ]]></text>
     * @return the HTML formatted question text
     */
    public static String removeTextCDATA(String text) {
        text = removeHtmlTags(text.trim()); // trim then remove xml tags

        if(text.contains("<![CDATA["))
            return text.substring("<![CDATA[".length(), text.length() - 3);

        else
            return text;
    }

    /**
     * Takes any tag with attributes and breaks out the attributes into a 2D array.
     *
     * @param tag <answer fraction="100" format="moodle_auto_format">
     * @return {"fraction", "format"}, {"100", "moodle_auto_format"}
     */
    public static String[] parseTagAttributes(String tag) {
        String[] tokens = tag.split(" "); // {fraction="100", format="moodle_auto_format"}
        String[] attributeValuePair = tokens[1].split("=");
        attributeValuePair[1] = attributeValuePair[1].substring(1, attributeValuePair[1].length() - 1); // remove quotes
        // from the
        // value
        // System.out.println(attributeValuePair[0]);
        // System.out.println(attributeValuePair[1].substring(1,attributeValuePair[1].length()-1));
        // // remove quotes from the value
        return attributeValuePair;
    }
}