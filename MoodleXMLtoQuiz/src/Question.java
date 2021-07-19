/**
 * Program: Returns question attributes such as id, type or name
 * Name: Question.java
 * @author  Mickey Byalsky
 */
public class Question {
    private String id, type, name, questionText;
    private double defaultGrade;

    /**
     *
     * @param id
     * @param type
     * @param name
     * @param questionText
     * @param defaultGrade
     */
    public Question(String id, String type, String name, String questionText, double defaultGrade) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.questionText = questionText;
        this.defaultGrade = defaultGrade;
    }

    public String getID() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getQuestionText() {
        return questionText;
    }


    public double getDefaultGrade() {
        return defaultGrade;
    }

    /**
     *
     * @return question attributes such as ID tag, type of question, question name, question text and default grade
     */
    public String toString() {
        return "ID = " + id + "\nTYPE = " + type + "\nNAME = " + name + "\nQUESTION TEXT = " + questionText + "\nDEFAULT GRADE = " + defaultGrade;
    }
}
