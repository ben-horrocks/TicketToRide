package cs340.TicketClient.card_fragments.deck_fragment;

import android.util.SparseArray;

import java.util.ArrayList;

import common.cards.TrainCard;
import common.cards.TrainColor;
import cs340.TicketClient.game.GameModel;
import cs340.TicketClient.R;

class DeckFragmentPresenter
{

    private DeckFragment fragment;
    private GameModel model;

    DeckFragmentPresenter(DeckFragment fragment)
    {
        this.fragment = fragment;
        this.model = GameModel.getInstance();
    }

    SparseArray<TrainCard> getFaceUpCards()
    {
        ArrayList<TrainCard> faceUp =
                (ArrayList<TrainCard>) GameModel.getInstance().getGameData().getFaceUp();
        SparseArray<TrainCard> graphics = new SparseArray<>();
        for (TrainCard t : faceUp)
        {
            switch (t.getType())
            {
                case RED:
                    graphics.append(R.drawable.traincard_red, new TrainCard(TrainColor.RED));
                    break;
                case BLUE:
                    graphics.append(R.drawable.traincard_blue, new TrainCard(TrainColor.BLUE));
                    break;
                case GRAY:
                    graphics.append(R.drawable.traincard_locomotive, new TrainCard(TrainColor.LOCOMOTIVE));
                    break;
                case PINK:
                    graphics.append(R.drawable.traincard_pink, new TrainCard(TrainColor.PINK));
                    break;
                case BLACK:
                    graphics.append(R.drawable.traincard_black, new TrainCard(TrainColor.BLACK));
                    break;
                case GREEN:
                    graphics.append(R.drawable.traincard_green, new TrainCard(TrainColor.GREEN));
                    break;
                case WHITE:
                    graphics.append(R.drawable.traincard_white, new TrainCard(TrainColor.WHITE));
                    break;
                case ORANGE:
                    graphics.append(R.drawable.traincard_orange, new TrainCard(TrainColor.ORANGE));
                    break;
                case YELLOW:
                    graphics.append(R.drawable.traincard_yellow, new TrainCard(TrainColor.YELLOW));
                    break;
                default:
                    System.out.println("No TrainCardImage for that Color");
                    break;
            }
        }
        return graphics;
    }

    boolean isMyTurn() { return model.isMyTurn(); }

    boolean spendTurnPoints(int toSpend) { return model.spendTurnPoints(toSpend); }
}
