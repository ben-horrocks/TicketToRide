package cs340.TicketClient.Login;


import common.player_info.User;

/**
 * Created by jhens on 2/5/2018.
 */

public interface ILoginActivity
{

    void setLogin();

    boolean loginReady();

    void setRegister();

    boolean registerReady();

    void gotoLobby(User user);
}
