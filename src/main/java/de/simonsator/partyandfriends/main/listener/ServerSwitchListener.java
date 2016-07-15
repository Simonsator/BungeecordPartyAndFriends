/**
 * The class with the ServerSwitchEvent
 *
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.main.listener;

import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

import static de.simonsator.partyandfriends.main.Main.getInstance;
import static de.simonsator.partyandfriends.main.Main.getPlayerManager;

/**
 * The class with the ServerSwitchEvent
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class ServerSwitchListener implements Listener {
	/**
	 * The list of the servers which the party will not join.
	 */
	private final List<String> notJoinServers;

	/**
	 * Initials the object
	 */
	public ServerSwitchListener() {
		notJoinServers = getInstance().getConfig().getStringList("General.PartyDoNotJoinTheseServers");
	}

	/**
	 * Will be executed if a player switches the server
	 *
	 * @param pEvent The ServerSwitchEvent event
	 */
	@EventHandler
	public void onServerSwitch(ServerSwitchEvent pEvent) {
		OnlinePAFPlayer player = getPlayerManager().getPlayer(pEvent.getPlayer());
		PlayerParty party = Main.getPartyManager().getParty(player);
		if (party != null) {
			if (party.isLeader(player)) {
				ServerInfo server = party.getLeader().getServer();
				if (notJoinServers.contains(server.getName())) {
					return;
				}
				for (OnlinePAFPlayer p : party.getPlayers())
					p.connect(party.getLeader().getServer());
				party.sendMessage(new TextComponent(getInstance().getPartyPrefix()
						+ getInstance().getMessagesYml().getString("Party.Command.General.ServerSwitched")
						.replace("[SERVER]", party.getLeader().getServer().getName())));
			}
		}
	}
}
