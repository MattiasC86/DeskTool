package logic;

import service.UserService;


public class loginLogic {

    UserService user;
    String dataUsername, dataPassword, userLevel, username = "userName", password = "password", role = "role";
    boolean access = false;

    public String loginValidation(String formUsername, String formPassword){

        user.read(username, dataUsername);
        user.read(password, dataPassword);

        if (formUsername.equals(dataUsername) && !formUsername.isEmpty()){
            if (formPassword.equals(dataPassword) && !formPassword.isEmpty()){
                access = true;
                userLevel = user.read(role, dataUsername);
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
    }

    public boolean Accesslevel(){

        return access;
    }
}
