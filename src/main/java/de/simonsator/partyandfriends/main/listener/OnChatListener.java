package de.simonsator.partyandfriends.main.listener;

import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class OnChatListener implements Listener {
	private final Set<String> COMMANDS = new HashSet<>();

	public OnChatListener(List<String> pCommands) {
		for (String command : pCommands)
			COMMANDS.add(command.toLowerCase(Locale.ROOT));
	}

	@EventHandler
	public void onChat(ChatEvent pEvent) {
		BukkitBungeeAdapter.getInstance().runAsync(Main.getInstance(), () -> {
			if (COMMANDS.contains(pEvent.getMessage().toLowerCase(Locale.ROOT))) {
				if (pEvent.getSender() instanceof ProxiedPlayer) {
					OnlinePAFPlayer player =
							PAFPlayerManager.getInstance().getPlayer((ProxiedPlayer) pEvent.getSender());
					PlayerParty party = player.getParty();
					if (party != null && party.isLeader(player)) {
						ServerInfo leaderServer = player.getServer();
						for (OnlinePAFPlayer member : party.getPlayers()) {
							if (leaderServer.equals(member.getServer()))
								member.getPlayer().chat(pEvent.getMessage());
						}
					}
				}
			}
		});
	}
}
