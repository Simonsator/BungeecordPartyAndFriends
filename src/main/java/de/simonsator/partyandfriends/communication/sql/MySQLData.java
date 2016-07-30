package de.simonsator.partyandfriends.communication.sql;

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

	public MySQLData(String host, String username, String password, int port, String database, String table_prefix) {
		HOST = host;
		USERNAME = username;
		PASSWORD = password;
		PORT = port;
		DATABASE = database;
		TABLE_PREFIX = table_prefix;
	}
}
