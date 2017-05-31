/*
* This class handles validation logic when a new user is to be created.
*/

package logic;

import entity.User;
import service.UserService;

public class RegisterLogic {

    public static int registerUser(String firstName, String lastName, String userName, String email, String password, String role) {
        String result1 = UserService.read("userName", userName, "userName");
        String result2 = UserService.read("email", email, "email");

        //if some field is empty
        if(firstName.equals("") || lastName.equals("") || userName.equals("") || email.equals("") || password.equals("")) {
            return 0;
        }

        //If username already exists in db - return 1
        else if(result1.equals(userName)) {
            return 1;
        }
        //If email already exists in db - return 2
        else if(result2.equals(email)) {
            return 2;
        }
        //If username and email is not taken, save new user in db - return 3
        UserService.create(new User(firstName, lastName, userName, email, password, role));
        return 3;
    }
}
