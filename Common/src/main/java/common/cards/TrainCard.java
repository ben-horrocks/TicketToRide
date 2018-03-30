package common.cards;

import java.io.Serializable;

/**
 * A train card. They have a color/type and a "cost".
 */
public class TrainCard implements Serializable
{
    private TrainColor type;

    public TrainCard(TrainColor type)
    {
        this.type = type;
    }

    public TrainColor getType() { return type; }

    @Override
    public String toString() { return type + " Train Card"; }
}
