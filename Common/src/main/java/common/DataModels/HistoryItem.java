package common.DataModels;

import java.io.Serializable;

import common.CommandParams;
import common.DataModels.GameData.Player;
import common.IServer;

/**
 * Created by Carter on 3/6/18.
 */

public class HistoryItem implements Serializable{

    private GameID game;
    private String action;
    private boolean reportable;

    public HistoryItem(CommandParams action) {
        reportable = true;
        this.action = parseCommand(action);
    }

    public boolean shouldReport(){
        return reportable;
    }

    public String getAction() {
        return action;
    }

    public GameID getGame() {return game;}

    private String parseCommand(CommandParams cmd){
        StringBuilder action = new StringBuilder();
        String commandName = cmd.getMethodName();
        Object[] params = cmd.getParameters();
        //the curly braces are to separate scope, they are on purpose.
        switch(commandName){
            case IServer.START_GAME_METHOD: {
                game = (GameID) params[0];
                action.append("Game: ");
                action.append(game.getId());
                action.append(" started!");
                break;
            }
            case IServer.SEND_CHAT_METHOD: {
                game = (GameID) params[0];
                ChatItem chat = (ChatItem) params[1];
                action.append(chat.getPlayerName());
                action.append(" said \"");
                action.append(chat.getMessage());
                action.append('\"');
                break;
            }
            case IServer.RETURN_DEST_CARDS_METHOD: {
                game = (GameID) params[0];
                Username user = (Username) params[1];
                HandDestinationCards selected = (HandDestinationCards) params[2];
                HandDestinationCards returned = (HandDestinationCards) params[3];
                action.append(user.getName());
                action.append(" drew ");
                action.append(selected.size());
                action.append(" destination cards");
                break;
            }
            case IServer.DRAW_DECK_METHOD: {
                game = (GameID) params[0];
                Username user = (Username) params[1];
                action.append(user.getName());
                action.append(" drew a card from the deck");
                break;
            }
            case IServer.DRAW_FACE_UP_METHOD: {
                game = (GameID) params[0];
                Username user = (Username) params[1];
                int index = (int) params[2];
                action.append(user.getName());
                action.append(" drew card ");
                action.append(index);
                action.append(" from the face up cards");
                break;
            }
            case IServer.CLAIM_EDGE_METHOD: {
                game = (GameID) params[0];
                Username user = (Username) params[1];
                Edge edge = (Edge) params[2];
                action.append(user.getName());
                action.append(" claimed the ");
                action.append(edge.getColor());
                action.append(" edge from ");
                action.append(edge.getFirstCity().getCityName());
                action.append(" to ");
                action.append(edge.getSecondCity().getCityName());
                break;
            }
            default:
                action.append("Unrecognized Command: ");
                action.append(commandName);
                reportable = false;
                game = null;
                break;
        }
        return action.toString();
    }
}
