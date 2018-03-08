package cs340.TicketClient.GameMenu;

import common.DataModels.ChatItem;
import common.DataModels.GameData.Player;
import cs340.TicketClient.Game.GameModel;

/**
 * Created by Carter on 3/7/18.
 */

public class ChatPresenter {

    ChatFragment fragment;
    GameModel model;

    public ChatPresenter(ChatFragment fragment) {
        this.fragment = fragment;
        this.model = GameModel.getInstance();
    }

    public void sendChatMessage(String message) {
        Player player = model.getPlayer();
        ChatItem chat = new ChatItem(player, message);
        //TODO: send chat message over socket connection
    }

    public void updateChatList() {

    }
}
