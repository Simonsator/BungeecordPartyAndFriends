/**
 * The command remove
 * @author Simonsator
 * @version 1.0.0
 */
package partyAndFriends.freunde.kommandos;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import partyAndFriends.main.Main;

/**
 * The command remove
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class remove {
	/**
	 * The command remove
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param player
	 *            The sender
	 * @param args
	 *            The arguments
	 */
	public static void entfernen(ProxiedPlayer player, String[] args) {
		if (args.length == 2) {
			int idAbfrage = Main.main.verbindung.getIDByPlayerName(args[1]);
			if (Main.main.verbindung.istBefreundetMit(player.getName(), args[1])) {
				Main.main.verbindung.freundLoeschen(Main.main.verbindung.getIDByPlayerName(player.getName()), idAbfrage,
						0);
				if (Main.main.language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
							+ " §7You have removed the friend §e" + args[1] + "§7."));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix + Main.main.messagesYml
								.getString("Friends.Command.Remove.Removed").replace("[PLAYER]", args[1])));
					} else {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
								+ "§7 Du hast den Freund §e" + args[1] + " §7entfernt"));
					}
				}
				return;
			} else {
				if (Main.main.language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + " §7The Player §e"
							+ args[1] + " §7is §7not §7online §7or §7you §7are §7not §7a §7friend §7of §7him"));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix + Main.main.messagesYml
								.getString("Friends.General.PlayerIsOffline").replace("[PLAYER]", args[1])));
					} else {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
								+ " §7Du §7bist §7nicht §7mit §e" + args[1] + " §7befreundet"));
					}
				}
			}
		} else {
			if (args.length < 2) {
				if (Main.main.language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(
							Main.main.friendsPrefix + ChatColor.RESET + "§7 You need to give a player"));
					player.sendMessage(new TextComponent("§8/§5friend §5remove §5[name §5of §5the §5friend]"
							+ ChatColor.RESET + " §8- §7removes §7a §7friend"));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix
								+ Main.main.messagesYml.getString("Friends.General.NoPlayerGiven")));
						player.sendMessage(new TextComponent(Main.main.friendsPrefix
								+ Main.main.messagesYml.getString("Friends.CommandUsage.Remove")));
					} else {
						player.sendMessage(new TextComponent(
								Main.main.friendsPrefix + ChatColor.RESET + "§7 Sie müssen einen Spieler angeben"));
						player.sendMessage(new TextComponent("§8/§5friends §5remove §5[Name §5des §5Spielers]"
								+ ChatColor.RESET + " §8- §7entfernt §7einen §7Freund"));
					}
				}
			} else {
				if (Main.main.language.equalsIgnoreCase("english")) {
					player.sendMessage(
							new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + "§7 Too many arguments"));
					player.sendMessage(new TextComponent("§8/§5friend §5remove §5[name §5of §5the §5friend]"
							+ ChatColor.RESET + " §8- §7removes §7a §7friend"));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix
								+ Main.main.messagesYml.getString("Friends.General.TooManyArguments")));
						player.sendMessage(new TextComponent(Main.main.friendsPrefix
								+ Main.main.messagesYml.getString("Friends.CommandUsage.Remove")));
					} else {
						player.sendMessage(
								new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + "§7 Zu viele Argumente"));
						player.sendMessage(new TextComponent("§8/§5friends §5remove §5[Name §5des §5Spielers]"
								+ ChatColor.RESET + " §8- §7entfernt §7einen §7Freund"));
					}
				}
			}
		}
	}

}
