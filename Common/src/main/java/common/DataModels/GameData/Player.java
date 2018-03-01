package common.DataModels.GameData;

import java.util.ArrayList;
import java.util.List;

import common.DataModels.*;

/**
 * Created by Vibro on 2/28/2018.
 */

public class Player {
    private User user;
    private List<TrainCard> hand;
    private List<DestinationCard> destinations;
    private int trainCars;
    private PlayerColor color;
    private int score;
    //private EdgeGraph claimedEdges;

    public Player(User user, PlayerColor color){
        this.user = user;
        this.hand = new ArrayList<TrainCard>();
        this.destinations = new ArrayList<DestinationCard>();
        this.trainCars = 0;
        this.color = color;
        this.score = 0;
    }

    public String getName(){
        return this.user.getName();
    }

    public User getUser() {return  this.user;}

    public List<TrainCard> getHand(){
        return this.hand;
    }

    public void drewTrainCards(List<TrainCard> cards){
        this.hand.addAll(cards);
    }

    public void claimedEdge(Edge edge){
        //claimedEdges.add(edge)
        ArrayList<TrainCard> toRemove = new ArrayList<TrainCard>();
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

    public List<DestinationCard> getDestinationCards(){
        return this.destinations;
    }

    public void drewDestinationCards(List<DestinationCard> cards){
        this.destinations.addAll(cards);
    }

    public PlayerColor getColor(){
        return this.color;
    }

    public int getScore(){
        return this.score;
    }

    public void addPoints(int points){
        this.score += points;
    }
    /*
    public EdgeGraph getClaimedEdges(){
        return this.claimedEdges;
    }*/
}
