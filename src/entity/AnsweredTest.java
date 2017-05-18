package entity;

import javax.persistence.*;

@Entity
public class AnsweredTest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int answeredTestId;

    private boolean aTCorrected;
    private boolean aTShowResult;
    private int aTPoints;
    private int aTTimeSec;
    private String aTGrade;

    @OneToOne
    private
    User user;

    @OneToOne
    private
    Test test;

    public AnsweredTest(boolean aTCorrected, boolean aTShowResult, int aTPoints, int aTTimeSec, String aTGrade, User user, Test test) {
        this.setaTCorrected(aTCorrected);
        this.setaTShowResult(aTShowResult);
        this.setaTPoints(aTPoints);
        this.setaTTimeSec(aTTimeSec);
        this.setaTGrade(aTGrade);
        this.user = user;
        this.test = test;
    }

    public AnsweredTest(){}

    public int getAnsweredTestId() {
        return answeredTestId;
    }

    public void setAnsweredTestId(int answeredTestId) {
        this.answeredTestId = answeredTestId;
    }

    public boolean isaTCorrected() {
        return aTCorrected;
    }

    public void setaTCorrected(boolean aTCorrected) {
        this.aTCorrected = aTCorrected;
    }

    public boolean isaTShowResult() {
        return aTShowResult;
    }

    public void setaTShowResult(boolean aTShowResult) {
        this.aTShowResult = aTShowResult;
    }

    public int getaTPoints() {
        return aTPoints;
    }

    public void setaTPoints(int aTPoints) {
        this.aTPoints = aTPoints;
    }

    public int getaTTimeSec() {
        return aTTimeSec;
    }

    public void setaTTimeSec(int aTTimeSec) {
        this.aTTimeSec = aTTimeSec;
    }

    public String getaTGrade() {
        return aTGrade;
    }

    public void setaTGrade(String aTGrade) {
        this.aTGrade = aTGrade;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}
