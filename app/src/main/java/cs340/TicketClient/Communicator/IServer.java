package cs340.TicketClient.Communicator;

/**
 * Created by Ben_D on 1/29/2018.
 */

public interface IServer
{
    boolean login(String username, String password);
    boolean register(String username, String password, String screenName);
    void startGame();
}
