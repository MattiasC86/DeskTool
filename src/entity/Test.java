package entity;

import javax.persistence.*;


@Entity
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int testId;

    private String tTitle;
    private int tTimeMin;
    private int tMaxPoints;
    private int tSelfCorrecting;

    @ManyToOne
    private User user;

    public Test(String tTitle, int tTimeMin, int tMaxPoints, int tSelfCorrecting, User user) {
        this.tTitle = tTitle;
        this.tTimeMin = tTimeMin;
        this.tMaxPoints = tMaxPoints;
        this.tSelfCorrecting = tSelfCorrecting;
        this.setUser(user);
    }

    public Test(){}

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String gettTitle() {
        return tTitle;
    }

    public void settTitle(String tTitle) {
        this.tTitle = tTitle;
    }

    public int gettTimeMin() {
        return tTimeMin;
    }

    public void settTimeMin(int tTimeMin) {
        this.tTimeMin = tTimeMin;
    }

    public int gettMaxPoints() {
        return tMaxPoints;
    }

    public void settMaxPoints(int tMaxPoints) {
        this.tMaxPoints = tMaxPoints;
    }

    public int gettSelfCorrecting() {
        return tSelfCorrecting;
    }

    public void settSelfCorrecting(int tSelfCorrecting) {
        this.tSelfCorrecting = tSelfCorrecting;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}