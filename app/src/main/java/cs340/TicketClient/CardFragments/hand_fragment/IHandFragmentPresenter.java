package cs340.TicketClient.CardFragments.hand_fragment;

import common.cards.HandDestinationCards;
import common.cards.HandTrainCards;

public interface IHandFragmentPresenter
{

    HandTrainCards getPlayerTrainCards();

    HandDestinationCards getPlayerDestinationCards();
}
