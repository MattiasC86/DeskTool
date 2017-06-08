/*
* LoginLogic class handles validation logic for login screen,
* also stores a static variable of currently logged in users Id
*/

package logic;

import entity.User;
import service.UserService;

public class LoginLogic {

    // Variable accessed from everywhere, containing User Id for currently logged in User
    private static int currId;

    // Checks if username and password input is correct, returns a status code 0-3
    public static int loginValidation(String formUsername, String formPassword){
        setCurrId(0);
        String userNameHolder = "";

        userNameHolder = UserService.read("userName", formUsername, "userName");
        if(userNameHolder.equals(null) || userNameHolder.equals("")) {
            return 0;
        }

        setCurrId(Integer.parseInt(UserService.read("userName", formUsername, "userId")));
        User user = UserService.read(getCurrId());

        if(user.getPassword().equals(formPassword)) {
            if(user.getRole().equals("Student")){
                return 1;
            }
            else if(user.getRole().equals("Teacher")){
                return 2;
            }
            else if(user.getRole().equals("Admin")){
                return 3;
            }
            else { return 0;}

        } else {
            setCurrId(0);
            return 0;
        }
    }

    public static int getCurrId() {
        return currId;
    }
    public static void setCurrId(int currId) {
        LoginLogic.currId = currId;
    }
}
