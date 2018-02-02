package cs340.TicketClient.Communicator;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class ServerProxy implements IServer
{
    private static ServerProxy singleton;

    public static ServerProxy getInstance()
    {
        if (singleton == null)
            singleton = new ServerProxy();
        return singleton;
    }

    @Override
    public Signal login(String username, String password) {

        //create Command
        
        return null;
    }

    @Override
    public boolean register(String username, String password, String screenName) {
        return false;
    }

    @Override
    public void startGame() {

    }
}
