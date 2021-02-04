package sample;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionController
{
	protected Connection getDatabaseConnection()
	{
		Connection connection = null;
		try {
			InputStream propertiesInputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
			Properties properties = new Properties();
			properties.load(propertiesInputStream);
			connection = DriverManager.getConnection(properties.getProperty("dbaseURL"), properties.getProperty("username"), properties.getProperty("password"));
		} catch (SQLException | IOException se) {
			System.out.println("Couldn't connect: print out a stack trace and exit.");
			se.printStackTrace();
			System.exit(1);
		}
		return connection;
	}
}
