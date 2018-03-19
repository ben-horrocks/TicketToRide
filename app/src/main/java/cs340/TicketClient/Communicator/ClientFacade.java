package cs340.TicketClient.Communicator;

import java.util.ArrayList;
import java.util.List;

import common.DataModels.ChatItem;
import common.DataModels.GameData.Opponent;
import common.DataModels.GameInfo;
import common.DataModels.HandDestinationCards;
import common.DataModels.HistoryItem;
import common.DataModels.Signal;
import common.DataModels.SignalType;
import common.DataModels.GameData.StartGamePacket;
import common.DataModels.TrainCard;
import common.DataModels.Username;
import common.IClient;
import cs340.TicketClient.Game.GameModel;
import cs340.TicketClient.Lobby.LobbyPresenter;

public class ClientFacade implements IClient
{
	private static ClientFacade SINGLETON = null;
	public static ClientFacade getSINGLETON()
	{
		if(SINGLETON == null)
		{
			SINGLETON = new ClientFacade();
		}
		return SINGLETON;
	}

	private ClientFacade()
	{
		if (SINGLETON != null)
		{
			throw new InstantiationError( "Creating of this object is not allowed.");
		}
	}

	@Override
  	public Signal updateGameList(List<GameInfo> gameList) {
    	LobbyPresenter.getInstance().addGames(gameList);
		return new Signal(SignalType.OK, "Accepted");
  	}

  	@Override
  	public Signal startGame(StartGamePacket packet) {
		System.out.println("Recieced StartGame packet");
    	LobbyPresenter.getInstance().gameStarted(packet);
    	System.out.println("Sending OK Signal");
		return new Signal(SignalType.OK, "Accepted");
  	}

	@Override
	public Signal opponentDrewDestinationCards(Username name, int amount) {
		ArrayList<Opponent> oppenents = (ArrayList<Opponent>) GameModel.getInstance().getOpponents();
		for (Opponent op : oppenents)
		{
			if (op.getUsername().toString().equals(name.toString()))
			{
				op.incrementDestinationCards(amount);
				GameModel.getInstance().decrementDestinationCount(amount);
				return new Signal(SignalType.OK, "Added to Opponent Dcard count correctly");
			}
		}
		return new Signal(SignalType.ERROR, "Opponent not found");
	}

	@Override
	public Signal opponentDrewFaceUpCard(Username name, int index, TrainCard replacement) {
		ArrayList<Opponent> opponents = (ArrayList<Opponent>) GameModel.getInstance().getOpponents();
		try {
			for (Opponent op : opponents) {
				if (op.getUsername().toString().equals(name.toString())) {
					op.incrementTrainCards(1);
					break;
				}
			}
			GameModel.getInstance().replaceFaceUp(index, replacement);
			return new Signal(SignalType.OK, "FaceUp card replaced successfully");
		}
		catch(Exception e)
		{
			return new Signal(SignalType.ERROR, e.getMessage());
		}
	}

	@Override
	public Signal opponentDrewDeckCard(Username name) {
		ArrayList<Opponent> opponents = (ArrayList<Opponent>) GameModel.getInstance().getOpponents();
		for (Opponent op : opponents) {
			if (!op.getUsername().toString().equals(name.toString())) {
				op.incrementTrainCards(1);
				return new Signal(SignalType.OK, "Opponent's traincards incremented");
			}
		}
		return new Signal(SignalType.ERROR, "Opponent not found");
	}

	@Override
	public Signal playerDrewDestinationCards(Username name, HandDestinationCards cards) {
		try {
			GameModel.getInstance().getPlayer().getDestinationCards().addAll(cards);
			return new Signal(SignalType.OK, "Destination Cards added sucessfully");
		}
		catch (Exception e) {
			return new Signal(SignalType.ERROR, e.getMessage());
		}
	}

	@Override
	public Signal addChatItem(Username name, ChatItem item) {
		try
		{
			GameModel.getInstance().addChatItem(item);
			return new Signal(SignalType.OK, "Chat Updated");
		}
		catch (Exception e)
		{
			return new Signal(SignalType.ERROR, e.getMessage());
		}
	}

	@Override
	public Signal addHistoryItem(Username name, HistoryItem item) {
		try
		{
			GameModel.getInstance().addHistoryItem(item);
			return new Signal(SignalType.OK, "history Updated");
		}
		catch (Exception e)
		{
			return new Signal(SignalType.ERROR, e.getMessage());
		}
	}
}
