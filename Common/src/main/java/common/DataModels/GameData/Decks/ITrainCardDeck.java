package common.DataModels.GameData.Decks;

import java.util.List;

import common.DataModels.TrainCard;

public interface ITrainCardDeck
{
  TrainCard drawFaceUp(int index);
  TrainCard drawFaceDown();
  void shuffle();
  void DiscardCards(List<TrainCard> cards);
  List<TrainCard> getFaceUpCards();
}
