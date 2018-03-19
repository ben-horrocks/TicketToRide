package common.DataModels;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DestinationCardTest
{
    private City city;
    private City city1;
    private City city2;
    private City city3;
    private DestinationCard destinationCard;
    private DestinationCard destinationCard1;
    private DestinationCard testCard;
    private DestinationCard badCard;

    @Before
    public void setUp()
    {
        city = new City(12.21, -900.14, "Los Angeles");
        city1 = new City(14, 14.1, "No-Where");
        city2 = new City(111, 555.2, "I don't care");
        city3 = new City(12.21, 900.14, "Not Los Angeles");
        destinationCard = new DestinationCard(city.getCityName(), city1.getCityName(), 10);
        destinationCard1 = new DestinationCard(city.getCityName(), city2.getCityName(), 20);
        testCard = new DestinationCard(city2.getCityName(), city3.getCityName(), 100);
        badCard = new DestinationCard(city1.getCityName(), city3.getCityName(), 1000);
    }

    @Test
    public void getCity1() throws Exception
    {
        assertEquals(destinationCard.getCity1(), city.getCityName());
        assertEquals(destinationCard1.getCity1(), city.getCityName());
        assertEquals(testCard.getCity1(), city2.getCityName());
        assertEquals(badCard.getCity1(), city1.getCityName());
    }

    @Test
    public void getCity2() throws Exception
    {
        assertEquals(destinationCard.getCity2(), city1.getCityName());
        assertEquals(destinationCard1.getCity2(), city2.getCityName());
        assertEquals(testCard.getCity2(), city3.getCityName());
        assertEquals(badCard.getCity2(), city3.getCityName());
    }

    @Test
    public void getPointValue() throws Exception
    {
        assertEquals(destinationCard.getPointValue(), 10);
        assertEquals(destinationCard1.getPointValue(), 20);
        assertEquals(testCard.getPointValue(), 100);
        assertEquals(badCard.getPointValue(), 1000);
    }

    @Test
    public void testToString() throws Exception
    {
        assertEquals(destinationCard.toString(), city.getCityName() + " to " + city1.getCityName());
        assertEquals(destinationCard1.toString(),
                     city.getCityName() + " to " + city2.getCityName());
        assertEquals(testCard.toString(), city2.getCityName() + " to " + city3.getCityName());
        assertEquals(badCard.toString(), city1.getCityName() + " to " + city3.getCityName());
    }

}