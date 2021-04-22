package de.simonsator.partyandfriends.main.listener;

import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.command.PartyCommand;
import de.simonsator.partyandfriends.utilities.ServerDisplayNameCollection;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

/**
 * The class with the ServerSwitchEvent
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class ServerSwitchListener implements Listener {
	private static ServerSwitchListener instance;
	/**
	 * The list of the servers which the party will not join.
	 */
	private final List<String> notJoinServers;
	private final int CONNECT_DELAY;

	/**
	 * Initials the object
	 */
	public ServerSwitchListener() {
		notJoinServers = Main.getInstance().getGeneralConfig().getStringList("General.PartyDoNotJoinTheseServers");
		CONNECT_DELAY = Main.getInstance().getGeneralConfig().getInt("General.PartyJoinDelayInSeconds");
		instance = this;
	}

	public static ServerSwitchListener getInstance() {
		return instance;
	}

	/**
	 * Will be executed if a player switches the server
	 *
	 * @param pEvent The ServerSwitchEvent event
	 */
	@EventHandler
	public void onServerSwitch(final ServerSwitchEvent pEvent) {
		BukkitBungeeAdapter.getInstance().runAsync(Main.getInstance(), () -> moveParty(pEvent));
	}

	private void moveParty(ServerSwitchEvent pEvent) {
		ServerInfo server = pEvent.getPlayer().getServer().getInfo();
		if (notJoinServers.contains(server.getName()))
			return;
		OnlinePAFPlayer player = PAFPlayerManager.getInstance().getPlayer(pEvent.getPlayer());
		PlayerParty party = PartyManager.getInstance().getParty(player);
		if (party != null && party.isLeader(player) && !party.getPlayers().isEmpty()) {
			for (OnlinePAFPlayer p : party.getPlayers())
				if (CONNECT_DELAY == 0)
					p.connect(server);
				else
					BukkitBungeeAdapter.getInstance().schedule(Main.getInstance(), () -> p.connect(server), CONNECT_DELAY);
			party.sendMessage((PartyCommand.getInstance().getPrefix()
					+ Main.getInstance().getMessages().getString("Party.Command.General.ServerSwitched")
					.replace("[SERVER]", ServerDisplayNameCollection.getInstance().getServerDisplayName(server))));
		}
	}
}
