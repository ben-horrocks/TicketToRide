package cs340.TicketClient.Login;

import cs340.TicketClient.common.DataModels.Player;

/**
 * Created by jhens on 2/5/2018.
 */

public interface ILoginActivity {

    void setLogin();
    boolean loginReady();
    void setRegister();
    boolean registerReady();
    void gotoLobby(Player player);
}
