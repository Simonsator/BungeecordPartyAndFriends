package de.simonsator.partyandfriends.communication.sql;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import static de.simonsator.partyandfriends.main.Main.getInstance;

/**
 * @author Simonsator
 * @version 2.0.0
 */
public class MySQL extends SQLCommunication {

	private final String tablePrefix;

	/**
	 * Connects to the MySQL server
	 *
	 * @param pMySQLData The MySQL data
	 */
	public MySQL(MySQLData pMySQLData) {
		super(pMySQLData.DATABASE, "jdbc:mysql://" + pMySQLData.HOST + ":" + pMySQLData.PORT + "/?user="
				+ pMySQLData.USERNAME + "&password=" + pMySQLData.PASSWORD);
		this.tablePrefix = pMySQLData.TABLE_PREFIX;
		importDatabase();
		new Importer(pMySQLData.DATABASE, "jdbc:mysql://" + pMySQLData.HOST + ":" + pMySQLData.PORT + "/?user="
				+ pMySQLData.USERNAME + "&password=" + pMySQLData.PASSWORD, this);
		closeConnection();
	}

	public UUID getUUID(int pPlayerID) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select player_uuid from `" + database + "`." + tablePrefix
					+ "players WHERE player_id='" + pPlayerID + "' LIMIT 1");
			if (rs.next()) {
				return UUID.fromString(rs.getString("player_uuid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return null;
	}

	private void importDatabase() {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("CREATE DATABASE IF NOT EXISTS " + database);
			prepStmt.executeUpdate();
			prepStmt.close();
			prepStmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + database + ".`" + tablePrefix + "players` ("
					+ "`player_id` INT(8) NOT NULL AUTO_INCREMENT, " + "`player_name` VARCHAR(16) NOT NULL, "
					+ "`player_uuid` CHAR(38) NOT NULL, PRIMARY KEY (`player_id`));");
			prepStmt.executeUpdate();
			prepStmt.close();
			prepStmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + database + ".`" + tablePrefix
					+ "last_player_wrote_to` (`player_id` INT(8) NOT NULL, `written_to_id` INT(8) NOT NULL);");
			prepStmt.executeUpdate();
			prepStmt.close();
			prepStmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + database + ".`" + tablePrefix
					+ "friend_assignment` (`friend1_id` INT(8) NOT NULL, `friend2_id` INT(8) NOT NULL);");
			prepStmt.executeUpdate();
			prepStmt.close();
			prepStmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + database + ".`" + tablePrefix
					+ "settings` (`player_id` INT(8) NOT NULL, " + "`settings_id` TINYINT(2) NOT NULL, "
					+ " `settings_worth` TINYINT(1) NOT NULL);");
			prepStmt.executeUpdate();
			prepStmt.close();
			prepStmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + database + ".`" + tablePrefix
					+ "friend_request_assignment` (`requester_id` INT(8) NOT NULL, "
					+ "`receiver_id` INT(8) NOT NULL);");
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
		importOfflineMessages();
	}

	/**
	 * Imports the offlineMessages database
	 */
	private void importOfflineMessages() {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS `" + database + "`.`" + tablePrefix + "friends_messages` ("
							+ "`Message` varchar(99) NOT NULL COMMENT ''," + "`Sender` INT(8) NOT NULL COMMENT '',"
							+ "`Reciver` INT(8) NOT NULL COMMENT ''," + "`Date` int(10) NULL);");
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

	public int getPlayerID(ProxiedPlayer pPlayer) {
		if (getInstance().getConfig().getString("General.OfflineServer").equalsIgnoreCase("true"))
			return getPlayerID(pPlayer.getName());
		return getPlayerID(pPlayer.getUniqueId());
	}

	/**
	 * Returns the ID of a player
	 *
	 * @param pUuid The UUID of the player
	 * @return Returns the ID of a player
	 */
	public int getPlayerID(UUID pUuid) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select player_id from `" + database + "`." + tablePrefix
					+ "players WHERE player_uuid='" + pUuid + "' LIMIT 1");
			if (rs.next()) {
				return rs.getInt("player_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return -1;
	}

	/**
	 * Returns the ID of a player
	 *
	 * @param pPlayerName Name of the player Returns the ID of a player
	 * @return Returns the ID of a player
	 */
	public int getPlayerID(String pPlayerName) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select player_id from `" + database + "`." + tablePrefix
					+ "players WHERE player_name='" + pPlayerName + "' LIMIT 1");
			if (rs.next()) {
				return rs.getInt("player_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return -1;
	}

	/**
	 * Will be executed if a player joins the first time on the server
	 *
	 * @param pPlayer The player
	 */
	public void firstJoin(ProxiedPlayer pPlayer) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement(
					"insert into  `" + database + "`." + tablePrefix + "players values (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			prepStmt.setNull(1, 1);
			prepStmt.setString(2, pPlayer.getName());
			prepStmt.setString(3, pPlayer.getUniqueId().toString());
			prepStmt.executeUpdate();
			ResultSet rs = prepStmt.getGeneratedKeys();
			if (rs.next())
				setStandardSettings(rs.getInt(1));
			rs.close();
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

	private void setStandardSettings(int pPlayerID) {
		setSetting(pPlayerID, 0, 1);
	}

	/**
	 * Gives out the IDs of the friends of a player
	 *
	 * @param pPlayerID The ID of the player
	 * @return Returns the IDs of the friends of a player
	 */
	public ArrayList<Integer> getFriends(int pPlayerID) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Integer> list = new ArrayList<>();
		try {
			rs = (stmt = con.createStatement()).executeQuery("select friend2_id from `" + database + "`." + tablePrefix
					+ "friend_assignment WHERE friend1_id='" + pPlayerID + "'");
			while (rs.next())
				list.add(rs.getInt("friend2_id"));
			stmt.close();
			rs.close();
			rs = (stmt = con.createStatement()).executeQuery("select friend1_id from `" + database + "`." + tablePrefix
					+ "friend_assignment WHERE friend2_id='" + pPlayerID + "'");
			while (rs.next())
				list.add(rs.getInt("friend1_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return list;
	}

	/**
	 * Returns the name of a player
	 *
	 * @param pPlayerID The ID of the player
	 * @return Returns the name of a player
	 */
	public String getName(int pPlayerID) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select player_name from `" + database + "`." + tablePrefix
					+ "players WHERE player_id='" + pPlayerID + "' LIMIT 1");
			if (rs.next()) {
				return rs.getString("player_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return "";
	}

	/**
	 * Updates the name of a player
	 *
	 * @param pPlayerID      The ID of the player
	 * @param pNewPlayerName New name of the player
	 */
	public void updatePlayerName(int pPlayerID, String pNewPlayerName) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("UPDATE `" + database + "`." + tablePrefix + "players set player_name='"
					+ pNewPlayerName + "' WHERE player_id='" + pPlayerID + "' LIMIT 1");
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

	public boolean hasRequestFrom(int pReceiver, int pRequester) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select requester_id from `" + database + "`."
					+ tablePrefix + "friend_request_assignment WHERE receiver_id='" + pReceiver + "' AND requester_id='"
					+ pRequester + "' LIMIT 1");
			if (rs.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return false;
	}

	/**
	 * Returns the IDs of the friends from a player
	 *
	 * @param pPlayerID The ID of the player
	 * @return Returns the IDs of the friends from a player
	 */
	public ArrayList<Integer> getRequests(int pPlayerID) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Integer> requests = new ArrayList<>();
		try {
			rs = (stmt = con.createStatement()).executeQuery("select requester_id from `" + database + "`."
					+ tablePrefix + "friend_request_assignment WHERE receiver_id='" + pPlayerID + "'");
			if (rs.next())
				requests.add(rs.getInt("requester_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return requests;
	}

	/**
	 * Adds a player to friends
	 *
	 * @param pIDRequester The sender of the command
	 * @param pIDReceiver  The new friend
	 */
	public void addFriend(int pIDRequester, int pIDReceiver) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement(
					"insert into `" + database + "`." + tablePrefix + "friend_assignment values (?, ?)");
			prepStmt.setInt(1, pIDRequester);
			prepStmt.setInt(2, pIDReceiver);
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
	 * Removes a friend request
	 *
	 * @param pReceiverSender The ID of the command executer
	 * @param pRequesterID    The ID of the person who had send the friend request
	 */
	public void denyRequest(int pReceiverSender, int pRequesterID) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement(
					"DELETE FROM `" + database + "`." + tablePrefix + "friend_request_assignment WHERE requester_id = '"
							+ pRequesterID + "' AND receiver_id='" + pReceiverSender + "' Limit 1");
			prepStmt.execute();
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
	 * Deletes a friend
	 *
	 * @param pFriend1ID The ID of the command sender
	 * @param pFriend2ID The ID of the friend, which should be deleted
	 */
	public void deleteFriend(int pFriend1ID, int pFriend2ID) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("DELETE FROM `" + database + "`." + tablePrefix
					+ "friend_assignment WHERE (friend1_id = '" + pFriend1ID + "' AND friend2_id='" + pFriend2ID
					+ "') OR (friend1_id = '" + pFriend2ID + "' AND friend2_id='" + pFriend1ID + "') Limit 1");
			prepStmt.execute();
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
	 * Send a friend request
	 *
	 * @param pSenderID The ID of Sender of the friend request
	 * @param pQueryID  The ID of the player, which gets the friend request
	 */
	public void sendFriendRequest(int pSenderID, int pQueryID) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement(
					"insert into  `" + database + "`." + tablePrefix + "friend_request_assignment values (?, ?)");
			prepStmt.setInt(1, pSenderID);
			prepStmt.setInt(2, pQueryID);
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
	 * Sets a setting
	 *
	 * @param pPlayer     The player
	 * @param pSettingsID The ID of the setting
	 * @return Returns the new worth
	 */
	public int changeSettingsWorth(ProxiedPlayer pPlayer, int pSettingsID) {
		int playerID = getPlayerID(pPlayer);
		int worth = getSettingsWorth(playerID, pSettingsID);
		if (worth == 1)
			worth = 0;
		else
			worth = 1;
		setSetting(playerID, pSettingsID, worth);
		return worth;
	}

	/**
	 * Checks if somebody is a friend of someone others
	 *
	 * @param pPlayer1 The player
	 * @param pPlayer2 The other player
	 * @return Returns if player one is a friend of player two true, otherwise
	 * false
	 */
	public boolean isAFriendOf(ProxiedPlayer pPlayer1, ProxiedPlayer pPlayer2) {
		return isAFriendOf(getPlayerID(pPlayer1), getPlayerID(pPlayer2));
	}

	public int getSettingsWorth(int pPlayerID, int pSettingsID) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery(
					"select settings_worth from `" + database + "`." + tablePrefix + "settings WHERE player_id='"
							+ pPlayerID + "' AND settings_id='" + pSettingsID + "' LIMIT 1");
			if (rs.next()) {
				return rs.getInt("settings_worth");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return 0;
	}

	public void setSetting(int pPlayerID, int pSettingsID, int pNewWorth) {
		removeSetting(pPlayerID, pSettingsID);
		if (pNewWorth != 0) {
			Connection con = getConnection();
			PreparedStatement prepStmt = null;
			try {
				prepStmt = con.prepareStatement(
						"insert into  `" + database + "`." + tablePrefix + "settings values (?, ?, ?)");
				prepStmt.setInt(1, pPlayerID);
				prepStmt.setInt(2, pSettingsID);
				prepStmt.setInt(3, pNewWorth);
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
	}

	private void removeSetting(int pPlayerID, int pSettingsID) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("DELETE FROM  `" + database + "`." + tablePrefix
					+ "settings WHERE player_id = '" + pPlayerID + "' AND settings_id='" + pSettingsID + "' Limit 1");
			prepStmt.execute();
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
	
	public boolean isAFriendOf(int pPlayerID1, int pPlayerID2) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("Select friend1_id FROM `" + database + "`." + tablePrefix
					+ "friend_assignment WHERE (friend1_id = '" + pPlayerID1 + "' AND friend2_id='" + pPlayerID2
					+ "') OR (friend1_id = '" + pPlayerID2 + "' AND friend2_id='" + pPlayerID1 + "') LIMIT 1");
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return false;
	}

	/**
	 * Returns the last player who wrote to the given player
	 *
	 * @param pID The ID of the player
	 * @return Returns the last player who wrote to the given player
	 */
	public int getLastPlayerWroteTo(int pID) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select written_to_id from `" + database + "`."
					+ tablePrefix + "last_player_wrote_to WHERE player_id='" + pID + "' LIMIT 1");
			if (rs.next()) {
				return rs.getInt("written_to_id");
			} else {
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return 0;
	}

	/**
	 * @param pPlayerID    The ID of the player
	 * @param pLastWroteTo The ID of the player who wrote to
	 * @param pI           The pass
	 */
	public void setLastPlayerWroteTo(int pPlayerID, int pLastWroteTo, int pI) {
		removeLastPlayerWroteFrom(pPlayerID);
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement(
					"insert into  `" + database + "`." + tablePrefix + "last_player_wrote_to values (?, ?)");
			prepStmt.setInt(1, pPlayerID);
			prepStmt.setInt(2, pLastWroteTo);
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
		if (pI == 0)
			setLastPlayerWroteTo(pLastWroteTo, pPlayerID, 1);
	}

	private void removeLastPlayerWroteFrom(int pPlayerID) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("DELETE FROM `" + database + "`." + tablePrefix
					+ "last_player_wrote_to WHERE player_id = '" + pPlayerID + "' Limit 1");
			prepStmt.execute();
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


}
