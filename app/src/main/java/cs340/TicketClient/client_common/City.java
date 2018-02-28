package cs340.TicketClient.client_common;

import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Kavika F.
 */

public class City
{
	private MarkerOptions coordinates;
	private String cityName;

	public City(MarkerOptions coordinates, String cityName)
	{
		this.coordinates = coordinates;
		this.cityName = cityName;
	}

	public MarkerOptions getCoordinates() { return coordinates; }

	public String getCityName() { return cityName; }

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		City city = (City) o;

		return coordinates.equals(city.coordinates) && cityName.equals(city.cityName);
	}

	@Override
	public int hashCode()
	{
		int result = coordinates.hashCode();
		result = 31 * result + cityName.hashCode();
		return result;
	}
}
