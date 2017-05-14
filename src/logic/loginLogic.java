package logic;
import entity.User;
import service.UserService;
import logic.UserLogic;


public class LoginLogic {

    private static int currId;

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
            return 1;
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
