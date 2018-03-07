package common.DataModels.GameData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import common.DataModels.*;

public class Player implements Serializable{
    private User user;
    private List<TrainCard> hand;
    private List<DestinationCard> destinations;
    private PlayerColor color;
    private int score;
    private EdgeGraph claimedEdges;

    public Player(User user, PlayerColor color) {
        this.user = user;
        this.hand = new ArrayList<>();
        this.destinations = new ArrayList<>();
        this.color = color;
        this.score = 0;
        this.claimedEdges = new EdgeGraph();
    }

    public String getName() { return this.user.getStringUserName(); }

    public User getUser() {return  this.user;}

    public List<TrainCard> getHand() { return this.hand; }

    public void drewTrainCards(List<TrainCard> cards) { this.hand.addAll(cards); }

    public void claimedEdge(Edge edge) {
        claimedEdges.addEdge(edge);
        ArrayList<TrainCard> toRemove = new ArrayList<>();
        for(int i=0; i<edge.getLength(); i++) {
            for (TrainCard t : hand) {
                if (t.getType() == edge.getColor()) {
                    toRemove.add(t);
                    break;
                }
            }
        }
        hand.removeAll(toRemove);
        //TODO if the newly claimed edge completed a destination card add points and remove the card
    }

    private boolean canClaimEdge(Edge edge)
	{
		return numCardsOfColor(edge.getColor()) >= edge.getLength();
	}

	private int numCardsOfColor(TrainColor color)
	{
		int sum = 0;
		for (TrainCard t : hand)
		{
			if (t.getType() == color)
			{
				sum++;
			}
		}
		return sum;
	}

    public List<DestinationCard> getDestinationCards() { return this.destinations; }

    public void drewDestinationCards(List<DestinationCard> cards){
        this.destinations.addAll(cards);
    }

    public PlayerColor getColor() { return this.color; }

    public int getScore() { return this.score; }

    public void addPoints(int points) { this.score += points; }

    public EdgeGraph getClaimedEdges() { return this.claimedEdges; }

    public int getNumberTrainCards() { return hand.size(); }
}
