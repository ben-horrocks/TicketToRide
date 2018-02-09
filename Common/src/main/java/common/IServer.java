package common;

import common.DataModels.*;

public interface IServer
{
	public Signal login(String username, String password);
	public Signal register(String username, String password, String displayName);
	public Signal startGame(GameID id);
	public Signal addGame(Game newgame);
	public Signal JoinGame(GameID id);
}
