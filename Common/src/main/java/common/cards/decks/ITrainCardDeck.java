package common.cards.decks;

import java.util.List;

import common.cards.TrainCard;

public interface ITrainCardDeck
{
    TrainCard drawFaceUp(int index);

    TrainCard drawFaceDown();

    void shuffle();

    void DiscardCards(List<TrainCard> cards);

    List<TrainCard> getFaceUpCards();

    int size();
}
