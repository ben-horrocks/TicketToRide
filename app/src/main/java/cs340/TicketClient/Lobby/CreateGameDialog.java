package cs340.TicketClient.Lobby;

import android.app.*;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import common.DataModels.Player;
import cs340.TicketClient.R;

/**
 * Created by Ben_D on 2/7/2018.
 */

public class CreateGameDialog extends DialogFragment
{
  EditText mNewGameName;

  @Override
  public Dialog onCreateDialog(final Bundle savedInstanceState)
  {
    Player player = savedInstanceState.getParcelable("person");
    mNewGameName = (EditText) getView().findViewById(R.id.newGameButton);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = getActivity().getLayoutInflater();
    builder.setView(inflater.inflate(R.layout.new_game_fragment,null))
            .setPositiveButton(R.string.new_game_confirm, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i)
              {
                Player player = (Player) savedInstanceState.getSerializable("player");
              }
            })
            .setNegativeButton(R.string.new_game_cancel, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i)
              {
                 CreateGameDialog.this.getDialog().cancel();
              }
            });
    return builder.create();
  }
}
