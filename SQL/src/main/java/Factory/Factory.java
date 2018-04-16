package Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import daos.*;
import daos.CommandDAO;

/**
 * Created by Carter on 4/14/18.
 */

public class Factory implements IFactory {

    boolean clearData = false;

    @Override
    public IUserDAO createUserDAO() {
        //logger.entering("Factory", "createUserDAO");
        
        //create conection to Database
        String dbName = "Server/TicketToRideDB.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbName;
        Connection connection = null;
        try {
            // Open a database connection
            connection = DriverManager.getConnection(connectionURL);
            // Start a transaction
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            // ERROR
            //logger.warning(e + " - creating user DAO in Factory ");
            //logger.exiting("Factory", "createUserDAO", false);
        }

        return new UserDAO(connection);
    }

    @Override
    public IPlayerDAO createPlayerDAO() {
        //logger.entering("Factory", "createPlayerDAO");

        //create conection to Database
        String dbName = "Server/TicketToRide.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbName;
        Connection connection = null;
        try {
            // Open a database connection
            connection = DriverManager.getConnection(connectionURL);
            // Start a transaction
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            // ERROR
            //logger.warning(e + " - creating player DAO in Factory ");
            //logger.exiting("Factory", "createPlayerDAO", false);
        }

        return new PlayerDAO(connection);
    }

    @Override
    public ICommandDAO createCommandDAO() {
        //logger.entering("Factory", "createCommandDAO");

        //create conection to Database
        String dbName = "Server/TicketToRide.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbName;
        Connection connection = null;
        try {
            // Open a database connection
            connection = DriverManager.getConnection(connectionURL);
            // Start a transaction
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            // ERROR
            //logger.warning(e + " - creating command DAO in Factory ");
            //logger.exiting("Factory", "createPlayerDAO", false);
        }

        return new CommandDAO(connection);
    }

    @Override
    public IGameDataDAO createGameDataDAO() {
        //logger.entering("Factory", "createGameDataDAO");

        //create conection to Database
        String dbName = "Server/TicketToRide.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbName;
        Connection connection = null;
        try {
            // Open a database connection
            connection = DriverManager.getConnection(connectionURL);
            // Start a transaction
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            // ERROR
            //logger.warning(e + " - creating game data DAO in Factory ");
            //logger.exiting("Factory", "createGameDataDAO", false);
        }

        return new GameDataDAO(connection);
    }

    @Override
    public void setClearData(boolean clearData)
    {
        this.clearData = clearData;
    }
}
