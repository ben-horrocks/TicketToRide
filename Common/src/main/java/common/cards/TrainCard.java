package common.cards;

import java.io.Serializable;

/**
 * A train card. They have a color/type and a "cost".
 */
public class TrainCard implements Serializable
{
    private TrainColor type;
    private int cost;

    public TrainCard(TrainColor type)
    {
        this.type = type;
        cost = type != TrainColor.LOCOMOTIVE ? 1 : 2;
    }

    public TrainColor getType() { return type; }

    public int getCost() { return cost; }

    @Override
    public String toString() { return type + " Train Card"; }
}
