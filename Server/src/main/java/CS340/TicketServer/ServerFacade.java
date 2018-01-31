package CS340.TicketServer;

import common.IServer;

/**
 * Created by Kavika F.
 */

public class ServerFacade implements IServer
{
	private ServerFacade() {}

	private static class SingletonHolder
	{
		public static final ServerFacade instance = new ServerFacade();
	}

	public static ServerFacade getInstance()
	{
		return SingletonHolder.instance;
	}

	public boolean login(String username, String password)
	{
		return false;
	}

	public boolean register(String username, String password, String displayName)
	{
		return true;
	}

	public void startGame()
	{

	}
}
