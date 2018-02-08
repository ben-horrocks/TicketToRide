package cs340.TicketClient.Login;

import common.DataModels.Player;

/**
 * Created by jhens on 2/5/2018.
 */

public interface ILoginPresenter {

    Player login(String username, String password);
    Player register(String username, String password, String screenname);
}
