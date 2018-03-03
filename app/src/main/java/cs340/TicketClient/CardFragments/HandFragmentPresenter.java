package cs340.TicketClient.CardFragments;


import java.util.ArrayList;

import common.DataModels.DestinationCard;
import common.DataModels.TrainCard;
import cs340.TicketClient.Game.GameModel;

/**
 * Created by jhens on 3/1/2018.
 */

public class HandFragmentPresenter {

    private HandFragment fragment;
    private GameModel model;

    public HandFragmentPresenter(HandFragment fragment)
    {
        this.fragment = fragment;
        model = null;
    }

    ArrayList<TrainCard> getPlayerTrainCards()
    {
        ArrayList<TrainCard> cards = null;
        //TODO get player's train cards from GameModel.ClientGame.Player
        return cards;
    }

    ArrayList<DestinationCard> getPlayerDestinationCards()
    {
        //TODO get player's destination cards from GameModel.ClientGame.Player
        return null;
    }
}
