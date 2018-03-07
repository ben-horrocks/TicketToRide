package common.DataModels;

import common.DataModels.GameData.Player;

/**
 * Created by Carter on 3/6/18.
 */

public class ChatItem {

    private Player mPlayer;
    private String message;

    public ChatItem(Player player, String message) {
        mPlayer = player;
        this.message = message;
    }

    public Player getPlayer() {
        return mPlayer;
    }

    public void setPlayer(Player player) {
        mPlayer = player;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
