package de.simonsator.partyandfriends.api.friends;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public interface ServerConnector {

	/**
	 * Connects a player to a spigot server
	 *
	 * @param pPlayer The player who should be send to a server
	 * @param pServer The server to which the player should be connected
	 */
	void connect(ProxiedPlayer pPlayer, ServerInfo pServer);
}
