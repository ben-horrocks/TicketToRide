package common.DataModels;

/**
 * Created by Ryan on 2/6/2018.
 */

public class GameInfo {
    private GameID id;
    private String name;
    private String creatorName;
    private Player[] players;

    public GameInfo(Game g){
        this.id = g.getId();
        this.name = g.getName();
        this.creatorName = g.getCreatorName();
        this.players = (Player[]) g.getPlayers().toArray();
    }

    public GameInfo(GameID id, String name, String creatorName, Player[] players) {
        this.id = id;
        this.name = name;
        this.creatorName = creatorName;
        this.players = players;
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

    public int getPlayerCount(){ return this.players.length; }

    public boolean isFull() {
        return players.length < 5;
    }
}
