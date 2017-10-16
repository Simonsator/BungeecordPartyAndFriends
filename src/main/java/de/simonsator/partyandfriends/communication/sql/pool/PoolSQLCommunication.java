package de.simonsator.partyandfriends.communication.sql.pool;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import de.simonsator.partyandfriends.communication.sql.DBCommunication;
import de.simonsator.partyandfriends.communication.sql.MySQLData;
import de.simonsator.partyandfriends.utilities.disable.Deactivated;

import java.beans.PropertyVetoException;
import java.sql.*;
import java.util.Properties;

/**
 * @author simonbrungs
 * @version 1.0.0 11.04.17
 */
public class PoolSQLCommunication extends DBCommunication implements Deactivated {
	private final MySQLData MYSQL_DATA;
	private final PoolData POOL_DATA;
	private final Properties connectionProperties;
	private ComboPooledDataSource cpds;

	public PoolSQLCommunication(MySQLData pMySQLData, PoolData pPoolData) throws SQLException {
		MYSQL_DATA = pMySQLData;
		POOL_DATA = pPoolData;
		connectionProperties = new Properties();
		connectionProperties.setProperty("user", MYSQL_DATA.USERNAME);
		connectionProperties.setProperty("password", MYSQL_DATA.PASSWORD);
		connectionProperties.setProperty("useSSL", pMySQLData.USE_SSL + "");
		createDatabase();
		cpds = createConnection();
	}

	private void createDatabase() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://" + MYSQL_DATA.HOST + ":" + MYSQL_DATA.PORT, connectionProperties);
			PreparedStatement prepStmt = con.prepareStatement("CREATE DATABASE IF NOT EXISTS `" + getDatabase() + "`");
			prepStmt.executeUpdate();
			prepStmt.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private ComboPooledDataSource createConnection() {
		try {
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			cpds.setDriverClass("com.mysql.jdbc.Driver");
			cpds.setJdbcUrl("jdbc:mysql://" + MYSQL_DATA.HOST + ":" + MYSQL_DATA.PORT + "/" + MYSQL_DATA.DATABASE);
			cpds.setProperties(connectionProperties);
			cpds.setInitialPoolSize(POOL_DATA.INITIAL_POOL_SIZE);
			cpds.setMinPoolSize(POOL_DATA.MIN_POOL_SIZE);
			cpds.setMaxPoolSize(POOL_DATA.MAX_POOL_SIZE);
			cpds.setTestConnectionOnCheckin(POOL_DATA.TEST_CONNECTION_ON_CHECKIN);
			cpds.setIdleConnectionTestPeriod(POOL_DATA.IDLE_CONNECTION_TEST_PERIOD);
			return cpds;
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void closeConnection() {
		if (cpds != null)
			cpds.close();
	}

	@Override
	public void onDisable() {
		closeConnection();
	}

	protected void close(Connection con, ResultSet rs, Statement stmt) {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close(rs, stmt);
	}

	protected void close(Connection con, ResultSet rs, Statement stmt, PreparedStatement prepStmt) {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close(rs, stmt, prepStmt);
	}

	protected void close(Connection con, PreparedStatement pPrepStmt) {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close(pPrepStmt);
	}

	public Connection getConnection() {
		try {
			return cpds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getDatabase() {
		return MYSQL_DATA.DATABASE;
	}
}
