/*
* Answer entity directly connected to a Question
*/

package entity;

import javax.persistence.*;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int answerId;

    private String aText;
    private int aPoints;
    private int aOrder;

    @ManyToOne
    private Question question;

    public Answer(String aText, int aPoints, int aOrder, Question question) {
        this.setaText(aText);
        this.setaPoints(aPoints);
        this.setaOrder(aOrder);
        this.setQuestion(question);
    }

    public Answer(){}

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getaOrder() {
        return aOrder;
    }

    public void setaOrder(int aOrder) {
        this.aOrder = aOrder;
    }

    public int getaPoints() {
        return aPoints;
    }

    public void setaPoints(int aPoints) {
        this.aPoints = aPoints;
    }

    public String getaText() {
        return aText;
    }

    public void setaText(String aText) {
        this.aText = aText;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}