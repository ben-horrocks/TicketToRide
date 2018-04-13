package daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import CS340.TicketServer.LogKeeper;
import common.game_data.GameID;
import common.game_data.ServerGameData;

/**
 * Created by Kavika F.
 */
public class SQLGameDataDAO extends AbstractSQL_DAO implements IGameDataDAO
{
	private static final Logger logger = LogKeeper.getSingleton().getLogger();

	SQLGameDataDAO(Connection connection)
	{
		super(connection);
	}

	private static class DataEntry
	{
		static final String TABLE_NAME = "GameData";
		static final String COLUMN_NAME_GAME_ID = "game_id";
		static final String COLUMN_NAME_GAME_DATA = "game_data";
	}

	@Override
	boolean createTable()
	{
		logger.entering("SQLGameDataDAO", "createTable");
		final String CREATE_GAMEDATA_TABLE =
				"CREATE TABLE " + DataEntry.TABLE_NAME + " ( " +
						DataEntry.COLUMN_NAME_GAME_ID + " TEXT NOT NULL UNIQUE, " +
						DataEntry.COLUMN_NAME_GAME_DATA + " BLOB NOT NULL UNIQUE, " +
						"PRIMARY KEY( " + DataEntry.COLUMN_NAME_GAME_ID + ") )";
		try
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(CREATE_GAMEDATA_TABLE);
		}
		catch (SQLException e)
		{
			logger.warning(e + " - creating table " + DataEntry.TABLE_NAME);
			logger.exiting("SQLGameDataDAO", "createTable", false);
			return false;
		}
		logger.exiting("SQLGameDataDAO", "createTable", true);
		return true;
	}

	@Override
	boolean deleteTable()
	{
		logger.entering("SQLGameDataDAO", "deleteTable");
		final String DELETE_GAME_DATA_TABLE = "DROP TABLE " + DataEntry.TABLE_NAME;
		try
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(DELETE_GAME_DATA_TABLE);
		}
		catch (SQLException e)
		{
			logger.warning(e + " - deleting table " + DataEntry.TABLE_NAME);
			logger.exiting("SQLGameDataDAO", "deleteTable", false);
			return false;
		}
		logger.exiting("SQLGameDataDAO", "deleteTable", true);
		return true;
	}

	@Override
	public boolean addNewGameData(ServerGameData gameData)
	{
		logger.entering("SQLGameDataDAO", "addNewGameData", gameData);
		final String ADD_GAME_DATA =
				"INSERT INTO " + DataEntry.TABLE_NAME +
						" (" + DataEntry.COLUMN_NAME_GAME_ID +
						", " + DataEntry.COLUMN_NAME_GAME_DATA +
						") VALUES (?,?)";
		try
		{
			byte[] dataAsBytes = objectToByteArray(gameData);

			PreparedStatement statement = connection.prepareStatement(ADD_GAME_DATA);
			statement.setString(1, gameData.getId().getId());
			statement.setObject(2, dataAsBytes);
			statement.executeUpdate();
		}
		catch (SQLException | IOException e)
		{
			logger.warning(e + " - adding new game data " + gameData);
			logger.exiting("SQLGameDataDAO", "addNewGameData", false);
			return false;
		}
		logger.exiting("SQLGameDataDAO", "addNewGameData", true);
		return true;
	}

	@Override
	public ServerGameData getGameData(GameID id)
	{
		logger.entering("SQLGameDataDAO", "getGameData", id);
		final String GET_GAME_DATA =
				"SELECT " + DataEntry.COLUMN_NAME_GAME_DATA +
						" FROM " + DataEntry.TABLE_NAME +
						" WHERE " + DataEntry.COLUMN_NAME_GAME_ID + " = ?";
		String gameID = id.getId();
		try
		{
			PreparedStatement statement = connection.prepareStatement(GET_GAME_DATA);
			statement.setString(1, gameID);
			ResultSet rs = statement.executeQuery();
			if (rs.next())
			{
				byte[] bytes = rs.getBytes(1);
				if (bytes == null)
				{
					logger.fine("Nothing found with gameID: " + gameID);
				}
				else
				{
					ServerGameData data = (ServerGameData)byteArrayToObject(bytes);
					rs.close();
					statement.close();
					logger.exiting("SQLGameDataDAO", "getGameData", data);
					return data;
				}
			}
		}
		catch (SQLException | IOException | ClassNotFoundException e)
		{
			logger.warning(e + " - getting game data " + gameID);
			e.printStackTrace();
		}
		logger.exiting("SQLGameDataDAO", "getGameData", null);
		return null;
	}

	@Override
	public List<ServerGameData> getAllGameData()
	{
		logger.entering("SQLGameDataDAO", "getAllGameData");
		final String GET_ALL_DATA =
				"SELECT " + DataEntry.COLUMN_NAME_GAME_DATA +
						" FROM " + DataEntry.TABLE_NAME;
		try
		{
			List<ServerGameData> games = new ArrayList<>();
			PreparedStatement statement = connection.prepareStatement(GET_ALL_DATA);
			ResultSet rs = statement.executeQuery();
			while (rs.next())
			{
				byte[] bytes = rs.getBytes(1);
				ServerGameData game = (ServerGameData)byteArrayToObject(bytes);
				games.add(game);
			}
			logger.exiting("SQLGameDataDAO", "getAllGameData", games);
			return games;
		}
		catch (SQLException | IOException | ClassNotFoundException e)
		{
			logger.warning(e + " - getting all games");
			e.printStackTrace();
		}
		logger.exiting("SQLGameDataDAO", "getAllGameData", null);
		return null;
	}

	@Override
	public boolean updateGameData(ServerGameData gameData)
	{
		logger.entering("SQLGameDataDAO", "updateGameData", gameData);
		final String UPDATE_GAME =
				"UPDATE " + DataEntry.TABLE_NAME +
						" SET " + DataEntry.COLUMN_NAME_GAME_DATA + " = ?" +
						" WHERE " + DataEntry.COLUMN_NAME_GAME_ID + " = ?";
		try
		{
			PreparedStatement statement = connection.prepareStatement(UPDATE_GAME);
			byte[] dataAsBytes = objectToByteArray(statement);
			statement.setObject(1, dataAsBytes);
			statement.setString(2, gameData.getId().getId());
			statement.executeUpdate();
			logger.exiting("SQLGameDataDAO", "updateGameData", true);
			return true;
		}
		catch (SQLException | IOException e)
		{
			logger.warning(e + " - updating GameData - " + gameData.getId());
			e.printStackTrace();
		}
		logger.exiting("SQLGameDataDAO", "updateGameData", false);
		return false;
	}

	@Override
	public boolean deleteGameData(ServerGameData gameData)
	{
		logger.entering("SQLGameDataDAO", "deleteGameData", gameData);
		final String DELETE_DATA =
				"DELETE FROM " + DataEntry.TABLE_NAME +
						" WHERE " + DataEntry.COLUMN_NAME_GAME_ID + " = ?";
		try
		{
			PreparedStatement statement = connection.prepareStatement(DELETE_DATA);
			statement.setObject(1, gameData.getId().getId());
			statement.executeUpdate();
			logger.exiting("SQLGameDataDAO", "deleteGameData", true);
			return true;
		}
		catch (SQLException e)
		{
			logger.warning(e + " - deleting GameData - " + gameData.getId());
			e.printStackTrace();
		}
		logger.exiting("SQLGameDataDAO", "deleteGameData", false);
		return false;
	}
}
