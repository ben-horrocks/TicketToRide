package common.DataModels;

import org.junit.Before;
import org.junit.Test;

import common.map.City;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CityTest
{
    private City city;
    private City city1;
    private City city2;

    @Before
    public void setUp()
    {
        double latitude;
        double longitude;
        String cityName;
        latitude = 12.21;
        longitude = -900.15;
        cityName = "Doesn't-Exist-Vile";
        city = new City(latitude, longitude, cityName);
        latitude = 21.12;
        longitude = 15.900;
        cityName = "Doesn't-Matter-Ton";
        city1 = new City(latitude, longitude, cityName);
        city2 = new City(latitude, longitude, cityName);
    }

    @Test
    public void getCityName() throws Exception
    {
        assertEquals(city.getCityName(), "Doesn't-Exist-Vile");
        assertEquals(city1.getCityName(), "Doesn't-Matter-Ton");
        assertEquals(city2.getCityName(), "Doesn't-Matter-Ton");
    }

    @Test
    public void getLatitude() throws Exception
    {
        assertEquals(city.getLatitude(), 12.21, 1);
        assertEquals(city1.getLatitude(), 21.12, 1);
        assertEquals(city2.getLatitude(), 21.12, 1);
    }

    @Test
    public void getLongitude() throws Exception
    {
        assertEquals(city.getLongitude(), -900.15, 3);
        assertEquals(city1.getLongitude(), 15.900, 3);
        assertEquals(city2.getLongitude(), 15.900, 3);
    }

    @Test
    public void equals() throws Exception
    {
        assertNotEquals(city, city1);
        assertNotEquals(city, city2);
        assertEquals(city1, city2);
    }

}