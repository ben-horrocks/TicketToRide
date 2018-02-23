package cs340.TicketClient.client_common;

import java.util.ArrayList;
import java.util.List;

import common.DataModels.Player;

/**
 * Created by Kavika F.
 */

public class Edge
{
	private City firstCity;
	private City secondCity;
	private EdgeColor color;
	private int length;
	private Player owner = null;

	public Edge(City firstCity, City secondCity, EdgeColor color, int length)
	{
		this.firstCity = firstCity;
		this.secondCity = secondCity;
		this.color = color;
		this.length = length;
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

	public EdgeColor getColor() { return color; }

	public int getLength() { return length; }

	public Player getOwner() { return owner; }

	public void setOwner(Player owner) { this.owner = owner; }

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Edge edge = (Edge) o;

		if (length != edge.length) return false;
		if (!firstCity.equals(edge.firstCity)) return false;
		if (!secondCity.equals(edge.secondCity)) return false;
		return color == edge.color &&
				owner != null ? owner.equals(edge.owner) : edge.owner == null;
	}

	@Override
	public int hashCode()
	{
		int result = firstCity.hashCode();
		result = 31 * result + secondCity.hashCode();
		result = 31 * result + color.hashCode();
		result = 31 * result + length;
		result = 31 * result + (owner != null ? owner.hashCode() : 0);
		return result;
	}
}
