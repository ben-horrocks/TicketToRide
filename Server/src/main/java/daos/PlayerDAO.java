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
import common.player_info.Player;
import common.player_info.Username;

public class PlayerDAO extends AbstractDAO implements IPlayerDAO
{
	private static final Logger logger = LogKeeper.getSingleton().getLogger();
	private Connection connection;

	public PlayerDAO(Connection connection)
	{
		this.connection = connection;
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
		logger.entering("PlayerDAO", "createTable");
		final String CREATE_PLAYER_TABLE =
				"CREATE TABLE " + PlayerEntry.TABLE_NAME + " ('" +
						PlayerEntry.COLUMN_NAME_USERNAME + "' TEXT NOT NULL UNIQUE, '" +
						PlayerEntry.COLUMN_NAME_PLAYER + "' BLOB NOT NULL UNIQUE, " +
						"PRIMARY KEY('" + PlayerEntry.COLUMN_NAME_USERNAME + "') )";
		try
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(CREATE_PLAYER_TABLE);
		} catch (SQLException e)
		{
			logger.warning(e + " - creating table " + PlayerEntry.TABLE_NAME);
			logger.exiting("PlayerDAO", "createTable", false);
			return false;
		}
		return true;
	}

	@Override
	boolean deleteTable()
	{
		logger.entering("PlayerDAO", "deleteTable");
		final String DELETE_PLAYER_TABLE = "DROP TABLE " + PlayerEntry.TABLE_NAME;
		try
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(DELETE_PLAYER_TABLE);
		} catch (SQLException e)
		{
			logger.warning(e + " - deleting table " + PlayerEntry.TABLE_NAME);
			logger.exiting("PlayerDAO", "deleteTable", false);
			return false;
		}
		logger.exiting("UserDAO", "deleteTable", true);
		return true;
	}

	@Override
	public boolean addNewPlayer(Player player)
	{
		logger.entering("PlayerDAO", "addNewPlayer", player);
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
			logger.warning(e + " - adding new player " + player);
			logger.exiting("PlayerDAO", "addNewPlayer", false);
			return false;
		}
		logger.exiting("PlayerDAO", "addNewPlayer", true);
		return true;
	}

	@Override
	public Player getPlayer(Username username)
	{
		logger.entering("PlayerDAO", "getPlayer", username);
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
					logger.fine("Nothing found with username: " + username);
				} else
				{
					Player player = (Player) byteArrayToObject(bytes);
					rs.close();
					statement.close();
					logger.exiting("PlayerDAO", "getPlayer", player);
					return player;
				}
			}
		} catch (SQLException | IOException | ClassNotFoundException e)
		{
			logger.warning(e + " - getting player " + username);
			e.printStackTrace();
		}
		logger.exiting("PlayerDAO", "getPlayer", null);
		return null;
	}

	@Override
	public List<Player> getAllPlayers()
	{
		logger.entering("PlayerDAO", "getAllPlayers");
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
			logger.exiting("PlayerDAO", "getAllPlayers", players);
			return players;
		} catch (SQLException | IOException | ClassNotFoundException e)
		{
			logger.warning(e + " - getting all players");
			e.printStackTrace();
		}
		logger.exiting("PlayerDAO", "getAllPlayers", null);
		return null;
	}

	@Override
	public boolean updatePlayer(Player player)
	{
		logger.entering("PlayerDAO", "updatePlayer", player);
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
			logger.exiting("PlayerDAO", "updatePlayer", true);
			return true;
		} catch (SQLException | IOException e)
		{
			logger.warning(e + " - updating player - " + player);
			e.printStackTrace();
		}
		logger.exiting("PlayerDAO", "updatePlayer", false);
		return false;
	}

	@Override
	public boolean deletePlayer(Player player)
	{
		logger.entering("PlayerDAO", "deletePlayer", player);
		final String DELETE_PLAYER =
				"DELETE FROM " + PlayerEntry.TABLE_NAME +
						" WHERE " + PlayerEntry.COLUMN_NAME_USERNAME + " = ?";
		try
		{
			PreparedStatement statement = connection.prepareStatement(DELETE_PLAYER);
			statement.setObject(1, player.getName());
			statement.executeUpdate();
			logger.exiting("PlayerDAO", "deletePlayer", true);
			return true;
		} catch (SQLException e)
		{
			logger.warning(e + " - deleting player - " + player);
			e.printStackTrace();
		}
		logger.exiting("PlayerDAO", "deletePlayer", false);
		return false;
	}
}