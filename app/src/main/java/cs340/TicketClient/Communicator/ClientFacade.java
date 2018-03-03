package cs340.TicketClient.Communicator;

import java.util.List;

import common.DataModels.GameID;
import common.DataModels.GameInfo;
import common.DataModels.Signal;
import common.DataModels.SignalType;
import common.DataModels.Username;
import common.IClient;
import cs340.TicketClient.Lobby.LobbyPresenter;

public class ClientFacade implements IClient
{
	private static ClientFacade SINGLETON = null;
	public static ClientFacade getSingleton()
	{
		if(SINGLETON == null)
		{
			SINGLETON = new ClientFacade();
		}
		return SINGLETON;
	}

	@Override
  	public Signal updateGameList(List<GameInfo> gameList) {
    	LobbyPresenter.getInstance().addGames(gameList);
		return new Signal(SignalType.OK, "Accepted");
  	}

  	@Override
  	public Signal startGame(GameID id) {
    	LobbyPresenter.getInstance().gameStarted();
		return new Signal(SignalType.OK, "Accepted");
  	}

	@Override
	public Signal playerDrewDestinationCards(Username name, int amount) {
		return new Signal(SignalType.OK, "Accepted");
	}

}
