package logic;

import service.UserService;


public class loginLogic {

    UserService user;
    String dataUsername, dataPassword, userLevel;
    boolean access = false;

    /*public String loginValidation(String formUsername, String formPassword){

        dataUsername = user.read("userName", formUsername);
        dataPassword = user.read("password", formPassword);

        if (formUsername.equals(dataUsername) && !formUsername.isEmpty()){
            if (formPassword.equals(dataPassword) && !formPassword.isEmpty()){
                access = true;
                userLevel = user.read2("userName", formUsername, "role");
            }

            else {
                access = false;
                System.out.println("Wrong username or password!");
            }
        }

        else {
            access = false;
            System.out.println("Wrong username or password!");
        }

        return(userLevel);
    }*/

    public String loginValidation(String formUsername, String formPassword){

        dataUsername = user.read2("userName", formUsername, "userId");
        dataPassword = user.read2("password", formPassword, "userId");

        if (dataUsername.equals(dataPassword)){
            access = true;
            userLevel = user.read2("userName", formUsername, "role");
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
