package cs340.TicketClient.CardFragments;

import common.DataModels.HandDestinationCards;

/**
 * Created by jhens on 3/9/2018.
 */

public interface IDestinationCardFragmentPresenter {

    HandDestinationCards getDCards();
    void confirmDestinationCards();

}
