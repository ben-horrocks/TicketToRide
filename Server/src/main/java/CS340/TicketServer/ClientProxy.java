package CS340.TicketServer;

import java.util.Set;

import common.DataModels.Game;
import common.IClient;

/**
 * Created by Carter on 2/5/18.
 */

public class ClientProxy implements IClient {

    @Override
    public boolean updateGameList(Set<Game> gameList) {
        //return all the games from the server
    }
}
