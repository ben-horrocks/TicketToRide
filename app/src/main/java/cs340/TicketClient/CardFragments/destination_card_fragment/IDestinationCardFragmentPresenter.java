package cs340.TicketClient.CardFragments.destination_card_fragment;

import common.DataModels.HandDestinationCards;

public interface IDestinationCardFragmentPresenter {

    HandDestinationCards getDCards();
    void confirmDestinationCards();

}
