package common.DataModels;

public class TrainCard
{
  private TrainCardType type;

  public TrainCard(TrainCardType type)
  {
    this.type = type;
  }

  public TrainCardType getType()
  {
    return type;
  }

  public enum TrainCardType
  {
    pink, white, blue, yellow, orange, black, red, green, locomotive
  }
}
