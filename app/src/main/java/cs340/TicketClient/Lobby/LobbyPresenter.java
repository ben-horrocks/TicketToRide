package cs340.TicketClient.Lobby;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import common.DataModels.*;
import cs340.TicketClient.ASyncTask.*;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class LobbyPresenter implements ILobbyPresenter
{
    private static final String TAG = "LOBBY";
    private LobbyActivity activity;
    private LobbyModel model;

    private static LobbyPresenter singleton = new LobbyPresenter();

    /**
     * A method that initializes the singleton object
     * @pre activity must be active
     * @post the singleton object will be initialized with an empty model and a reference to the
     * activity that initialized it.
     * @param activity
     */
    public static void setActivity(LobbyActivity activity){
        singleton = new LobbyPresenter(activity);
    }

    /**
     * Gets a reference to the singleton object
     * @pre the singleton must have been properly initialized using the initSingleton method
     * @post the singleton reference will be returned
     * @return the singleton
     */
    public static LobbyPresenter getInstance() {
        return singleton;
    }

    public LobbyPresenter(){
        this(null);
    }

    /**
     * The bare-minimum constructor that stores a reference to the activity that initialized it
     * @param activity the initializing activity.
     */
    private LobbyPresenter(LobbyActivity activity){
        this.activity = activity;
        model = new LobbyModel(new HashMap<GameID, GameInfo>());
    }

    private void updateGameList(){
        if(activity != null)
            activity.updateGameList();
    }

    /**
     * Gets all the ServerGameData info in the LobbyModel, converting it into a List instead of a Map.
     * @pre none
     * @post returns a list representing all the GameInfo items in the model.
     * @return The list of GameInfo
     */
    public List<GameInfo> getAllGames(){
        ArrayList<GameInfo> list = new ArrayList<GameInfo>();
        for (GameInfo g: model.getAllGames()){
            list.add(g);
        }
        return list;
    }

    /**
     * //TODO Documentation
     * @return
     */
    public GameID getJoinedGameID()
    {
        return model.getJoinedGame();
    }

    /**
     * Searches through the model for games containing the filter in their game names and returns
     * them.
     *
     * @pre none
     * @post The returned list will contain all games that have the filter phrase. If the filter
     * phrase is not set, it will contain all the games in the model.
     *
     * @param  filter  The phrase to search for
     * @return The list of games containing the filter phrase
     */
    public List<GameInfo> searchGames(String filter){
        ArrayList<GameInfo> list = new ArrayList<GameInfo>();
        if(filter == null || filter == "") {
            return getAllGames();
        } else {
            for (GameInfo g : model.getAllGames()) {
                String name = g.getName().toLowerCase();
                if (name.contains(filter.toLowerCase())) {
                    list.add(g);
                }
            }
        }
        return list;
    }


    /**
     * Removes the game with the specified ID and updates the GUI
     * @pre None
     * @post The game with the specified ID will be removed from the game list, and the GUI will be
     * notified that it needs to update. If the game ID was not found in the list, a warning will be
     * logged.
     *
     * @param  id The id of the game to be removed.
     */
    public void removeGame(GameID id){
        try {
            model.removeGame(id);
        } catch (LobbyModel.GameNotFoundException e) {
            Log.w(TAG, e.getMessage(), e);
        }
        updateGameList();
    }

    /**
     * Removes multiple games from the game list and updates the GUI
     * @pre None
     * @post The games with the specified IDs will be removed from the game list, and the GUI will
     * be notified that it needs to update. If a game ID was not found in the list, a warning will
     * be logged.
     *
     * @param ids The list of game IDs to be removed
     */
    public void removeGame(List<GameID> ids){
        for(GameID i: ids) {
            try {
                model.removeGame(i);
            } catch (LobbyModel.GameNotFoundException e) {
                Log.w(TAG, e.getMessage(), e);
            }
        }
        updateGameList();
    }

    /**
     * Adds a GameInfo Object to the model
     * @pre none
     * @post The model will include an entry for the new GameInfo object
     * @param g The GameInfo object to be added to the model
     */
    public void addGame(GameInfo g){
        model.addGame(g);
        updateGameList();
    }

    /**
     * Adds a List of GameInfo objects to the model
     * @pre none
     * @post The model will include entries for all the new GameInfo objects
     * @param games The list of GameInfo objects to add to the model
     */
    public void addGames(List<GameInfo> games){
        model.addGame(games);
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
     *//*
    public void fetchGames(){
        List<GameInfo> games = new ArrayList<GameInfo>();
        Signal s = ServerProxy.getInstance().getAvailableGameInfo();
        games = (List<GameInfo>) s.getObject();
        model.setGames(games);
    }*/

    @Override
    public boolean canStartGame(GameID id){
        try {
            GameInfo info = model.getGame(id);
            return info.getPlayerCount() > 1 && info.getPlayerCount() <= 5;
        } catch (LobbyModel.GameNotFoundException e) {
            Log.w(TAG, e.getMessage(), e);
        }
        return false;
    }

    public LobbyModel getModel()
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
        } else {
            Toast.makeText(activity, "Invalid number of Players", Toast.LENGTH_SHORT).show();
        }

    }

    public void gameStarted()
    {
        activity.startGame();
    }

    public User getPlayer() {
        return model.getUser();
    }

    public boolean isMyGame(GameID id) {
        try
        {
            return model.getGame(id).getCreatorName().equals(model.getUser().getName());
        } catch(LobbyModel.GameNotFoundException e)
        {
            Toast.makeText(activity, "GAME NOT FOUND", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean hasJoinedGame(GameID id) {
        try
        {
            return model.getGame(id).getUsers().contains(model.getUser());
        } catch(LobbyModel.GameNotFoundException e)
        {
            Toast.makeText(activity, "GAME NOT FOUND", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
