package cs340.TicketClient.Login;

import common.DataModels.Password;
import common.DataModels.ScreenName;
import common.DataModels.Username;

/**
 * Created by jhens on 2/7/2018.
 */

public class LoginModel {

    private static LoginModel model = null;

    public static LoginModel getInstance()
    {
        if(model == null)
            model = new LoginModel();
        return model;
    }

    public boolean isValidLogin(String username, String password)
    {
        return Username.isValidUserName(username) && Password.isValidPass(password);
    }

    public boolean isValidRegister(String username, String password, String screenname)
    {
        return Username.isValidUserName(username) && Password.isValidPass(password) && ScreenName.isValidScreenName(screenname);
    }
}
