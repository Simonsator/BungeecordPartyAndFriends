package de.simonsator.partyandfriends.communication.sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static de.simonsator.partyandfriends.main.Main.getInstance;

/**
 * Used to import data of the old table structure
 *
 * @author Simonsator
 */
class Importer extends SQLCommunication {
	private final MySQL connection;

	Importer(String pDatabase, String pURL, MySQL pConnection) {
		super(pDatabase, pURL);
		connection = pConnection;
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

	private void dropOldTable() {
		Connection con = getConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("Drop Table IF exists " + database + ".freunde;");
			stmt.executeUpdate("Drop Table IF exists " + database + ".friends_messages;");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void importFriendRequests(PlayerCollection player) {
		int[] list = getRequests(player.ID);
		assert list != null;
		for (int requester : list)
			connection.sendFriendRequest(requester, player.ID);
	}

	private int[] getRequests(int pID) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery(
					"select FreundschaftsAnfragenID from " + database + ".freunde WHERE ID='" + pID + "' LIMIT 1");
			if (rs.next())
				return stringToIntegerArray(rs.getString("FreundschaftsAnfragenID"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
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
		Connection con = getConnection();
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
			close(rs, stmt);
		}
		return 0;
	}

	private int[] getSettings(int pPlayerID) {
		Connection con = getConnection();
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
			rs = stmt.executeQuery("select EinstellungSendMessages from " + database + ".freunde WHERE ID='" + pPlayerID
					+ "' LIMIT 1");
			if (rs.next()) {
				feld[2] = rs.getInt("EinstellungSendMessages");
			} else {
				feld[2] = 0;
			}
			rs.close();
			rs = stmt.executeQuery("select EinstellungImmerOffline from " + database + ".freunde WHERE ID='" + pPlayerID
					+ "' LIMIT 1");
			if (rs.next()) {
				feld[3] = rs.getInt("EinstellungImmerOffline");
			} else {
				feld[3] = 0;
			}
			rs.close();
			rs = stmt.executeQuery(
					"select EinstellungJump from " + database + ".freunde WHERE ID='" + pPlayerID + "' LIMIT 1");
			if (rs.next()) {
				feld[4] = rs.getInt("EinstellungJump");
			} else {
				feld[4] = 0;
			}
			return feld;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return new int[5];
	}

	private int[] getFriendsArray(int idSender) {
		return stringToIntegerArray(getFriends(idSender));
	}

	private String getFriends(int pID) {
		Connection con = getConnection();
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
			close(rs, stmt);
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
		Connection con = getConnection();
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
		} catch (SQLException ignored) {

		} finally {
			close(rs, stmt);
		}
		return null;
	}

	private void createPlayerEntry(String pName, String pUUID, int pID) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("insert into  `" + database + "`."
					+ getInstance().getConfig().getString("MySQL.TablePrefix") + "players values (?, ?, ?)");
			prepStmt.setInt(1, pID);
			prepStmt.setString(2, pName);
			prepStmt.setString(3, pUUID);
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (prepStmt != null)
					prepStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Imports the HidePlayers column
	 */
	private void importTableHidePlayers() {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("ALTER TABLE " + database
					+ ".`freunde` ADD `EinstellungHidePlayers` tinyint(1) NOT NULL AFTER `einstellungPartyNurFreunde`;");
			prepStmt.executeUpdate();
		} catch (SQLException ignored) {

		} finally {
			try {
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
	 */
	private void importTableNewSettings() {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("ALTER TABLE `" + database + "`.`freunde`\n"
					+ "ADD COLUMN `freunde`.`EinstellungSendMessages` tinyint(1) NOT NULL COMMENT '' AFTER `EinstellungHidePlayers`,\n"
					+ "ADD COLUMN `freunde`.`EinstellungImmerOffline` tinyint(1) NOT NULL COMMENT '' AFTER `EinstellungSendMessages`,\n"
					+ "ADD COLUMN `freunde`.`EinstellungJump` tinyint(1) NOT NULL COMMENT '' AFTER `EinstellungImmerOffline`;");
			prepStmt.executeUpdate();
		} catch (SQLException ignored) {
		} finally {
			try {
				if (prepStmt != null)
					prepStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private class PlayerCollection {
		private final String NAME;
		private final String UUID;
		private final int ID;

		PlayerCollection(String pName, String pUUID, int pID) {
			NAME = pName;
			UUID = pUUID;
			ID = pID;
		}
	}

	private static int[] stringToIntegerArray(String string) {
		StringTokenizer st = new StringTokenizer(string, "|");
		int stLength = st.countTokens();
		int[] stArray = new int[stLength];
		for (int i = 0; i < stLength; i++) {
			stArray[i] = Integer.parseInt(st.nextToken());
		}
		return stArray;
	}
}
