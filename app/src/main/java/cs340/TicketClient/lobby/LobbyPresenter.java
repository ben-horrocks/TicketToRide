package cs340.TicketClient.lobby;

import android.util.Log;
import android.widget.Toast;

import java.util.*;

import common.game_data.GameID;
import common.game_data.GameInfo;
import common.game_data.StartGamePacket;
import common.player_info.User;
import cs340.TicketClient.async_task.*;

public class LobbyPresenter implements ILobbyPresenter
{
    private static final String TAG = "LOBBY";
    private LobbyActivity activity;
    private LobbyModel model;

    private static LobbyPresenter singleton;

    /**
     * A method that initializes the singleton object
     *
     * @param activity The activity to set for the presenter
     * @pre activity must be active
     * @post the singleton object will be initialized with an empty model and a reference to the
     * activity that initialized it.
     */
    static void setActivity(LobbyActivity activity)
    {
        singleton = new LobbyPresenter(activity);
    }

    /**
     * Gets a reference to the singleton object
     *
     * @return the singleton
     * @pre the singleton must have been properly initialized using the initSingleton method
     * @post the singleton reference will be returned
     */
    public static LobbyPresenter getInstance()
    {
    	if (singleton == null)
		{
			singleton = new LobbyPresenter();
		}
        return singleton;
    }

    private LobbyPresenter()
    {
        this(null);
    }

    /**
     * The bare-minimum constructor that stores a reference to the activity that initialized it
     *
     * @param activity the initializing activity.
     */
    private LobbyPresenter(LobbyActivity activity)
    {
        this.activity = activity;
        model = LobbyModel.getSingleton();
    }

    public void updateGameList()
    {
        if (activity != null)
        {
            activity.updateGameList();
        }
    }

    /**
     * Gets all the ServerGameData info in the LobbyModel, converting it into a List instead of a Map.
     *
     * @return The list of GameInfo
     * @pre none
     * @post returns a list representing all the GameInfo items in the model.
     */
    public List<GameInfo> getAllGames()
    {
        ArrayList<GameInfo> list = new ArrayList<>();
        list.addAll(model.getAllGames());
        return list;
    }

    /**
     * Returns the gameIDs of the current games the player has joined.
     *
     * @return A List of GameIDs.
     */
    public List<GameID> getJoinedGameID()
    {
        return model.getJoinedGames();
    }

    /**
     * Searches through the model for games containing the filter in their game names and returns
     * them.
     *
     * @param filter The phrase to search for
     * @return The list of games containing the filter phrase
     * @pre none
     * @post The returned list will contain all games that have the filter phrase. If the filter
     * phrase is not set, it will contain all the games in the model.
     */
    public List<GameInfo> searchGames(String filter)
    {
        ArrayList<GameInfo> list = new ArrayList<>();
        if (filter == null || filter.equals(""))
        {
            return getAllGames();
        } else
        {
            for (GameInfo g : model.getAllGames())
            {
                String name = g.getName().toLowerCase();
                if (name.contains(filter.toLowerCase()))
                {
                    list.add(g);
                }
            }
        }
        return list;
    }


    /**
     * Removes the game with the specified ID and updates the GUI
     *
     * @param id The id of the game to be removed.
     * @pre None
     * @post The game with the specified ID will be removed from the game list, and the GUI will be
     * notified that it needs to update. If the game ID was not found in the list, a warning will be
     * logged.
     */
    public void removeGame(GameID id)
    {
        try
        {
            model.removeGame(id);
        } catch (LobbyModel.GameNotFoundException e)
        {
            Log.w(TAG, e.getMessage(), e);
        }
        updateGameList();
    }

    /**
     * Removes multiple games from the game list and updates the GUI
     *
     * @param ids The list of game IDs to be removed
     * @pre None
     * @post The games with the specified IDs will be removed from the game list, and the GUI will
     * be notified that it needs to update. If a game ID was not found in the list, a warning will
     * be logged.
     */
    public void removeGame(List<GameID> ids)
    {
        for (GameID i : ids)
        {
            try
            {
                model.removeGame(i);
            } catch (LobbyModel.GameNotFoundException e)
            {
                Log.w(TAG, e.getMessage(), e);
            }
        }
        updateGameList();
    }

    /**
     * Adds a GameInfo Object to the model
     *
     * @param g The GameInfo object to be added to the model
     * @pre none
     * @post The model will include an entry for the new GameInfo object
     */
    public void addGame(GameInfo g)
    {
        model.addGame(g);
        updateGameList();
    }

    /**
     * Adds a List of GameInfo objects to the model
     *
     * @param games The list of GameInfo objects to add to the model
     * @pre none
     * @post The model will include entries for all the new GameInfo objects
     */
    public void addGames(List<GameInfo> games)
    {
        model.addGames(games);
        updateGameList();
    }

    /**
     * Sends a request to the server to get the list of non-full games that haven't started yet.
     * Then adds the returned list to the model and updates the LobbyActivity.
     *
     * @pre none
     * @post The LobbyModel will contain entries for each game on the list. Any entries that are not
     * returned by the server, but were previously contained in the model are removed so that the
     * model only contains the updated list. Then the GUI is notified to update the displayed games.
     */
    @Override
    public boolean canStartGame(GameID id)
    {
        try
        {
            GameInfo info = model.getGame(id);
            return info.getPlayerCount() > 1 && info.getPlayerCount() <= 5;
        } catch (LobbyModel.GameNotFoundException e)
        {
            Log.w(TAG, e.getMessage(), e);
        }
        return false;
    }

    LobbyModel getModel()
    {
        return model;
    }

    @Override
    public void addGame(String newGame)
    {
        AddGameTask task = new AddGameTask(activity.getBaseContext());
        task.execute(newGame, model.getUser());
    }

    @Override
    public void joinGame(GameID id)
    {
        JoinGameTask task = new JoinGameTask(activity.getBaseContext());
        Object[] obj = {model.getUser(), id};
        task.execute(obj);
    }

    @Override
    public void startGame(GameID id)
    {
        if (LobbyPresenter.getInstance().canStartGame(id))
        {
            StartGameTask task = new StartGameTask(activity.getBaseContext());
            task.execute(id);
        } else
        {
            Toast.makeText(activity, "Invalid number of Players", Toast.LENGTH_SHORT).show();
        }

    }

    public void resumeGame(GameID id)
	{

	}

    public void gameStarted(StartGamePacket packet)
    {
        activity.startGame(packet);
    }

    User getPlayer()
    {
        return model.getUser();
    }

    boolean isMyGame(GameID id)
    {
        try
        {
            return model.getGame(id).getCreatorName().equals(model.getUser().getStringUserName());
        } catch (LobbyModel.GameNotFoundException e)
        {
            Toast.makeText(activity, "GAME NOT FOUND", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    boolean hasJoinedGame(GameID id)
    {
        try
        {
            return model.getGame(id).hasUser(model.getUser().getUsername());
        } catch (LobbyModel.GameNotFoundException e)
        {
            Toast.makeText(activity, "GAME NOT FOUND", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    void setUser(User user) { model.setUser(user); }

    User getUser() { return model.getUser(); }
}
