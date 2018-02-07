package common.DataModels;

/**
 * Created by Ryan on 2/6/2018.
 */

public class GameInfo {
    private GameID id;
    private String name;
    private String creatorName;
    private int playerCount;

    public GameInfo(GameID id, String name, String creatorName, int playerCount){
        this.id = id;
        this.name = name;
        this.creatorName = creatorName;
        this.playerCount = playerCount;
    }

    public GameID getID(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getCreatorName() {
        return this.creatorName;
    }

    public int getPlayerCount(){
        return this.playerCount;
    }

    public boolean isFull() {
        return playerCount < 5;
    }
}
