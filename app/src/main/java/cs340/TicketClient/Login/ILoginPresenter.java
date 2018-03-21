package cs340.TicketClient.Login;


/**
 * Created by jhens on 2/5/2018.
 */

public interface ILoginPresenter
{

    void login(String username, String password);

    void register(String username, String password, String screenname);
}
