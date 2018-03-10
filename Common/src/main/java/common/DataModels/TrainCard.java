package common.DataModels;

import java.io.Serializable;

/**
 * A train card. They only have a color/type. No other important information.
 */
public class TrainCard implements Serializable
{
  private TrainColor type;

  public TrainCard(TrainColor type) { this.type = type; }

  public TrainColor getType() { return type; }
}
