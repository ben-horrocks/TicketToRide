package cs340.TicketClient.CardFragments.deck_fragment;

import java.util.ArrayList;

import common.DataModels.TrainCard;
import cs340.TicketClient.Game.GameModel;
import cs340.TicketClient.R;

/**
 * Created by jhens on 3/1/2018.
 */

public class DeckFragmentPresenter implements IDeckFragmentPresenter
{

    private DeckFragment fragment;
    private GameModel model;

    public DeckFragmentPresenter(DeckFragment fragment)
    {
        this.fragment = fragment;
        this.model = null;
    }

    @Override
    public ArrayList<Integer> getFaceUpCards()
    {
        ArrayList<TrainCard> faceUp =
                (ArrayList<TrainCard>) GameModel.getInstance().getGameData().getFaceUp();
        ArrayList<Integer> graphics = new ArrayList<>();
        for (TrainCard t : faceUp)
        {
            switch (t.getType())
            {
                case RED:
                    graphics.add(R.drawable.traincard_red);
                    break;
                case BLUE:
                    graphics.add(R.drawable.traincard_blue);
                    break;
                case GRAY:
                    graphics.add(R.drawable.traincard_locomotive);
                    break;
                case PINK:
                    graphics.add(R.drawable.traincard_pink);
                    break;
                case BLACK:
                    graphics.add(R.drawable.traincard_black);
                    break;
                case GREEN:
                    graphics.add(R.drawable.traincard_green);
                    break;
                case WHITE:
                    graphics.add(R.drawable.traincard_white);
                    break;
                case ORANGE:
                    graphics.add(R.drawable.traincard_orange);
                    break;
                case YELLOW:
                    graphics.add(R.drawable.traincard_yellow);
                    break;
                default:
                    System.out.println("No TrainCardImage for that Color");
                    break;
            }
        }
        return graphics;
    }
}
