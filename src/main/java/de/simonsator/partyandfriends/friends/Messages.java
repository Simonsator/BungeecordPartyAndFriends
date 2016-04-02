/**
 * This class is to sends a message from a sender to a friend
 * @author Simonsator
 * @version 1.0.0
 * @see freunde.kommandos.message#callMessageSend()
 */
package de.simonsator.partyandfriends.friends;

import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * This class is to sends a message from a sender to a friend
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Messages {
	/**
	 * Send a message from a sender to a receiver
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param player
	 *            Sender
	 * @param args
	 *            Arguments
	 * @param type
	 *            The type of the used command either 0 if the player used the
	 *            command /friend msg or 1 if the player used the command /msg
	 */
	public static void send(ProxiedPlayer player, String[] args, int type) {
		int begin = 1;
		if (type == 1) {
			begin = 0;
		}
		if (args.length > 1 + begin) {
			if (args[begin].equalsIgnoreCase(player.getName())) {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ " §7You cannot §7write §7to §7yourself."));
					player.sendMessage(new TextComponent("§8/§5friend §5msg §5[name §5of §5the §5friend] §5[message]"
							+ ChatColor.RESET + " §8- §7send §7a §7friend §7a §7message"));
				} else {
					if (Main.getInstance().getLanguage().equals("own")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
								.getMessagesYml().getString("Friends.Command.Message.ErrorSenderEqualreceiver")));
						player.sendMessage(new TextComponent(
								Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.MSG")));
					} else {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7Du kannst dich nicht selber anschreiben."));
						player.sendMessage(
								new TextComponent("§8/§5friends §5msg §5[Name §5des §5Freundes] §5[Nachricht]"));
					}
				}
				return;
			}
			if (Main.getInstance().getConnection().getIDByPlayerName(args[begin]) == -1) {
				switch (Main.getInstance().getLanguage()) {
				case "english":
					player.sendMessage(new TextComponent(
							Main.getInstance().getFriendsPrefix() + " §7You cannot write to this player."));
					break;
				case "own":
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
							+ Main.getInstance().getMessagesYml().getString("Friends.Command.MSG.CanNotWriteToHim")));
					break;
				default:
					player.sendMessage(new TextComponent(
							Main.getInstance().getFriendsPrefix() + " §7Du kannst diesem Spieler nicht schreiben."));
					break;
				}
				return;
			}
			if (Main.getInstance().getConnection().isFriendWith(player.getName(), args[begin])) {
				if (Main.getInstance().getConnection().querySettings(player)[2] == 1) {
					switch (Main.getInstance().getLanguage()) {
					case "english":
						player.sendMessage(new TextComponent(
								Main.getInstance().getFriendsPrefix() + " §7You cannot write to this player."));
						break;
					case "own":
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
								.getMessagesYml().getString("Friends.Command.MSG.CanNotWriteToHim")));
						break;
					default:
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
								+ " §7Du kannst diesem Spieler nicht schreiben."));
						break;
					}
					return;
				}
				ProxiedPlayer wroteTo = ProxyServer.getInstance().getPlayer(args[begin]);
				if (wroteTo == null) {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(
								Main.getInstance().getFriendsPrefix() + " §7The §7player §7is §7not §7online."));
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							player.sendMessage(
									new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
											.getMessagesYml().getString("Friends.General.NotAFriendOfOrOffline")));
						} else {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
									+ " §7Der §7Spieler §7ist §7nicht §7online."));
						}
					}
					return;
				} else {
					int n = 2;
					if (type == 1) {
						n = 1;
					}
					nachrichtSenden(args, n, wroteTo, player);
					return;
				}
			} else {
				switch (Main.getInstance().getLanguage()) {
				case "english":
					player.sendMessage(new TextComponent(
							Main.getInstance().getFriendsPrefix() + " §7You cannot write to this player."));
					break;
				case "own":
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
							+ Main.getInstance().getMessagesYml().getString("Friends.Command.MSG.CanNotWriteToHim")));
					break;
				default:
					player.sendMessage(new TextComponent(
							Main.getInstance().getFriendsPrefix() + " §7Du kannst diesem Spieler nicht schreiben."));
					break;
				}
				return;
			}
		} else {

			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
						+ " §7You §7need §7to §7give §7a §7player §7and §7a §7message."));
				player.sendMessage(new TextComponent("§8/§5friend §5msg §5[name §5of §5the §5friend] §5[message]"
						+ ChatColor.RESET + " §8- §7send §7a §7friend §7a §7message"));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					player.sendMessage(new TextComponent(
							Main.getInstance().getMessagesYml().getString("Friends.General.NoPlayerGiven")));
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
							+ Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.MSG")));
				} else {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ " §7Du §7musst §7einen §7Spieler §7und §7eine §7Nachricht §7angeben."));
					player.sendMessage(new TextComponent("§8/§5friend §5msg §5[Name §5des §5Freundes] §5[Nachricht]"));
				}
			}
		}

	}

	/**
	 * Sends a message to a given player from an other player
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param args
	 *            The arguments in an Array
	 * @param n
	 *            Says where the loop should start
	 * @param wroteTo
	 *            The person who was wrote to
	 * @param player
	 *            The sender of the command
	 */
	private static void nachrichtSenden(String[] args, int n, ProxiedPlayer wroteTo, ProxiedPlayer player) {
		String content = toMessage(args, n);
		if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
			wroteTo.sendMessage(
					new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
							.getString("Friends.Command.MSG.SendedMessage").replace("[SENDER]", player.getDisplayName())
							.replace("[PLAYER]", wroteTo.getDisplayName()).replace("[CONTENT]", content)));
			player.sendMessage(
					new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
							.getString("Friends.Command.MSG.SendedMessage").replace("[SENDER]", player.getDisplayName())
							.replace("[PLAYER]", wroteTo.getDisplayName()).replace("[CONTENT]", content)));
		} else

		{
			wroteTo.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET + " §e"
					+ player.getDisplayName() + "§5-> §e" + wroteTo.getDisplayName() + "§7:" + content));
			player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET + " §e"
					+ player.getDisplayName() + "§5-> §e" + wroteTo.getDisplayName() + "§7:" + content));
		}
	}

	/**
	 * Returns a styled message
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param args
	 *            The Arguments The Main.main class
	 * @param n
	 *            At which argument the while loob should start
	 * @return Returns a styled message
	 */
	public static String toMessage(String[] args, int n) {
		String contentColor;
		if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
			contentColor = Main.getInstance().getMessagesYml().getString("Friends.Command.MSG.ColorOfMessage");
		} else {
			contentColor = " §7";
		}
		String content = "";
		while (n < args.length) {
			content = content + contentColor + args[n];
			n++;
		}
		return content;
	}
}
