package Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import daos.ICommandDAO;
import daos.IGameDataDAO;
import daos.IPlayerDAO;
import daos.IUserDAO;
import daos.SQLCommandDAO;
import daos.SQLGameDataDAO;
import daos.SQLPlayerDAO;
import daos.SQLUserDAO;

/**
 * Created by Carter on 4/14/18.
 */

public class SQL_Factory implements IFactory {

    boolean clearData = false;

    @Override
    public IUserDAO createUserDAO() {
        //logger.entering("SQL_Factory", "createUserDAO");
        
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
            //logger.warning(e + " - creating user DAO in SQL_Factory ");
            //logger.exiting("SQL_Factory", "createUserDAO", false);
        }

        return new SQLUserDAO(connection);
    }

    @Override
    public IPlayerDAO createPlayerDAO() {
        //logger.entering("SQL_Factory", "createPlayerDAO");

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
            //logger.warning(e + " - creating player DAO in SQL_Factory ");
            //logger.exiting("SQL_Factory", "createPlayerDAO", false);
        }

        return new SQLPlayerDAO(connection);
    }

    @Override
    public ICommandDAO createCommandDAO() {
        //logger.entering("SQL_Factory", "createCommandDAO");

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
            //logger.warning(e + " - creating command DAO in SQL_Factory ");
            //logger.exiting("SQL_Factory", "createPlayerDAO", false);
        }

        return new SQLCommandDAO(connection);
    }

    @Override
    public IGameDataDAO createGameDataDAO() {
        //logger.entering("SQL_Factory", "createGameDataDAO");

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
            //logger.warning(e + " - creating game data DAO in SQL_Factory ");
            //logger.exiting("SQL_Factory", "createGameDataDAO", false);
        }

        return new SQLGameDataDAO(connection);
    }

    @Override
    public void setClearData(boolean clearData)
    {
        this.clearData = clearData;
    }
}
