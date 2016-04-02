/**
 * The command accept
 * 
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.friends.commands;

import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.utilities.ContainsIgnoreCase;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * The command accept
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Accept {
	/**
	 * The command accept
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param player
	 *            The player who used this command
	 * @param args
	 *            The arguments
	 */
	public static void accept(ProxiedPlayer player, String[] args) {
		if (args.length == 2) {
			int idSender = Main.getInstance().getConnection().getIDByPlayerName(player.getName());
			if (ContainsIgnoreCase.containsIgnoreCase(Main.getInstance().getConnection().getRequestsAsArrayList(idSender),
					args[1])) {
				Main.getInstance().getConnection().addPlayer(idSender,
						Main.getInstance().getConnection().getIDByPlayerName(args[1]));
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ " §7You and §e" + args[1] + " §7are §7now §7friends"));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(
								Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
										.getString("Friends.Command.Accept.NowFriends").replace("[PLAYER]", args[1])));
					} else {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7Du bist jetzt mit §e" + args[1] + " §7befreundet"));
					}
				}
				ProxiedPlayer friendOf = ProxyServer.getInstance().getPlayer(args[1]);
				if (friendOf != null) {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						friendOf.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7You and §e" + player.getDisplayName() + " §7are §7now §7friends"));
						friendOf.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7The friend §e" + player.getDisplayName() + "§7 is §7now §aonline."));
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7The friend §e" + friendOf.getDisplayName() + "§7 is §7now §aonline."));
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							friendOf.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
									+ Main.getInstance().getMessagesYml().getString("Friends.Command.Accept.NowFriends")
											.replace("[PLAYER]", player.getDisplayName())));
							friendOf.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
									+ Main.getInstance().getMessagesYml().getString("Friends.General.PlayerIsNowOnline")
											.replace("[PLAYER]", player.getDisplayName())));
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
									+ Main.getInstance().getMessagesYml().getString("Friends.General.PlayerIsNowOnline")
											.replace("[PLAYER]", friendOf.getDisplayName())));
						} else {
							friendOf.sendMessage(
									new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
											+ " §7Du bist jetzt mit §e" + player.getDisplayName() + " §7befreundet"));
							friendOf.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
									+ ChatColor.RESET + " §e" + player.getDisplayName() + "§7 ist §7jetzt §aOnline"));
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
									+ " §e" + args[1] + "§7 ist §7jetzt §aOnline"));
						}
					}
				}
				return;
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ " §7You didn´t receive a §7friend §7request §7from §e" + args[1] + "§7."));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
								.getMessagesYml().getString("Friends.Command.Accept.ErrorNoFriendShipInvitation")
								.replace("[PLAYER]", args[1])));
					} else {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ "§7 Du hast keine §7Freundschaftsanfrage von §e" + args[1] + " §7keine §7erhalten"));
					}
				}
			}
		} else {
			if (args.length < 2) {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(
							Main.getInstance().getFriendsPrefix() + ChatColor.RESET + "§7 Not enough arguments"));
					player.sendMessage(new TextComponent("§8/§5friend §5accept §5[name §5of §5the §5player]"
							+ ChatColor.RESET + " §8- §7accept §7a §7friend request"));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
								+ Main.getInstance().getMessagesYml().getString("Friends.General.NoPlayerGiven")));
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
								+ Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Accept")));
					} else {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ "§7 Du musst einen Spieler angeben"));
						player.sendMessage(new TextComponent("§8/§5friend accept [Name des Spielers]" + ChatColor.RESET
								+ " §8- §7akzeptiert eine §7Freundschaftsanfrage"));
					}
				}
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(
							Main.getInstance().getFriendsPrefix() + ChatColor.RESET + "§7 Too many arguments"));
					player.sendMessage(new TextComponent("§8/§5friend §5accept §5[name §5of §5the §5player]"
							+ ChatColor.RESET + " §8- §7accept §7a §7friend request"));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
								+ Main.getInstance().getMessagesYml().getString("Friends.General.TooManyArguments")));
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
								+ Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Accept")));
					} else {
						player.sendMessage(new TextComponent(
								Main.getInstance().getFriendsPrefix() + ChatColor.RESET + "§7Zu viele Argumente"));
						player.sendMessage(new TextComponent("§8/§5friend accept [Name des Spielers]" + ChatColor.RESET
								+ " §8- §7akzeptiert eine §7Freundschaftsanfrage"));
					}
				}
			}
		}
	}
}
