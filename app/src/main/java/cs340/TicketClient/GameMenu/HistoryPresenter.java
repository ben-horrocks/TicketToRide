package cs340.TicketClient.GameMenu;

import common.DataModels.HistoryItem;
import common.DataModels.GameData.Player;
import cs340.TicketClient.Game.GameModel;

/**
 * Created by Carter on 3/7/18.
 */

public class HistoryPresenter {

    private static HistoryPresenter SINGLETON;

    HistoryFragment fragment;
    GameModel model;

    public static HistoryPresenter getSINGLETON(HistoryFragment historyFragment) {
        if (SINGLETON == null) {
            SINGLETON = new HistoryPresenter(historyFragment);
        }
        return SINGLETON;
    }

    public static HistoryPresenter getSINGLETON() {
        return SINGLETON;
    }

    private HistoryPresenter(HistoryFragment fragment) {
        this.fragment = fragment;
        this.model = GameModel.getInstance();
    }

    public void updateHistoryList() {
        if (fragment != null) {
            fragment.updateHistoryList();
        }
    }

}
