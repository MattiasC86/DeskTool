/*
* StudentGroup entity only storing the StudentGroup name
*/

package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by matti on 2017-05-24.
 */

@Entity
public class StudentGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private
    int StudentGroupId;

    private String groupName;

    public StudentGroup(String groupName) {
        this.groupName = groupName;
    }

    public StudentGroup() {}

    public int getStudentGroupId() {
        return StudentGroupId;
    }

    public void setStudentGroupId(int studentGroupId) {
        StudentGroupId = studentGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
