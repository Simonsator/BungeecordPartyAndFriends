/**
 * This class is to sends a message from a sender to a friend
 * @author Simonsator
 * @version 1.0.0
 * @see freunde.kommandos.message#callMessageSend()
 */
package partyAndFriends.freunde;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import partyAndFriends.main.Main;

/**
 * This class is to sends a message from a sender to a friend
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class nachricht {
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
				if (Main.main.language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(
							Main.main.friendsPrefix + ChatColor.RESET + " §7You can §7not §7write §7to §7yourself."));
					player.sendMessage(new TextComponent("§8/§5friend §5msg §5[name §5of §5the §5friend] §5[message]"
							+ ChatColor.RESET + " §8- §7send §7a §7friend §7a §7message"));
				} else {
					if (Main.main.language.equals("own")) {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix
								+ Main.main.messagesYml.getString("Friends.Command.Message.ErrorSenderEqualReciver")));
						player.sendMessage(
								new TextComponent(Main.main.messagesYml.getString("Friends.CommandUsage.MSG")));
					} else {
						player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
								+ " §7Du kannst dich nicht selber anschreiben."));
						player.sendMessage(
								new TextComponent("§8/§5friends §5msg §5[Name §5des §5Freundes] §5[Nachricht]"));
					}
				}
				return;
			}
			if (Main.main.verbindung.getIDByPlayerName(args[begin]) == -1) {
				switch (Main.main.language) {
				case "english":
					player.sendMessage(
							new TextComponent(Main.main.friendsPrefix + " §7You can not write to this player."));
					break;
				case "own":
					player.sendMessage(new TextComponent(Main.main.friendsPrefix
							+ Main.main.messagesYml.getString("Friends.Command.MSG.CanNotWriteToHim")));
					break;
				default:
					player.sendMessage(new TextComponent(
							Main.main.friendsPrefix + " §7Du kannst diesem Spieler nicht schreiben."));
					break;
				}
				return;
			}
			if (Main.main.verbindung.istBefreundetMit(player.getName(), args[begin])) {
				if (Main.main.verbindung.einstellungenAbfragen(player)[2] == 1) {
					switch (Main.main.language) {
					case "english":
						player.sendMessage(
								new TextComponent(Main.main.friendsPrefix + " §7You can not write to this player."));
						break;
					case "own":
						player.sendMessage(new TextComponent(Main.main.friendsPrefix
								+ Main.main.messagesYml.getString("Friends.Command.MSG.CanNotWriteToHim")));
						break;
					default:
						player.sendMessage(new TextComponent(
								Main.main.friendsPrefix + " §7Du kannst diesem Spieler nicht schreiben."));
						break;
					}
					return;
				}
				ProxiedPlayer angeschrieben = BungeeCord.getInstance().getPlayer(args[begin]);
				if (angeschrieben == null) {
					if (Main.main.language.equalsIgnoreCase("english")) {
						player.sendMessage(
								new TextComponent(Main.main.friendsPrefix + " §7The §7player §7is §7not §7online."));
					} else {
						if (Main.main.language.equalsIgnoreCase("own")) {
							player.sendMessage(new TextComponent(Main.main.friendsPrefix
									+ Main.main.messagesYml.getString("Friends.General.NotAFriendOfOrOffline")));
						} else {
							player.sendMessage(new TextComponent(
									Main.main.friendsPrefix + " §7Der §7Spieler §7ist §7nicht §7online."));
						}
					}
					return;
				} else {
					int durchlauf = 2;
					if (type == 1) {
						durchlauf = 1;
					}
					nachrichtSenden(args, durchlauf, angeschrieben, player);
					return;
				}
			} else {
				switch (Main.main.language) {
				case "english":
					player.sendMessage(
							new TextComponent(Main.main.friendsPrefix + " §7You can not write to this player."));
					break;
				case "own":
					player.sendMessage(new TextComponent(Main.main.friendsPrefix
							+ Main.main.messagesYml.getString("Friends.Command.MSG.CanNotWriteToHim")));
					break;
				default:
					player.sendMessage(new TextComponent(
							Main.main.friendsPrefix + " §7Du kannst diesem Spieler nicht schreiben."));
					break;
				}
				return;
			}
		} else {

			if (Main.main.language.equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
						+ " §7You §7need §7to §7give §7a §7player §7and §7a §7message."));
				player.sendMessage(new TextComponent("§8/§5friend §5msg §5[name §5of §5the §5friend] §5[message]"
						+ ChatColor.RESET + " §8- §7send §7a §7friend §7a §7message"));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					player.sendMessage(
							new TextComponent(Main.main.messagesYml.getString("Friends.General.NoPlayerGiven")));
					player.sendMessage(new TextComponent(
							Main.main.friendsPrefix + Main.main.messagesYml.getString("Friends.CommandUsage.MSG")));
				} else {
					player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET
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
	 * @param durchlauf
	 *            Says where the loop should start
	 * @param angeschrieben
	 *            The person who was wrote to
	 * @param player
	 *            The sender of the command
	 */
	private static void nachrichtSenden(String[] args, int durchlauf, ProxiedPlayer angeschrieben,
			ProxiedPlayer player) {
		String Inhalt = message(args, durchlauf);
		if (Main.main.language.equalsIgnoreCase("own")) {
			angeschrieben.sendMessage(new TextComponent(Main.main.friendsPrefix + Main.main.messagesYml
					.getString("Friends.Command.MSG.SendedMessage").replace("[SENDER]", player.getDisplayName())
					.replace("[PLAYER]", angeschrieben.getDisplayName()).replace("[CONTENT]", Inhalt)));
			player.sendMessage(new TextComponent(Main.main.friendsPrefix + Main.main.messagesYml
					.getString("Friends.Command.MSG.SendedMessage").replace("[SENDER]", player.getDisplayName())
					.replace("[PLAYER]", angeschrieben.getDisplayName()).replace("[CONTENT]", Inhalt)));
		} else

		{
			angeschrieben.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + " §e"
					+ player.getDisplayName() + "§5-> §e" + angeschrieben.getDisplayName() + "§7:" + Inhalt));
			player.sendMessage(new TextComponent(Main.main.friendsPrefix + ChatColor.RESET + " §e"
					+ player.getDisplayName() + "§5-> §e" + angeschrieben.getDisplayName() + "§7:" + Inhalt));
		}
	}

	/**
	 * Returns a styled message
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param args
	 *            The Arguments The Main.main class
	 * @param durchlauf
	 *            At which argument the while loob should start
	 * @return Returns a styled message
	 */
	public static String message(String[] args, int durchlauf) {
		String inhaltFarbe;
		if (Main.main.language.equalsIgnoreCase("own")) {
			inhaltFarbe = Main.main.messagesYml.getString("Friends.Command.MSG.ColorOfMessage");
		} else {
			inhaltFarbe = " §7";
		}
		String Inhalt = "";
		while (durchlauf < args.length) {
			Inhalt = Inhalt + inhaltFarbe + args[durchlauf];
			durchlauf++;
		}
		return inhaltFarbe + Inhalt;
	}
}
