/**
 * @author Simonsator
 * @version 1.0.1
 */
package de.simonsator.partyandfriends.connection;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @author Simonsator
 * @version 1.0.1
 */
public class MySQL {
	/**
	 * Connection to the MySQL server
	 */
	private Connection connect;
	/**
	 * The statement
	 */
	private Statement statement = null;
	/**
	 * The PreparedStatement
	 */
	private PreparedStatement preparedStatement = null;
	/**
	 * The results
	 */
	private ResultSet resultSet = null;
	/**
	 * The MySQL database
	 */
	private String database;
	/**
	 * The url of the SQL server
	 */
	private String url;

	/**
	 * Connects to the MySQL server
	 * 
	 * @param pHost
	 *            The MySQL host
	 * @param pUsername
	 *            The MySQL user
	 * @param pPassword
	 *            The MySQL password
	 * @param pPort
	 *            The port of the MySQL server
	 * @param pDatabase
	 *            The MySQL database
	 * @throws ClassNotFoundException
	 *             Will never happen, because it is integrated in Bungeecord
	 * @throws SQLException
	 *             Happens if the plugin cannot connect to the MySQL Server
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public void connect(String pHost, String pUsername, String pPassword, int pPort, String pDatabase)
			throws ClassNotFoundException, SQLException {
		url = "jdbc:mysql://" + pHost + ":" + pPort + "/?user=" + pUsername + "&password=" + pPassword;
		database = pDatabase;
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection(url);
		statement = connect.createStatement();
		importDatabase();
	}

	/**
	 * Rebuilds the connection
	 * 
	 * @throws ClassNotFoundException
	 *             Will never happen
	 * @throws SQLException
	 *             Happens if the plugin cannot connect to the MySQL Server
	 */
	public void connectTo() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection(url);
		statement = connect.createStatement();
	}

	/**
	 * Returns the connection if there is a connection or if not it creates a
	 * connection and then it returns the connection.
	 * 
	 * @return Returns the connection
	 * @author Simonsator
	 * @version 1.0.1
	 */
	private Connection connect() {
		try {
			if (statement != null && connect != null) {
				return connect;
			} else {
				connect = DriverManager.getConnection(url);
				statement = connect.createStatement();
				return connect;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return connect;
		}
	}

	/**
	 * Returns the statement if there is a statement or if not it creates a
	 * connection and then it returns the statement.
	 * 
	 * @return Returns the statement
	 * @author Simonsator
	 * @version 1.0.0
	 */
	private Statement statement() {
		try {
			if (statement != null && connect != null) {
				return statement;
			} else {
				connect = DriverManager.getConnection(url);
				statement = connect.createStatement();
				return statement;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return statement;
		}
	}

	/**
	 * Imports the databases
	 * 
	 * @author Simonsator
	 * @version 1.0.1
	 */
	private void importDatabase() {
		importDatabaseStandart();
		importHidePlayers();
		importDatabaseNewSettings();
	}

	/**
	 * Imports the standard part of the database
	 * 
	 * @author Simonsator
	 * @version 1.0.1
	 */
	private void importDatabaseStandart() {
		try {
			this.preparedStatement = connect.prepareStatement("CREATE DATABASE IF NOT EXISTS " + database);
			this.preparedStatement.executeUpdate();
			this.preparedStatement = connect.prepareStatement("CREATE TABLE IF NOT EXISTS " + database
					+ ".`freunde` (`UUID` char(38) NOT NULL, `SpielerName` varchar(16) NOT NULL, `FreundeID` varchar(7000) NOT NULL, `FreundschaftsAnfragenID`"
					+ " varchar(7000) NOT NULL, `ID` int(10) NOT NULL, `einstellungAkzeptieren` tinyint(1) NOT NULL, `einstellungPartyNurFreunde`"
					+ " tinyint(1) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
			this.preparedStatement.executeUpdate();
			try {
				this.preparedStatement = connect
						.prepareStatement("ALTER TABLE " + database + ".`freunde` ADD PRIMARY KEY (`ID`);");
				this.preparedStatement.executeUpdate();
			} catch (SQLException e) {

			}
			this.preparedStatement = connect.prepareStatement(
					"ALTER TABLE " + database + ".`freunde` MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT;");
			this.preparedStatement.executeUpdate();
		} catch (SQLException e) {

		}
	}

	/**
	 * Imports the HidePlayers column
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	private void importHidePlayers() {
		try {
			this.preparedStatement = this.connect().prepareStatement("ALTER TABLE " + database
					+ ".`freunde` ADD `EinstellungHidePlayers` tinyint(1) NOT NULL AFTER `einstellungPartyNurFreunde`;");
			this.preparedStatement.executeUpdate();
		} catch (SQLException e) {

		}
	}

	/**
	 * Imports the new settings columns
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	private void importDatabaseNewSettings() {
		try {
			this.preparedStatement = this.connect().prepareStatement("ALTER TABLE `" + database + "`.`freunde`\n"
					+ "ADD COLUMN `freunde`.`EinstellungSendMessages` tinyint(1) NOT NULL COMMENT '' AFTER `EinstellungHidePlayers`,\n"
					+ "ADD COLUMN `freunde`.`EinstellungImmerOffline` tinyint(1) NOT NULL COMMENT '' AFTER `EinstellungSendMessages`,\n"
					+ "ADD COLUMN `freunde`.`EinstellungJump` tinyint(1) NOT NULL COMMENT '' AFTER `EinstellungImmerOffline`;");
			this.preparedStatement.executeUpdate();
		} catch (SQLException e) {
		}
	}

	/**
	 * Returns the ID of a player
	 * 
	 * @param pUuid
	 *            The UUID of the player
	 * @return Returns the ID of a player
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public int getIDByUUID(String pUuid) {
		try {
			try {
				resultSet = statement()
						.executeQuery("select ID from " + database + ".freunde WHERE UUID='" + pUuid + "' LIMIT 1");
				if (resultSet.next()) {
					return resultSet.getInt("ID");
				} else {
					return -1;
				}
			} catch (SQLException e) {
				try {
					close();
					connectTo();
					return getIDByUUID(pUuid);
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
					return -1;
				}
			}
		} catch (NullPointerException e) {
			return -1;
		}
	}

	/**
	 * Returns the ID of a player
	 * 
	 * @param pName
	 *            Name of the player Returns the ID of a player
	 * @return Returns the ID of a player
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public int getIDByPlayerName(String pName) {
		try {
			try {
				resultSet = statement().executeQuery(
						"select ID from " + database + ".freunde WHERE SpielerName='" + pName + "' LIMIT 1");
				if (resultSet.next()) {
					return resultSet.getInt("ID");
				} else {
					return -1;
				}
			} catch (SQLException e) {
				try {
					close();
					connectTo();
					return getIDByPlayerName(pName);
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
					return -1;
				}
			}
		} catch (NullPointerException e) {
			return -1;
		}
	}

	/**
	 * Will be executed if a player joins the first time on the server
	 * 
	 * @param spieler
	 *            The player
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public void firstJoin(ProxiedPlayer spieler) {
		try {
			this.preparedStatement = this.connect().prepareStatement(
					"insert into  " + this.database + ".freunde values (?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?)");
			this.preparedStatement.setString(1, spieler.getUniqueId() + "");
			this.preparedStatement.setString(2, spieler.getName());
			this.preparedStatement.setString(3, "");
			this.preparedStatement.setString(4, "");
			this.preparedStatement.setNull(5, 5);
			this.preparedStatement.setInt(6, 1);
			this.preparedStatement.setInt(7, 0);
			this.preparedStatement.setInt(8, 0);
			this.preparedStatement.setInt(9, 0);
			this.preparedStatement.setInt(10, 0);
			this.preparedStatement.setInt(11, 0);
			this.preparedStatement.executeUpdate();
		} catch (SQLException e) {
			try {
				close();
				connectTo();
				firstJoin(spieler);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Gives out the IDs of the friends of a player
	 * 
	 * @param iD
	 *            The ID of the player
	 * @return Returns the IDs of the friends of a player
	 * @author Simonsator
	 * @version 1.0.0
	 */
	private String getFriends(int iD) {
		try {
			resultSet = statement()
					.executeQuery("select FreundeID from " + database + ".freunde WHERE ID='" + iD + "' LIMIT 1");
			if (resultSet.next()) {
				return resultSet.getString("FreundeID");
			} else {
				return "";
			}
		} catch (SQLException e) {
			try {
				close();
				connectTo();
				return getFriends(iD);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
				return "";
			}
		}
	}

	/**
	 * Returns the name of a player
	 * 
	 * @param pID
	 *            The ID of the player
	 * @return Returns the name of a player
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public String getPlayerName(int pID) {
		try {
			resultSet = statement()
					.executeQuery("select SpielerName from " + database + ".freunde WHERE ID='" + pID + "' LIMIT 1");
			if (resultSet.next()) {
				return resultSet.getString("SpielerName");
			} else {
				return "";
			}
		} catch (SQLException e) {
			try {
				close();
				connectTo();
				return getPlayerName(pID);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
				return "";
			}
		}
	}

	/**
	 * Updates the name of a player
	 * 
	 * @param id
	 *            The ID of the player
	 * @param nameDesSpielers
	 *            New name of the player
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public void updatePlayerName(int id, String nameDesSpielers) {
		try {
			this.preparedStatement = this.connect().prepareStatement("UPDATE " + this.database
					+ ".freunde set SpielerName='" + nameDesSpielers + "' WHERE ID='" + id + "' LIMIT 1");
			this.preparedStatement.executeUpdate();
		} catch (SQLException e) {
			try {
				close();
				connectTo();
				updatePlayerName(id, nameDesSpielers);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Returns the IDs of the friends from a player
	 * 
	 * @param id
	 *            The ID of the player
	 * @return Returns the IDs of the friends from a player
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public String getRequests(int id) {
		try {
			resultSet = statement().executeQuery(
					"select FreundschaftsAnfragenID from " + database + ".freunde WHERE ID='" + id + "' LIMIT 1");
			resultSet.next();
			return resultSet.getString("FreundschaftsAnfragenID");
		} catch (SQLException e) {
			try {
				close();
				connectTo();
				return getRequests(id);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
				return "";
			}
		}
	}

	/**
	 * Returns an ArrayList, which contains the names of the friends
	 * 
	 * @param id
	 *            The ID of the player
	 * @return Returns an ArrayList, which contains the names of the friends
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public ArrayList<String> getRequestsAsArrayList(int id) {
		StringTokenizer st = new StringTokenizer(getRequests(id), "|");
		ArrayList<String> friends = new ArrayList<>();
		while (st.hasMoreTokens()) {
			friends.add(getPlayerName(Integer.parseInt(st.nextToken())));
		}
		return friends;

	}

	/**
	 * Adds a player to friends
	 * 
	 * @param idSender
	 *            The sender of the command
	 * @param idQuery
	 *            The new friend
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public void addPlayer(int idSender, int idQuery) {
		try {
			String friendIDSenderToEdit = getFriends(idSender);
			String friendIDSenderString = "";
			if (friendIDSenderToEdit.equals("")) {
				friendIDSenderString = "" + idQuery;
			} else {
				friendIDSenderString = friendIDSenderToEdit + "|" + idQuery;
			}
			this.preparedStatement = this.connect().prepareStatement("UPDATE " + this.database
					+ ".freunde set FreundeID='" + friendIDSenderString + "' WHERE ID='" + idSender + "' LIMIT 1");
			this.preparedStatement.executeUpdate();
			String freundeIDAbfragebearbeiten = getFriends(idQuery);
			String freundeIDAbfrageString = "";
			if (freundeIDAbfragebearbeiten.equals("")) {
				freundeIDAbfrageString = "" + idSender;
			} else {
				freundeIDAbfrageString = freundeIDAbfragebearbeiten + "|" + idSender;
			}
			this.preparedStatement = this.connect().prepareStatement("UPDATE " + this.database
					+ ".freunde set FreundeID='" + freundeIDAbfrageString + "' WHERE ID='" + idQuery + "' LIMIT 1");
			this.preparedStatement.executeUpdate();
			this.preparedStatement = this.connect().prepareStatement("UPDATE " + this.database
					+ ".freunde set FreundeID='" + freundeIDAbfrageString + "' WHERE ID='" + idQuery + "' LIMIT 1");
			this.preparedStatement.executeUpdate();
			denyPlayer(idSender, idQuery);
		} catch (SQLException e) {
			try {
				close();
				connectTo();
				addPlayer(idSender, idQuery);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Removes a friend request
	 * 
	 * @param idSender
	 *            The ID of the command executer
	 * @param idQuery
	 *            The ID of the person who had send the friend request
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public void denyPlayer(int idSender, int idQuery) {
		try {
			String toSplit = getRequests(idSender);
			String friendRequestString = "";
			if (!toSplit.equals(idQuery + "")) {
				StringTokenizer st = new StringTokenizer(toSplit, "|");
				int[] requestID = new int[st.countTokens()];
				int n = 0;
				while (st.hasMoreTokens()) {
					requestID[n] = Integer.parseInt(st.nextToken());
				}
				boolean found = false;
				int i = 0;
				while (found != true && i < requestID.length) {
					if (idQuery == requestID[i]) {
						requestID[i] = -1;
						found = true;
					}
					i++;
				}
				String together = "";
				if (requestID[0] != -1) {
					together = requestID[0] + "";
				}
				i = 1;
				while (requestID.length > i) {
					if (requestID[i] != -1 && requestID[i] != 0) {
						if (together.equals("")) {
							together = requestID[i] + "";
						} else {
							together = together + "|" + requestID[i];
						}
					}
					i++;
				}
				friendRequestString = together;
			}
			this.preparedStatement = connect
					.prepareStatement("UPDATE " + this.database + ".freunde set FreundschaftsAnfragenID='"
							+ friendRequestString + "' WHERE ID='" + idSender + "' LIMIT 1");
			this.preparedStatement.executeUpdate();
		} catch (SQLException e) {
			try {
				close();
				connectTo();
				denyPlayer(idSender, idQuery);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Deletes a friend
	 * 
	 * @param idSender
	 *            The ID of the command sender
	 * @param idQuery
	 *            The ID of the friend, which should be deleted
	 * @param aufrufAnzahl
	 *            If it´s the first passage or the second
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public void deleteFriend(int idSender, int idQuery, int aufrufAnzahl) {
		try {
			String request = getFriends(idSender);
			String friendRequestString = "";
			if (request.equals(idQuery + "")) {
			} else {
				String toSplit = getFriends(idSender);
				StringTokenizer st = new StringTokenizer(toSplit, "|");
				int[] requestID = new int[st.countTokens()];
				int n = 0;
				while (st.hasMoreTokens()) {
					requestID[n] = Integer.parseInt(st.nextToken());
					n++;
				}
				boolean found = false;
				int i = 0;
				while (found != true) {
					if (idQuery == requestID[i]) {
						requestID[i] = -1;
						found = true;
					}
					i++;
				}
				String ersterWert = "";
				if (requestID[0] != -1) {
					ersterWert = requestID[0] + "";
				}
				String together = ersterWert;
				i = 1;
				while (requestID.length > i) {
					if (requestID[i] == -1) {

					} else {
						if (together.equals("")) {
							together = requestID[i] + "";
						} else {
							together = together + "|" + requestID[i];
						}
					}
					i++;
				}
				friendRequestString = together;
			}
			this.preparedStatement = this.connect().prepareStatement("UPDATE " + this.database
					+ ".freunde set FreundeID='" + friendRequestString + "' WHERE ID='" + idSender + "' LIMIT 1");
			this.preparedStatement.executeUpdate();
			if (aufrufAnzahl == 0) {
				deleteFriend(idQuery, idSender, 1);
			}
		} catch (SQLException e) {
			try {
				close();
				connectTo();
				deleteFriend(idSender, idQuery, aufrufAnzahl);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Send a friend request
	 * 
	 * @param idSender
	 *            The ID of Sender of the friend request
	 * @param idQuery
	 *            The ID of the player, which gets the friend request
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public void sendFriendRequest(int idSender, int idQuery) {
		try {
			String requests = getRequests(idQuery);
			String zuSetzten = "";
			if (requests.equals("")) {
				zuSetzten = "" + idSender;
			} else {
				zuSetzten = requests + "|" + idSender;
			}
			this.preparedStatement = this.connect().prepareStatement("UPDATE " + this.database
					+ ".freunde set FreundschaftsAnfragenID='" + zuSetzten + "' WHERE ID='" + idQuery + "' LIMIT 1");
			this.preparedStatement.executeUpdate();
		} catch (SQLException e) {
			try {
				close();
				connectTo();
				sendFriendRequest(idSender, idQuery);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Sets a setting
	 * 
	 * @param player
	 *            The player
	 * @param typ
	 *            The type of setting
	 * @return Returns the new worth
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public int setSettings(ProxiedPlayer player, int typ) {
		try {
			int playerID = getIDByPlayerName(player.getName());
			int newWorth = 0;
			switch (typ) {
			case 0: {
				resultSet = statement().executeQuery("select einstellungPartyNurFreunde from " + database
						+ ".freunde WHERE ID='" + playerID + "' LIMIT 1");
				resultSet.next();
				int momWorth = resultSet.getInt("einstellungPartyNurFreunde");
				if (momWorth == 0) {
					newWorth = 1;
				}
				this.preparedStatement = this.connect()
						.prepareStatement("UPDATE " + this.database + ".freunde set einstellungPartyNurFreunde='"
								+ newWorth + "' WHERE ID='" + playerID + "' LIMIT 1");
				this.preparedStatement.executeUpdate();
				break;
			}
			case 1: {
				resultSet = statement().executeQuery("select einstellungAkzeptieren from " + database
						+ ".freunde WHERE ID='" + playerID + "' LIMIT 1");
				resultSet.next();
				int momWorth = resultSet.getInt("einstellungAkzeptieren");
				if (momWorth == 0) {
					newWorth = 1;
				}
				this.preparedStatement = this.connect().prepareStatement("UPDATE " + this.database
						+ ".freunde set einstellungAkzeptieren='" + newWorth + "' WHERE ID='" + playerID + "' LIMIT 1");
				this.preparedStatement.executeUpdate();
				break;
			}
			case 2: {
				resultSet = statement().executeQuery("select EinstellungSendMessages from " + database
						+ ".freunde WHERE ID='" + playerID + "' LIMIT 1");
				resultSet.next();
				int momWorth = resultSet.getInt("EinstellungSendMessages");
				if (momWorth == 0) {
					newWorth = 1;
				}
				this.preparedStatement = this.connect()
						.prepareStatement("UPDATE " + this.database + ".freunde set EinstellungSendMessages='"
								+ newWorth + "' WHERE ID='" + playerID + "' LIMIT 1");
				this.preparedStatement.executeUpdate();
				break;
			}
			case 3: {
				resultSet = statement().executeQuery("select EinstellungImmerOffline from " + database
						+ ".freunde WHERE ID='" + playerID + "' LIMIT 1");
				resultSet.next();
				int momWorth = resultSet.getInt("EinstellungImmerOffline");
				if (momWorth == 0) {
					newWorth = 1;
				}
				this.preparedStatement = this.connect()
						.prepareStatement("UPDATE " + this.database + ".freunde set EinstellungImmerOffline='"
								+ newWorth + "' WHERE ID='" + playerID + "' LIMIT 1");
				this.preparedStatement.executeUpdate();
				break;
			}
			case 4: {
				resultSet = statement().executeQuery(
						"select EinstellungJump from " + database + ".freunde WHERE ID='" + playerID + "' LIMIT 1");
				resultSet.next();
				int momWorth = resultSet.getInt("EinstellungJump");
				if (momWorth == 0) {
					newWorth = 1;
				}
				this.preparedStatement = this.connect().prepareStatement("UPDATE " + this.database
						+ ".freunde set EinstellungJump='" + newWorth + "' WHERE ID='" + playerID + "' LIMIT 1");
				break;
			}
			}
			return newWorth;
		} catch (SQLException e) {
			try {
				close();
				connectTo();
				return setSettings(player, typ);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
				return 0;
			}
		}
	}

	/**
	 * Query the worth of the settings
	 * 
	 * @param player
	 *            The player
	 * @return Returns the worth of the settings
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public int[] querySettings(ProxiedPlayer player) {
		try {
			int playerID = getIDByPlayerName(player.getName());
			resultSet = statement().executeQuery(
					"select einstellungAkzeptieren from " + database + ".freunde WHERE ID='" + playerID + "' LIMIT 1");
			int[] feld = new int[5];
			if (resultSet.next()) {
				feld[0] = resultSet.getInt("einstellungAkzeptieren");
			} else {
				feld[0] = 1;
			}
			resultSet = statement().executeQuery("select einstellungPartyNurFreunde from " + database
					+ ".freunde WHERE ID='" + playerID + "' LIMIT 1");
			if (resultSet.next()) {
				feld[1] = resultSet.getInt("einstellungPartyNurFreunde");
			} else {
				feld[1] = 0;
			}
			resultSet = statement().executeQuery(
					"select EinstellungSendMessages from " + database + ".freunde WHERE ID='" + playerID + "' LIMIT 1");
			if (resultSet.next()) {
				feld[2] = resultSet.getInt("EinstellungSendMessages");
			} else {
				feld[2] = 0;
			}
			resultSet = statement().executeQuery(
					"select EinstellungImmerOffline from " + database + ".freunde WHERE ID='" + playerID + "' LIMIT 1");
			if (resultSet.next()) {
				feld[3] = resultSet.getInt("EinstellungImmerOffline");
			} else {
				feld[3] = 0;
			}
			resultSet = statement().executeQuery(
					"select EinstellungJump from " + database + ".freunde WHERE ID='" + playerID + "' LIMIT 1");
			if (resultSet.next()) {
				feld[4] = resultSet.getInt("EinstellungJump");
			} else {
				feld[4] = 0;
			}
			return feld;
		} catch (SQLException e) {
			try {
				close();
				connectTo();
				return querySettings(player);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * Checks if somebody is a friend of someone others
	 * 
	 * @param player
	 *            The player
	 * @param angeschrieben
	 *            The other player
	 * @return Returns if player one is a friend of player two true, otherwise
	 *         false
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public boolean isFriendWith(ProxiedPlayer player, ProxiedPlayer angeschrieben) {
		int idSender = getIDByPlayerName(player.getName());
		int idQuery = getIDByPlayerName(angeschrieben.getName());
		int[] requestID = getFriendsAsArray(idSender);
		int i = 0;
		while (requestID.length > i) {
			if (requestID[i] == idQuery) {
				return true;
			}
			i++;
		}
		return false;
	}

	/**
	 * Returns The IDs of the friends of the sender
	 * 
	 * @param idSender
	 *            The ID of the sender of the command
	 * @return Returns The IDs of the friends of the sender
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public int[] getFriendsAsArray(int idSender) {
		StringTokenizer st = new StringTokenizer(getFriends(idSender), "|");
		int[] requestID = new int[st.countTokens()];
		int n = 0;
		while (st.hasMoreTokens()) {
			requestID[n] = Integer.parseInt(st.nextToken());
			n++;
		}
		return requestID;
	}

	/**
	 * Quest if someone allows friend requests
	 * 
	 * @param idQuery
	 *            The ID of the player
	 * @return Returns true if he allows friend requests, otherwise it returns
	 *         false
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public boolean allowsFriendRequsts(int idQuery) {
		try {
			resultSet = statement().executeQuery(
					"select einstellungAkzeptieren from " + database + ".freunde WHERE ID='" + idQuery + "' LIMIT 1");
			resultSet.next();
			if (resultSet.getInt("einstellungAkzeptieren") == 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			try {
				close();
				connectTo();
				return allowsFriendRequsts(idQuery);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * Quest if someone allows party invitations
	 * 
	 * @param playerName
	 *            The name of the player
	 * @return Returns true if he allows party invitations, otherwise it returns
	 *         false
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public boolean allowsPartyRequests(String playerName) {
		int idQuery = getIDByPlayerName(playerName);
		try {
			resultSet = statement().executeQuery("select einstellungPartyNurFreunde from " + database
					+ ".freunde WHERE ID='" + idQuery + "' LIMIT 1");
			resultSet.next();
			if (resultSet.getInt("einstellungPartyNurFreunde") == 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			try {
				close();
				connectTo();
				return allowsPartyRequests(playerName);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * Checks if somebody is a friend of someone others
	 * 
	 * @param name
	 *            The player
	 * @param string
	 *            The other player
	 * @return Returns if player one is a friend of player two true, otherwise
	 *         false
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public boolean isFriendWith(String name, String string) {
		int idSender = getIDByPlayerName(name);
		int idQuery = getIDByPlayerName(string);
		String toSplit = getFriends(idSender);
		int[] requestID = new int[0];
		StringTokenizer st = new StringTokenizer(toSplit, "|");
		while (st.hasMoreTokens()) {
			Object newArray = Array.newInstance(requestID.getClass().getComponentType(),
					Array.getLength(requestID) + 1);
			System.arraycopy(requestID, 0, newArray, 0, Array.getLength(requestID));
			requestID = (int[]) newArray;
			requestID[Array.getLength(requestID) - 1] = Integer.parseInt(st.nextToken());
		}
		boolean found = false;
		int i = 0;
		while (requestID.length > i && found == false) {
			if (requestID[i] == idQuery) {
				found = true;
			}
			i++;
		}
		return found;
	}

	/**
	 * Closes the MySQL connection.
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect() != null) {
				connect().close();
			}
		} catch (Exception e) {

		}
	}
}