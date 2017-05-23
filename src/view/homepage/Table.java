package view.homepage;

/**
 * Created by Rasmus on 2017-05-23.
 */
public class Table {


    private String title;
    private int timeLimit;
    private int questions;

    public Table(String title, int timeLimit, int questions) {
        this.title = title;
        this.timeLimit = timeLimit;
        this.questions = questions;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getQuestions() {
        return questions;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }
}
