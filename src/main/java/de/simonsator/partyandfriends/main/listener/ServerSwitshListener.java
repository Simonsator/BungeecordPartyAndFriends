/**
 * The class with the ServerSwitchEvent
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.main.listener;

import java.util.List;

import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.PartyManager;
import de.simonsator.partyandfriends.party.PlayerParty;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * The class with the ServerSwitchEvent
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class ServerSwitshListener implements Listener {
	/**
	 * The list of the servers which the party will not join.
	 */
	private List<String> notJoinServers;

	/**
	 * Initials the object
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public ServerSwitshListener() {
		notJoinServers = Main.getInstance().getConfig().getStringList("General.PartyDoNotJoinTheseServers");
	}

	/**
	 * Will be executed if a player switches the server
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param pEvent
	 *            The ServerSwitchEvent event
	 */
	@EventHandler
	public void onServerSwitch(ServerSwitchEvent pEvent) {
		ProxiedPlayer player = pEvent.getPlayer();
		PlayerParty party = PartyManager.getParty(player);
		if (party != null) {
			if (party.isLeader(player)) {
				if (notJoinServers.contains(party.getLeader().getServer().getInfo().getName())) {
					return;
				}
				for (ProxiedPlayer p : party.getPlayers())
					p.connect(party.getLeader().getServer().getInfo());
				party.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
						+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ServerSwitched")
								.replace("[SERVER]", party.getLeader().getServer().getInfo().getName())));
			}
		}
	}
}
