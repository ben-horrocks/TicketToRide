package cs340.TicketClient.Lobby;

import java.util.*;

import cs340.TicketClient.common.DataModels.*;

public class LobbyModel
{
  private Map<GameID, Game> games = new HashMap();


  public Boolean isGameFull(GameID id) throws GameNotFoundException
  {
    Game game = games.get(id);
    if(game == null)
    {
      throw new GameNotFoundException();
    }
    return game.isGameFull();
  }

  public void updateGameList(Map<GameID, Game> newgames) {
    this.games = newgames;
    //Notify the Activity that we have changed some games.
  }


}
