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

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TrainCard trainCard = (TrainCard) o;

		return type == trainCard.type;
	}

	@Override
	public int hashCode()
	{
		return type.hashCode();
	}
}
