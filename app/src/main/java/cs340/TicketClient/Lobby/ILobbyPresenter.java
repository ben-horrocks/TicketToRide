package cs340.TicketClient.Lobby;

import java.util.List;

import common.DataModels.GameID;
import common.DataModels.GameInfo;

public interface ILobbyPresenter
{
  public boolean canStartGame(GameID id);
  public List<GameInfo> getAllGames();
  public List<GameInfo> searchGames(String search);

}
