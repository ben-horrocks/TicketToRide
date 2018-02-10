package common;

import java.util.List;
import java.util.Set;

import common.DataModels.Game;
import common.DataModels.GameID;
import common.DataModels.GameInfo;
import common.DataModels.Signal;

public interface IClient {

    Signal updateGameList(List<GameInfo> gameList);
    Signal addGame(GameInfo game);
    Signal removeGame(GameID id);
}
