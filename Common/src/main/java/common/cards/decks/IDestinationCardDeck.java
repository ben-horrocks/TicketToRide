package common.cards.decks;

import java.util.List;

import common.cards.DestinationCard;

public interface IDestinationCardDeck
{
    List<DestinationCard> draw();

    void shuffle();

    void putBackInDeck(List<DestinationCard> cards);

    int getDeckSize();
}
