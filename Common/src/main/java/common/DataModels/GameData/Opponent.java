package common.DataModels.GameData;

import java.util.List;

import common.DataModels.*;

/**
 * Created by Vibro on 2/28/2018.
 */

public class Opponent {
    private User user;
    private int numberHandCards;
    private int numberDestinationCards;
    private PlayerColor color;
    private int score;
    //private EdgeGraph claimedEdges;

    public Opponent(User user, PlayerColor color){
        this.user = user;
        this.numberHandCards = 0;
        this.numberDestinationCards = 0;
        this.color = color;
        this.score = 0;
        //this.claimedEdges = new EdgeGraph();
    }

    String getName() {
        return user.getName();
    }

    public int getNumberHandCards(){
        return numberHandCards;
    }

    public void addHandCard(int amount){
        this.numberHandCards += amount;
    }

    public void removeHandCard(int amount){
        this.numberHandCards -= amount;
    }

    public int getDestinationCardCount(){
        return this.numberDestinationCards;
    }

    public void addDestinationCard(int amount){
        this.numberDestinationCards += amount;
    }

    public void removeDestinationCard(int amount){
        this.numberDestinationCards -= amount;
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
        return this.clamedEdges;
    }*/
}
