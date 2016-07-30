package de.simonsator.partyandfriends.pafplayers.manager;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.communication.sql.MySQL;
import de.simonsator.partyandfriends.communication.sql.MySQLData;
import de.simonsator.partyandfriends.pafplayers.mysql.OnlinePAFPlayerMySQL;
import de.simonsator.partyandfriends.pafplayers.mysql.PAFPlayerMySQL;
import de.simonsator.partyandfriends.utilities.disable.Deactivated;
import de.simonsator.partyandfriends.utilities.disable.Disabler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class PAFPlayerManagerMySQL extends PAFPlayerManager implements Deactivated {
	private static MySQL connection;

	public PAFPlayerManagerMySQL(MySQLData pMySQLData) {
		connection = new MySQL(pMySQLData);
		Disabler.getInstance().registerDeactivated(this);
	}

	public PAFPlayer getPlayer(String pPlayer) {
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(pPlayer);
		if (player == null)
			return new PAFPlayerMySQL(getConnection().getPlayerID(pPlayer));
		else
			return this.getPlayer(player);
	}

	public OnlinePAFPlayer getPlayer(ProxiedPlayer pPlayer) {
		return new OnlinePAFPlayerMySQL(getConnection().getPlayerID(pPlayer), pPlayer);
	}

	@Override
	public PAFPlayer getPlayer(UUID pPlayer) {
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(pPlayer);
		if (player != null)
			return this.getPlayer(player);
		return this.getPlayer(getConnection().getPlayerID(pPlayer));
	}

	public PAFPlayer getPlayer(int pPlayerID) {
		return this.getPlayer(getConnection().getName(pPlayerID));
	}

	public static MySQL getConnection() {
		return connection;
	}

	@Override
	public void onDisable() {
		connection.closeConnection();
	}
}
