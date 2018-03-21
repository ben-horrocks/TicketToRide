package common.chat;

import java.io.Serializable;

import common.player_info.Player;

public class ChatItem implements Serializable
{

    private Player mPlayer;
    private String message;

    public ChatItem(Player player, String message)
    {
        mPlayer = player;
        this.message = message;
    }

    public Player getPlayer()
    {
        return mPlayer;
    }

    public void setPlayer(Player player)
    {
        mPlayer = player;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getPlayerName()
    {
        return mPlayer.getName();
    }
}
