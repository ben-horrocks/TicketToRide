package common.DataModels.GameData.Decks;

import common.DataModels.TrainCard;

public interface ITrainCardDeck
{
  public TrainCard drawFaceUp(int index);
  public TrainCard drawFaceDown();
  public void shuffle();
}
