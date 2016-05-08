package de.simonsator.partyandfriends.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.utilities.StringToArray;

/**
 * Used to import data of the old table structure
 * 
 * @author Simonsator
 *
 */
public class Importer {
	private MySQL connection;
	private String database = Main.getInstance().getConfig().getString("MySQL.Database");

	public Importer(MySQL pCon) {
		connection = pCon;
		importTableHidePlayers();
		ArrayList<PlayerCollection> players = importPlayers();
		if (players == null)
			return;
		for (PlayerCollection player : players) {
			createPlayerEntry(player.NAME, player.UUID, player.ID);
			importFriends(player);
			importFriendRequests(player);
			importSettings(player);
		}
		dropOldTable();
	}

	class PlayerCollection {
		private final String NAME;
		private final String UUID;
		private final int ID;

		public PlayerCollection(String pName, String pUUID, int pID) {
			NAME = pName;
			UUID = pUUID;
			ID = pID;
		}
	}

	private void dropOldTable() {
		Connection con = connection.connect();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("Drop Table IF exists " + database + ".freunde;");
			stmt.executeUpdate("Drop Table IF exists " + database + ".friends_messages;");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void importFriendRequests(PlayerCollection player) {
		for (int requester : getRequests(player.ID))
			connection.sendFriendRequest(requester, player.ID);
	}

	public int[] getRequests(int pID) {
		Connection con = connection.connect();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery(
					"select FreundschaftsAnfragenID from " + database + ".freunde WHERE ID='" + pID + "' LIMIT 1");
			if (rs.next())
				return StringToArray.stringToIntegerArray(rs.getString("FreundschaftsAnfragenID"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private void importSettings(PlayerCollection player) {
		int[] toImport = getSettings(player.ID);
		for (int i = 0; i < 5; i++)
			connection.setSetting(player.ID, i, toImport[i]);
		connection.setSetting(player.ID, 6, getHideModeImport(player.ID));
	}

	private int getHideModeImport(int playerID) {
		Connection con = connection.connect();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery(
					"select EinstellungHidePlayers from " + database + ".freunde WHERE ID='" + playerID + "' LIMIT 1");
			if (rs.next()) {
				return rs.getInt("EinstellungHidePlayers");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	private int[] getSettings(int pPlayerID) {
		Connection con = connection.connect();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery(
					"select einstellungAkzeptieren from " + database + ".freunde WHERE ID='" + pPlayerID + "' LIMIT 1");
			int[] feld = new int[5];
			if (rs.next()) {
				feld[0] = rs.getInt("einstellungAkzeptieren");
			} else {
				feld[0] = 1;
			}
			rs.close();
			rs = stmt.executeQuery("select einstellungPartyNurFreunde from " + database + ".freunde WHERE ID='"
					+ pPlayerID + "' LIMIT 1");
			if (rs.next()) {
				feld[1] = rs.getInt("einstellungPartyNurFreunde");
			} else {
				feld[1] = 0;
			}
			rs.close();
			return feld;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return new int[5];
	}

	private int[] getFriendsArray(int idSender) {
		return StringToArray.stringToIntegerArray(getFriends(idSender));
	}

	private String getFriends(int pID) {
		Connection con = connection.connect();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement())
					.executeQuery("select FreundeID from " + database + ".freunde WHERE ID='" + pID + "' LIMIT 1");
			if (rs.next()) {
				return rs.getString("FreundeID");
			} else {
				return "";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	private void importFriends(PlayerCollection pPlayer) {
		for (int pFriendID : getFriendsArray(pPlayer.ID)) {
			if (!connection.isAFriendOf(pFriendID, pPlayer.ID)) {
				connection.addFriend(pFriendID, pPlayer.ID);
			}
		}
	}

	private boolean containsPlayer(ArrayList<PlayerCollection> pPlayers, PlayerCollection pPlayer) {
		for (PlayerCollection players : pPlayers)
			if (players.NAME.equals(pPlayer.NAME))
				return true;
		return false;
	}

	private ArrayList<PlayerCollection> importPlayers() {
		Connection con = connection.connect();
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<PlayerCollection> players = new ArrayList<>();
		try {
			rs = (stmt = con.createStatement())
					.executeQuery("select SpielerName, UUID, ID  from " + database + ".freunde");
			while (rs.next()) {
				PlayerCollection player = new PlayerCollection(rs.getString("SpielerName"), rs.getString("UUID"),
						rs.getInt("ID"));
				if (!containsPlayer(players, player)) {
					players.add(player);
				}
			}
			return players;
		} catch (SQLException e) {

		} finally {
			try {
				if (con != null)
					con.close();
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private void createPlayerEntry(String pName, String pUUID, int pID) {
		Connection con = connection.connect();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("insert into  `" + database + "`."
					+ Main.getInstance().getConfig().getString("MySQL.TablePrefix") + "players values (?, ?, ?)");
			prepStmt.setInt(1, pID);
			prepStmt.setString(2, pName);
			prepStmt.setString(3, pUUID);
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
				if (prepStmt != null)
					prepStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Imports the HidePlayers column
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	private void importTableHidePlayers() {
		Connection con = connection.connect();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("ALTER TABLE " + database
					+ ".`freunde` ADD `EinstellungHidePlayers` tinyint(1) NOT NULL AFTER `einstellungPartyNurFreunde`;");
			prepStmt.executeUpdate();
		} catch (SQLException e) {

		} finally {
			try {
				if (con != null)
					con.close();
				if (prepStmt != null)
					prepStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		importTableNewSettings();
	}

	/**
	 * Imports the new settings columns
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	private void importTableNewSettings() {
		Connection con = connection.connect();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("ALTER TABLE `" + database + "`.`freunde`\n"
					+ "ADD COLUMN `freunde`.`EinstellungSendMessages` tinyint(1) NOT NULL COMMENT '' AFTER `EinstellungHidePlayers`,\n"
					+ "ADD COLUMN `freunde`.`EinstellungImmerOffline` tinyint(1) NOT NULL COMMENT '' AFTER `EinstellungSendMessages`,\n"
					+ "ADD COLUMN `freunde`.`EinstellungJump` tinyint(1) NOT NULL COMMENT '' AFTER `EinstellungImmerOffline`;");
			prepStmt.executeUpdate();
		} catch (SQLException e) {
		} finally {
			try {
				if (con != null)
					con.close();
				if (prepStmt != null)
					prepStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
