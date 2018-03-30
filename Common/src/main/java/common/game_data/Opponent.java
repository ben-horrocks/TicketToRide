package common.game_data;

import java.io.Serializable;

import common.map.Edge;
import common.map.EdgeGraph;
import common.player_info.Player;
import common.player_info.PlayerColor;
import common.player_info.TrainPieces;
import common.player_info.User;
import common.player_info.Username;

/**
 * A restricted view of a player.
 */
public class Opponent implements Serializable
{
    private User user;
    private int numberHandCards;
    private int numberDestinationCards;
    private PlayerColor color;
    private int score;
    private EdgeGraph claimedEdges;
    private TrainPieces mTrainPieces;

    public Opponent(User user, PlayerColor color)
    {
        this.user = user;
        this.numberHandCards = 0;
        this.numberDestinationCards = 0;
        this.color = color;
        this.score = 0;
        this.claimedEdges = new EdgeGraph();
        this.mTrainPieces = new TrainPieces(true);
    }

    public void incrementDestinationCards(int number)
    {
        numberDestinationCards += number;
    }

    public void incrementTrainCards(int number)
    {
        numberHandCards += number;
    }

    public Opponent(Player player)
    {
        this.user = player.getUser();
        this.numberHandCards = player.getNumberTrainCards();
        this.numberDestinationCards = player.getDestinationCards().size();
        this.color = player.getColor();
        this.score = player.getScore();
        this.claimedEdges = player.getClaimedEdges();
        this.mTrainPieces = player.getPieces();
    }

    public String getName()
    {
        return user.getStringUserName();
    }

    public Username getUsername()
    {
        return user.getUsername();
    }

    public int getNumberHandCards()
    {
        return numberHandCards;
    }

    public void addHandCard(int amount)
    {
        this.numberHandCards += amount;
    }

    public void removeHandCard(int amount)
    {
        this.numberHandCards -= amount;
    }

    public int getDestinationCardCount()
    {
        return this.numberDestinationCards;
    }

    public void addDestinationCards(int amount)
    {
        this.numberDestinationCards += amount;
    }

    public void removeDestinationCard(int amount)
    {
        this.numberDestinationCards -= amount;
    }

    public PlayerColor getColor()
    {
        return this.color;
    }

    public int getScore()
    {
        return this.score;
    }

    public void addPoints(int points)
    {
        this.score += points;
    }

    public EdgeGraph getClaimedEdges()
    {
        return this.claimedEdges;
    }

    public TrainPieces getTrainPieces() { return mTrainPieces; }

    public void setTrainPieces(TrainPieces trainPieces) { mTrainPieces = trainPieces; }

    public boolean decrementCardsAndCarsForEdge(Edge edge) {
        if (mTrainPieces.getNumTrainPieces() > edge.getLength()) {
            mTrainPieces.useTrainPieces(edge.getLength());
            if (numberHandCards > edge.getLength()) {
                numberHandCards -= edge.getLength();
                return true;
            }
            return false;
        }
        return false;
    }
}
