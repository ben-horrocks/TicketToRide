package common.DataModels.GameData.Decks;

import common.DataModels.TrainCard;

public interface ITrainCardDeck
{
  TrainCard drawFaceUp(int index);
  TrainCard drawFaceDown();
  void shuffle();
}
