package de.simonsator.partyandfriends.communication.sql;

import java.sql.*;
import java.util.Properties;

/**
 * @author simonsator
 * @version 1.0.0 created on 12.06.16
 */
public abstract class SQLCommunication {
	/**
	 * The MySQL DATABASE
	 */
	protected final String DATABASE;
	/**
	 * The URL of the SQL server
	 */
	private final String URL;
	private final Properties connectionProperties;
	private Connection connection;

	protected SQLCommunication(String pDatabase, String pURL, String pUserName, String pPassword, boolean pUseSSL) {
		this.DATABASE = pDatabase;
		this.URL = pURL;
		connectionProperties = new Properties();
		connectionProperties.setProperty("user", pUserName);
		connectionProperties.setProperty("password", pPassword);
		connectionProperties.setProperty("useSSL", pUseSSL + "");
		connection = createConnection();
	}

	protected SQLCommunication(String pDatabase, String pURL, String pUserName, String pPassword) {
		this.DATABASE = pDatabase;
		this.URL = pURL;
		connectionProperties = new Properties();
		connectionProperties.setProperty("user", pUserName);
		connectionProperties.setProperty("password", pPassword);
		connection = createConnection();
	}

	protected void close(ResultSet rs, Statement stmt) {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void close(ResultSet rs, Statement stmt, PreparedStatement prepStmt) {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (prepStmt != null)
				prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

	protected void close(PreparedStatement pPrepStmt) {
		try {
			if (pPrepStmt != null)
				pPrepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Connection createConnection() {
		try {
			closeConnection();
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(URL, connectionProperties);
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
