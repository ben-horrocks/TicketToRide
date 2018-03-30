package common.game_data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import common.cards.DestinationCard;
import common.cards.TrainCard;
import common.chat.ChatItem;
import common.history.HistoryItem;
import common.map.Edge;
import common.map.EdgeGraph;
import common.player_info.Player;
import common.player_info.Username;

public class ClientGameData implements IGameData, Serializable
{

    private GameID id;
    private Player player;
    private List<Opponent> opponents;
    private EdgeGraph gameboard;
    private List<TrainCard> faceUp;
    private List<HistoryItem> history;
    private List<ChatItem> chat;
    private TurnQueue turnQueue;
    private int destinationCardsLeft;
    private int trainCardsLeft;

    public ClientGameData(ServerGameData game, Username username)
    {
        this.id = game.getId();
        this.opponents = new ArrayList<>();
        for (Player p : game.getPlayers())
        {
            if (p.getUser().getUsername().equals(username))
            {
                this.player = p;
            } else
            {
                opponents.add(new Opponent(p));
            }
        }
        this.gameboard = game.getGameBoard();
        this.faceUp = game.getFaceUpCards();
        this.history = game.getHistory();
        this.chat = game.getChat();
        this.turnQueue = game.getTurnQueue();
        destinationCardsLeft = 30;
        this.trainCardsLeft = game.getTrainCardsLeft();
    }

    public int getDestinationCardsLeft()
    {
        return destinationCardsLeft;
    }

    public void decDestinationCardsLeft(int count)
    {
        destinationCardsLeft -= count;
    }

    public List<TrainCard> getFaceUp()
    {
        return faceUp;
    }

    public void setFaceUpCards(List<TrainCard> cards)
    {
        faceUp = cards;
    }

    public GameID getId()
    {
        return id;
    }

    public int getTrainCardsLeft()
    {
        return trainCardsLeft;
    }

    public void decrementTrainCardsLeft()
    {
        trainCardsLeft--;
    }

    public List<Opponent> getOpponents()
    {
        return opponents;
    }

    public List<ChatItem> getChat()
    {
        return chat;
    }

    public List<HistoryItem> getHistory()
    {
        return history;
    }

    public Player getPlayer()
    {
        return player;
    }

    public EdgeGraph getGameboard()
    {
        return gameboard;
    }

    public void edgeClaimed(Edge edge, List<TrainCard> spent)
    {
        this.player.claimedEdge(edge, spent);
        this.gameboard.claimed(edge);
    }

    public Edge markClaimedEdge(Edge edge)
    {
        //Get Opponent
        Opponent opponent = null;
        List<Opponent> opList = getOpponents();
        for (Opponent op : opList) {
            if (op.getUsername().equals(edge.getOwner()))
            {
                opponent = op;
            }
        }
        if (opponent == null)
        {
            System.out.println("Could not find opponent with which to update route");
            return null;
        }

        //Find edge in gameboard and update with opponent
        Edge foundEdge = null;
        List<Edge> list = getGameboard().getGraph().get(edge.getFirstCity());
        for (Edge toFind : list)
        {
            if (toFind.getID().equals(edge.getID()))
            {
                foundEdge = toFind;
                break;
            }
        }
        if (foundEdge == null)
        {
            System.out.println("Edge not found or edge is already owned. Could not mark as claimed");
            return null;
        }

        //Set Owner of found Edge
        foundEdge.setOwner(opponent);

        //subtract the appropriate number of train cars from opponent
        opponent.subtractTrainCarsForEdge(foundEdge);

        return foundEdge;
    }

    private Opponent getOpponent(Username username)
    {
        for (Opponent o : opponents)
        {
            if (o.getUsername().equals(username))
            {
                return o;
            }
        }
        return null; // NOTE: may return null, thus throwing NullPointerException
    }

    public Username whoseTurn()
    {
        return turnQueue.peek();
    }

    public boolean isMyTurn() { return turnQueue.peek().equals(player.getUser().getUsername()); }

    public TurnQueue getTurnQueue()
    {
        return turnQueue;
    }

    public void nextTurn() { turnQueue.nextTurn(); }

    // TODO: determine what this is doing and deal with it
    @Override
    public void deckDraw(Username username, List<TrainCard> drawn)
    {
        Opponent o = getOpponent(username);
        if (o != null)
        {
            o.addHandCard(drawn.size());
        }
    }

    @Override
    public void faceUpDraw(Username username, List<TrainCard> drawn, List<TrainCard> replacements)
    {
        ArrayList<Integer> toRemove = new ArrayList<>();
        for (int i = 0; i < drawn.size(); i++)
        {
            for (int j = 0; j < faceUp.size(); j++)
            {
                if (faceUp.get(j).getType() == drawn.get(i).getType())
                {
                    faceUp.remove(j);
                }
            }
        }
    }

    @Override
    public void destinationDraw(Username username, List<DestinationCard> drawn)
    {
        getOpponent(username).addDestinationCards(drawn.size());
    }

    @Override
    public void addHistoryItem(HistoryItem event)
    {
        this.history.add(event);
    }

    @Override
    public void addChatMessage(ChatItem m)
    {
        this.chat.add(m);
    }
}
