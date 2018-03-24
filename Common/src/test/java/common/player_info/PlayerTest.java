package common.player_info;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import common.cards.TrainColor;
import common.map.City;
import common.map.Edge;

public class PlayerTest
{

    List<City> cities = new ArrayList<>();
    Player player;
    @Before
    public void setup()
    {
        player = new Player(new User(new Username("ben"), new Password("ben")), PlayerColor.BLACK);
        cities.add(new City(0,0,"a"));
        cities.add(new City(0,0,"b"));
        cities.add(new City(0,0,"c"));
        cities.add(new City(0,0,"d"));
        cities.add(new City(0,0,"e"));
        cities.add(new City(0,0,"f"));
        cities.add(new City(0,0,"g"));
        cities.add(new City(0,0,"h"));
        cities.add(new City(0,0,"i"));
    }

    @Test
    public void testLongestContinuousPath()
    {
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
    }

    @Test
    public void test1()
    {
        player.getClaimedEdges().addEdge(new Edge(cities.get(0), cities.get(1), TrainColor.BLACK, 5, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(1), cities.get(2), TrainColor.BLACK, 5, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(2), cities.get(3), TrainColor.BLACK, 5, false));
        int path = player.computeLongestPath();
        assert(path == 15);
    }

    @Test
    public void test2()
    {
        player.getClaimedEdges().addEdge(new Edge(cities.get(0), cities.get(1), TrainColor.BLACK, 5, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(1), cities.get(2), TrainColor.BLACK, 4, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(2), cities.get(3), TrainColor.BLACK, 3, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(1), cities.get(4), TrainColor.BLACK, 4, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(4), cities.get(5), TrainColor.BLACK, 3, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(5), cities.get(6), TrainColor.BLACK, 2, false));
        int path = player.computeLongestPath();
        assert(path == 14);
    }

    @Test
    public void test3()
    {
        player.getClaimedEdges().addEdge(new Edge(cities.get(0), cities.get(1), TrainColor.BLACK, 5, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(1), cities.get(2), TrainColor.BLACK, 5, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(2), cities.get(3), TrainColor.BLACK, 5, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(1), cities.get(4), TrainColor.BLACK, 4, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(4), cities.get(5), TrainColor.BLACK, 3, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(5), cities.get(6), TrainColor.BLACK, 2, false));
        int path = player.computeLongestPath();
        assert(path == 15);
    }

    @Test
    public void test4()
    {
        player.getClaimedEdges().addEdge(new Edge(cities.get(0), cities.get(1), TrainColor.BLACK, 5, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(1), cities.get(2), TrainColor.BLACK, 5, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(2), cities.get(3), TrainColor.BLACK, 5, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(3), cities.get(4), TrainColor.BLACK, 5, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(4), cities.get(5), TrainColor.BLACK, 5, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(5), cities.get(0), TrainColor.BLACK, 5, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(2), cities.get(6), TrainColor.BLACK, 5, false));
        int path = player.computeLongestPath();
        assert(path == 35);
    }

    @Test
    public void test5()
    {
        player.getClaimedEdges().addEdge(new Edge(cities.get(0), cities.get(1), TrainColor.BLACK, 5, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(1), cities.get(2), TrainColor.BLACK, 5, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(1), cities.get(4), TrainColor.BLACK, 5, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(1), cities.get(5), TrainColor.BLACK, 5, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(2), cities.get(3), TrainColor.BLACK, 5, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(3), cities.get(4), TrainColor.BLACK, 5, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(5), cities.get(6), TrainColor.BLACK, 5, false));
        int path = player.computeLongestPath();
        assert(path == 35);
    }

    @Test
    public void test6()
    {
        player.getClaimedEdges().addEdge(new Edge(cities.get(0), cities.get(1), TrainColor.BLACK, 1, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(0), cities.get(2), TrainColor.BLACK, 1, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(1), cities.get(3), TrainColor.BLACK, 1, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(2), cities.get(3), TrainColor.BLACK, 1, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(2), cities.get(4), TrainColor.BLACK, 1, false));
        player.getClaimedEdges().addEdge(new Edge(cities.get(3), cities.get(4), TrainColor.BLACK, 1, false));
        int path = player.computeLongestPath();
        assert(path == 6);
    }
}
