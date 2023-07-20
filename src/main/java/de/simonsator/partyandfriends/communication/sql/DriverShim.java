package de.simonsator.partyandfriends.communication.sql;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class DriverShim implements Driver {
	private final Driver DRIVER;

	public DriverShim(Driver d) {
		this.DRIVER = d;
	}

	public boolean acceptsURL(String u) throws SQLException {
		return this.DRIVER.acceptsURL(u);
	}

	public Connection connect(String u, Properties p) throws SQLException {
		return this.DRIVER.connect(u, p);
	}

	public int getMajorVersion() {
		return this.DRIVER.getMajorVersion();
	}

	public int getMinorVersion() {
		return this.DRIVER.getMinorVersion();
	}

	public DriverPropertyInfo[] getPropertyInfo(String u, Properties p) throws SQLException {
		return this.DRIVER.getPropertyInfo(u, p);
	}

	public boolean jdbcCompliant() {
		return this.DRIVER.jdbcCompliant();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return DRIVER.getParentLogger();
	}
}