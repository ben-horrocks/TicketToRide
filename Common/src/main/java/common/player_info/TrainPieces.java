package common.player_info;

public class TrainPieces
{
    private int numTrainPieces;

    public TrainPieces() { numTrainPieces = 45; }

    public int getNumTrainPieces() { return numTrainPieces; }

    public void useTrainPieces(int numUsed) { numTrainPieces -= numUsed; }
}
