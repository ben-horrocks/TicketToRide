package CS340.TicketServer;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * A class to hold a single logger for the server side.
 */
public class LogKeeper
{
	/*
	===============================================
	* Reminder for logger levels:
	* - FINEST: Very detailed.
	* - FINER: Somewhat detailed.
	* - FINE: Regular detail. Basic information.
	* - WARNING: Something could go wrong; caution.
	* - SEVERE: Something is going wrong; broken.
	* =============================================
	* For more info, follow this link:
	* https://docs.oracle.com/javase/7/docs/api/java/util/logging/Level.html
	* */

	private Logger logger; // Singleton
	private static LogKeeper singleton;

	public static LogKeeper getSingleton()
	{
		if (singleton == null)
		{
			singleton = new LogKeeper();
		}
		return singleton;
	}

	private LogKeeper()
	{
		try
		{
			initialize();
		}
		catch (IOException e)
		{
			System.out.println("Error initializing logger.");
			e.printStackTrace();
		}
	}

	/**
	 * Set up the logging level and handlers for the logger.
	 * @throws IOException Thrown if the file handler can't create a new FileHandler.
	 */
	private void initialize() throws IOException
	{
		Level logLevel = Level.FINE;
		logger = Logger.getLogger("server");
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);

		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("server_log.txt", false);
		fileHandler.setLevel(Level.ALL);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}

	public Logger getLogger() { return logger; }
}
