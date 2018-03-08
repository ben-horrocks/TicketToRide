package common.DataModels;

import java.io.Serializable;

public class TrainCard implements Serializable
{
  private TrainColor type;

  public TrainCard(TrainColor type) { this.type = type; }

  public TrainColor getType() { return type; }
}
