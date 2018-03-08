package common.DataModels;

import java.io.Serializable;

import common.DataModels.GameData.Player;

/**
 * Created by Carter on 3/6/18.
 */

public class HistoryItem implements Serializable{

    private Player mPlayer;
    private String action;

    public HistoryItem(Player player, String action) {
        mPlayer = player;
        this.action = action;
    }

    public Player getPlayer() {
        return mPlayer;
    }

    public void setPlayer(Player player) {
        mPlayer = player;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPlayerName() {
        return mPlayer.getName();
    }
}
