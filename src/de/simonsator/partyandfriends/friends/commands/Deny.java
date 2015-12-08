/**
 * The command deny
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.friends.commands;

import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.utilities.ContainsIgnoreCase;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * The command deny
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Deny {
	/**
	 * Deny a friend request
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param player
	 *            The sender
	 * @param args
	 *            The arguments
	 */
	public static void deny(ProxiedPlayer player, String[] args) {
		if (args.length == 2) {
			int idSender = Main.getInstance().getConnection().getIDByPlayerName(player.getName());
			if (ContainsIgnoreCase
					.containsIgnoreCase(Main.getInstance().getConnection().getRequestsAsArrayList(idSender), args[1])) {
				Main.getInstance().getConnection().denyPlayer(idSender,
						Main.getInstance().getConnection().getIDByPlayerName(args[1]));
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ " §7You have denied the friend request of §e" + args[1] + "."));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(
								Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
										.getString("Friends.Command.Deny.HasDenied").replace("[PLAYER]", args[1])));
					} else {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7Du hast die Anfrage von §e" + args[1] + " §7abglehnt"));
					}
				}
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ " §7You didn´t receive a §7friend §7request §7from §e" + args[1] + "§7."));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
								+ Main.getInstance().getMessagesYml().getString("Friends.Command.Deny.NoFriendRequest")
										.replace("[PLAYER]", args[1])));
					} else {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ "§7Du hast von §e" + args[1] + "§7 keine §7Freundschaftsanfrage §7erhalten"));
					}
				}
			}
		} else {
			if (args.length < 2) {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(
							Main.getInstance().getFriendsPrefix() + ChatColor.RESET + "§7 You need to give a player"));
					player.sendMessage(new TextComponent("§8/§5friend §5deny §5[name §5of §5the §5player]"
							+ ChatColor.RESET + " §8- §7deny §7a §7friendrequest"));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
								+ Main.getInstance().getMessagesYml().getString("Friends.General.NoPlayerGiven")));
						player.sendMessage(new TextComponent(
								Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Deny")));
					} else {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ "§7 Du musst einen Spieler angeben"));
						player.sendMessage(new TextComponent("§8/§5friends §5deny §5[Name §5des §5Spielers]"
								+ ChatColor.RESET + " §8- §7Lehnt eine §7Freundschaftsanfrage §7ab"));
					}
				}
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(
							Main.getInstance().getFriendsPrefix() + ChatColor.RESET + "§7 Too many arguments"));
					player.sendMessage(new TextComponent("§8/§5friend §5deny §5[name §5of §5the §5player]"
							+ ChatColor.RESET + " §8- §7deny §7a §7friendrequest"));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(
								Main.getInstance().getMessagesYml().getString("Friends.General.TooManyArguments")));
						player.sendMessage(new TextComponent(
								Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Deny")));
					} else {
						player.sendMessage(new TextComponent(
								Main.getInstance().getFriendsPrefix() + ChatColor.RESET + "§7 Zu viele Argumente"));
						player.sendMessage(new TextComponent("§8/§5friends §5deny §5[Name §5des §5Spielers]"
								+ ChatColor.RESET + " §8- §7Lehnt eine §7Freundschaftsanfrage §7ab"));
					}
				}
			}
		}
	}

}
