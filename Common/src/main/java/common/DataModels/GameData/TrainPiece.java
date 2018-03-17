package common.DataModels.GameData;

public class TrainPiece
{
  private int numTrainPieces = 45;

  public int getNumTrainPieces()
  {
    return numTrainPieces;
  }

  public void useTrainPieces(int numUsed)
  {
    numTrainPieces -= numUsed;
  }
}
