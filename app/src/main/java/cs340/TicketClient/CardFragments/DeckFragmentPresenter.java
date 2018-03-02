package cs340.TicketClient.CardFragments;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import common.DataModels.TrainCard;
import cs340.TicketClient.Game.GameModel;

/**
 * Created by jhens on 3/1/2018.
 */

public class DeckFragmentPresenter {

    private DeckFragment fragment;
    private GameModel model;

    public DeckFragmentPresenter(DeckFragment fragment)
    {
        this.fragment = fragment;
        this.model = null;
    }

    ArrayList<Drawable> getFaceUpCards()
    {
        ArrayList<TrainCard> faceUp = new ArrayList<>();
        ArrayList<Drawable> graphics = new ArrayList<>();
        for (TrainCard t : faceUp)
        {
            switch (t.getType())
            {
                case RED:
                    break;
                case BLUE:
                    break;
                case GRAY:
                    break;
                case PINK:
                    break;
                case BLACK:
                    break;
                case GREEN:
                    break;
                case WHITE:
                    break;
                case ORANGE:
                    break;
                case YELLOW:
                    break;
                default:
                    break;
            }
        }
        return graphics;
    }
}
