package cs340.TicketClient.login;

public interface ILoginPresenter
{

    void login(String username, String password);

    void register(String username, String password);
}
