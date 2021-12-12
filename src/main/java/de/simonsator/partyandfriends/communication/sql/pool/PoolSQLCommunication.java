package de.simonsator.partyandfriends.communication.sql.pool;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.simonsator.partyandfriends.communication.sql.DBCommunication;
import de.simonsator.partyandfriends.communication.sql.MySQLData;
import de.simonsator.partyandfriends.utilities.disable.Deactivated;
import de.simonsator.partyandfriends.utilities.disable.Disabler;

import java.beans.PropertyVetoException;
import java.sql.*;
import java.util.Properties;

/**
 * @author simonbrungs
 * @version 1.0.0 11.04.17
 */
public class PoolSQLCommunication extends DBCommunication implements Deactivated {
	private static ComboPooledDataSource cpds;
	private static HikariDataSource hikariDataSource;
	private final MySQLData MYSQL_DATA;
	private final PoolData POOL_DATA;
	private final Properties connectionProperties;

	public PoolSQLCommunication(MySQLData pMySQLData, PoolData pPoolData) throws SQLException {
		MYSQL_DATA = pMySQLData;
		POOL_DATA = pPoolData;
		connectionProperties = new Properties();
		connectionProperties.setProperty("user", MYSQL_DATA.USERNAME);
		connectionProperties.setProperty("password", MYSQL_DATA.PASSWORD);
		connectionProperties.setProperty("useSSL", pMySQLData.USE_SSL + "");
		connectionProperties.setProperty("allowPublicKeyRetrieval", !pMySQLData.USE_SSL + "");
		connectionProperties.setProperty("rewriteBatchedStatements", "true");
		connectionProperties.setProperty("createDatabaseIfNotExist", "true");
		if (hikariDataSource == null && cpds == null) {
			checkIfConnectionIsValid();
			if (pPoolData.CONNECTION_POOL.equals("HIKARICP")) {
				hikariDataSource = createHikariConnection();
				cpds = null;
			} else {
				cpds = createCPDSConnection();
				hikariDataSource = null;
			}
		}
		Disabler.getInstance().registerDeactivated(this);
	}

	private void checkIfConnectionIsValid() throws SQLException {
		Connection con = null;
		try {
			Class.forName(MYSQL_DRIVER_CLASS);
			con = DriverManager.getConnection("jdbc:mysql://" + MYSQL_DATA.HOST + ":" + MYSQL_DATA.PORT, connectionProperties);
			con.isValid(15);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				con.close();
		}
	}

	private ComboPooledDataSource createCPDSConnection() {
		try {
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			cpds.setDriverClass(MYSQL_DRIVER_CLASS);
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

	private HikariDataSource createHikariConnection() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(MYSQL_DRIVER_CLASS);
		config.setJdbcUrl("jdbc:mysql://" + MYSQL_DATA.HOST + ":" + MYSQL_DATA.PORT + "/" + MYSQL_DATA.DATABASE);
		config.setDataSourceProperties(connectionProperties);
		config.setMinimumIdle(POOL_DATA.MIN_POOL_SIZE);
		config.setMaximumPoolSize(POOL_DATA.MAX_POOL_SIZE);
		return new HikariDataSource(config);
	}

	private void closeConnection() {
		if (cpds != null)
			cpds.close();
		if (hikariDataSource != null)
			hikariDataSource.close();
		hikariDataSource = null;
		cpds = null;
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
			if (hikariDataSource != null)
				return hikariDataSource.getConnection();
			else return cpds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getDatabase() {
		return MYSQL_DATA.DATABASE;
	}
}
