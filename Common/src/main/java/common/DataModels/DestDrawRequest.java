package common.DataModels;

/**
 * The parameters to request a destination card.
 */
public class DestDrawRequest {
    private GameID id;
    private Username user;

	/**
	 * @pre All parameters must be non-null.
	 * @post A valid set of parameters for requesting a destination card.
	 * @param id The id of the game trying to be drawn from.
	 * @param user The username of the player trying to draw the destination card.
	 */
	public DestDrawRequest(GameID id, Username user)
    {
        this.id = id;
        this.user = user;
    }

    public GameID getId() {
        return id;
    }

    public Username getUser() {
        return user;
    }
}
