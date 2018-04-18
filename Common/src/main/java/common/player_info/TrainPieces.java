package common.player_info;

import java.io.Serializable;

public class TrainPieces implements Serializable
{
    private int numTrainPieces;

    public TrainPieces() {
            numTrainPieces = 45;
    }

    public TrainPieces(int numTrainPieces) {
        this.numTrainPieces = numTrainPieces;
    }

    public int getNumTrainPieces() { return numTrainPieces; }

    public void useTrainPieces(int numUsed) { numTrainPieces -= numUsed; }

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TrainPieces that = (TrainPieces) o;

		return numTrainPieces == that.numTrainPieces;
	}

	@Override
	public int hashCode()
	{
		return numTrainPieces;
	}
}
