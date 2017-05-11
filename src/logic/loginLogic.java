package logic;
import service.UserService;
import logic.UserLogic;


public class LoginLogic {

    UserService user;
    UserLogic currentUser;
    String dataUsername, dataPassword, userLevel;
    String curUserFirstName, curUserlastName,  curUseruserName,  curUserpassword, curUseremail, curUserrole ;
    public static int access = 1; //0 = false, 1 = true, 2 = username empty, 3 = password empty

    public String loginValidation(String formUsername, String formPassword){

        dataUsername = user.read("userName", formUsername, "userId");
        dataPassword = user.read("password", formPassword, "userId");

        curUserFirstName = user.read("userName", formUsername, "firstName");
        curUserlastName = user.read("userName", formUsername, "lastName");
        curUseruserName = user.read("userName", formUsername, "userName");
        curUserpassword = user.read("userName", formUsername, "password");
        curUseremail = user.read("userName", formUsername, "email");
        curUserrole = user.read("userName", formUsername, "role");
/*
        if (dataUsername != null && !dataUsername.isEmpty()){
            access = 2;
        }

        else {
            if (dataPassword != null && !dataPassword.isEmpty()){
                access = 3;
            }

            else {
                if (dataUsername.equals(dataPassword)){
                    access = 1;
                    userLevel = user.read("userName", formUsername, "role");
                    //currentUser = new UserLogic(curUserFirstName, curUserlastName, curUseruserName, curUserpassword, curUseremail, curUserrole);
                }

                else {
                    access = 0;
                    System.out.println("Wrong username or password!");
                }
            }
        }*/

        if (dataUsername.equals(dataPassword)){
            access = 1;
            userLevel = user.read("userName", formUsername, "role");
            //currentUser = new UserLogic(curUserFirstName, curUserlastName, curUseruserName, curUserpassword, curUseremail, curUserrole);
        }

        else {
            access = 0;
            System.out.println("Wrong username or password!");
        }


        return(userLevel);
    }

    public int Accesslevel(){
        return access;
    }
}
