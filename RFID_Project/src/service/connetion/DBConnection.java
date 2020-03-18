/**
 * @autor  Yasin Fakhar
 * edited : 13 March 2020
 * **/

package service.connetion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class DBConnection {
    private Connection connection;
    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());
    private Properties properties;
    public Connection getConnection() {
        return connection;
    }

    public DBConnection() {
        try {

            //setting a property to get the connection properties form setting.txt
	properties = new Properties();
	InputStream settings = this.getClass().getClassLoader()
	        .getResource("settings/settings.txt").openStream();
	properties.load(settings);

        } catch (FileNotFoundException e) {
	LOGGER.info("could not find settings.txt");
        } catch (IOException e) {
	e.printStackTrace();
        }
        try {
	Class.forName(properties.getProperty("db.drivername"));
        } catch (ClassNotFoundException e) {
	System.out.println("class not found");
	LOGGER.severe(e.getMessage());
	throw new IllegalStateException("class could not load", e);
        }

    }


    public Connection openConnection()  {

        try {
	connection = DriverManager.getConnection(
	        properties.getProperty("db.url"),
	        properties.getProperty("db.username"),
	        properties.getProperty("db.password"));
        } catch (SQLException e) {
	e.printStackTrace();
        }

        LOGGER.info("connection stablished succssesfully");
        try {
	connection.setAutoCommit(false);
        } catch (SQLException e) {
	e.printStackTrace();
        }
        return connection;

    }

    public void closeConnetion() throws SQLException {
        connection.close();
        LOGGER.info("connection closed");
    }

}
