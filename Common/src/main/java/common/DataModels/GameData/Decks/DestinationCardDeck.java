package common.DataModels.GameData.Decks;

import java.util.ArrayList;
import java.util.List;

import common.DataModels.DestinationCard;

public class DestinationCardDeck implements IDestinationCardDeck
{
  private List<DestinationCard> deck;
  public DestinationCardDeck() {
    this.deck = addDestinationCards();
    shuffle();
  }

  @Override
  public List<DestinationCard> draw()
  {
    return null;
  }

  @Override
  public void shuffle()
  {
    List<DestinationCard> newdeck = new ArrayList<>();
    int decksize = deck.size();
    for(int i=0; i<decksize; i++)
    {
      int randomcard = (int) (Math.random()) % deck.size();
      newdeck.add(deck.get(randomcard));
      deck.remove(randomcard);
    }
    this.deck = newdeck;
  }

  private List<DestinationCard> addDestinationCards()
  {
    List<DestinationCard> newdeck = new ArrayList<>();
    //Add destination cards here
    return newdeck;
  }
}
