/**
 * The command accept
 * 
 * @author Simonsator
 * @version 1.0.0
 */
package partyAndFriends.freunde.kommandos;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import partyAndFriends.main.Main;
import partyAndFriends.utilities.ContainsIgnoreCase;

/**
 * The command accept
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class accept {
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
	public static void akzeptieren(ProxiedPlayer player, String[] args) {
		if (args.length == 2) {
			int idSender = Main.main.verbindung.getIDByPlayerName(player.getName());
			if (ContainsIgnoreCase.containsIgnoreCase(Main.main.verbindung.getAnfragenArrayList(idSender), args[1])) {
				Main.main.verbindung.spielerHinzufuegen(idSender, Main.main.verbindung.getIDByPlayerName(args[1]));
				if (Main.main.language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + " §7You and §e"
							+ args[1] + " §7are §7now §7friends"));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix + Main.main.messagesYml
								.getString("Friends.Command.Accept.NowFriends").replace("[PLAYER]", args[1])));
					} else {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
								+ " §7Du bist jetzt mit §e" + args[1] + " §7befreundet"));
					}
				}
				ProxiedPlayer befreundet = BungeeCord.getInstance().getPlayer(args[1]);
				if (befreundet != null) {
					if (Main.main.language.equalsIgnoreCase("english")) {
						befreundet.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
								+ " §7You and §e" + player.getDisplayName() + " §7are §7now §7friends"));
						befreundet.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
								+ " §7The friend §e" + player.getDisplayName() + "§7 is §7now §aonline."));
						player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
								+ " §7The friend §e" + befreundet.getDisplayName() + "§7 is §7now §aonline."));
					} else {
						if (Main.main.language.equalsIgnoreCase("own")) {
							befreundet.sendMessage(new TextComponent(Main.main.friendsPrefix
									+ Main.main.messagesYml.getString("Friends.Command.Accept.NowFriends")
											.replace("[PLAYER]", player.getDisplayName())));
							befreundet.sendMessage(new TextComponent(Main.main.friendsPrefix
									+ Main.main.messagesYml.getString("Friends.General.PlayerIsNowOnline")
											.replace("[PLAYER]", player.getDisplayName())));
							player.sendMessage(new TextComponent(Main.main.friendsPrefix
									+ Main.main.messagesYml.getString("Friends.General.PlayerIsNowOnline")
											.replace("[PLAYER]", befreundet.getDisplayName())));
						} else {
							befreundet.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
									+ " §7Du bist jetzt mit §e" + player.getDisplayName() + " §7befreundet"));
							befreundet.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + " §e"
									+ player.getDisplayName() + "§7 ist §7jetzt §aOnline"));
							player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + " §e"
									+ args[1] + "§7 ist §7jetzt §aOnline"));
						}
					}
				}
				return;
			} else {
				if (Main.main.language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
							+ " §7You didn´t recive a §7friend §7request §7from §e" + args[1] + "§7."));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix
								+ Main.main.messagesYml.getString("Friends.Command.Accept.ErrorNoFriendShipInvitation")
										.replace("[PLAYER]", args[1])));
					} else {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
								+ "§7 Du hast keine §7Freundschaftsanfrage von §e" + args[1] + " §7keine §7erhalten"));
					}
				}
			}
		} else {
			if (args.length < 2) {
				if (Main.main.language.equalsIgnoreCase("english")) {
					player.sendMessage(
							new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + "§7 Not enough arguments"));
					player.sendMessage(new TextComponent("§8/§5friend §5accept §5[name §5of §5the §5player]"
							+ ChatColor.RESET + " §8- §7accept §7a §7friend request"));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix
								+ Main.main.messagesYml.getString("Friends.General.NoPlayerGiven")));
						player.sendMessage(new TextComponent(Main.main.friendsPrefix
								+ Main.main.messagesYml.getString("Friends.CommandUsage.Accept")));
					} else {
						player.sendMessage(new TextComponent(
								Main.main.friendsPrefix + ChatColor.RESET + "§7 Du musst einen Spieler angeben"));
						player.sendMessage(new TextComponent("§8/§5friend accept [Name des Spielers]" + ChatColor.RESET
								+ " §8- §7akzeptiert eine §7Freundschaftsanfrage"));
					}
				}
			} else {
				if (Main.main.language.equalsIgnoreCase("english")) {
					player.sendMessage(
							new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + "§7 Too many arguments"));
					player.sendMessage(new TextComponent("§8/§5friend §5accept §5[name §5of §5the §5player]"
							+ ChatColor.RESET + " §8- §7accept §7a §7friend request"));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix
								+ Main.main.messagesYml.getString("Friends.General.TooManyArguments")));
						player.sendMessage(new TextComponent(Main.main.friendsPrefix
								+ Main.main.messagesYml.getString("Friends.CommandUsage.Accept")));
					} else {
						player.sendMessage(
								new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + "§7Zu viele Argumente"));
						player.sendMessage(new TextComponent("§8/§5friend accept [Name des Spielers]" + ChatColor.RESET
								+ " §8- §7akzeptiert eine §7Freundschaftsanfrage"));
					}
				}
			}
		}
	}
}
