/**
 * The class with the PlayerDisconnectEvent event.
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.main.listener;

import java.util.ArrayList;

import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.PartyManager;
import de.simonsator.partyandfriends.party.PlayerParty;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * The class with the PlayerDisconnectEvent event.
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class PlayerDisconnectListener implements Listener {
	/**
	 * The Main.main class
	 */
	/**
	 * Will be executed on pEvent.getPlayer() disconnect
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param pEvent
	 *            The disconnect event
	 */
	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent pEvent) {
		PlayerParty party = PartyManager.getParty(pEvent.getPlayer());
		if (party != null) {
			party.leaveParty(pEvent.getPlayer());
		}
		ArrayList<Integer> friendsIDs = Main.getInstance().getConnection()
				.getFriends(Main.getInstance().getConnection().getPlayerID(pEvent.getPlayer().getName()));
		for (int friendID : friendsIDs) {
			ProxiedPlayer freundGeladen = ProxyServer.getInstance()
					.getPlayer(Main.getInstance().getConnection().getName(friendID));
			if (freundGeladen != null)
				freundGeladen.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
						+ Main.getInstance().getMessagesYml().getString("Friends.General.PlayerIsNowOffline")
								.replace("[PLAYER]", pEvent.getPlayer().getDisplayName())));
		}
	}
}