package cs340.TicketClient.Game;

import java.util.List;

import common.cards.DestinationCard;
import common.cards.HandDestinationCards;
import common.cards.TrainCard;
import common.cards.TrainColor;
import common.chat.ChatItem;
import common.game_data.ClientGameData;
import common.game_data.GameID;
import common.game_data.Opponent;
import common.history.HistoryItem;
import common.player_info.Player;
import common.player_info.Username;
import cs340.TicketClient.GameMenu.chat.ChatPresenter;
import cs340.TicketClient.GameMenu.history.HistoryPresenter;

public class GameModel
{
    private ClientGameData gameData;
    private static GameModel singleton;
    private HandDestinationCards initialDCards;

    public static GameModel getInstance()
    {
        if (singleton == null)
        {
            singleton = new GameModel();
        }
        return singleton;
    }

    private GameModel()
    {
    }

    // Game Methods
    public void setGameData(ClientGameData gameData) { this.gameData = gameData; }

    public ClientGameData getGameData() { return gameData; }

    public void setInitialDCards(HandDestinationCards initialDCards) { this.initialDCards = initialDCards; }

    public GameID getGameID() { return gameData.getId(); }

    // Player and Opponent Methods
    public Player getPlayer() { return gameData.getPlayer(); }

    public List<Opponent> getOpponents() { return gameData.getOpponents(); }

    // TODO: update to get correct opponent
    public void addTrainToOpponent(int number)
    {
        getOpponents().get(0).addHandCard(number);
    }

    // TODO: update to get correct opponent
    public void removeTrainFromOpponent(int number)
    {
        getOpponents().get(0).removeHandCard(number);
    }

    // TODO: update to get correct opponent
    public void addDestToOpponent(int number)
    {
        getOpponents().get(0).addDestinationCards(number);
    }

    // TODO: update to get correct opponent
    public void removeDestFromOpponent(int number)
    {
        getOpponents().get(0).removeDestinationCard(number);
    }

    // Train Card Deck and Face Up Methods
    public void replaceFaceUp(int index, TrainCard replacement)
    {
        gameData.getFaceUp().set(index, replacement);
    }

    public int getTrainCardDeckSize()
    {
        return gameData.getTrainCardsLeft();
    }

    /* Fake draw method. Only for test button. */
    public TrainCard drawFromTrainCardDeck()
    {
        gameData.decrementTrainCardsLeft();
        return new TrainCard(TrainColor.RED);
    }

    /* Fake draw method. Only for test button. "Scary" i can access face up list directly(?) */
    public TrainCard drawFromFaceUp()
    {
        TrainCard card = gameData.getFaceUp().remove(0);
        gameData.getFaceUp().add(new TrainCard(drawFromTrainCardDeck().getType()));
        return card;
    }

    // Train Card Hand Methods
    // TODO: update (or at least look at)
    public void addTrainCard(TrainColor color)
    {
        getPlayer().getHand().add(new TrainCard(color));
    }

    // TODO: update to remove specific card(s)
    public void removeTrainCard()
    {
        getPlayer().getHand().remove(0);
    }

    // Destination Card Deck Methods
    public void decrementDestinationCount(int count)
    {
        gameData.decDestinationCardsLeft(count);
    }

    public HandDestinationCards getInitialDCards()
    {
        return initialDCards;
    }

    public void updateDDeckCount(int number)
    {
        gameData.decDestinationCardsLeft(number);
    }

    public void clearDCards()
    {
        initialDCards = null;
    }

    // Destination Hand Methods
    // TODO: update (or at least look at)
    public void addDestCard(DestinationCard card)
    {
        getPlayer().getDestinationCards().add(card);
    }

    // TODO: update to remove specific card(s)
    public void removeDestCard()
    {
        getPlayer().getDestinationCards().remove(0);
    }

    // Chat Methods
    public void addChatItem(ChatItem item)
    {
        gameData.getChat().add(item);
        ChatPresenter.getSINGLETON().updateChatList();
    }

    public List<ChatItem> getChatMessages()
    {
        return gameData.getChat();
    }

    public void setChatMessages(List<ChatItem> chats)
    {
        for (ChatItem item : chats)
        {
            gameData.addChatMessage(item);

        }
    }

    public void updateChat(ChatItem item)
    {
        gameData.addChatMessage(item);
    }

    // History Methods
    public void addHistoryItem(HistoryItem item)
    {
        gameData.getHistory().add(item);
        if (HistoryPresenter.getSINGLETON() != null)
        {
            HistoryPresenter.getSINGLETON().updateHistoryList();
        }
    }

    public List<HistoryItem> getPlayHistory()
    {
        return gameData.getHistory();
    }

    public void setPlayHistory(List<HistoryItem> history)
    {
        for (HistoryItem event : history)
        {
            gameData.addHistoryItem(event);

        }
    }

    public void updateHistory(HistoryItem item)
    {
        gameData.addHistoryItem(item);
    }

    // Turn Queue Methods
    public Username whoseTurn()
    {
        return gameData.whoseTurn();
    }

    public boolean isMyTurn()
    {
        return gameData.isMyTurn();
    }

    public void addPoints(int number)
    {
        getPlayer().addPoints(number);
    }
}
