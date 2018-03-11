package cs340.TicketClient.GameMenu;

import common.DataModels.ChatItem;
import common.DataModels.GameData.Player;
import common.DataModels.GameID;
import cs340.TicketClient.ASyncTask.SendChatTask;
import cs340.TicketClient.Communicator.ServerProxy;
import cs340.TicketClient.Game.GameModel;

public class ChatPresenter {

    private static ChatPresenter SINGLETON;

    private ChatFragment fragment;
    private GameModel model;

    public static ChatPresenter getSINGLETON(ChatFragment fragment) {
        if (SINGLETON == null) {
            SINGLETON = new ChatPresenter(fragment);
        }
        return SINGLETON;
    }

    public static ChatPresenter getSINGLETON() {
        return SINGLETON;
    }

    private ChatPresenter(ChatFragment fragment) {
        this.fragment = fragment;
        this.model = GameModel.getInstance();
    }

    public void sendChatMessage(String message) {
        Player player = model.getPlayer();
        GameID id = model.getGameID();
        ChatItem chatItem = new ChatItem(player, message);
        SendChatTask task = new SendChatTask(fragment.getContext());
        task.execute(id, chatItem);
    }

    public void updateChatList() {
        if (fragment != null) {
            fragment.updateChatList();
        }
    }
}
