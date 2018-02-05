package cs340.TicketClient.Login;

import cs340.TicketClient.Communicator.ServerProxy;
import cs340.TicketClient.common.DataModels.Password;
import cs340.TicketClient.common.DataModels.Player;
import cs340.TicketClient.common.DataModels.ScreenName;
import cs340.TicketClient.common.DataModels.Username;


public class LoginPresenter
{
    LoginModel model;


    public LoginPresenter()
    {

    }

    Player login(String username, String password) {
        if (Password.isValidPass(password)) {
            boolean success = ServerProxy.getInstance().login(username, password);
            return new Player(new Username(username), new Password(password), new ScreenName("test"));
        }
        else
            return null;
    }

    Player register(String username, String password, String screenname) {
        if(ScreenName.isValidScreenName(screenname) && Username.isValidUserName(username) && Password.isValidPass(password)) {
            boolean success = ServerProxy.getInstance().login(username, password);
            return new Player(new Username(username), new Password(password), new ScreenName(screenname));
        }
        else
            return null;
    }

}
