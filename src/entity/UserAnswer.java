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
    private int checkedAnswer;  //ska vara 1 om användaren klickat i, annars 0. Kan vara 0 på alla ranked
    private int order;  // 0,1,2,3...etc, används vid ranked questions, spelar annars ingen roll.

    //OBS! Tog bort UAText. Eftersom varje UserAnswer ska ha en koppling till ett Answer-objekt så kan
    // texten alltid hämtas från Answer-objektet

    @OneToOne
    private Question question;  //Måste ha en koppling till den fråga det hör till
    @OneToOne
    private Answer answer;  // Måste ha en koppling till sitt Answer-objekt. Viktigt!

    public UserAnswer(int checkedAnswer, int order, Question question, Answer answer) {
        this.setCheckedAnswer(checkedAnswer);
        this.setOrder(order);
        this.setQuestion(question);
        this.setAnswer(answer);
    }

    public UserAnswer(){}

    public int getUserAnswerId() {
        return userAnswerId;
    }

    public void setUserAnswerId(int userAnswerId) {
        this.userAnswerId = userAnswerId;
    }

    public int getCheckedAnswer() {
        return checkedAnswer;
    }

    public void setCheckedAnswer(int checkedAnswer) {
        this.checkedAnswer = checkedAnswer;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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
}
