package cs340.TicketClient.GameMenu;


import java.util.Queue;

import common.DataModels.GameData.Player;
import common.DataModels.GameData.TurnQueue;
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

	public String getPlayerName()
	{
		String name = player.getUser().getStringUserName();
		return name;
	}

	public String getPlayerColor()
	{
		String color = player.getColor().name();
		return color;
	}

	public String getPlayerPoints()
	{
		int points = player.getScore();
		String sPoints = String.valueOf(points);
		return sPoints;
	}

	public String getTurnQueue()
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
}
