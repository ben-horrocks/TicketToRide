package cs340.TicketClient.CardFragments.hand_fragment;


import common.cards.HandDestinationCards;
import common.cards.HandTrainCards;
import cs340.TicketClient.Game.GameModel;

public class HandFragmentPresenter implements IHandFragmentPresenter
{

    private HandFragment fragment;
    private GameModel model;

    HandFragmentPresenter(HandFragment fragment)
    {
        this.fragment = fragment;
        model = null;
    }

    public HandTrainCards getPlayerTrainCards()
    {
        return GameModel.getInstance().getPlayer().getHand();
    }

    public HandDestinationCards getPlayerDestinationCards()
    {
        return GameModel.getInstance().getGameData().getPlayer().getDestinationCards();
    }
}
