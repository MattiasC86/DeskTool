package logic;


import service.UserService;


public class loginLogic {

    UserService user;
    int userLevel;
    String dataUsername, datapassword;

    public int loginValidation(String username, String password){

        user.readByName(dataUsername);

        if (username == dataUsername){
            System.out.println("username ok but not password");
        }

        else {
            System.out.println("do not work");
        }

        return(userLevel);
    }
}
