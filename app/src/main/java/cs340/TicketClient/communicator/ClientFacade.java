package cs340.TicketClient.communicator;

import java.util.ArrayList;
import java.util.List;

import common.cards.HandTrainCards;
import common.communication.CommandParams;
import common.game_data.*;
import common.chat.ChatItem;
import common.communication.IClient;
import common.cards.HandDestinationCards;
import common.cards.TrainCard;
import common.communication.Signal;
import common.communication.SignalType;
import common.history.HistoryItem;
import common.map.Edge;
import common.player_info.Player;
import common.player_info.Username;
import cs340.TicketClient.async_task.TurnEndedTask;
import cs340.TicketClient.game.GameModel;
import cs340.TicketClient.game.GamePresenter;
import cs340.TicketClient.lobby.LobbyModel;
import cs340.TicketClient.lobby.LobbyPresenter;

public class ClientFacade implements IClient
{
    private static ClientFacade SINGLETON = null;

    public static ClientFacade getSINGLETON()
    {
        if (SINGLETON == null)
        {
            SINGLETON = new ClientFacade();
        }
        return SINGLETON;
    }

    private ClientFacade()
    {
        if (SINGLETON != null)
        {
            throw new InstantiationError("Creating of this object is not allowed.");
        }
    }

    @Override
    public Signal updateGameList(Username user, List<GameInfo> gameList)
    {
        LobbyModel.getSingleton().setGames(gameList);
        return new Signal(SignalType.OK, "Accepted");
    }

    @Override
    public Signal startGame(StartGamePacket packet)
    {
        System.out.println("Received StartGame packet");
        LobbyPresenter.getInstance().gameStarted(packet);
        System.out.println("Sending OK Signal");
        return new Signal(SignalType.OK, "Accepted");
    }

    @Override
	public Signal resumeGame(Username username)
	{
		return new Signal(SignalType.ERROR, "implement resumeGame in ClientFacade");
	}

    @Override
    public Signal updateFaceUpCards(Username name, HandTrainCards newFaceUps) {
        return new Signal (SignalType.ERROR, "unimplemented");
    }

    @Override
    public Signal opponentDrewDestinationCards(Username name, int amount)
    {
        GameModel.getInstance().decrementDestinationCount(amount);
        ArrayList<Opponent> oppenents =
                (ArrayList<Opponent>) GameModel.getInstance().getOpponents();
        for (Opponent op : oppenents)
        {
            if (op.getUsername().toString().equals(name.toString()))
            {
                op.incrementDestinationCards(amount);
                return new Signal(SignalType.OK, "Added to Opponent Dcard count correctly");
            }
        }
        return new Signal(SignalType.ERROR, "Opponent not found");
    }

    @Override
    public Signal opponentDrewFaceUpCard(Username name, int index, TrainCard replacement)
    {
        ArrayList<Opponent> opponents =
                (ArrayList<Opponent>) GameModel.getInstance().getOpponents();
        try
        {
            for (Opponent op : opponents)
            {
                if (op.getUsername().toString().equals(name.toString()))
                {
                    op.incrementTrainCards(1);
                    break;
                }
            }
            GameModel.getInstance().replaceFaceUp(index, replacement);
            return new Signal(SignalType.OK, "FaceUp card replaced successfully");
        } catch (Exception e)
        {
            return new Signal(SignalType.ERROR, e.getMessage());
        }
    }

    @Override
    public Signal opponentDrewDeckCard(Username name)
    {
        ArrayList<Opponent> opponents =
                (ArrayList<Opponent>) GameModel.getInstance().getOpponents();
        for (Opponent op : opponents)
        {
            if (!op.getUsername().toString().equals(name.toString()))
            {
                op.incrementTrainCards(1);
                return new Signal(SignalType.OK, "Opponent's traincards incremented");
            }
        }
        return new Signal(SignalType.ERROR, "Opponent not found");
    }

    @Override
    public Signal playerClaimedEdge(Username name, Edge edge) {
        boolean routeClaimed = GameModel.getInstance().markClaimedRoute(edge);
        if (routeClaimed) {
            return new Signal(SignalType.OK, "Route claimed successfully");
        }
        return new Signal(SignalType.ERROR, "could not claim route");
    }

    @Override
    public Signal playerDrewDestinationCards(Username name, HandDestinationCards cards, GameID gameID)
    {
        try
        {
        	Player player = GameModel.getInstance().getPlayer();
        	boolean isMyTurn = GameModel.getInstance().isMyTurn();
        	boolean nextTurn = player.drewDestinationCards(cards, isMyTurn);
        	GameModel.getInstance().decrementDestinationCount(cards.size());
        	if (nextTurn)
			{
				TurnEndedTask task = new TurnEndedTask();
				task.execute(gameID, name);
				return new Signal(SignalType.OK, "Successful next turn switch");
			}
			else
			{
				return new Signal(SignalType.OK, "Destination Cards added successfully");
			}
        } catch (Exception e)
        {
            return new Signal(SignalType.ERROR, e.getMessage());
        }
    }

    @Override
    public Signal addChatItem(Username name, ChatItem item)
    {
        try
        {
            GameModel.getInstance().addChatItem(item);
            return new Signal(SignalType.OK, "Chat Updated");
        } catch (Exception e)
        {
            return new Signal(SignalType.ERROR, e.getMessage());
        }
    }

    @Override
    public Signal addHistoryItem(Username name, HistoryItem item)
    {
        try
        {
            GameModel.getInstance().addHistoryItem(item);
            return new Signal(SignalType.HISTORY, "history Updated");
        } catch (Exception e)
        {
            return new Signal(SignalType.ERROR, e.getMessage());
        }
    }

    @Override
    public Signal lastTurn(Username name)
    {
        //TODO: implement Last Turn (Probably popping up a toast)
        return new Signal(SignalType.ERROR, "unimplemented");
    }

	@Override
	public Signal updateTurnQueue(Username username)
	{
		GameModel.getInstance().nextTurn();
		return new Signal(SignalType.OK, "TurnQueue for " + username + " updated.");
	}

	@Override
    public Signal gameEnded(Username name, EndGame players)
    {
//        call game Presenter here
        return new Signal(SignalType.ERROR, "unimplemented");
    }

    @Override
    public Signal startTurn(Username name)
    {
    	Player player = GameModel.getInstance().getPlayer();
        player.getTurnState().turnStarted(player);
        return new Signal(SignalType.OK, "implemented startTurn *wink*");
    }
}
