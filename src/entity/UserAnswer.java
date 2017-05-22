package entity;

import javax.persistence.*;

/**
 * Created by matti on 2017-05-22.
 */

@Entity
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int UserAnswerId;

    private String UAText;
    @OneToOne
    private AnsweredQuestion answeredQuestion;
    @OneToOne
    private Answer answer;

    public UserAnswer(String UAText, AnsweredQuestion answeredQuestion, Answer answer) {
        this.setUAText(UAText);
        this.setAnsweredQuestion(answeredQuestion);
        this.setAnswer(answer);
    }

    public UserAnswer(){}

    public int getUserAnswerId() {
        return UserAnswerId;
    }

    public void setUserAnswerId(int userAnswerId) {
        UserAnswerId = userAnswerId;
    }

    public String getUAText() {
        return UAText;
    }

    public void setUAText(String UAText) {
        this.UAText = UAText;
    }

    public AnsweredQuestion getAnsweredQuestion() {
        return answeredQuestion;
    }

    public void setAnsweredQuestion(AnsweredQuestion answeredQuestion) {
        this.answeredQuestion = answeredQuestion;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
