package cs340.TicketClient.login;

import common.player_info.Password;
import common.player_info.Username;

/**
 * Created by jhens on 2/7/2018.
 */

public class LoginModel
{

    private static LoginModel model = null;

    public static LoginModel getInstance()
    {
        if (model == null)
        {
            model = new LoginModel();
        }
        return model;
    }

    public boolean isValidLogin(String username, String password)
    {
        return Username.isValidUserName(username) && Password.isValidPass(password);
    }

    public boolean isValidRegister(String username, String password)
    {
        return Username.isValidUserName(username) && Password.isValidPass(password);
    }
}
