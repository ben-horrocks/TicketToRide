package cs340.TicketClient.game;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.cards.DestinationCard;
import common.cards.HandDestinationCards;
import common.cards.TrainCard;
import common.cards.TrainColor;
import common.chat.ChatItem;
import common.game_data.ClientGameData;
import common.game_data.EndGame;
import common.game_data.GameID;
import common.game_data.Opponent;
import common.history.HistoryItem;
import common.map.City;
import common.map.Edge;
import common.player_info.Player;
import common.player_info.User;
import common.player_info.Username;
import cs340.TicketClient.card_fragments.deck_fragment.DeckFragmentPresenter;
import cs340.TicketClient.game_menu.chat.ChatPresenter;
import cs340.TicketClient.game_menu.history.HistoryPresenter;

public class GameModel
{
    private ClientGameData gameData;
    private static GameModel singleton;
    private HandDestinationCards initialDCards;
    GamePresenter presenter;
    private ArrayList<TrainCard> queuedCards = new ArrayList<>();
    Edge selectedEdge;

    public static GameModel getInstance()
    {
        if (singleton == null)
        {
            singleton = new GameModel();
        }
        return singleton;
    }

    private GameModel() {}

    public void setQueuedCards(ArrayList<TrainCard> queuedCards) {
        this.queuedCards = queuedCards;
    }

    public ArrayList<TrainCard> getQueuedCards() {
        return queuedCards;
    }

    // Game Methods
    public void setGameData(ClientGameData gameData) { this.gameData = gameData; }

    public ClientGameData getGameData() { return gameData; }

    public void setPresenter(GamePresenter presenter)
    {
        this.presenter = presenter;
    }

    public void setInitialDCards(HandDestinationCards initialDCards) {this.initialDCards = initialDCards; }

    public GameID getGameID() { return gameData.getId(); }

    // Player and Opponent Methods
    public Player getPlayer() { return gameData.getPlayer(); }

	public Username getUserName() { User user = getPlayer().getUser(); return user.getUsername(); }

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

    public void updateFaceUpCards(List<TrainCard>cards)
    {
        gameData.setFaceUpCards(cards);
        DeckFragmentPresenter.getInstance().refreshFaceUpCards(cards);
    }

    public int getTrainCardDeckSize() { return gameData.getTrainCardsLeft(); }

    // Train Card Hand Methods
    // TODO: update (or at least look at)
    public void addTrainCard(TrainCard card)
    {
        getPlayer().getHand().add(card);
    }

    // TODO: update to remove specific card(s)
    public void removeTrainCard() { getPlayer().getHand().remove(0); }

    // Destination Card Deck Methods
    public void decrementDestinationCount(int count) { gameData.decDestinationCardsLeft(count); }

    public HandDestinationCards getInitialDCards() { return initialDCards; }

    public void updateDDeckCount(int number) { gameData.decDestinationCardsLeft(number); }

    public void clearDCards() { initialDCards = null; }

    // Destination Hand Methods
    // TODO: update (or at least look at)
    public void addDestCard(DestinationCard card)
    {
        getPlayer().getDestinationCards().add(card);
    }

    public void addDestinationCards(HandDestinationCards cards)
	{
		getPlayer().getDestinationCards().addAll(cards.getDestinationCards());
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

    public void nextTurn() { gameData.nextTurn(); }

    public void endGame(EndGame players)
    {
        presenter.endGame(players);
    }

    //Selected Edge Methods
    public Edge getSelectedEdge() {
        return selectedEdge;
    }

    public void setSelectedEdge(Edge selectedEdge) {
        this.selectedEdge = selectedEdge;
    }

    //Claimed route edges
    public boolean markClaimedRoute(Edge edge)
    {

        Edge foundEdge = gameData.markClaimedEdge(edge);

        //Update Map Fragment
        if (foundEdge != null)
        {
            presenter.refreshMapFragment(foundEdge);
            return true;
        }
        return false;
    }

    private boolean doubleEdgeClaimChecks(Username agent, Edge toClaim, int totalPlayers)
    {
        if(!toClaim.isDoubleEdge()) return true;
        Map<City,List<Edge>> map = gameData.getGameboard().getGraph();
        List<Edge> edgesConnectedToSecondCity = map.get(toClaim.getSecondCity());
        Edge twin = null;
        for(Edge edge : edgesConnectedToSecondCity)
        {
            if(toClaim.getFirstCity().equals(edge.getSecondCity()))
            {
                twin = edge;
                break;
            }
        }
        if(twin == null) return true; //hopefully never gets here, but if you messed up registering edges the game must go on
        boolean notManyPlayers = totalPlayers == 2 || totalPlayers == 3;
        if(notManyPlayers && twin.getOwner() != null) return false;
        if(twin.getOwner()!= null && twin.getOwner().equals(agent)) return false;
        return true;
    }

    private boolean grayEdgeClaimCheck(List<TrainCard> cards) {
        final TrainColor wild = TrainColor.GRAY;
        TrainColor cardSetColor = cards.get(0).getType();
        for (TrainCard card : cards)
        {
            TrainColor cardColor = card.getType();
            if (cardSetColor.equals(wild))
            {
                cardSetColor = cardColor;
            }
            boolean matches = cardColor.equals(cardSetColor) || cardColor.equals(wild);
            if(!matches) return false;
        }
        return true;
    }

    private boolean coloredEdgeClaimCheck(List<TrainCard> cards, final TrainColor edgeColor) {
        final TrainColor wild = TrainColor.GRAY;
        for (TrainCard card : cards)
        {
            TrainColor cardColor = card.getType();
            boolean matches = cardColor.equals(edgeColor) || cardColor.equals(wild);
            if(!matches) return false;
        }
        return true;
    }

    public List<TrainCard> canClaimRoute(TrainColor color, int length) throws GamePresenter.InsufficientCardsException
    {
       List<TrainCard> cards = gameData.getPlayer().getHand().getTrainCards();
       List<TrainCard> cardsToUse = new ArrayList<>();
       List<TrainCard> wilds = new ArrayList<>();
       for(TrainCard card : cards)
       {
            if(card.getType() == color)
            {
                cardsToUse.add(card);
                if(cardsToUse.size() == length)
                {
                    break;
                }
            } else if (card.getType() == TrainColor.GRAY)
            {
                wilds.add(card);
            }
       }
       if(cardsToUse.size() == length)
       {
           gameData.getPlayer().getHand().removeAll(cardsToUse);
           return cardsToUse;
       } else if( cardsToUse.size() < length)
       {
           while (wilds.size() > 0)
           {
               cardsToUse.add(wilds.get(0));
               wilds.remove(0);
               if(cardsToUse.size() == length)
               {
                   gameData.getPlayer().getHand().removeAll(cardsToUse);
                   return cardsToUse;
               }
           }
       }
       throw new GamePresenter.InsufficientCardsException();
    }

    public void lastTurn()
    {
        Toast.makeText(presenter.getGameActivity(), "Last Turn!", Toast.LENGTH_SHORT).show();
    }
}
