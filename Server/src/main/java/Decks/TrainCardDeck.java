package Decks;

import java.util.ArrayList;
import java.util.List;

import common.DataModels.TrainCard;
import common.DataModels.TrainColor;

public class TrainCardDeck implements ITrainCardDeck
{

  List<TrainCard> faceUpCards;
  List<TrainCard> deck;

  public TrainCardDeck()
  {
    deck = addTrainCards();
    shuffle();
  }
  @Override
  public TrainCard drawFaceUp(int index)
  {
    TrainCard card = faceUpCards.get(index);
    faceUpCards.remove(index);
    faceUpCards.add(index, drawFaceDown());
    return card;
  }

  @Override
  public TrainCard drawFaceDown()
  {
    TrainCard card = deck.get(0);
    deck.remove(0);
    return card;
  }

  @Override
  public void shuffle()
  {
    List<TrainCard> newdeck = new ArrayList<>();
    int decksize = deck.size();
    for(int i=0; i<decksize; i++)
    {
      int randomcard = (int) (Math.random()) % deck.size();
      newdeck.add(deck.get(randomcard));
      deck.remove(randomcard);
    }
    this.deck = newdeck;
  }

  private List<TrainCard> addTrainCards()
  {
    List<TrainCard> cards = new ArrayList<>();
    int LOCOMOTIVE_CARD_NUMBER = 14, NORMAL_TRAIN_CARD_NUMBER = 12;
    for(int i=0;i<NORMAL_TRAIN_CARD_NUMBER;i++)
    {
      cards.add(new TrainCard(TrainColor.PINK));
    }
    for(int i=0;i<NORMAL_TRAIN_CARD_NUMBER;i++)
    {
      cards.add(new TrainCard(TrainColor.WHITE));
    }
    for(int i=0;i<NORMAL_TRAIN_CARD_NUMBER;i++)
    {
      cards.add(new TrainCard(TrainColor.BLUE));
    }
    for(int i=0;i<NORMAL_TRAIN_CARD_NUMBER;i++)
    {
      cards.add(new TrainCard(TrainColor.YELLOW));
    }
    for(int i=0;i<NORMAL_TRAIN_CARD_NUMBER;i++)
    {
      cards.add(new TrainCard(TrainColor.ORANGE));
    }
    for(int i=0;i<NORMAL_TRAIN_CARD_NUMBER;i++)
    {
      cards.add(new TrainCard(TrainColor.BLACK));
    }
    for(int i=0;i<NORMAL_TRAIN_CARD_NUMBER;i++)
    {
      cards.add(new TrainCard(TrainColor.RED));
    }
    for(int i=0;i<NORMAL_TRAIN_CARD_NUMBER;i++)
    {
      cards.add(new TrainCard(TrainColor.GREEN));
    }
    for(int i=0;i<LOCOMOTIVE_CARD_NUMBER;i++)
    {
      cards.add(new TrainCard(TrainColor.GRAY));
    }
    return cards;
  }
}
