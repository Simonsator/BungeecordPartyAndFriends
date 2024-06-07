package de.simonsator.partyandfriends.communication.sql;

import de.simonsator.partyandfriends.main.Main;

/**
 * @author Simonsator
 * @version 1.0.0 on 19.07.16.
 */
public class MySQLData {
	public final String HOST;
	public final String USERNAME;
	public final String PASSWORD;
	public final int PORT;
	public final String DATABASE;
	public final String TABLE_PREFIX;
	public final boolean USE_SSL;
	public final boolean CACHE;
	public final int EXPIRATION_TIME;
	public final boolean EXPIRATION_ACTIVATED;
	public final boolean USE_MARIA_DB_DRIVER = Main.getInstance().getGeneralConfig().getBoolean("MySQL.UseMariaDBConnector");

	public MySQLData(String host, String username, String password, int port, String database, String pTablePrefix) {
		this(host, username, password, port, database, pTablePrefix, false);
	}

	public MySQLData(String host, String username, String password, int port, String database, String pTablePrefix, boolean pUseSSL) {
		this(host, username, password, port, database, pTablePrefix, pUseSSL, true);
	}

	public MySQLData(String host, String username, String password, int port, String database, String pTablePrefix, boolean pUseSSL, boolean pCache) {
		this(host, username, password, port, database, pTablePrefix, pUseSSL, pCache, false, 0);
	}

	public MySQLData(String host, String username, String password, int port, String database, String pTablePrefix, boolean pUseSSL, boolean pCache, boolean pExpirationActivated, int pExpirationTime) {
		if (host.endsWith(":3306")) {
			host = host.substring(0, host.length() - 5);
		}
		HOST = host;
		USERNAME = username;
		PASSWORD = password;
		PORT = port;
		DATABASE = database;
		TABLE_PREFIX = pTablePrefix;
		USE_SSL = pUseSSL;
		CACHE = pCache;
		EXPIRATION_ACTIVATED = pExpirationActivated;
		EXPIRATION_TIME = pExpirationTime;
	}

}
