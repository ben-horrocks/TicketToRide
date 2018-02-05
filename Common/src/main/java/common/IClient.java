package common;

import java.util.Set;

import common.DataModels.Game;

/**
 * Created by Ben_D on 1/29/2018.
 */

public interface IClient {

    public boolean updateGameList(Set<Game> gameList);
}
