package common;

/**
 * Created by Kavika F.
 */

public interface IServer
{
	public ISignal login(String username, String password);
	public ISignal register(String username, String password, String displayName);
	public ISignal startGame();
}
