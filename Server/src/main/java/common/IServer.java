package common;

/**
 * Created by Kavika F.
 */

public interface IServer
{
	public boolean login(String username, String password);
	public boolean register(String username, String password, String displayName);
	public void startGame();
}
