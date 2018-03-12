package common.DataModels;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import common.DataModels.GameData.Player;
import common.DataModels.GameData.PlayerColor;

import static org.junit.Assert.*;

public class EdgeTest
{
	private Edge edge1;
	private Edge edge2;
	private Edge redEdge;
	private Edge blueEdge;

	@Before
	public void setUp()
	{
		City city1 = new City(15.51, 12.21, "California City");
		City city2 = new City(1212.4, 11.999, "Nebraska Town");
		edge1 = new Edge(city1, city2, TrainColor.GREEN, 4, false);
		Username username = new Username("tacoMan");
		Password password = new Password("passw0rd");
		ScreenName screenName = new ScreenName("Fighter Boi");
		User user = new User(username, password, screenName);
		Player player = new Player(user, PlayerColor.BLACK);
		edge1.setOwner(player);

		city1 = new City(568, 1245.4, "Whaaa t?");
		city2 = new City(9009.1, 87.43, "bananas.");
		edge2 = new Edge(city1, city2, TrainColor.GRAY, 1, true);

		city1 = new City(41, 14.41, "Almost the Answer");
		city2 = new City(42, 42, "The Answer");
		redEdge = new Edge(city1, city2, TrainColor.RED, 3, false);

		city1 = new City(87, 5124.1, "please");
		city2 = new City(911, 801.42, "popo");
		blueEdge = new Edge(city1, city2, TrainColor.BLUE, 9, false);
	}

	@Test
	public void getFirstCity() throws Exception
	{
		City city1 = new City(15.51, 12.21, "California City");
		assertEquals(edge1.getFirstCity(), city1);
		city1 = new City(568, 1245.4, "Whaaa t?");
		assertEquals(edge2.getFirstCity(), city1);
		city1 = new City(41, 14.41, "Almost the Answer");
		assertEquals(redEdge.getFirstCity(), city1);
		city1 = new City(87, 5124.1, "please");
		assertEquals(blueEdge.getFirstCity(), city1);
	}

	@Test
	public void getSecondCity() throws Exception
	{
		City city2 = new City(1212.4, 11.999, "Nebraska Town");
		assertEquals(edge1.getSecondCity(), city2);
		city2 = new City(9009.1, 87.43, "bananas.");
		assertEquals(edge2.getSecondCity(), city2);
		city2 = new City(42, 42, "The Answer");
		assertEquals(redEdge.getSecondCity(), city2);
		city2 = new City(911, 801.42, "popo");
		assertEquals(blueEdge.getSecondCity(), city2);
	}

	@Test
	public void getCities() throws Exception
	{
		City city1 = new City(15.51, 12.21, "California City");
		City city2 = new City(1212.4, 11.999, "Nebraska Town");
		List<City> cities = new ArrayList<>();
		cities.add(city1);
		cities.add(city2);
		assertEquals(edge1.getCities(), cities);
		cities.clear();

		city1 = new City(568, 1245.4, "Whaaa t?");
		city2 = new City(9009.1, 87.43, "bananas.");
		cities.add(city1);
		cities.add(city2);
		assertEquals(edge2.getCities(), cities);
		cities.clear();

		city1 = new City(41, 14.41, "Almost the Answer");
		city2 = new City(42, 42, "The Answer");
		cities.add(city1);
		cities.add(city2);
		assertEquals(redEdge.getCities(), cities);
		cities.clear();

		city1 = new City(87, 5124.1, "please");
		city2 = new City(911, 801.42, "popo");
		cities.add(city1);
		cities.add(city2);
		assertEquals(blueEdge.getCities(), cities);
		cities.clear();
	}

	@Test
	public void getColor() throws Exception
	{
		assertEquals(edge1.getColor(), TrainColor.GREEN);
		assertEquals(edge2.getColor(), TrainColor.GRAY);
		assertEquals(redEdge.getColor(), TrainColor.RED);
		assertEquals(blueEdge.getColor(), TrainColor.BLUE);
	}

	@Test
	public void getLength() throws Exception
	{
		assertEquals(edge1.getLength(), 4);
		assertEquals(edge2.getLength(), 1);
		assertEquals(redEdge.getLength(), 3);
		assertEquals(blueEdge.getLength(), 9);
	}

	@Test
	public void getOwner() throws Exception
	{
		Username username = new Username("tacoMan");
		Password password = new Password("passw0rd");
		ScreenName screenName = new ScreenName("Fighter Boi");
		User user = new User(username, password, screenName);
		Player player = new Player(user, PlayerColor.BLACK);
		assertEquals(edge1.getOwner(), username);
		edge2.setOwner(player);
		assertEquals(edge2.getOwner(), username);
		assertEquals(redEdge.getOwner(), null);
		redEdge.setOwner(player);
		assertEquals(redEdge.getOwner(), username);
		assertEquals(blueEdge.getOwner(), null);
	}

	@Test
	public void setOwner() throws Exception
	{
		Username username = new Username("tacoMan");
		Password password = new Password("passw0rd");
		ScreenName screenName = new ScreenName("Fighter Boi");
		User user = new User(username, password, screenName);
		Player player = new Player(user, PlayerColor.BLACK);
		assertEquals(edge1.getOwner(), username);
		edge2.setOwner(player);
		assertEquals(edge2.getOwner(), username);
		assertEquals(redEdge.getOwner(), null);
		redEdge.setOwner(player);
		assertEquals(redEdge.getOwner(), username);
		assertEquals(blueEdge.getOwner(), null);
	}

	@Test
	public void isClaimed() throws Exception
	{
		assertEquals(edge1.isClaimed(), true);
		Username username = new Username("tacoMan");
		Password password = new Password("passw0rd");
		ScreenName screenName = new ScreenName("Fighter Boi");
		User user = new User(username, password, screenName);
		Player player = new Player(user, PlayerColor.BLACK);
		edge2.setOwner(player);
		assertEquals(edge2.isClaimed(), true);
		assertEquals(redEdge.isClaimed(), false);
		redEdge.setOwner(player);
		assertEquals(redEdge.isClaimed(), true);
		assertEquals(blueEdge.isClaimed(), false);
	}

	@Test
	public void isDoubleEdge() throws Exception
	{
		assertEquals(edge1.isDoubleEdge(), false);
		assertEquals(edge2.isDoubleEdge(), true);
		assertEquals(redEdge.isDoubleEdge(), false);
		assertEquals(blueEdge.isDoubleEdge(), false);
	}

	@Test
	public void equals() throws Exception
	{
		assertNotEquals(edge1, edge2);
		assertNotEquals(edge1, redEdge);
		assertNotEquals(edge1, blueEdge);
		assertNotEquals(edge2, redEdge);
		assertNotEquals(edge2, blueEdge);
		assertNotEquals(redEdge, blueEdge);
	}

	@Test
	public void testToString() throws Exception
	{
		String expected = "Owner: Username: tacoMan\n" +
				"From California City to Nebraska Town\n" +
				"Length: 4. Color: GREEN";
		assertEquals(edge1.toString(), expected);

		expected = "Owner: null\n" +
				"From Whaaa t? to bananas.\n" +
				"Length: 1. Color: GRAY";
		assertEquals(edge2.toString(), expected);

		expected = "Owner: null\n" +
				"From Almost the Answer to The Answer\n" +
				"Length: 3. Color: RED";
		assertEquals(redEdge.toString(), expected);

		expected = "Owner: null\n" +
				"From please to popo\n" +
				"Length: 9. Color: BLUE";
		assertEquals(blueEdge.toString(), expected);
	}
}