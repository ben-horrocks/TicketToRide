package common.DataModels.GameData;

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

    public String getName(){
        return this.user.getName();
    }

    public List<TrainCard> getHand(){
        return this.hand;
    }

    public void drewTrainCards(List<TrainCard> cards){
        this.hand.addAll(cards);
    }

    public void claimedEdge(Edge edge){
        //claimedEdges.add(edge)
        //TODO comb through the hand and remove the appropriate cards for claiming the edge
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
