package common;

import java.util.Set;

import common.DataModels.Game;
import common.DataModels.Signal;

public interface IClient {

    Signal updateGameList(Set<Game> gameList);
}
