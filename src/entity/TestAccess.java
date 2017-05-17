package entity;


import javax.persistence.*;

@Entity
public class TestAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int testAccessId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Test test;

    public TestAccess(User user, Test test) {
        this.setUser(user);
        this.setTest(test);
    }

    public TestAccess(){}

    public int getTestAccessId() {
        return testAccessId;
    }

    public void setTestAccessId(int testAccessId) {
        this.testAccessId = testAccessId;
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
