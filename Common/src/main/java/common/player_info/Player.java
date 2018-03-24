package common.player_info;

import java.io.Serializable;
import java.util.*;

import common.cards.HandDestinationCards;
import common.cards.HandTrainCards;
import common.cards.TrainCard;
import common.cards.TrainColor;
import common.map.City;
import common.map.Edge;
import common.map.EdgeGraph;
import common.player_info.turn_state.ITurnState;
import common.player_info.turn_state.InitialDestinationCardDraw;

public class Player implements Serializable
{
    private User user;
    private HandTrainCards hand;
    private HandDestinationCards destinations;
    private PlayerColor color;
    private int score;
    private EdgeGraph claimedEdges;
    private TrainPieces pieces;
    private ITurnState turnState;

    public Player(User user, PlayerColor color)
    {
        this.user = user;
        this.hand = new HandTrainCards();
        this.destinations = new HandDestinationCards();
        this.color = color;
        this.score = 0;
        this.claimedEdges = new EdgeGraph();
        this.turnState = new InitialDestinationCardDraw();
    }

    public String getName() { return this.user.getStringUserName(); }

    public User getUser() { return this.user; }

    public HandTrainCards getHand()
    {
        return this.hand;
    }

    public void drewTrainCards(HandTrainCards cards)
    {
        this.hand.addAll(cards);
    }

    public void drewFaceUpCard(TrainCard trainCard) {  getTurnState().drawFaceUp(this, trainCard); }

    public boolean canClaimEdge(Edge e){
        Map<TrainColor, Integer> coloredCardMap;
        coloredCardMap = hand.getColorCounts();

        int wildCards = 0;
        if(coloredCardMap.get(TrainColor.LOCOMOTIVE) != null)
            wildCards = coloredCardMap.get(TrainColor.LOCOMOTIVE);

        boolean enoughCards = false; //NEVER ENOUGH!!!
        switch(e.getColor())
        {
            //if the edge is colorless we need to see if any sets of a single color are enough to claim the route
            case LOCOMOTIVE:
                for(TrainColor color: coloredCardMap.keySet())
                {
                    int correctColorCards = coloredCardMap.get(color);
                    enoughCards = correctColorCards + wildCards >= e.getLength();
                    if(enoughCards) //if we have enough cards we can stop checking
                        break;
                }
                //if we get here without breaking mid-loop then enoughCards = false
                break;

            //otherwise we just check the specific color
            default:
                int correctColorCards = coloredCardMap.get(e.getColor());
                enoughCards = correctColorCards + wildCards >= e.getLength();
                break;
        }
        boolean enoughTrainCards = pieces.getNumTrainPieces() >= e.getLength();

        return enoughTrainCards && enoughCards;
    }

    public void claimedEdge(Edge edge)
    {
        if (canClaimEdge(edge))
        {
            claimedEdges.addEdge(edge);
            ArrayList<TrainCard> toRemove = new ArrayList<>();
            for (int i = 0; i < edge.getLength(); i++)
            {
                for (TrainCard t : hand.getTrainCards())
                {
                    if (t.getType() == edge.getColor())
                    {
                        toRemove.add(t);
                        break;
                    }
                }
            }
            hand.getTrainCards().removeAll(toRemove);
        }
        //TODO if the newly claimed edge completed a destination card add points and remove the card
    }

	public void setTurnState(ITurnState turnState) { this.turnState = turnState; }

	public ITurnState getTurnState() { return turnState; }

    public HandDestinationCards getDestinationCards() { return this.destinations; }

    public void drewDestinationCards(HandDestinationCards cards)
    {
        getTurnState().drawDestinationCards(this, cards);
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

    public int getNumberTrainCards()
    {
        return hand.size();
    }

    public int getTrainPiecesRemaining()
    {
        return pieces.getNumTrainPieces();
    }

    public int computeLongestPath()
    {
        Set<Edge> unusedEdges = claimedEdges.getAllEdges();
        int longestPath = 0;
        List<City> endCities = Edge.findCitiesWithNumEdges(unusedEdges, 1);
        Set<Edge> usedEdges = new HashSet<>();
        for(City city : endCities)
        {
            Edge edge = Edge.findEdgesWithCity(unusedEdges, city).get(0);
            unusedEdges.remove(edge);
            if(!usedEdges.contains(edge))
            {
                usedEdges.add(edge);
            }
            int edgeLongestPath = edge.computeLongestPathOneDirection(unusedEdges, usedEdges);
            longestPath = longestPath < edgeLongestPath ? edgeLongestPath : longestPath;
        }
        unusedEdges.removeAll(usedEdges);
        //check if there are any circular paths that we missed and therefore need to check.
        if(unusedEdges.size() > 0)
        {
            for (Edge edge : unusedEdges)
            {
                Set<Edge> newEdges = new HashSet<>();
                newEdges.addAll(unusedEdges);
                newEdges.remove(edge);
                int newLongestPath = edge.computeLongestPathTwoDirections(newEdges);
                if (newLongestPath > longestPath)
                {
                    longestPath = newLongestPath;
                }
        }
        }
        return longestPath;
    }

}
