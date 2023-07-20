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
	private final String DRIVER_URL;

	public PoolSQLCommunication(MySQLData pMySQLData, PoolData pPoolData) throws SQLException {
		super(pMySQLData.USE_MARIA_DB_DRIVER);
		MYSQL_DATA = pMySQLData;
		POOL_DATA = pPoolData;
		if (pMySQLData.USE_MARIA_DB_DRIVER) {
			DRIVER_URL = "jdbc:mariadb://";
		} else {
			DRIVER_URL = "jdbc:mysql://";
		}
		connectionProperties = new Properties();
		connectionProperties.setProperty("user", MYSQL_DATA.USERNAME);
		connectionProperties.setProperty("password", MYSQL_DATA.PASSWORD);
		connectionProperties.setProperty("useSSL", String.valueOf(pMySQLData.USE_SSL));
		connectionProperties.setProperty("allowPublicKeyRetrieval", String.valueOf(!pMySQLData.USE_SSL));
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

	/**
	 * This method should only be used if you know that another object extending this class has already called {@link #PoolSQLCommunication(MySQLData, PoolData)} to initialize the connection pool.
	 *
	 * @throws IllegalStateException If the connection has not yet been initialized by {@link #PoolSQLCommunication(MySQLData, PoolData)} by another object extending this class (probably Party and Friends)
	 */
	public PoolSQLCommunication() throws IllegalStateException {
		super(false);
		MYSQL_DATA = null;
		POOL_DATA = null;
		connectionProperties = null;
		DRIVER_URL = null;
	}

	public static boolean hasBeenInitialized() {
		return hikariDataSource != null || cpds != null;
	}

	private void checkIfConnectionIsValid() throws SQLException {
		try (Connection con = DriverManager.getConnection(DRIVER_URL + MYSQL_DATA.HOST + ":" + MYSQL_DATA.PORT, connectionProperties)) {
			con.isValid(15);
		}
	}

	private ComboPooledDataSource createCPDSConnection() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setJdbcUrl(DRIVER_URL + MYSQL_DATA.HOST + ":" + MYSQL_DATA.PORT + "/" + MYSQL_DATA.DATABASE);
		cpds.setProperties(connectionProperties);
		cpds.setInitialPoolSize(POOL_DATA.INITIAL_POOL_SIZE);
		cpds.setMinPoolSize(POOL_DATA.MIN_POOL_SIZE);
		cpds.setMaxPoolSize(POOL_DATA.MAX_POOL_SIZE);
		cpds.setTestConnectionOnCheckin(POOL_DATA.TEST_CONNECTION_ON_CHECKIN);
		cpds.setIdleConnectionTestPeriod(POOL_DATA.IDLE_CONNECTION_TEST_PERIOD);
		return cpds;
	}

	private HikariDataSource createHikariConnection() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(DRIVER_URL + MYSQL_DATA.HOST + ":" + MYSQL_DATA.PORT + "/" + MYSQL_DATA.DATABASE);
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
