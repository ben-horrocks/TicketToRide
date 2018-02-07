package cs340.TicketClient.Lobby;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import common.DataModels.GameInfo;
import common.DataModels.GameID;

/**
 * Created by Ben_D on 1/29/2018.
 */

public class LobbyPresenter
{
    private static final String TAG = "LOBBY";
    private LobbyActivity activity;
    private LobbyModel model;

    public LobbyPresenter(LobbyActivity activity){
        this.activity = activity;
        model = new LobbyModel(new ArrayList<GameInfo>());
    }

    public LobbyPresenter(LobbyActivity activity, List<GameInfo> games){
        this.activity = activity;
        model = new LobbyModel(games);
    }

    private void updateGameList(){
        String filter = activity.getFilter();
        activity.updateGameList(getFilteredGames(filter));
    }

    public List<GameInfo> getAllGames(){
        return model.getAllGames();
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
    public List<GameInfo> getFilteredGames(String filter){
        if(filter == null || filter == "")
            return model.getAllGames();

        List<GameInfo> filteredList = new ArrayList<GameInfo>();
        for (GameInfo g: model.getAllGames()) {
            String name = g.getName().toLowerCase();
            if (name.contains(filter.toLowerCase())) {
                filteredList.add(g);
            }
        }
        return filteredList;
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

    public void addGame(GameInfo g){
        model.addGame(g);
        updateGameList();
    }

    public void addGames(List<GameInfo> games){
        model.addGame(games);
        updateGameList();
    }
}
