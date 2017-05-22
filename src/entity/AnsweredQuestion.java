package entity;

import javax.persistence.*;

@Entity
public class AnsweredQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int AnsweredQuestionId;

    private int AQPoints;
    @OneToOne
    private Question question;
    @ManyToOne
    private AnsweredTest answeredTest;

    public AnsweredQuestion(int AQPoints, Question question, AnsweredTest answeredTest) {
        this.setAQPoints(AQPoints);
        this.setQuestion(question);
        this.setAnsweredTest(answeredTest);
    }

    public AnsweredQuestion(){}

    public int getAnsweredQuestionId() {
        return AnsweredQuestionId;
    }

    public void setAnsweredQuestionId(int answeredQuestionId) {
        AnsweredQuestionId = answeredQuestionId;
    }

    public int getAQPoints() {
        return AQPoints;
    }

    public void setAQPoints(int AQPoints) {
        this.AQPoints = AQPoints;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public AnsweredTest getAnsweredTest() {
        return answeredTest;
    }

    public void setAnsweredTest(AnsweredTest answeredTest) {
        this.answeredTest = answeredTest;
    }
}
