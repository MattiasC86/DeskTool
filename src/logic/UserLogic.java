package logic;

import service.UserService;

public class UserLogic {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String role;

    public void UserLogic(String firstName, String lastName, String userName, String password, String email, String role)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
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
