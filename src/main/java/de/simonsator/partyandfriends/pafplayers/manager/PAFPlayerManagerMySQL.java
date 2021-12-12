package de.simonsator.partyandfriends.pafplayers.manager;

import de.simonsator.partyandfriends.api.pafplayers.IDBasedPAFPlayerManager;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.communication.sql.MySQL;
import de.simonsator.partyandfriends.communication.sql.MySQLData;
import de.simonsator.partyandfriends.communication.sql.data.PlayerDataSet;
import de.simonsator.partyandfriends.communication.sql.pool.PoolData;
import de.simonsator.partyandfriends.pafplayers.mysql.OnlinePAFPlayerMySQL;
import de.simonsator.partyandfriends.pafplayers.mysql.PAFPlayerMySQL;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.SQLException;
import java.util.UUID;

public class PAFPlayerManagerMySQL extends IDBasedPAFPlayerManager {
	private static MySQL connection;

	public PAFPlayerManagerMySQL(MySQLData pMySQLData, PoolData pPoolData) throws SQLException {
		this(pMySQLData, pPoolData, null);
	}

	public PAFPlayerManagerMySQL(MySQLData pMySQLData, PoolData pPoolData, Object pJedisPool) throws SQLException {
		connection = new MySQL(pMySQLData, pPoolData, pJedisPool);
	}

	public static MySQL getConnection() {
		return connection;
	}

	public PAFPlayer getPlayer(String pPlayer) {
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(pPlayer);
		if (player == null)
			return getPlayerNotOnlineOnBungeeCord(getConnection().getPlayerData(pPlayer));
		else
			return getPlayer(player);
	}

	public OnlinePAFPlayer getPlayer(ProxiedPlayer pPlayer) {
		return new OnlinePAFPlayerMySQL(getConnection().getPlayerID(pPlayer), pPlayer);
	}

	@Override
	public PAFPlayer getPlayer(UUID pPlayer) {
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(pPlayer);
		if (player != null)
			return getPlayer(player);
		return getPlayerNotOnlineOnBungeeCord(getConnection().getPlayerData(pPlayer));
	}

	@Override
	public PAFPlayer getPlayer(PlayerDataSet playerDataSet) {
		ProxiedPlayer player;
		if (playerDataSet.UUID != null)
			player = ProxyServer.getInstance().getPlayer(playerDataSet.UUID);
		else
			player = ProxyServer.getInstance().getPlayer(playerDataSet.NAME);
		if (player != null)
			return new OnlinePAFPlayerMySQL(playerDataSet.ID, player);
		return getPlayerNotOnlineOnBungeeCord(playerDataSet);
	}

	@Override
	public PAFPlayer getPlayer(int pPlayerID) {
		PlayerDataSet playerDataSet = getConnection().getPlayerData(pPlayerID);
		if (playerDataSet.UUID != null) {
			ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playerDataSet.UUID);
			if (player != null)
				return new OnlinePAFPlayerMySQL(playerDataSet.ID, player);
		}
		return getPlayerNotOnlineOnBungeeCord(playerDataSet);
	}

	/**
	 * IMPORTANT!! only call after you have already checked that the player is not on this bungeecord
	 *
	 * @param pData The player data
	 * @return The PAFPlayer which is linked to this data
	 */
	protected PAFPlayer getPlayerNotOnlineOnBungeeCord(PlayerDataSet pData) {
		return new PAFPlayerMySQL(pData);
	}
}
