/**
 * The class with the ServerSwitchEvent
 * @author Simonsator
 * @version 1.0.0
 */
package partyAndFriends.main.listener;

import java.util.ArrayList;
import java.util.StringTokenizer;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import partyAndFriends.main.Main;
import partyAndFriends.party.PartyManager;
import partyAndFriends.party.PlayerParty;

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
	private ArrayList<String> notJoinServers = new ArrayList<String>();

	/**
	 * Initials the object
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public ServerSwitshListener() {
		StringTokenizer st = new StringTokenizer(Main.main.config.getString("General.PartyDoNotJoinTheseServers"), "|");
		while (st.hasMoreTokens()) {
			notJoinServers.add(st.nextToken());
		}
	}

	/**
	 * Will be executed if a player switches the server
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param e
	 *            The ServerSwitchEvent event
	 */
	@EventHandler
	public void onServerSwitch(ServerSwitchEvent e) {
		ProxiedPlayer player = e.getPlayer();
		if (PartyManager.getParty(player) != null) {
			PlayerParty party = PartyManager.getParty(player);
			if (party.isleader(player)) {
				if (notJoinServers.contains(party.getServerInfo().getName())) {
					return;
				}
				for (ProxiedPlayer p : party.getPlayer()) {
					p.connect(party.getServerInfo());
					if (Main.main.language.equalsIgnoreCase("english")) {
						p.sendMessage(new TextComponent(
								Main.main.partyPrefix + "§bThe §bparty §bhas §bjoinend §bthe §bServer §e"
										+ party.getServerInfo().getName() + "§b."));
					} else {
						if (Main.main.language.equalsIgnoreCase("own")) {
							p.sendMessage(new TextComponent(Main.main.partyPrefix
									+ Main.main.messagesYml.getString("Party.Command.General.ServerSwitched")
											.replace("[SERVER]", party.getServerInfo().getName())));
						} else {
							p.sendMessage(
									new TextComponent(Main.main.partyPrefix + "§bDie §bParty §bhat §bden §bServer §e"
											+ party.getServerInfo().getName() + " §bbetreten."));
						}
					}
				}
				if (Main.main.language.equalsIgnoreCase("english")) {
					player.sendMessage(
							new TextComponent(Main.main.partyPrefix + "§bThe §bparty §bhas §bjoinend §bthe §bServer §e"
									+ party.getServerInfo().getName() + "§b."));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.main.partyPrefix
								+ Main.main.messagesYml.getString("Party.Command.General.ServerSwitched")
										.replace("[SERVER]", party.getServerInfo().getName())));
					} else {
						player.sendMessage(
								new TextComponent(Main.main.partyPrefix + "§bDie §bParty §bhat §bden §bServer §e"
										+ party.getServerInfo().getName() + " §bbetreten."));
					}
				}
			}
		}
	}
}
