package cs340.TicketClient.CardFragments;


import java.util.ArrayList;

import common.DataModels.DestinationCard;
import common.DataModels.TrainCard;
import cs340.TicketClient.Game.GameModel;

public class HandFragmentPresenter {

    private HandFragment fragment;
    private GameModel model;

    HandFragmentPresenter(HandFragment fragment)
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
