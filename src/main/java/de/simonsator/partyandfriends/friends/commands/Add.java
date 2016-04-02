/**
 * The command add
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
import net.md_5.bungee.protocol.packet.Chat;

/**
 * The command add
 * 
 * @author Simonsator
 * @version 1.0.1
 */
public class Add {
	/**
	 * Called on /friend add
	 * 
	 * @author Simonsator
	 * @version 1.0.1
	 * @param player
	 *            The player who executes the command
	 * @param args
	 *            The arguments
	 */
	public static void add(ProxiedPlayer player, String[] args) {
		if (args.length == 2) {
			int idRequest = Main.getInstance().getConnection().getIDByPlayerName(args[1]);
			if (idRequest == -1) {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ " §7The player §e" + args[1] + " §7doesn´t §7exist"));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(
								Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
										.getString("Friends.General.DoesnotExist").replace("[PLAYER]", args[1])));
					} else {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7Der Spieler §e" + args[1] + " §7exestiert §7nicht"));
					}
				}
				return;
			}
			if (player.getName().equalsIgnoreCase(args[1])) {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ " §7You §7cannot §7send §7yourself §7a §7friend §7request."));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
								.getMessagesYml().getString("Friends.Command.Accept.ErrorSenderEqualreceiver")));
					} else {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7Du kannst dir nicht selber eine §7Freundschaftsanfrage §7senden"));
					}
				}
				return;
			}
			int idSender = Main.getInstance().getConnection().getIDByPlayerName(player.getName());
			if (Main.getInstance().getConnection().isFriendWith(player.getName(), args[1])) {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ "§7 You and §e" + args[1] + " §7are §7already §7friends."));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(
								Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
										.getString("Friends.Command.Add.AlreadyFriends").replace("[PLAYER]", args[1])));
					} else {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ "§7 Du bist schon mit §e" + args[1] + " §7befreundet"));
					}
				}
				return;
			} else {
				if (ContainsIgnoreCase.containsIgnoreCase(
						Main.getInstance().getConnection().getRequestsAsArrayList(idRequest), player.getName())) {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ "§7 You already have send §7the §7player §e" + args[1] + " §7a §7friend §7request."));
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main
									.getInstance().getMessagesYml().getString("Friends.Command.Accept.ErrorAlreadySend")
									.replace("[PLAYER]", args[1])));
						} else {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
									+ "§7 Du hast dem Spieler §e" + args[1]
									+ " §7schon §7eine §7Freundschaftsanfrage §7gesendet."));
						}
					}
					return;
				}
				if (ContainsIgnoreCase.containsIgnoreCase(
						Main.getInstance().getConnection().getRequestsAsArrayList(idSender), args[1])) {
					String command = "/friend accept " + args[1];
					String toWrite;
					String hover;
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(
								Main.getInstance().getFriendsPrefix() + ChatColor.RESET + " §7The player §e" + args[1]
										+ " §7has §7already §7sent §7you §7a §7friend §7request."));
						toWrite = Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7Accept the friend request with §6/friend accept §6" + args[1] + "§7.";
						hover = "§aClick here to accept the friendship request";
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							player.sendMessage(
									new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
											.getMessagesYml().getString("Friends.Command.Add.FriendRequestFromreceiver")
											.replace("[PLAYER]", args[1])));
							toWrite = Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
									.getString("Friends.Command.Add.HowToAccept").replace("[PLAYER]", args[1]);
							hover = Main.getInstance().getMessagesYml().getString("Friends.Command.Add.ClickHere");
						} else {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
									+ " §7Der Spieler §e" + args[1]
									+ " §7hat §7dir §7schon §7eine §7Freundschaftsanfrage §7gesendet."));
							toWrite = Main.getInstance().getFriendsPrefix() + ChatColor.RESET
									+ " §7Nimm sie mit §6/friend accept " + args[1] + " §7an";
							hover = "§aHier klicken um die Freundschaftsanfrage anzunehmen";
						}
					}
					player.unsafe()
							.sendPacket(new Chat("{\"text\":\"" + toWrite
									+ "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + command
									+ "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
									+ hover + "\"}]}}}"));
					return;
				}
			}
			if (Main.getInstance().getConnection().allowsFriendRequsts(idRequest)) {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ " §7You §7cannot §7send §7the §7player §e" + args[1] + " §7a §7friend §7request"));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
								.getMessagesYml().getString("Friends.Command.Add.CanNotSendThisPlayer")
								.replace("[PLAYER]", args[1])));
					} else {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7Du §7kannst §7dem §7Spieler §e" + args[1]
								+ " §7keine §7Freundschaftsanfrage §7senden"));
					}
				}
				return;
			}
			Main.getInstance().getConnection().sendFriendRequest(idSender, idRequest);
			ProxiedPlayer receiver = ProxyServer.getInstance().getPlayer(args[1]);
			if (receiver != null) {
				String toWrite;
				String hover;
				String command = "/friend accept " + player.getName();
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					receiver.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ "§7 You have received a friend request from §e" + player.getDisplayName() + " §7."));
					toWrite = "§7 Accept the friend request with /friend accept §e" + player.getName() + " §7.";
					hover = "§aClick here to accept the friend request";
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						receiver.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main
								.getInstance().getMessagesYml().getString("Friends.Command.Add.FriendRequestreceived")
								.replace("[PLAYER]", player.getDisplayName())));
						toWrite = Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
								.getString("Friends.Command.Add.HowToAccept").replace("[PLAYER]", player.getName());
						hover = Main.getInstance().getMessagesYml().getString("Friends.Command.Add.ClickHere");
					} else {
						receiver.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ "§7 Du hast eine Freundschaftsanfrage von §e" + player.getDisplayName()
								+ " §7erhalten"));
						toWrite = "§7 Nimm die Freundschaftsanfrage mit /friend accept §e" + player.getName()
								+ " §7an.";
						hover = "§aHier klicken um die Freundschaftsanfrage anzunehmen";
					}
				}
				receiver.unsafe()
						.sendPacket(new Chat("{\"text\":\"" + toWrite
								+ "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + command
								+ "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
								+ hover + "\"}]}}}"));
			}
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
						+ "§7 The player §e" + args[1] + "§7 was §7send §7a §7friend §7request"));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
							+ Main.getInstance().getMessagesYml().getString("Friends.Command.Add.SendedAFriendRequest")
									.replace("[PLAYER]", args[1])));
				} else {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ "§7 Dem Spieler §e" + args[1] + "§7 wurde §7eine §7Freundschaftsanfrage §7gesendet"));
				}
			}
			return;
		}
		if (args.length < 2) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent(
						Main.getInstance().getFriendsPrefix() + ChatColor.RESET + " §7You need to give a player"));
				player.sendMessage(new TextComponent("§8/§5friend §5add §5[name §5of §5the §5player]" + ChatColor.RESET
						+ " §8- §7Add §7a §7friend"));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					player.sendMessage(new TextComponent(
							Main.getInstance().getMessagesYml().getString("Friends.General.NoPlayerGiven")));
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
							+ Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.ADD")));
				} else {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ " §7Du musst einen Spieler angeben"));
					player.sendMessage(new TextComponent("§8/§5friend §5add §5[name §5of §5the §5player]"
							+ ChatColor.RESET + " §8- §7Fügt einen Freund hinzu"));
				}
			}
			return;
		}
		if (args.length > 2) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent(
						Main.getInstance().getFriendsPrefix() + ChatColor.RESET + "§7 Too many arguments"));
				player.sendMessage(new TextComponent("§8/§5friend §5add §5[name §5of §5the §5player]" + ChatColor.RESET
						+ " §8- §7Add §7a §7friend"));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
							+ Main.getInstance().getMessagesYml().getString("Friends.General.TooManyArguments")));
					player.sendMessage(new TextComponent(
							Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.ADD")));
				} else {
					player.sendMessage(new TextComponent(
							Main.getInstance().getFriendsPrefix() + ChatColor.RESET + "§7 Zu viele Argumente"));
					player.sendMessage(new TextComponent("§8/§5friends add [Name des Spielers]" + ChatColor.RESET
							+ " §8- §7Fügt einen Freund hinzu"));
				}
			}
			return;
		}
	}
}
