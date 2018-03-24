package common.game_data;

public class Point
{
    private int destinationCardPoints = 0;
    private int longestPathPoints = 0;
    private int routesClaimedPoints = 0;

    public int getDestinationCardPoints()
    {
        return destinationCardPoints;
    }

    public int getLongestPathPoints()
    {
        return longestPathPoints;
    }

    public int getRoutesClaimedPoints()
    {
        return routesClaimedPoints;
    }

    public void incrementRoutesClaimed(int points)
    {
        routesClaimedPoints += routesClaimedPoints;
    }

    public int computeFinalPoints()
    {
        return destinationCardPoints + longestPathPoints + routesClaimedPoints;
    }
}
