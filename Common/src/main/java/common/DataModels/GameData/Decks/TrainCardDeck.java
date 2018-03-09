/**
 * TrainCardDeck.java
 * Author: Ben Horrocks
 * Last Commit: 4 March, 2018
 * Notes: Model to store Train Card deck and face up cards
 */
package common.DataModels.GameData.Decks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import common.DataModels.TrainCard;
import common.DataModels.TrainColor;

public class TrainCardDeck implements ITrainCardDeck, Serializable
{

  private List<TrainCard> faceUpCards;
  private List<TrainCard> deck;
  private List<TrainCard> discard;
  public TrainCardDeck()
  {
    faceUpCards = new ArrayList<>();
    discard = new ArrayList<>();
    deck = addTrainCards();
    shuffle();
    for(int x=0;x<5;x++)
    {
      faceUpCards.add(x, drawFaceDown());
    }
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
    if(deck.size() == 0)
    {
      addDiscardToDeck();
      shuffle();
    }
    return card;
  }

  @Override
  public void shuffle()
  {
    List<TrainCard> newdeck = new ArrayList<>();
    while(deck.size()>0)
    {
      int randomcard = (int) (Math.random()) % deck.size();
      newdeck.add(deck.get(randomcard));
		deck.remove(randomcard);
    }
    this.deck = newdeck;
  }

  @Override
  public void DiscardCards(List<TrainCard> cards)
  {
    for(TrainCard card : cards)
    {
      discard.add(card);
    }
  }

  @Override
  public List<TrainCard> getFaceUpCards()
  {
    return faceUpCards;
  }

  @Override
  public int getDeckSize()
  {
    return deck.size();
  }

  private void addDiscardToDeck()
  {
    while(discard.size() > 0)
    {
      deck.add(discard.get(0));
      discard.remove(0);
    }
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
