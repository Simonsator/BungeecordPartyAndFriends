/**
 * The command add
 * @author Simonsator
 * @version 1.0.0
 */
package partyAndFriends.freunde.kommandos;

import java.sql.SQLException;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.Chat;
import partyAndFriends.main.Main;
import partyAndFriends.utilities.ContainsIgnoreCase;

/**
 * The command add
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class add {
	/**
	 * Called on /friend add
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param player
	 *            The player who executes the command
	 * @param args
	 *            The arguments
	 * @throws SQLException
	 *             Can throw a {@link SQLException}
	 */
	public static void hinzufuegen(ProxiedPlayer player, String[] args) throws SQLException {
		if (args.length == 2) {
			int idAbfrage = Main.main.verbindung.getIDByPlayerName(args[1]);
			if (idAbfrage == -1) {
				if (Main.main.language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + "§7The player §e"
							+ args[1] + " §7doesn´t §7exist"));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix
								+ Main.main.messagesYml.getString("Friends.General.DoesntExist")));
					} else {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
								+ "§7Der Spieler §e" + args[1] + " §7exestiert §7nicht"));
					}
				}
				return;
			}
			if (player.getName().equalsIgnoreCase(args[1])) {
				if (Main.main.language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
							+ " §7You §7can §7not §7send §7yourself §7a §7friend §7request."));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix
								+ Main.main.messagesYml.getString("Friends.Command.Accept.ErrorSenderEqualReciver")));
					} else {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
								+ " §7Du kannst dir nicht selber eine §7Freundschaftsanfrage §7senden"));
					}
				}
				return;
			}
			int idSender = Main.main.verbindung.getIDByPlayerName(player.getName());
			if (Main.main.verbindung.istBefreundetMit(player.getName(), args[1])) {
				if (Main.main.language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + "§7 You and §e"
							+ args[1] + " §7are §7already §7friends."));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix
								+ Main.main.messagesYml.getString("Friends.Command.Add.AlreadyFriends")));
					} else {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
								+ "§7 Du bist schon mit §e" + args[1] + " §7befreundet"));
					}
				}
				return;
			} else {
				if (ContainsIgnoreCase.containsIgnoreCase(Main.main.verbindung.getAnfragenArrayList(idAbfrage),
						args[1])) {
					if (Main.main.language.equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
								+ "§7 You already have send §7the §7player §e" + args[1] + " §7a §7friend §7request."));
					} else {
						if (Main.main.language.equalsIgnoreCase("own")) {
							player.sendMessage(new TextComponent(Main.main.friendsPrefix
									+ Main.main.messagesYml.getString("Friends.Command.Message.Add.ErrorAlreadySend")
											.replace("[PLAYER]", args[1])));
						} else {
							player.sendMessage(new TextComponent(
									Main.main.friendsPrefix + ChatColor.RESET + "§7 Du hast dem Spieler §e" + args[1]
											+ " §7schon §7eine §7Freundschaftsanfrage §7gesendet."));
						}
					}
					return;
				}
				if (ContainsIgnoreCase.containsIgnoreCase(Main.main.verbindung.getAnfragenArrayList(idSender),
						args[1])) {
					String command = "/friend accept " + args[1];
					String jsoncode = "";
					if (Main.main.language.equalsIgnoreCase("english")) {
						player.sendMessage(
								new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + " §7The player §e"
										+ args[1] + " §7has §7already §7send §7you §7a §7friend §7request."));
						String zuschreiben = Main.main.friendsPrefix + ChatColor.RESET
								+ " §7Accept the friend request with §6/friend accept §6" + args[1] + " §7.";
						jsoncode = "{'text':'" + zuschreiben + "', 'clickEvent':{'action':'run_command','value':'"
								+ command
								+ "'},'hoverEvent':{'action':'show_text','value':'§aClick here to accept the friendship request'}}";
					} else {
						if (Main.main.language.equalsIgnoreCase("own")) {
							player.sendMessage(new TextComponent(Main.main.friendsPrefix
									+ Main.main.messagesYml.getString("Friends.Command.Add.FriendRequestFromReciver")
											.replace("[PLAYER]", args[1])));
							String zuschreiben = Main.main.friendsPrefix + Main.main.messagesYml
									.getString("Friends.Command.Add.HowToAccept").replace("[PLAYER]", args[1]);
							String clickHere = Main.main.messagesYml.getString("Friends.Command.Add.ClickHere");
							jsoncode = "{'text':'" + zuschreiben + "', 'clickEvent':{'action':'run_command','value':'"
									+ command + "'},'hoverEvent':{'action':'show_text','value':'" + clickHere + "'}}";
						} else {
							player.sendMessage(new TextComponent(
									Main.main.friendsPrefix + ChatColor.RESET + " §7Der Spieler §e" + args[1]
											+ " §7hat §7dir §7schon §7eine §7Freundschaftsanfrage §7gesendet."));
							String zuschreiben = Main.main.friendsPrefix + ChatColor.RESET
									+ " §7Nimm sie mit §6/friend accept " + args[1] + " §7an";
							jsoncode = "{'text':'" + zuschreiben + "', 'clickEvent':{'action':'run_command','value':'"
									+ command
									+ "'},'hoverEvent':{'action':'show_text','value':'§aHier klicken um die Freundschaftsanfrage anzunehmen'}}";
						}
					}
					player.unsafe().sendPacket(new Chat(jsoncode));
					return;
				}
			}
			if (Main.main.verbindung.erlaubtAnfragen(idAbfrage)) {
				if (Main.main.language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
							+ " §7You §7can §7not §7send §7the §7player §e" + args[1] + " §7a §7friend §7request"));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix + Main.main.messagesYml
								.getString("Friends.Command.Add.CanNotSendThisPlayer").replace("[PLAYER]", args[1])));
					} else {
						player.sendMessage(new TextComponent(
								Main.main.friendsPrefix + ChatColor.RESET + " §7Du §7kannst §7dem §7Spieler §e"
										+ args[1] + " §7keine §7Freundschaftsanfrage §7senden"));
					}
				}
				return;
			}
			Main.main.verbindung.sendFreundschaftsAnfrage(idSender, idAbfrage);
			ProxiedPlayer empfaenger = BungeeCord.getInstance().getPlayer(args[1]);
			if (empfaenger != null) {
				String jsoncode = "";
				String command = "/friend accept " + player.getName();
				if (Main.main.language.equalsIgnoreCase("english")) {
					empfaenger.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
							+ "§7 You have recived a friend request from §e" + player.getDisplayName() + " §7."));
					String zuschreiben = "§7 Accept the friend request with /friend accept §e" + player.getName()
							+ " §7.";
					jsoncode = "{'text':'" + zuschreiben + "', 'clickEvent':{'action':'run_command','value':'" + command
							+ "'},'hoverEvent':{'action':'show_text','value':'§aClick here to accept the friend request'}}";
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						empfaenger.sendMessage(new TextComponent(Main.main.friendsPrefix
								+ Main.main.messagesYml.getString("Friends.Command.Add.FriendRequestRecived")
										.replace("[PLAYER]", player.getDisplayName())));
						String zuschreiben = Main.main.friendsPrefix + Main.main.messagesYml
								.getString("Friends.Command.Add.HowToAccept").replace("[PLAYER]", player.getName());
						String clickHere = Main.main.messagesYml.getString("Friends.Command.Add.ClickHere");
						jsoncode = "{'text':'" + zuschreiben + "', 'clickEvent':{'action':'run_command','value':'"
								+ command + "'},'hoverEvent':{'action':'show_text','value':'" + clickHere + "'}}";
					} else {
						empfaenger.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
								+ "§7 Du hast eine Freundschafftsanfrage von §e" + player.getDisplayName()
								+ " §7erhalten"));
						String zuschreiben = "§7 Nimm die Freundschafftsanfrage mit /friend accept §e"
								+ player.getName() + " §7an.";
						jsoncode = "{'text':'" + zuschreiben + "', 'clickEvent':{'action':'run_command','value':'"
								+ command
								+ "'},'hoverEvent':{'action':'show_text','value':'§aHier klicken um die Freundschaftsanfrage anzunehmen'}}";
					}
				}
				empfaenger.unsafe().sendPacket(new Chat(jsoncode));
			}
			if (Main.main.language.equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + "§7 The player §e"
						+ args[1] + "§7 was §7send §7a §7friend §7request"));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					player.sendMessage(new TextComponent(Main.main.friendsPrefix + Main.main.messagesYml
							.getString("Friends.Command.Add.SendedAFriendRequest").replace("[PLAYER]", args[1])));
				} else {
					player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + "§7 Dem Spieler §e"
							+ args[1] + "§7 wurde §7eine §7Freundschafftsanfrage §7gesendet"));
				}
			}
			return;
		}
		if (args.length < 2) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				player.sendMessage(
						new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + " §7You need to give a player"));
				player.sendMessage(new TextComponent("§8/§5friend §5add §5[name §5of §5the §5player]" + ChatColor.RESET
						+ " §8- §7Add §7a §7friend"));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					player.sendMessage(
							new TextComponent(Main.main.messagesYml.getString("Friends.General.NoPlayerGiven")));
					player.sendMessage(new TextComponent(
							Main.main.friendsPrefix + Main.main.messagesYml.getString("Friends.CommandUsage.ADD")));
				} else {
					player.sendMessage(new TextComponent(
							Main.main.friendsPrefix + ChatColor.RESET + " §7Du musst einen Spieler angeben"));
					player.sendMessage(new TextComponent("§8/§5friend §5add §5[name §5of §5the §5player]"
							+ ChatColor.RESET + " §8- §7Add §7a §7friend"));
				}
			}
			return;
		}
		if (args.length > 2) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				player.sendMessage(
						new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + "§7 Too many arguments"));
				player.sendMessage(new TextComponent("§8/§5friend §5add §5[name §5of §5the §5player]" + ChatColor.RESET
						+ " §8- §7Add §7a §7friend"));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					player.sendMessage(new TextComponent(Main.main.friendsPrefix
							+ Main.main.messagesYml.getString("Friends.General.TooManyArguments")));
					player.sendMessage(new TextComponent(Main.main.messagesYml.getString("Friends.CommandUsage.ADD")));
				} else {
					player.sendMessage(
							new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + "§7 Zu viele Argumente"));
					player.sendMessage(new TextComponent("§8/§5friends add [Name des Spielers]" + ChatColor.RESET
							+ " §8- §7Fügt einen Freund hinzu"));
				}
			}
			return;
		}
	}
}
