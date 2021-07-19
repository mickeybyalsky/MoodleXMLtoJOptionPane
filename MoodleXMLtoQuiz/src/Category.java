/**
 * Program: Reads question attributes and lets user print questions of a specific category
 * Name: Category.java
 * @author  Mickey Byalsky
 */
import java.util.ArrayList;

public class Category {

    private String categoryName;
    private ArrayList<Question> questionList;

    public Category() {

        this("default");
    }

    /**
     * Program: Finds category name and adds it to question arraylist
     * @param name
     */
    public Category(String name) {
        this.categoryName = name;
        questionList = new ArrayList<Question>();
    }

    /**
     * Program sets category name to the string that was passed on
     * @param name
     */
    public void setName(String name) {

        this.categoryName=name;
    }

    /**
     * Program adds passed question into questionList
     * @param question
     */
    public void addQuestion(Question question) {

        questionList.add(question);
    }

    /**
     * Program prints all the questions in the list
     */
    public void printAll() {
        System.out.println("Printing questions in Category: " + categoryName);
        for(Question q: questionList)
            System.out.println(q);
    }

    /**
     * Program returns category name free of extra content
     * @return categoryNameEdited
     */
    public String toString() {
        int index = categoryName.indexOf('/');
        String categoryNameEdited = categoryName.substring(index+1);
        return categoryNameEdited;
    }
}
