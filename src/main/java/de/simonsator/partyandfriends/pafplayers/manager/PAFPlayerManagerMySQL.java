package de.simonsator.partyandfriends.pafplayers.manager;

import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.pafplayers.mysql.OnlinePAFPlayerMySQL;
import de.simonsator.partyandfriends.pafplayers.mysql.PAFPlayerMySQL;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

import static de.simonsator.partyandfriends.main.Main.getInstance;

public class PAFPlayerManagerMySQL extends PAFPlayerManager {
	public PAFPlayer getPlayer(String pPlayer) {
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(pPlayer);
		if (player == null)
			return new PAFPlayerMySQL(getInstance().getConnection().getPlayerID(pPlayer));
		else
			return getPlayer(player);
	}

	public OnlinePAFPlayer getPlayer(ProxiedPlayer pPlayer) {
		return new OnlinePAFPlayerMySQL(getInstance().getConnection().getPlayerID(pPlayer), pPlayer);
	}

	@Override
	public PAFPlayer getPlayer(UUID pPlayer) {
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(pPlayer);
		if (player != null)
			return getPlayer(player);
		return getPlayer(Main.getInstance().getConnection().getPlayerID(pPlayer));
	}

	public PAFPlayer getPlayer(int pPlayerID) {
		return getPlayer(getInstance().getConnection().getName(pPlayerID));
	}

}
