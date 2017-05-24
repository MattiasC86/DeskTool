package entity;

import javax.persistence.*;

/**
 * Created by matti on 2017-05-24.
 */
@Entity
public class GroupDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private
    int GroupDetailsId;

    @OneToOne
    private
    StudentGroup studentGroup;

    @OneToOne
    private
    User user;

    public GroupDetails(StudentGroup studentGroup, User user) {
        this.studentGroup = studentGroup;
        this.user = user;
    }

    public GroupDetails() {}

    public int getGroupDetailsId() {
        return GroupDetailsId;
    }

    public void setGroupDetailsId(int groupDetailsId) {
        GroupDetailsId = groupDetailsId;
    }

    public entity.StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(entity.StudentGroup studentGroup) {
        studentGroup = studentGroup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
