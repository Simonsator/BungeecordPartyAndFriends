/***
 * The command list
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.friends.commands;

import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/***
 * The command list
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class List {
	/**
	 * The command list
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param p
	 *            The sender
	 * @param args
	 *            The arguments
	 */
	public static void list(ProxiedPlayer p, String[] args) {
		int[] friendArrayID = Main.getInstance().getConnection()
				.getFriendsAsArray(Main.getInstance().getConnection().getIDByPlayerName(p.getName()));
		if (friendArrayID.length == 0) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
						+ " §7Till now, §7you don´t §7have §7added §7friends."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
							+ Main.getInstance().getMessagesYml().getString("Friends.Command.List.NoFriendsAdded")));
				} else {
					p.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ " §7Du hast noch keine Freunde hinzugefügt."));
				}
			}
		} else {
			int i = 0;
			String[] freundeName = new String[friendArrayID.length];
			String freundeZusammen = "";
			while (friendArrayID.length > i) {
				freundeName[i] = Main.getInstance().getConnection().getPlayerName(friendArrayID[i]);
				ProxiedPlayer freundGeladen = ProxyServer.getInstance().getPlayer(freundeName[i]);
				String zusatz;
				String farbe;
				if (freundGeladen == null) {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						zusatz = Main.getInstance().getMessagesYml().getString("Friends.Command.List.OfflineTitle");
						farbe = Main.getInstance().getMessagesYml().getString("Friends.Command.List.OfflineColor");
					} else {
						zusatz = "(offline)";
						farbe = ChatColor.RED + "";
					}
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						freundeName[i] = freundGeladen.getDisplayName();
						zusatz = Main.getInstance().getMessagesYml().getString("Friends.Command.List.OnlineTitle");
						farbe = Main.getInstance().getMessagesYml().getString("Friends.Command.List.OnlineColor");
					} else {
						freundeName[i] = freundGeladen.getDisplayName();
						zusatz = "(online)";
						farbe = ChatColor.GREEN + "";
					}
				}
				String komma = " ";
				if (i > 0) {
					komma = "§7, ";
				}
				freundeZusammen = freundeZusammen + komma + farbe
						+ Main.getInstance().getConnection().getPlayerName(friendArrayID[i]) + zusatz;
				i++;
			}
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
						+ " §7These §7are §7your §7friends:" + freundeZusammen));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
							+ Main.getInstance().getMessagesYml().getString("Friends.Command.List.FriendsList")
							+ freundeZusammen));
				} else {
					p.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ " §7Dies §7sind §7deine §7Freunde:" + freundeZusammen));
				}
			}
		}
	}
}
