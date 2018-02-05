package cs340.TicketClient.Login;

import cs340.TicketClient.Communicator.ServerProxy;
import cs340.TicketClient.common.DataModels.Password;
import cs340.TicketClient.common.DataModels.Player;
import cs340.TicketClient.common.DataModels.ScreenName;
import cs340.TicketClient.common.DataModels.Username;
import cs340.TicketClient.common.Signal;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class LoginPresenter implements ILoginPresenter
{


    public LoginPresenter()
    {

    }
    /**
    * Method to login the player given a username and password
    * @param username the player's username
    * @param password the player's password
    * @return A player object representing the logged in player
     */
    @Override
    public Player login(String username, String password) {
        if (Password.isValidPass(password)) {
            Signal response = ServerProxy.getInstance().login(username, password);
            return new Player(new Username(username), new Password(password), new ScreenName("test"));
        }
        else
            return null;
    }

    /**
    * Method to register a new player given the relevant information
    * @param username the player's username (must be unique)
    * @param password the player's password
    * @param screenname the name to be displayed for user
    * @return A player object representing the logged in player
    *
     */
    @Override
    public Player register(String username, String password, String screenname) {
        if(ScreenName.isValidScreenName(screenname) && Username.isValidUserName(username) && Password.isValidPass(password)) {
            Signal response = ServerProxy.getInstance().login(username, password);
            return new Player(new Username(username), new Password(password), new ScreenName(screenname));
        }
        else
            return null;
    }

}
