package logic;


import entity.User;
import service.UserService;


public class registerLogic {

    public static int registerUser(String firstName, String lastName, String userName, String email, String password, String role) {
        String result1 = UserService.read("userName", userName, "userName");
        String result2 = UserService.read("email", email, "email");
        if(result1.equals(userName)) {
            return 1;
        }
        if(result2.equals(email)) {
            return 2;
        }
        UserService.create(new User(firstName, lastName, userName, email, password, role));
        return 3;
    }


}
