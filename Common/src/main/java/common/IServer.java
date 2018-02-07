package common;

/**
 * Created by Kavika F.
 */

public interface IServer
{
	public AbstractSignal login(String username, String password);
	public AbstractSignal register(String username, String password, String displayName);
	public AbstractSignal startGame();
}
