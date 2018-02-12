/**
 * CreateGameDialog.java
 * Author: Ben Horrocks
 * Date of Last Commit: 11 February, 2018
 * Notes: Need to thoroughly test
 */

package cs340.TicketClient.Lobby;

import android.app.*;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cs340.TicketClient.R;

/**
 * CreateGameDialog
 * Abstract: Android dialog to confirm creation of new game, get Game Name to pass to server & check for valid GameName
 * Note: We should probably move the paramater checking to the model, probably by getting a class called isValidGameName
 *
 * @domain mNewGameName  EditText  Text field to enter new game name
 */
public class CreateGameDialog extends DialogFragment
{
  EditText mNewGameName;
  CreateGameDialogListener mlistener;

  @Override
  public Dialog onCreateDialog(final Bundle savedInstanceState)
  {
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.new_game_fragment, null);
    mNewGameName = (EditText) view.findViewById(R.id.newGameText);
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setView(view)
            .setPositiveButton(R.string.new_game_confirm, new DialogInterface.OnClickListener()
            {

              @Override
              public void onClick(DialogInterface dialogInterface, int i)
              {
                String newGameName = mNewGameName.getText().toString();
                if (newGameName.length() > 0)
                {
                  mlistener.onAddGame(CreateGameDialog.this, newGameName);
                } else
                {
                  Toast.makeText(getActivity().getBaseContext(), "Please specify a game name",
                                 Toast.LENGTH_SHORT).show();
                }
              }
            }).setNegativeButton(R.string.new_game_cancel, new DialogInterface.OnClickListener()
    {
      @Override
      public void onClick(DialogInterface dialogInterface, int i)
      {
        CreateGameDialog.this.getDialog().cancel();
      }
    });
    return builder.create();
  }

  @Override
  public void onAttach(Activity activity)
  {
    super.onAttach(activity);

    try
    {
      mlistener = (CreateGameDialogListener) activity;
    } catch (ClassCastException e)
    {
      Toast.makeText(activity, "This activity must implement CreateGameDialogListener",
                     Toast.LENGTH_SHORT).show();
    }
  }


  /**
   * CreateGameDialogListener
   * Abstract: Interface to successfully pass newGameName to Activity so that it can correctly create the NewGameTask.
   */
  public interface CreateGameDialogListener
  {

    /**
     * Abstract: Function to return data from Dialog and start newGameTask to send to server.
     *
     * @pre User has just asked for a new game t be created via the dialog, newGame.length >0
     * @post A new AddGameTask will have been executed to add a new game on the server.
     */
    public void onAddGame(DialogFragment frag, String newGameName);
  }
}
