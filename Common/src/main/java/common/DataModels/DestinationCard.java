package common.DataModels;

import java.io.Serializable;

/**
 * A destination card.
 */
public class DestinationCard implements Serializable
{
    private String city1, city2;
    private int pointValue;

    /**
     * @param city1  The name of the "start" city.
     * @param city2  The name of the "destination" city.
     * @param points The amount of points this route is worth.
     * @pre All parameters must be non-null.
     * @post A valid DestinationCard object.
     */
    public DestinationCard(String city1, String city2, int points)
    {
        this.city1 = city1;
        this.city2 = city2;
        this.pointValue = points;
    }

    public String getCity1()
    {
        return city1;
    }

    public String getCity2()
    {
        return city2;
    }

    public int getPointValue()
    {
        return pointValue;
    }

    @Override
    public String toString()
    {
        return city1 + " to " + city2;
    }
}