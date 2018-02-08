package common;

import common.DataModels.*;

/**
 * Created by Kavika F.
 */

public interface IServer
{
	public Signal login(String username, String password);
	public Signal register(String username, String password, String displayName);
	public Signal startGame();
	public Signal addGame(Game newgame);
	public Signal JoinGame(GameID id);
}
