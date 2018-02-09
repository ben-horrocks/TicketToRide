package common;

import common.DataModels.*;

public interface IServer
{
	public Signal login(String username, String password);
	public Signal register(String username, String password, String displayName);
<<<<<<< HEAD
	public Signal addGame(String gameName, Player player);
	public Signal joinGame(Player player , GameID id);
	public Signal startGame(GameID id);
=======
	public Signal startGame(GameID game);
	public Signal addGame(Game newgame);
	public Signal JoinGame(GameID id);
>>>>>>> 00fc5948b4c48d0d5c8abc0ec400cadcdc2965d9
}
