package common.player_info;

public class TrainPieces
{
    private int numTrainPieces;

    public TrainPieces(Boolean bool) {
        if (bool)
            numTrainPieces = 45;
        else
            numTrainPieces = 5;
    }

    public int getNumTrainPieces() { return numTrainPieces; }

    public void useTrainPieces(int numUsed) { numTrainPieces -= numUsed; }
}
