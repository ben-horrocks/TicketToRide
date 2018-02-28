package CS340.TicketServer;

import java.util.Timer;
import java.util.TimerTask;

public class PushTimer
{
	private static final int timeInterval = 1000;

	public PushTimer() {
        Timer timer = new Timer();
        timer.schedule(new PushTask(), 0, timeInterval);
    }

    class PushTask extends TimerTask {

        @Override
        public void run() {
            ClientProxy.getSINGLETON().updateGameList(Database.SINGLETON.getAllOpenGames());
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
