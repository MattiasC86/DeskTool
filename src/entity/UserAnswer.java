package entity;

import javax.persistence.*;

/**
 * Created by matti on 2017-05-22.
 */

@Entity
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userAnswerId;   // Sätts automatiskt i databasen, finns inte med i konstruktorn

    private int uACheckedAnswer;  //ska vara 1 om användaren klickat i, annars 0. Kan vara 0 på alla ranked
    private int uAOrder;  // 0,1,2,3...etc, används vid ranked questions, spelar annars ingen roll.
    private String uAText;

    @OneToOne
    private Question question;  //Måste ha en koppling till den fråga det hör till
    @OneToOne
    private Answer answer;  // Måste ha en koppling till sitt Answer-objekt. Viktigt!
    @ManyToOne
    private AnsweredTest answeredTest;

    public UserAnswer(int uACheckedAnswer, int uAOrder, String uAText, Question question, Answer answer, AnsweredTest answeredTest) {
        this.setUACheckedAnswer(uACheckedAnswer);
        this.setUAOrder(uAOrder);
        this.setUAText(uAText);
        this.setQuestion(question);
        this.setAnswer(answer);
        this.setAnsweredTest(answeredTest);
    }

    public void setAnsweredTest(AnsweredTest answeredTest) {
        this.answeredTest = answeredTest;
    }

    public AnsweredTest getAnsweredTest() {
        return answeredTest;
    }

    public UserAnswer(){}

    public int getUserAnswerId() {
        return userAnswerId;
    }

    public void setUserAnswerId(int userAnswerId) {
        this.userAnswerId = userAnswerId;
    }

    public int getUACheckedAnswer() {
        return uACheckedAnswer;
    }

    public void setUACheckedAnswer(int uACheckedAnswer) {
        this.uACheckedAnswer = uACheckedAnswer;
    }

    public int getUAOrder() {
        return uAOrder;
    }

    public void setUAOrder(int uAOrder) {
        this.uAOrder = uAOrder;
    }


    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public String getUAText() {
        return uAText;
    }

    public void setUAText(String UAText) {
        this.uAText = UAText;
    }
}
