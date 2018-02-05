package cs340.TicketClient.Lobby;

import android.content.Context;
import android.widget.Toast;

import cs340.TicketClient.ASyncTask.AddGameTask;
import cs340.TicketClient.Communicator.ServerProxy;
import cs340.TicketClient.common.DataModels.*;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class LobbyPresenter
{
  public LobbyModel model = new LobbyModel();
  public Player currentPlayer;
  public LobbyPresenter(Player newPlayer)
  {
    this.currentPlayer = newPlayer;
  }

  public void createGame(Context context) {
    Game newgame = new Game(currentPlayer);
    AddGameTask task = new AddGameTask();
    task.execute(newgame);
  }

  public void joinGame(Context context, GameID id)
  {
    try
    {
      if(!model.isGameFull(id))
      {
      }
    } catch(GameNotFoundException e)
    {
      Toast.makeText(context, "INVALID GAME", Toast.LENGTH_LONG).show();
    }
  }

  public
}
