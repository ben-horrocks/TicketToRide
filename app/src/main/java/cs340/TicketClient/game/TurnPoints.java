package cs340.TicketClient.game;

public class TurnPoints
{
	private int points;

	public TurnPoints() { points = 2; }

	public int getPoints() { return points; }

	public void resetPoints() { points = 2; }

	public boolean spendPoints(int toSpend)
	{
		if (toSpend > points)
		{
			return false;
		}
		points -= toSpend;
		return true;
	}
}
