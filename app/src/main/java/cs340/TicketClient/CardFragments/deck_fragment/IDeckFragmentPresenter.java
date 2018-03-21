package cs340.TicketClient.CardFragments.deck_fragment;

import java.util.ArrayList;

public interface IDeckFragmentPresenter
{

    ArrayList<Integer> getFaceUpCards();

    /**
     * This method draws the specified faceup card and adds it to the player's hand
     * @pre cardNumber is between 1 and 5
     * @post card will be added to player
     * @post new traincard will be put faceup
     * @param cardNumber card number that was picked from view
     */
    void DrawFaceUp(int cardNumber);

    /**
     * This method draws the top card of the deck
     * @pre deck is not empty
     * @post card will be added to player
     */
    void DrawDeck();

}
