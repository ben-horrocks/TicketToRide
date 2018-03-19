package cs340.TicketClient.CardFragments.hand_fragment;

import common.DataModels.HandDestinationCards;
import common.DataModels.HandTrainCards;

public interface IHandFragmentPresenter
{

    HandTrainCards getPlayerTrainCards();

    HandDestinationCards getPlayerDestinationCards();
}
