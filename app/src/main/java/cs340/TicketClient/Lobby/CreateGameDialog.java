package cs340.TicketClient.Lobby;

import android.app.*;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.widget.EditText;

import common.DataModels.Player;
import cs340.TicketClient.ASyncTask.AddGameTask;
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
    mNewGameName = (EditText) getView().findViewById(R.id.newGameText);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = getActivity().getLayoutInflater();
    builder.setView(inflater.inflate(R.layout.new_game_fragment,null))
            .setPositiveButton(R.string.new_game_confirm, new DialogInterface.OnClickListener() {
              @RequiresApi(api = Build.VERSION_CODES.M)
              @Override
              public void onClick(DialogInterface dialogInterface, int i)
              {
                String gamename = mNewGameName.getText().toString();
                Player player = (Player) savedInstanceState.getSerializable("player");
                AddGameTask task = new AddGameTask(getContext());
                task.execute(gamename, player);
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
