package de.simonsator.partyandfriends.communication.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author simonsator
 * @version 1.0.0 created on 12.06.16
 */
public abstract class SQLCommunication extends DBCommunication {
	/**
	 * The MySQL DATABASE
	 */
	protected final String DATABASE;
	private final Properties connectionProperties;
	/**
	 * The URL of the SQL server
	 */
	private String url;
	private Connection connection;

	protected SQLCommunication(String pDatabase, String pURL, String pUserName, String pPassword, boolean pUseSSL) {
		this.DATABASE = pDatabase;
		this.url = pURL;
		connectionProperties = new Properties();
		connectionProperties.setProperty("user", pUserName);
		connectionProperties.setProperty("password", pPassword);
		connectionProperties.setProperty("useSSL", pUseSSL + "");
		connection = createConnection();
	}

	@Deprecated
	protected SQLCommunication(String pDatabase, String pURL, String pUserName, String pPassword) {
		this.DATABASE = pDatabase;
		this.url = pURL;
		connectionProperties = new Properties();
		connectionProperties.setProperty("user", pUserName);
		connectionProperties.setProperty("password", pPassword);
		connection = createConnection();
	}

	protected SQLCommunication(MySQLData pMySQLData) {
		this.DATABASE = pMySQLData.DATABASE;
		this.url = "jdbc:mysql://" + pMySQLData.HOST + ":" + pMySQLData.PORT + "/";
		connectionProperties = new Properties();
		connectionProperties.setProperty("user", pMySQLData.USERNAME);
		connectionProperties.setProperty("password", pMySQLData.PASSWORD);
		connectionProperties.setProperty("useSSL", pMySQLData.USE_SSL + "");
		connectionProperties.setProperty("allowPublicKeyRetrieval", !pMySQLData.USE_SSL + "");
		Connection con = createConnection();
		PreparedStatement prepStmt;
		try {
			prepStmt = con.prepareStatement("CREATE DATABASE IF NOT EXISTS `" + DATABASE + "`");
			prepStmt.executeUpdate();
			prepStmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		url += pMySQLData.DATABASE;
		connection = createConnection();
	}

	protected Connection getConnection() {
		try {
			if (connection != null && connection.isValid(6))
				return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection = createConnection();
	}

	private Connection createConnection() {
		try {
			closeConnection();
			Class.forName(MYSQL_DRIVER_CLASS);
			return DriverManager.getConnection(url, connectionProperties);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void closeConnection() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException ignored) {
		}
	}

}
