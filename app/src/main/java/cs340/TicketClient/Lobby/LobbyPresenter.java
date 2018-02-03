package cs340.TicketClient.Lobby;

import java.util.ArrayList;
import java.util.List;

import cs340.TicketClient.common.DataModels.Game;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class LobbyPresenter
{
    private LobbyModel model;

    public LobbyPresenter(){
        model = new LobbyModel(new ArrayList<Game>());
    }

    public LobbyPresenter(List<Game> games){
        model = new LobbyModel(games);
    }

    public List<Game> getAllGames(){
        return model.getAllGames();
    }
}
