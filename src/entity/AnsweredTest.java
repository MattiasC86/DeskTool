package entity;

import javax.persistence.*;

@Entity
public class AnsweredTest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int answeredTestId;

    private int aTCorrected;
    private int aTShowResult;
    private int aTPoints;
    private int aTTimeSec;
    private String aTGrade;

    @OneToOne
    User user;

    @OneToOne
    Test test;



    public AnsweredTest(){}
}
