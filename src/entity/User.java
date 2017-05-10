package entity;

import javax.persistence.*;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    private String firstName;
    private String lastName;
    private String password;
    private String userName;
    private String email;
    private String role;

    public User(String firstName, String lastName, String userName, String email, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.role = role;
    }

    public User(){}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {return userName; }

    public void setUserName(String userName) {this.userName = userName; }

    public String getEmail () { return email; }

    public void setEmail (String email) {this.email = email;}

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}