package logic;


import com.sun.xml.internal.ws.api.ha.StickyFeature;
import service.UserService;


public class loginLogic {

    UserService user;
    String dataUsername, dataPassword, userLevel, username = "username", password = "password", role = "role";
    boolean access = false;


    public String loginValidation(String formUsername, String formPassword){

        user.read(username, dataUsername);
        user.read(password, dataPassword);

        if (formUsername == dataUsername){
            if (formPassword == dataPassword){
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
}
