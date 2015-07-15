package party.listener;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import party.Party;
import party.PartyManager;
import party.PlayerParty;

public class ServerSwitshListener implements Listener {
	@EventHandler
	public void onServerSwitch(ServerSwitchEvent e) {
		ProxiedPlayer player = e.getPlayer();
		if (PartyManager.getParty(player) != null) {
			PlayerParty party = PartyManager.getParty(player);
			if (party.isleader(player)) {
				for (ProxiedPlayer p : party.getPlayer()) {
					p.connect(party.getServerInfo());
					p.sendMessage(new TextComponent(Party.prefix
							+ "§bDie §bParty §bhat §bden §bServer §e"
							+ party.getServerInfo().getName() + " §bbetreten."));
				}
				player.sendMessage(new TextComponent(Party.prefix
						+ "§bDie §bParty §bhat §bden §bServer §e"
						+ party.getServerInfo().getName() + " §bbetreten."));
			}
		}
	}
}
