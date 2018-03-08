package cs340.TicketClient.GameMenu;

import common.DataModels.HistoryItem;
import common.DataModels.GameData.Player;
import cs340.TicketClient.Game.GameModel;

/**
 * Created by Carter on 3/7/18.
 */

public class HistoryPresenter {

    HistoryFragment fragment;
    GameModel model;

    public HistoryPresenter(HistoryFragment fragment) {
        this.fragment = fragment;
        this.model = GameModel.getInstance();
    }

    public void updateChatList() {

    }

}
