package common.game_data;

import java.io.Serializable;

import common.cards.DestinationCard;
import common.cards.HandDestinationCards;

public class Point implements Serializable
{
    private int destinationCardPoints = 0;
    private int longestPathPoints = 0;
    private int routesClaimedPoints = 0;
    private int incompleteDestincationCardPoints;

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

    public int getIncompleteDestincationCardPoints()
    {
        return incompleteDestincationCardPoints;
    }

    public void setDestinationPoints(HandDestinationCards cards)
    {
        for (DestinationCard card : cards.toArray())
        {
            if (card.isComplete())
            {
                destinationCardPoints += card.getPointValue();
            }
            else
                incompleteDestincationCardPoints -= card.getPointValue();
        }
    }

    public void incrementRoutesClaimed(int points)
    {
        routesClaimedPoints += points;
    }

    public int computeFinalPoints()
    {
        return destinationCardPoints + longestPathPoints + routesClaimedPoints + incompleteDestincationCardPoints;
    }
}
