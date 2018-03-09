package cs340.TicketClient.Communicator;

import java.util.List;

import common.DataModels.ChatItem;
import common.DataModels.DestinationCard;
import common.DataModels.GameInfo;
import common.DataModels.HandDestinationCards;
import common.DataModels.HistoryItem;
import common.DataModels.Signal;
import common.DataModels.SignalType;
import common.DataModels.GameData.StartGamePacket;
import common.DataModels.TrainCard;
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
  	public Signal startGame(StartGamePacket packet) {
    	LobbyPresenter.getInstance().gameStarted(packet);
		return new Signal(SignalType.OK, "Accepted");
  	}

	@Override
	public Signal opponentDrewDestinationCards(Username name, int amount) {
		return new Signal(SignalType.ERROR, "Unimplemented method");
	}

	@Override
	public Signal opponentDrewFaceUpCard(Username name, int index, TrainCard replacement) {
		return new Signal(SignalType.ERROR, "Unimplemented method");
	}

	@Override
	public Signal opponentDrewDeckCard(Username name) {
		return new Signal(SignalType.ERROR, "Unimplemented method");
	}

	@Override
	public Signal playerDrewDestinationCards(Username name, HandDestinationCards cards) {
		return new Signal(SignalType.ERROR, "Unimplemented method");
	}

	@Override
	public Signal addChatItem(Username name, ChatItem item) {
		return new Signal(SignalType.ERROR, "Unimplemented method");
	}

	@Override
	public Signal addHistoryItem(Username name, HistoryItem item) {
		return new Signal(SignalType.ERROR, "Unimplemented method");
	}
}
