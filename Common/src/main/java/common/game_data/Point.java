package common.game_data;

import java.io.Serializable;

public class Point implements Serializable
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
        routesClaimedPoints += points;
    }

    public int computeFinalPoints()
    {
        return destinationCardPoints + longestPathPoints + routesClaimedPoints;
    }
}
