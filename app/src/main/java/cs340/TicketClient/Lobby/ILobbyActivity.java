package cs340.TicketClient.Lobby;

import java.util.List;

import common.DataModels.GameInfo;

public interface ILobbyActivity
{
  public void updateGameList(List<GameInfo> games);
  public void startGame();
}
