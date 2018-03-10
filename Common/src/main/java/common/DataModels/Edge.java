package common.DataModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A route between two cities.
 */
public class Edge implements Serializable
{
	private City firstCity;
	private City secondCity;
	private TrainColor color;
	private int length;
	private User owner = null;
	private boolean isDoubleEdge;

	/**
	 * @pre All parameters must be non-null.
	 * @post A valid Edge object.
	 * @param firstCity The "starting" city.
	 * @param secondCity The "destination" city.
	 * @param color The color of the edge.
	 * @param length The distance between the two cities (as defined by the game).
	 * @param isDoubleEdge Whether or not there is another edge between the same cities.
	 */
	public Edge(City firstCity, City secondCity, TrainColor color, int length, boolean isDoubleEdge)
	{
		this.firstCity = firstCity;
		this.secondCity = secondCity;
		this.color = color;
		this.length = length;
		this.isDoubleEdge = isDoubleEdge;
	}

	public City getFirstCity() { return firstCity; }

	public City getSecondCity() { return secondCity; }

	public List<City> getCities()
	{
		List<City> cities = new ArrayList<>();
		cities.add(firstCity);
		cities.add(secondCity);
		return cities;
	}

	public TrainColor getColor() { return color; }

	public int getLength() { return length; }

	public User getOwner() { return owner; }

	public void setOwner(User owner) { this.owner = owner; }

	public boolean isClaimed() { return owner != null; }

	public boolean isDoubleEdge() { return isDoubleEdge; }

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Edge edge = (Edge) o;

		if (length != edge.length) return false;
		if (isDoubleEdge != edge.isDoubleEdge) return false;
		if (!firstCity.equals(edge.firstCity)) return false;
		if (!secondCity.equals(edge.secondCity)) return false;
		if (color != edge.color) return false;
		return owner.equals(edge.owner);
	}

	@Override
	public int hashCode()
	{
		int result = firstCity.hashCode();
		result = 31 * result + secondCity.hashCode();
		result = 31 * result + color.hashCode();
		result = 31 * result + length;
		result = 31 * result + (isDoubleEdge ? 1 : 0);
		return result;
	}

	@Override
	public String toString()
	{
		return "Owner: " + owner + "\n" +
				"From " + firstCity.getCityName() + " to " + secondCity.getCityName() + "\n" +
				"Length: " + length + ". Color: " + color;
	}
}
