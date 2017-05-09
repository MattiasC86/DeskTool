package entity;

import javax.persistence.*;


@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int questionId;

    private String qText;
    private int qPoints;
    private String qType;
    private int qOrder;
    private String qGrade;

    @ManyToOne
    private Test test;

    public Question(String qText, int qPoints, String qType, int qOrder, String qGrade, Test test) {
        this.qText = qText;
        this.qPoints = qPoints;
        this.qType = qType;
        this.qOrder = qOrder;
        this.qGrade = qGrade;
        this.test = test;
    }

    public Question(){}

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getqText() {
        return qText;
    }

    public void setqText(String qText) {
        this.qText = qText;
    }

    public int getqPoints() {
        return qPoints;
    }

    public void setqPoints(int qPoints) {
        this.qPoints = qPoints;
    }

    public String getqType() {
        return qType;
    }

    public void setqType(String qType) {
        this.qType = qType;
    }

    public int getqOrder() {
        return qOrder;
    }

    public void setqOrder(int qOrder) {
        this.qOrder = qOrder;
    }

    public String getqGrade() {
        return qGrade;
    }

    public void setqGrade(String qGrade) {
        this.qGrade = qGrade;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}