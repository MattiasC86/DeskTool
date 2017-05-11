
package logic;

import service.UserService;


public class LoginLogic {

    UserService user;
    String dataUsername, dataPassword, userLevel;
    boolean access = false;

    public String loginValidation(String formUsername, String formPassword){

        dataUsername = user.read("userName", formUsername, "userId");
        dataPassword = user.read("password", formPassword, "userId");

        if (dataUsername.equals(dataPassword)){
            access = true;
            userLevel = user.read("userName", formUsername, "role");
        }

        else {
            access = false;
            System.out.println("Wrong username or password!");
        }

        return(userLevel);
    }

    public boolean Accesslevel(){

        return access;
    }
}
