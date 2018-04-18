package daos;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import common.game_data.GameID;
import common.player_info.Player;
import common.player_info.Username;

public class SQLPlayerDAO extends AbstractSQL_DAO implements IPlayerDAO
{
////	private static final Logger logger = LogKeeper.getSingleton().getLogger();

	public SQLPlayerDAO()
	{
		super();
	}

	private static class PlayerEntry
	{
		static final String TABLE_NAME = "Players";
		static final String COLUMN_NAME_GAME_ID = "gameID";
		static final String COLUMN_NAME_USERNAME = "username";
		static final String COLUMN_NAME_PLAYER = "player";
	}

	@Override
	boolean tableExists()
	{
		try
		{
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet rs = meta.getTables(null, null,
					PlayerEntry.TABLE_NAME, new String[] {"TABLE"});
			if (rs.next())
			{
				rs.close();
				return true;
			}
		}
		catch (SQLException e)
		{
			// Shouldn't really have errors.
			System.out.println("SQLException when checking if Player table exists.");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	boolean createTable()
	{
//		logger.entering("SQLPlayerDAO", "createTable");
		final String CREATE_PLAYER_TABLE =
				"CREATE TABLE " + PlayerEntry.TABLE_NAME + " ('" +
						PlayerEntry.COLUMN_NAME_GAME_ID + "' TEXT NOT NULL, '" +
						PlayerEntry.COLUMN_NAME_USERNAME + "' TEXT NOT NULL, '" +
						PlayerEntry.COLUMN_NAME_PLAYER + "' BLOB NOT NULL UNIQUE, " +
						"PRIMARY KEY('" + PlayerEntry.COLUMN_NAME_GAME_ID + "') )";
		try
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(CREATE_PLAYER_TABLE);
			statement.close();
		} catch (SQLException e)
		{
			System.err.println("ERROR: Creating table in SQLPlayerDAO");
			e.printStackTrace();
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
			statement.close();
		} catch (SQLException e)
		{
			System.err.println("ERROR: Deleting table in SQLUserDAO");
			e.printStackTrace();
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
				"INSERT INTO " + PlayerEntry.TABLE_NAME + "("
						+ PlayerEntry.COLUMN_NAME_GAME_ID +
						", " + PlayerEntry.COLUMN_NAME_USERNAME +
						", " + PlayerEntry.COLUMN_NAME_PLAYER +
						") VALUES (?,?,?)";
		try
		{
			byte[] playerAsBytes = objectToByteArray(player);
			PreparedStatement statement = connection.prepareStatement(INSERT_PLAYER);
			statement.setString(1, gameID.getId());
			statement.setString(2, player.getName());
			statement.setObject(3, playerAsBytes);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException | IOException e)
		{
			System.err.println("ERROR: Adding new player");
			e.printStackTrace();
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
						" WHERE " + PlayerEntry.COLUMN_NAME_USERNAME + " = ? " +
						"AND " + PlayerEntry.COLUMN_NAME_GAME_ID + " = ?";
		try
		{
			PreparedStatement statement = connection.prepareStatement(GET_PLAYER);
			statement.setString(1, username.getName());
			statement.setString(2, id.getId());
			ResultSet rs = statement.executeQuery();
			if (rs.next())
			{
				byte[] bytes = rs.getBytes(1);
				if (bytes != null)
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
			rs.close();
			statement.close();
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

		//		logger.entering("SQLPlayerDAO", "getAllPlayers");
		final String GET_PLAYERS =
				"SELECT " + PlayerEntry.COLUMN_NAME_PLAYER +
						" FROM " + PlayerEntry.TABLE_NAME +
				" WHERE " + PlayerEntry.COLUMN_NAME_GAME_ID + " = ?";
		try
		{
			List<Player> players = new ArrayList<>();
			PreparedStatement statement = connection.prepareStatement(GET_PLAYERS);
			statement.setString(1, game.getId());
			ResultSet rs = statement.executeQuery();
			while (rs.next())
			{
				byte[] bytes = rs.getBytes(1);
				Player player = (Player) byteArrayToObject(bytes);
				players.add(player);
			}
//			logger.exiting("SQLPlayerDAO", "getAllPlayers", players);
			rs.close();
			statement.close();
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
	public boolean updatePlayer(GameID id, Player player)
	{
//		logger.entering("SQLPlayerDAO", "updatePlayer", player);
		final String UPDATE_PLAYER =
				"UPDATE " + PlayerEntry.TABLE_NAME +
						" SET " + PlayerEntry.COLUMN_NAME_PLAYER + " = ?" +
						" WHERE " + PlayerEntry.COLUMN_NAME_USERNAME + " = ? " +
						" AND " + PlayerEntry.COLUMN_NAME_GAME_ID + " = ?";
		try
		{
			PreparedStatement statement = connection.prepareStatement(UPDATE_PLAYER);
			byte[] playerAsBytes = objectToByteArray(player);
			statement.setObject(1, playerAsBytes);
			statement.setString(2, player.getName());
			statement.setString(3, id.getId());
			int resultCount = statement.executeUpdate();
			statement.close();
//			logger.exiting("SQLPlayerDAO", "updatePlayer", true);
			return resultCount > 0;
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
			statement.close();
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