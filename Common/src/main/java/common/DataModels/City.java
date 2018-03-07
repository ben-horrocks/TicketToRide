
package common.DataModels;

import java.io.Serializable;

public class City implements Serializable
{
	private double latitude;
	private double longitude;
	private String cityName;

	public City(double latitude, double longitude, String cityName)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		this.cityName = cityName;
	}

	public String getCityName() { return cityName; }

	public double getLatitude() { return latitude; }

	public double getLongitude() { return longitude; }

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		City city = (City) o;

		if (Double.compare(city.latitude, latitude) != 0) return false;
		if (Double.compare(city.longitude, longitude) != 0) return false;
		return cityName.equals(city.cityName);
	}

	@Override
	public int hashCode()
	{
		int result;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + cityName.hashCode();
		return result;
	}
}