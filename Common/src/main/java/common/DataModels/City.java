<<<<<<< HEAD
package common.DataModels;

/**
 * Created by Kavika F.
 */

public class City
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
=======
package common.DataModels;

public enum City
{
  Los_Angeles, New_York_City, Duluth, Houston, Sault_Saint_Marie, Nashville, New_York, Atlanta,
  Portland, Vancouver, Montreal, El_Paso, Toronto, Miami, Phoenix, Dallas, Calgary, Salt_Lake_City,
  Pheonix, Chicago, New_Orleans
}
>>>>>>> f873fa080879db442ee0fd99e573187b63b28eff
