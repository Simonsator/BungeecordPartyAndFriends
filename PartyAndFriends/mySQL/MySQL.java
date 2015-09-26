/**
 * @author Simonsator
 * @version 1.0.1
 */
package partyAndFriends.mySQL;

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
	 *             Happens if the plugin can not connect to the MySQL Server
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public void verbinde(String pHost, String pUsername, String pPassword, int pPort, String pDatabase)
			throws ClassNotFoundException, SQLException {
		url = "jdbc:mysql://" + pHost + ":" + pPort + "/?user=" + pUsername + "&password=" + pPassword;
		database = pDatabase;
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection(url);
		statement = connect.createStatement();
		datenbankImportieren();
	}

	/**
	 * Rebuilds the connection
	 * 
	 * @throws ClassNotFoundException
	 *             Will never happen
	 * @throws SQLException
	 *             Happens if the plugin can not connect to the MySQL Server
	 */
	public void verbinde() throws ClassNotFoundException, SQLException {
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
	private void datenbankImportieren() {
		datenbankImportierenStandart();
		datenbankImportierenHidePlayers();
		datenbankImportierenNeueEinstellungen();
	}

	/**
	 * Imports the standard part of the database
	 * 
	 * @author Simonsator
	 * @version 1.0.1
	 */
	private void datenbankImportierenStandart() {
		try {
			this.preparedStatement = connect.prepareStatement("CREATE DATABASE IF NOT EXISTS " + database);
			this.preparedStatement.executeUpdate();
			this.preparedStatement = connect.prepareStatement("CREATE TABLE IF NOT EXISTS " + database
					+ ".`freunde` (`UUID` char(38) NOT NULL, `SpielerName` varchar(16) NOT NULL, `FreundeID` text NOT NULL, `FreundschaftsAnfragenID`"
					+ " text NOT NULL, `ID` int(255) NOT NULL, `einstellungAkzeptieren` tinyint(1) NOT NULL, `einstellungPartyNurFreunde`"
					+ " tinyint(1) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
			this.preparedStatement.executeUpdate();
			try {
				this.preparedStatement = connect
						.prepareStatement("ALTER TABLE " + database + ".`freunde` ADD PRIMARY KEY (`ID`);");
				this.preparedStatement.executeUpdate();
			} catch (SQLException e) {

			}
			this.preparedStatement = connect.prepareStatement(
					"ALTER TABLE " + database + ".`freunde` MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT;");
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
	private void datenbankImportierenHidePlayers() {
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
	private void datenbankImportierenNeueEinstellungen() {
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
	public int ID(String pUuid) {
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
				verbinde();
				return ID(pUuid);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
				return -1;
			}
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
					verbinde();
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
	public void erstesmal(ProxiedPlayer spieler) {
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
				verbinde();
				erstesmal(spieler);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Gives out the IDs of the friends of a player
	 * 
	 * @param uuid
	 *            The UUID of the player
	 * @return Returns the IDs of the friends of a player
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public String freundeAusgeben(String uuid) {
		try {
			int iD = ID(uuid);
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
				verbinde();
				return freundeAusgeben(uuid);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
				return "";
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
	public String freundeAusgeben(int iD) {
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
				verbinde();
				return freundeAusgeben(iD);
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
	public String getNameDesSpielers(int pID) {
		try {
			resultSet = statement()
					.executeQuery("select SpielerName from " + database + ".freunde WHERE ID='" + pID + "' LIMIT 1");
			resultSet.next();
			return resultSet.getString("SpielerName");
		} catch (SQLException e) {
			try {
				close();
				verbinde();
				return getNameDesSpielers(pID);
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
	public void updateSpielerName(int id, String nameDesSpielers) {
		try {
			this.preparedStatement = this.connect().prepareStatement("UPDATE " + this.database
					+ ".freunde set SpielerName='" + nameDesSpielers + "' WHERE ID='" + id + "' LIMIT 1");
			this.preparedStatement.executeUpdate();
		} catch (SQLException e) {
			try {
				close();
				verbinde();
				updateSpielerName(id, nameDesSpielers);
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
	public String getAnfragen(int id) {
		try {
			resultSet = statement().executeQuery(
					"select FreundschaftsAnfragenID from " + database + ".freunde WHERE ID='" + id + "' LIMIT 1");
			resultSet.next();
			return resultSet.getString("FreundschaftsAnfragenID");
		} catch (SQLException e) {
			try {
				close();
				verbinde();
				return getAnfragen(id);
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
	public ArrayList<String> getAnfragenArrayList(int id) {
		StringTokenizer st = new StringTokenizer(getAnfragen(id), "|");
		ArrayList<String> friends = new ArrayList<>();
		while (st.hasMoreTokens()) {
			friends.add(getNameDesSpielers(Integer.parseInt(st.nextToken())));
		}
		return friends;

	}

	/**
	 * Adds a player to friends
	 * 
	 * @param idSender
	 *            The sender of the command
	 * @param idAbfrage
	 *            The new friend
	 * @author Simonsator
	 * @version 1.0.0
	 */

	public void spielerHinzufuegen(int idSender, int idAbfrage) {
		try {
			String freundeIDSenderbearbeiten = freundeAusgeben(idSender);
			String freundeIDSenderString = "";
			if (freundeIDSenderbearbeiten.equals("")) {
				freundeIDSenderString = "" + idAbfrage;
			} else {
				freundeIDSenderString = freundeIDSenderbearbeiten + "|" + idAbfrage;
			}
			this.preparedStatement = this.connect().prepareStatement("UPDATE " + this.database
					+ ".freunde set FreundeID='" + freundeIDSenderString + "' WHERE ID='" + idSender + "' LIMIT 1");
			this.preparedStatement.executeUpdate();
			String freundeIDAbfragebearbeiten = freundeAusgeben(idAbfrage);
			String freundeIDAbfrageString = "";
			if (freundeIDAbfragebearbeiten.equals("")) {
				freundeIDAbfrageString = "" + idSender;
			} else {
				freundeIDAbfrageString = freundeIDAbfragebearbeiten + "|" + idSender;
			}
			this.preparedStatement = this.connect().prepareStatement("UPDATE " + this.database
					+ ".freunde set FreundeID='" + freundeIDAbfrageString + "' WHERE ID='" + idAbfrage + "' LIMIT 1");
			this.preparedStatement.executeUpdate();
			this.preparedStatement = this.connect().prepareStatement("UPDATE " + this.database
					+ ".freunde set FreundeID='" + freundeIDAbfrageString + "' WHERE ID='" + idAbfrage + "' LIMIT 1");
			this.preparedStatement.executeUpdate();
			spielerAblehnen(idSender, idAbfrage);
		} catch (SQLException e) {
			try {
				close();
				verbinde();
				spielerHinzufuegen(idSender, idAbfrage);
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
	 * @param idAbfrage
	 *            The ID of the person who had send the friend request
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public void spielerAblehnen(int idSender, int idAbfrage) {
		try {
			String anfragen = getAnfragen(idSender);
			String freundschaftsAnfragenString = "";
			if (!anfragen.equals(idAbfrage + "")) {
				String zuTrennen = getAnfragen(idSender);
				StringTokenizer st = new StringTokenizer(zuTrennen, "|");
				int[] anfragenID = new int[st.countTokens()];
				int dl = 0;
				while (st.hasMoreTokens()) {
					anfragenID[dl] = Integer.parseInt(st.nextToken());
				}
				boolean gefunden = false;
				int i = 0;
				while (gefunden != true) {
					if (idAbfrage == anfragenID[i]) {
						anfragenID[i] = -1;
						gefunden = true;
					}
					i++;
				}
				String ersterWert = "";
				if (anfragenID[0] != -1) {
					ersterWert = anfragenID[0] + "";
				}
				String zusammengesetzt = ersterWert;
				i = 1;
				while (anfragenID.length > i) {
					if (anfragenID[i] == -1) {

					} else {
						if (zusammengesetzt.equals("")) {
							zusammengesetzt = anfragenID[i] + "";
						} else {
							zusammengesetzt = zusammengesetzt + "|" + anfragenID[i];
						}
					}
					i++;
				}
				freundschaftsAnfragenString = zusammengesetzt;
			}
			this.preparedStatement = this.connect()
					.prepareStatement("UPDATE " + this.database + ".freunde set FreundschaftsAnfragenID='"
							+ freundschaftsAnfragenString + "' WHERE ID='" + idSender + "' LIMIT 1");
			this.preparedStatement.executeUpdate();
		} catch (SQLException e) {
			try {
				close();
				verbinde();
				spielerHinzufuegen(idSender, idAbfrage);
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
	 * @param idAbfrage
	 *            The ID of the friend, which should be deleted
	 * @param aufrufAnzahl
	 *            If it´s the first passage or the second
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public void freundLoeschen(int idSender, int idAbfrage, int aufrufAnzahl) {
		try {
			String anfragen = freundeAusgeben(idSender);
			String freundschaftsAnfragenString = "";
			if (anfragen.equals(idAbfrage + "")) {
			} else {
				String zuTrennen = freundeAusgeben(idSender);
				StringTokenizer st = new StringTokenizer(zuTrennen, "|");
				int[] anfragenID = new int[st.countTokens()];
				int dl = 0;
				while (st.hasMoreTokens()) {
					anfragenID[dl] = Integer.parseInt(st.nextToken());
					dl++;
				}
				boolean gefunden = false;
				int i = 0;
				while (gefunden != true) {
					if (idAbfrage == anfragenID[i]) {
						anfragenID[i] = -1;
						gefunden = true;
					}
					i++;
				}
				String ersterWert = "";
				if (anfragenID[0] != -1) {
					ersterWert = anfragenID[0] + "";
				}
				String zusammengesetzt = ersterWert;
				i = 1;
				while (anfragenID.length > i) {
					if (anfragenID[i] == -1) {

					} else {
						if (zusammengesetzt.equals("")) {
							zusammengesetzt = anfragenID[i] + "";
						} else {
							zusammengesetzt = zusammengesetzt + "|" + anfragenID[i];
						}
					}
					i++;
				}
				freundschaftsAnfragenString = zusammengesetzt;
			}
			this.preparedStatement = this.connect()
					.prepareStatement("UPDATE " + this.database + ".freunde set FreundeID='"
							+ freundschaftsAnfragenString + "' WHERE ID='" + idSender + "' LIMIT 1");
			this.preparedStatement.executeUpdate();
			if (aufrufAnzahl == 0) {
				freundLoeschen(idAbfrage, idSender, 1);
			}
		} catch (SQLException e) {
			try {
				close();
				verbinde();
				freundLoeschen(idSender, idAbfrage, aufrufAnzahl);
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
	 * @param idAbfrage
	 *            The ID of the player, which gets the friend request
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public void sendFreundschaftsAnfrage(int idSender, int idAbfrage) {
		try {
			String schonBekommeneAnfragen = getAnfragen(idAbfrage);
			String zuSetzten = "";
			if (schonBekommeneAnfragen.equals("")) {
				zuSetzten = "" + idSender;
			} else {
				zuSetzten = schonBekommeneAnfragen + "|" + idSender;
			}
			this.preparedStatement = this.connect().prepareStatement("UPDATE " + this.database
					+ ".freunde set FreundschaftsAnfragenID='" + zuSetzten + "' WHERE ID='" + idAbfrage + "' LIMIT 1");
			this.preparedStatement.executeUpdate();
		} catch (SQLException e) {
			try {
				close();
				verbinde();
				sendFreundschaftsAnfrage(idSender, idAbfrage);
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
	public int einstellungenSetzen(ProxiedPlayer player, int typ) {
		try {
			int playerID = getIDByPlayerName(player.getName());
			int neuerWert = 0;
			switch (typ) {
			case 0: {
				resultSet = statement().executeQuery("select einstellungPartyNurFreunde from " + database
						+ ".freunde WHERE ID='" + playerID + "' LIMIT 1");
				resultSet.next();
				int momentanerWert = resultSet.getInt("einstellungPartyNurFreunde");
				if (momentanerWert == 0) {
					neuerWert = 1;
				}
				this.preparedStatement = this.connect()
						.prepareStatement("UPDATE " + this.database + ".freunde set einstellungPartyNurFreunde='"
								+ neuerWert + "' WHERE ID='" + playerID + "' LIMIT 1");
				this.preparedStatement.executeUpdate();
				break;
			}
			case 1: {
				resultSet = statement().executeQuery("select einstellungAkzeptieren from " + database
						+ ".freunde WHERE ID='" + playerID + "' LIMIT 1");
				resultSet.next();
				int momentanerWert = resultSet.getInt("einstellungAkzeptieren");
				if (momentanerWert == 0) {
					neuerWert = 1;
				}
				this.preparedStatement = this.connect()
						.prepareStatement("UPDATE " + this.database + ".freunde set einstellungAkzeptieren='"
								+ neuerWert + "' WHERE ID='" + playerID + "' LIMIT 1");
				this.preparedStatement.executeUpdate();
				break;
			}
			case 2: {
				resultSet = statement().executeQuery("select EinstellungSendMessages from " + database
						+ ".freunde WHERE ID='" + playerID + "' LIMIT 1");
				resultSet.next();
				int momentanerWert = resultSet.getInt("EinstellungSendMessages");
				if (momentanerWert == 0) {
					neuerWert = 1;
				}
				this.preparedStatement = this.connect()
						.prepareStatement("UPDATE " + this.database + ".freunde set EinstellungSendMessages='"
								+ neuerWert + "' WHERE ID='" + playerID + "' LIMIT 1");
				this.preparedStatement.executeUpdate();
				break;
			}
			case 3: {
				resultSet = statement().executeQuery("select EinstellungImmerOffline from " + database
						+ ".freunde WHERE ID='" + playerID + "' LIMIT 1");
				resultSet.next();
				int momentanerWert = resultSet.getInt("EinstellungImmerOffline");
				if (momentanerWert == 0) {
					neuerWert = 1;
				}
				this.preparedStatement = this.connect()
						.prepareStatement("UPDATE " + this.database + ".freunde set EinstellungImmerOffline='"
								+ neuerWert + "' WHERE ID='" + playerID + "' LIMIT 1");
				this.preparedStatement.executeUpdate();
				break;
			}
			case 4: {
				resultSet = statement().executeQuery(
						"select EinstellungJump from " + database + ".freunde WHERE ID='" + playerID + "' LIMIT 1");
				resultSet.next();
				int momentanerWert = resultSet.getInt("EinstellungJump");
				if (momentanerWert == 0) {
					neuerWert = 1;
				}
				this.preparedStatement = this.connect().prepareStatement("UPDATE " + this.database
						+ ".freunde set EinstellungJump='" + neuerWert + "' WHERE ID='" + playerID + "' LIMIT 1");
				break;
			}
			}
			return neuerWert;
		} catch (SQLException e) {
			try {
				close();
				verbinde();
				return einstellungenSetzen(player, typ);
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
	public int[] einstellungenAbfragen(ProxiedPlayer player) {
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
				verbinde();
				return einstellungenAbfragen(player);
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
	public boolean istBefreundetMit(ProxiedPlayer player, ProxiedPlayer angeschrieben) {
		int idSender = getIDByPlayerName(player.getName());
		int idAbfrage = getIDByPlayerName(angeschrieben.getName());
		int[] anfragenID = getFreundeArray(idSender);
		int i = 0;
		while (anfragenID.length > i) {
			if (anfragenID[i] == idAbfrage) {
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
	public int[] getFreundeArray(int idSender) {
		StringTokenizer st = new StringTokenizer(freundeAusgeben(idSender), "|");
		int[] anfragenID = new int[st.countTokens()];
		int dl = 0;
		while (st.hasMoreTokens()) {
			anfragenID[dl] = Integer.parseInt(st.nextToken());
			dl++;
		}
		return anfragenID;
	}

	/**
	 * Quest if someone allows friend requests
	 * 
	 * @param idAbfrage
	 *            The ID of the player
	 * @return Returns true if he allows friend requests, otherwise it returns
	 *         false
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public boolean erlaubtAnfragen(int idAbfrage) {
		try {
			resultSet = statement().executeQuery(
					"select einstellungAkzeptieren from " + database + ".freunde WHERE ID='" + idAbfrage + "' LIMIT 1");
			resultSet.next();
			if (resultSet.getInt("einstellungAkzeptieren") == 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			try {
				close();
				verbinde();
				return erlaubtAnfragen(idAbfrage);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * Quest if someone allows party invitations
	 * 
	 * @param spielerName
	 *            The name of the player
	 * @return Returns true if he allows party invitations, otherwise it returns
	 *         false
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public boolean erlaubtPartyAnfragen(String spielerName) {
		int idAbfrage = getIDByPlayerName(spielerName);
		try {
			resultSet = statement().executeQuery("select einstellungPartyNurFreunde from " + database
					+ ".freunde WHERE ID='" + idAbfrage + "' LIMIT 1");
			resultSet.next();
			if (resultSet.getInt("einstellungPartyNurFreunde") == 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			try {
				close();
				verbinde();
				return erlaubtAnfragen(idAbfrage);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * Returns the HidePlayers settings worth
	 * 
	 * @param playerID
	 *            The ID of the player
	 * @return Returns the HidePlayers settings worth
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public int getEinstellungHidePlayers(int playerID) {
		try {
			resultSet = statement().executeQuery(
					"select EinstellungHidePlayers from " + database + ".freunde WHERE ID='" + playerID + "' LIMIT 1");
			if (resultSet.next()) {
				return resultSet.getInt("EinstellungHidePlayers");
			} else {
				return 0;
			}
		} catch (SQLException e) {
			try {
				close();
				verbinde();
				return getEinstellungHidePlayers(playerID);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
				return 0;
			}
		}
	}

	/**
	 * Set the worth of the HidePlayers setting
	 * 
	 * @param spielerNameSender
	 *            Name of the player
	 * @param neuerWert
	 *            The new worth of the HidePlayers setting
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public void setEinstellungVersteckte(String spielerNameSender, int neuerWert) {
		try {
			this.preparedStatement = this.connect()
					.prepareStatement("UPDATE " + this.database + ".freunde set EinstellungHidePlayers='" + neuerWert
							+ "' WHERE ID='" + getIDByPlayerName(spielerNameSender) + "' LIMIT 1");
			this.preparedStatement.executeUpdate();
		} catch (SQLException e) {
			try {
				close();
				verbinde();
				setEinstellungVersteckte(spielerNameSender, neuerWert);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
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
	public boolean istBefreundetMit(String name, String string) {
		int idSender = getIDByPlayerName(name);
		int idAbfrage = getIDByPlayerName(string);
		String zuTrennen = freundeAusgeben(idSender);
		int[] anfragenID = new int[0];
		StringTokenizer st = new StringTokenizer(zuTrennen, "|");
		while (st.hasMoreTokens()) {
			Object newArray = Array.newInstance(anfragenID.getClass().getComponentType(),
					Array.getLength(anfragenID) + 1);
			System.arraycopy(anfragenID, 0, newArray, 0, Array.getLength(anfragenID));
			anfragenID = (int[]) newArray;
			anfragenID[Array.getLength(anfragenID) - 1] = Integer.parseInt(st.nextToken());
		}
		boolean gefunden = false;
		int i = 0;
		while (anfragenID.length > i && gefunden == false) {
			if (anfragenID[i] == idAbfrage) {
				gefunden = true;
			}
			i++;
		}
		return gefunden;
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