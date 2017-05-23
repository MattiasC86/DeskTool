package view.homepage;

/**
 * Created by Rasmus on 2017-05-23.
 */
public class Table {


    private String title;
    private int timeLimit;
    private int questions;
    private String user;

    public Table(String title, int timeLimit, int questions, String user) {
        this.title = title;
        this.timeLimit = timeLimit;
        this.questions = questions;
        this.user = user;

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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
