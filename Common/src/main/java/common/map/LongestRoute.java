package common.map;

import java.util.ArrayList;
import java.util.List;

import common.player_info.Username;

public class LongestRoute
{
    List<Username> longestRouteOwners = new ArrayList<>();
    int length = 0;

    public void setLongestRoute(List<Username> users, int length) {
        this.longestRouteOwners.clear();
        this.longestRouteOwners.addAll(users);
        this.length = length;
    }

    public List<Username> getLongestRouteOwners()
    {
        return longestRouteOwners;
    }

    public int getLength()
    {
        return length;
    }
}
