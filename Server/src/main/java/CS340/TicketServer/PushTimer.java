package CS340.TicketServer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Carter on 2/10/18.
 */

public class PushTimer {

    Timer timer;

    public PushTimer() {
        timer = new Timer();
        timer.schedule(new PushTask(), 0, 1000);
    }

    class PushTask extends TimerTask {
        Integer count = new Integer(1);

        @Override
        public void run() {
            ClientProxy.getSINGLETON().updateGameList(Database.SINGLETON.getAllGames());
        }

        @Override
        public boolean cancel() {
            return super.cancel();
        }
    }

    public static void main(String argv[]) {
        new PushTimer();
    }
}
