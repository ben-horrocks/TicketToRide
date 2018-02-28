package Decks;

import java.util.List;

import common.DataModels.DestinationCard;

public interface IDestinationCardDeck
{
  public List<DestinationCard> draw();
  public void shuffle();

}
