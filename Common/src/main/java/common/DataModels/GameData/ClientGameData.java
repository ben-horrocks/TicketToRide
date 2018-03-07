package common.DataModels.GameData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import common.DataModels.ChatItem;
import common.DataModels.DestinationCard;
import common.DataModels.Edge;
import common.DataModels.EdgeGraph;
import common.DataModels.GameID;
import common.DataModels.HistoryItem;
import common.DataModels.TrainCard;
import common.DataModels.Username;

public class ClientGameData implements IGameData, Serializable
{

    private GameID id;
    private Player player;
	private List<Opponent> opponents;
	private EdgeGraph gameboard;
	private List<TrainCard> faceUp;
	private List<HistoryItem> history;
	private List<ChatItem> chat;

    public ClientGameData(ServerGameData game, Username username){
        this.id = game.getId();
        this.opponents = new ArrayList<Opponent>();
        for(Player p: game.getPlayers()){
        	if (p.getUser().getUsername().equals(username))
			{
				this.player = p;
			}
			else
			{
				opponents.add(new Opponent(p));
			}
        }
        this.gameboard = game.getGameBoard();
        this.faceUp = game.getFaceUpCards();
        this.history = game.getHistory();
        this.chat = new ArrayList<>();
    }

    public void edgeClaimed(Edge edge)
	{

	}

    private Opponent getOpponent(Username username){
        for(Opponent o: opponents){
            if(o.getUsername().equals(username))
                return o;
        }
        return null;
    }

    @Override
    public void deckDraw(Username username, List<TrainCard> drawn) {
        Opponent o = getOpponent(username);
        if(o != null)
            o.addHandCard(drawn.size());
    }

    @Override
    public void faceUpDraw(Username username, List<TrainCard> drawn, List<TrainCard> replacements){
    ArrayList<Integer> toRemove = new ArrayList<>();
        for(int i=0; i<drawn.size(); i++){
            for(int j=0; j<faceUp.size(); j++){
                if(faceUp.get(j).getType() == drawn.get(i).getType()){
                    faceUp.remove(j);
                    getOpponent(username).addHandCard(1);
                    faceUp.add(replacements.get(i));
                }
            }
        }
    }

    @Override
    public void destinationDraw(Username username, List<DestinationCard> drawn){
        getOpponent(username).addDestinationCards(drawn.size());
    }

    @Override
    public void addHistoryItem(HistoryItem event){
        this.history.add(event);
    }

    @Override
    public void addChatMessage(ChatItem m){
        this.chat.add(m);
    }

}
