package common.DataModels.GameData.Decks;

import java.util.List;

import common.DataModels.DestinationCard;

public interface IDestinationCardDeck
{
  List<DestinationCard> draw();
  void shuffle();
  void putBackInDeck(List<DestinationCard> cards);
  int getDeckSize();
}
