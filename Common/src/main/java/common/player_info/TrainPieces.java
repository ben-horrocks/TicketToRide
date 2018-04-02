package common.player_info;

import java.io.Serializable;

public class TrainPieces implements Serializable
{
    private int numTrainPieces;

    public TrainPieces(int num) {
        numTrainPieces = num;
    }

    public int getNumTrainPieces() { return numTrainPieces; }

    public void useTrainPieces(int numUsed) { numTrainPieces -= numUsed; }
}
