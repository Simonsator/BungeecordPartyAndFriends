package de.simonsator.partyandfriends.main.listener;

import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.api.system.WaitForTasksToFinish;
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

public class PAFMiniGameCommandHandler implements Listener {
	private final Set<String> COMMANDS = new HashSet<>();
	private final WaitForTasksToFinish TASK_COUNTER = new WaitForTasksToFinish();

	public PAFMiniGameCommandHandler(List<String> pCommands) {
		for (String command : pCommands)
			COMMANDS.add(command.toLowerCase(Locale.ROOT));
	}

	@EventHandler
	public void onChat(ChatEvent pEvent) {
		BukkitBungeeAdapter.getInstance().runAsync(Main.getInstance(), () -> {
			try {
				TASK_COUNTER.taskStarts();
				if (COMMANDS.contains(pEvent.getMessage().toLowerCase(Locale.ROOT))) {
					if (pEvent.getSender() instanceof ProxiedPlayer) {
						OnlinePAFPlayer player =
								PAFPlayerManager.getInstance().getPlayer((ProxiedPlayer) pEvent.getSender());
						PlayerParty party = player.getParty();
						if (party != null && party.isLeader(player)) {
							ServerInfo leaderServer = player.getServer();
							for (OnlinePAFPlayer member : party.getPlayers()) {
								if (leaderServer.equals(member.getServer())) {
									if (member.getPlayer().getPendingConnection().getVersion() >= 759) {
										member.sendMessage("The party and friends function 'MiniGameStartingCommands' is not available on minecraft versions 1.19 and up");
									} else {
										member.getPlayer().chat(pEvent.getMessage());
									}
								}
							}
						}
					}
				}
			} finally {
				TASK_COUNTER.taskFinished();
			}
		});
	}
}
