package cs340.TicketClient.login;


public class LoginPresenter implements ILoginPresenter
{

    private LoginPresenterFacade facade;

    public LoginPresenter(LoginActivity activity)
    {
        this.facade = new LoginPresenterFacade(activity);
    }


    /**
     * Method to login the player given a username and password
     *
     * @param username the player's username
     * @param password the player's password
     * @return A player object representing the logged in player
     */
    @Override
    public void login(String username, String password)
    {
        facade.login(username, password);
    }

    /**
     * Method to register a new player given the relevant information
     *
     * @param username   the player's username (must be unique)
     * @param password   the player's password
     * @param screenname the name to be displayed for user
     * @return A player object representing the logged in player
     */
    @Override
    public void register(String username, String password, String screenname)
    {
        facade.register(username, password, screenname);
    }
}
