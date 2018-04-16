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

import common.game_data.GameID;
import common.player_info.Player;
import common.player_info.Username;

public class SQLPlayerDAO extends AbstractSQL_DAO implements IPlayerDAO
{
////	private static final Logger logger = LogKeeper.getSingleton().getLogger();

	public SQLPlayerDAO(Connection connection)
	{
		super(connection);
	}

	private static class PlayerEntry
	{
		static final String TABLE_NAME = "Players";
		static final String COLUMN_NAME_USERNAME = "username";
		static final String COLUMN_NAME_PLAYER = "player";
	}


	@Override
	boolean createTable()
	{
//		logger.entering("SQLPlayerDAO", "createTable");
		final String CREATE_PLAYER_TABLE =
				"CREATE TABLE " + PlayerEntry.TABLE_NAME + " ('" +
						PlayerEntry.COLUMN_NAME_USERNAME + "' TEXT NOT NULL, '" +
						PlayerEntry.COLUMN_NAME_PLAYER + "' BLOB NOT NULL UNIQUE, " +
						"PRIMARY KEY('" + PlayerEntry.COLUMN_NAME_USERNAME + "') )";
		try
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(CREATE_PLAYER_TABLE);
		} catch (SQLException e)
		{
//			logger.warning(e + " - creating table " + PlayerEntry.TABLE_NAME);
//			logger.exiting("SQLPlayerDAO", "createTable", false);
			return false;
		}
		return true;
	}

	@Override
	boolean deleteTable()
	{
//		logger.entering("SQLPlayerDAO", "deleteTable");
		final String DELETE_PLAYER_TABLE = "DROP TABLE " + PlayerEntry.TABLE_NAME;
		try
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(DELETE_PLAYER_TABLE);
		} catch (SQLException e)
		{
//			logger.warning(e + " - deleting table " + PlayerEntry.TABLE_NAME);
//			logger.exiting("SQLPlayerDAO", "deleteTable", false);
			return false;
		}
//		logger.exiting("SQLPlayerDAO", "deleteTable", true);
		return true;
	}

	@Override
	public boolean addNewPlayer(GameID gameID, Player player)
	{
//		logger.entering("SQLPlayerDAO", "addNewPlayer", player);
		final String INSERT_PLAYER =
				"INSERT INTO Player (" + PlayerEntry.COLUMN_NAME_USERNAME +
						", " + PlayerEntry.COLUMN_NAME_PLAYER +
						") VALUES (?,?)";
		try
		{
			byte[] playerAsBytes = objectToByteArray(player);
			PreparedStatement statement = connection.prepareStatement(INSERT_PLAYER);
			statement.setString(1, player.getName());
			statement.setObject(2, playerAsBytes);
			statement.executeUpdate();
		} catch (SQLException | IOException e)
		{
//			logger.warning(e + " - adding new player " + player);
//			logger.exiting("SQLPlayerDAO", "addNewPlayer", false);
			return false;
		}
//		logger.exiting("SQLPlayerDAO", "addNewPlayer", true);
		return true;
	}

	@Override
	public Player getPlayer(GameID id, Username username)
	{
//		logger.entering("SQLPlayerDAO", "getPlayer", username);
		final String GET_PLAYER =
				"SELECT " + PlayerEntry.COLUMN_NAME_PLAYER +
						" FROM " + PlayerEntry.TABLE_NAME +
						" WHERE " + PlayerEntry.COLUMN_NAME_USERNAME + " = ?";
		try
		{
			PreparedStatement statement = connection.prepareStatement(GET_PLAYER);
			statement.setString(1, username.getName());
			ResultSet rs = statement.executeQuery();
			if (rs.next())
			{
				byte[] bytes = rs.getBytes(1);
				if (bytes == null)
				{
//					logger.fine("Nothing found with username: " + username);
				} else
				{
					Player player = (Player) byteArrayToObject(bytes);
					rs.close();
					statement.close();
//					logger.exiting("SQLPlayerDAO", "getPlayer", player);
					return player;
				}
			}
		} catch (SQLException | IOException | ClassNotFoundException e)
		{
//			logger.warning(e + " - getting player " + username);
			e.printStackTrace();
		}
//		logger.exiting("SQLPlayerDAO", "getPlayer", null);
		return null;
	}

	@Override
	public List<Player> getAllPlayers()
	{
//		logger.entering("SQLPlayerDAO", "getAllPlayers");
		final String GET_PLAYERS =
				"SELECT " + PlayerEntry.COLUMN_NAME_PLAYER +
						" FROM " + PlayerEntry.TABLE_NAME;
		try
		{
			List<Player> players = new ArrayList<>();
			PreparedStatement statement = connection.prepareStatement(GET_PLAYERS);
			ResultSet rs = statement.executeQuery();
			while (rs.next())
			{
				byte[] bytes = rs.getBytes(1);
				Player player = (Player) byteArrayToObject(bytes);
				players.add(player);
			}
//			logger.exiting("SQLPlayerDAO", "getAllPlayers", players);
			return players;
		} catch (SQLException | IOException | ClassNotFoundException e)
		{
//			logger.warning(e + " - getting all players");
			e.printStackTrace();
		}
//		logger.exiting("SQLPlayerDAO", "getAllPlayers", null);
		return null;
	}

	@Override
	public List<Player> getAllPlayersInGame(GameID game) {
		return null;
	}

	@Override
	public boolean updatePlayer(GameID id, Player player)
	{
//		logger.entering("SQLPlayerDAO", "updatePlayer", player);
		final String UPDATE_PLAYER =
				"UPDATE " + PlayerEntry.TABLE_NAME +
						" SET " + PlayerEntry.COLUMN_NAME_PLAYER + " = ?" +
						" WHERE " + PlayerEntry.COLUMN_NAME_USERNAME + " = ?";
		try
		{
			PreparedStatement statement = connection.prepareStatement(UPDATE_PLAYER);
			byte[] playerAsBytes = objectToByteArray(player);
			statement.setObject(1, playerAsBytes);
			statement.setString(2, player.getName());
			statement.executeUpdate();
//			logger.exiting("SQLPlayerDAO", "updatePlayer", true);
			return true;
		} catch (SQLException | IOException e)
		{
//			logger.warning(e + " - updating player - " + player);
			e.printStackTrace();
		}
//		logger.exiting("SQLPlayerDAO", "updatePlayer", false);
		return false;
	}

	@Override
	public boolean deletePlayer(GameID gameID, Player player)
	{
//		logger.entering("SQLPlayerDAO", "deletePlayer", player);
		final String DELETE_PLAYER =
				"DELETE FROM " + PlayerEntry.TABLE_NAME +
						" WHERE " + PlayerEntry.COLUMN_NAME_USERNAME + " = ?";
		try
		{
			PreparedStatement statement = connection.prepareStatement(DELETE_PLAYER);
			statement.setObject(1, player.getName());
			statement.executeUpdate();
//			logger.exiting("SQLPlayerDAO", "deletePlayer", true);
			return true;
		} catch (SQLException e)
		{
//			logger.warning(e + " - deleting player - " + player);
			e.printStackTrace();
		}
//		logger.exiting("SQLPlayerDAO", "deletePlayer", false);
		return false;
	}
}