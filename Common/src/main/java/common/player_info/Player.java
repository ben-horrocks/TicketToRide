package common.player_info;

import java.io.Serializable;
import java.util.*;

import common.cards.DestinationCard;
import common.cards.HandDestinationCards;
import common.cards.HandTrainCards;
import common.cards.TrainCard;
import common.cards.TrainColor;
import common.game_data.Point;
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
    private Point score;
    private EdgeGraph claimedEdges;
    private TrainPieces pieces;
    private ITurnState turnState;

    public Player(User user, PlayerColor color)
    {
        this.user = user;
        this.hand = new HandTrainCards();
        this.destinations = new HandDestinationCards();
        this.color = color;
        this.score = new Point();
        this.claimedEdges = new EdgeGraph();
        this.turnState = new InitialDestinationCardDraw();
        this.pieces = new TrainPieces(true);
    }

    public void setPieces(TrainPieces pieces) {
        this.pieces = pieces;
    }

    public String getName() { return this.user.getStringUserName(); }

    public User getUser() { return this.user; }

    public HandTrainCards getHand()
    {
        return this.hand;
    }

    private void checkDestinationCards()
    {
        for (DestinationCard card : destinations.getDestinationCards())
        {
            if (!card.isComplete())
                card.setComplete(EdgeGraph.findRoute(claimedEdges.getAllEdges(), card.getCity1(), card.getCity2()));
        }
    }

    public void drewTrainCards(HandTrainCards cards)
    {
		getTurnState().draw;
    }

    public void drewFaceUpCard(TrainCard trainCard) {  getTurnState().drawFaceUp(this, trainCard); }

    public boolean canClaimEdge(Edge e)
    {
        if (e.isClaimed())
        {
            return false;
        }
        Map<TrainColor, Integer> coloredCardMap;
        coloredCardMap = hand.getColorCounts();

        int wildCards = 0;
        if(coloredCardMap.get(TrainColor.LOCOMOTIVE) != null)
            wildCards = coloredCardMap.get(TrainColor.LOCOMOTIVE);

        boolean enoughCards = false; //NEVER ENOUGH!!!
        switch(e.getColor())
        {
            //if the edge is colorless we need to see if any sets of a single color are enough to claim the route
            case GRAY:
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
        boolean enoughTrainCars = pieces.getNumTrainPieces() >= e.getLength();

        return enoughTrainCars && enoughCards;
    }

    public void claimedEdge(Edge edge, List<TrainCard> spent)
    {
        claimedEdges.addEdge(edge);
        this.checkDestinationCards();
        hand.getTrainCards().remove(spent);
    }

	public void setTurnState(ITurnState turnState) { this.turnState = turnState; }

	public ITurnState getTurnState() { return turnState; }

    public HandDestinationCards getDestinationCards() { return this.destinations; }

    public boolean drewDestinationCards(HandDestinationCards cards, boolean isMyTurn)
    {
        return getTurnState().drawDestinationCards(this, cards, isMyTurn);
    }

    public PlayerColor getColor()
    {
        return this.color;
    }

    public int getScore()
    {
        return this.score.getRoutesClaimedPoints();
    }

    public Point getPoints()
    {
        return this.score;
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

    public Username getUsername()
    {
        return user.getUsername();
    }

}
