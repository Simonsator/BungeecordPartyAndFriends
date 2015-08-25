/***
 * The command list
 * @author Simonsator
 * @version 1.0.0
 */
package partyAndFriends.freunde.kommandos;

import java.sql.SQLException;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import partyAndFriends.main.Main;

/***
 * The command list
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class list {
	/**
	 * The command list
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param p
	 *            The sender
	 * @param args
	 *            The arguments
	 * @param main
	 *            The main class
	 * @throws SQLException
	 *             Can throw a {@link SQLException}
	 */
	public static void auflisten(ProxiedPlayer p, String[] args, Main main) throws SQLException {
		int[] freundeArrayID = main.verbindung.getFreundeArray(main.verbindung.getIDByPlayerName(p.getName()));
		if (freundeArrayID.length == 0) {
			if (main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						main.friendsPrefix + ChatColor.RESET + " §7Till now, §7you don´t §7have §7added §7friends."));
			} else {
				if (main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(
							main.friendsPrefix + main.messagesYml.getString("Friends.Command.List.NoFriendsAdded")));
				} else {
					p.sendMessage(new TextComponent(
							main.friendsPrefix + ChatColor.RESET + " §7Du hast noch keine Freunde hinzugefügt."));
				}
			}
		} else {
			int i = 0;
			String[] freundeName = new String[freundeArrayID.length];
			String freundeZusammen = "";
			while (freundeArrayID.length > i) {
				freundeName[i] = main.verbindung.getNameDesSpielers(freundeArrayID[i]);
				ProxiedPlayer freundGeladen = BungeeCord.getInstance().getPlayer(freundeName[i]);
				String zusatz;
				String farbe;
				if (freundGeladen == null) {
					if (main.language.equalsIgnoreCase("own")) {
						zusatz = main.messagesYml.getString("Friends.Command.List.OfflineTitle");
						farbe = main.messagesYml.getString("Friends.Command.List.OfflineColor");
					} else {
						zusatz = "(offline)";
						farbe = ChatColor.RED + "";
					}
				} else {
					if (main.language.equalsIgnoreCase("own")) {
						freundeName[i] = freundGeladen.getDisplayName();
						zusatz = main.messagesYml.getString("Friends.Command.List.OnlineTitle");
						farbe = main.messagesYml.getString("Friends.Command.List.OnlineColor");
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
						+ main.verbindung.getNameDesSpielers(freundeArrayID[i]) + zusatz + "§7";
				i++;
			}
			if (main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						main.friendsPrefix + ChatColor.RESET + " §7This §7are §7your §7friends:" + freundeZusammen));
			} else {
				if (main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(
							main.friendsPrefix + main.messagesYml.getString("Friends.Command.List.FriendsList")));
				} else {
					p.sendMessage(new TextComponent(main.friendsPrefix + ChatColor.RESET
							+ " §7Dies §7sind §7deine §7Freunde:" + freundeZusammen));
				}
			}
		}
	}
}
