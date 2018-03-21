package cs340.TicketClient.Game;

import java.util.List;

import common.DataModels.*;
import common.DataModels.GameData.StartGamePacket;

/**
 * Created by jhens on 3/10/2018.
 */

public interface IGamePresenter
{

    void fillModel(StartGamePacket packet);

    void claimRoute(TrainColor color, int number) throws GamePresenter.InsufficientCardsException;

    public List<ChatItem> getChatMessages();

    public void setChatMessages(List<ChatItem> chatMessages);

    public List<HistoryItem> getPlayHistory();

    public void setPlayHistory(List<HistoryItem> playHistory);

    public HandDestinationCards getDestinationCards();
}