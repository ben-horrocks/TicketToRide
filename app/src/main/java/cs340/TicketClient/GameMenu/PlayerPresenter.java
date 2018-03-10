package cs340.TicketClient.GameMenu;


import java.util.List;
import java.util.Map;
import java.util.Queue;

import common.DataModels.DestinationCard;
import common.DataModels.GameData.Opponent;
import common.DataModels.GameData.Player;
import common.DataModels.GameData.TurnQueue;
import common.DataModels.HandDestinationCards;
import common.DataModels.HandTrainCards;
import common.DataModels.TrainColor;
import common.DataModels.Username;
import cs340.TicketClient.Game.GameModel;

public class PlayerPresenter
{
	private static PlayerPresenter SINGLETON;
	private PlayerFragment fragment;
	private GameModel model;
	private Player player;

	public static PlayerPresenter getSINGLETON(PlayerFragment fragment)
	{
		if (SINGLETON == null)
		{
			SINGLETON = new PlayerPresenter(fragment);
		}
		return SINGLETON;
	}

	public static PlayerPresenter getSINGLETON() { return SINGLETON; }

	private PlayerPresenter(PlayerFragment fragment)
	{
		this.fragment = fragment;
		this.model = GameModel.getInstance();
		this.player = model.getPlayer();
	}

	/*
	public void displayPlayerInfo()
	{
		Player player = model.getPlayer();
		StringBuilder sb = new StringBuilder();
		sb.append(getPlayerName(player));
		sb.append(getPlayerColor(player));
		sb.append(getPlayerPoints(player));
		sb.append(getTurnQueue());
	}
	*/

	String getPlayerName()
	{
		String name = player.getUser().getStringUserName();
		return name;
	}

	String getPlayerColor()
	{
		String color = player.getColor().name();
		return color;
	}

	String getPlayerPoints()
	{
		int points = player.getScore();
		String sPoints = String.valueOf(points);
		return sPoints;
	}

	String getTurnQueue()
	{
		TurnQueue queue = model.getGameData().getTurnQueue();
		Username[] usernames = queue.toArray();
		StringBuilder sb = new StringBuilder();
		int position = 1;
		for (Username username : usernames)
		{
			sb.append(String.valueOf(position));
			sb.append(": ");
			sb.append(username.toString());
			sb.append("\n");
			position++;
		}
		return sb.toString();
	}

	String getTrainCards()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Train Cards: \n");
		Map<TrainColor, Integer> handColorCounts = player.getHand().getColorCounts();
		for (TrainColor color : handColorCounts.keySet())
		{
			String text = "\t" + color + ": " + handColorCounts.get(color) + "\n";
			sb.append(text);
		}
		return sb.toString();
	}

	String getDestinationCards()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Destination Cards: \n");
		for (DestinationCard card : player.getDestinationCards().getDestinationCards())
		{
			String text = "\t" + card.getCity1() + " to " + card.getCity2() +
					": " + card.getPointValue() + "\n";
			sb.append(text);
		}
		return sb.toString();
	}

	String getOpponentInfo()
	{
		List<Opponent> opponents = model.getOpponents();
		StringBuilder sb = new StringBuilder();
		for (Opponent opponent : opponents)
		{
			String text = "Name: " + opponent.getName() + "\n";
			sb.append(text);
			text = "\tColor: " + opponent.getColor() + "\n";
			sb.append(text);
			text = "\tScore: " + opponent.getScore() + "\n";
			sb.append(text);
			text = "\tTrain Cards: " + opponent.getNumberHandCards() + "\n";
			sb.append(text);
			text = "\tDestination Cards: " + opponent.getDestinationCardCount() + "\n";
			sb.append(text);
			sb.append("\n");
		}
		return sb.toString();
	}
}
