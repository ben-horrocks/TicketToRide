package common;

import common.DataModels.*;

public interface IServer
{
	public Signal login(String username, String password);
	public Signal register(String username, String password, String displayName);
	public Signal addGame(String gameName, Player player);
	public Signal joinGame(Player player , GameID id);
	public Signal startGame(GameID id);
	public Signal getAvailableGameInfo();
}
