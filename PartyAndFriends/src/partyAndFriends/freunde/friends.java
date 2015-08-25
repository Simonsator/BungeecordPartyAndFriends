/**
 * The /friend command class
 * @author Simonsator
 * @version 1.0.0
 */
package partyAndFriends.freunde;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import partyAndFriends.freunde.kommandos.accept;
import partyAndFriends.freunde.kommandos.add;
import partyAndFriends.freunde.kommandos.deny;
import partyAndFriends.freunde.kommandos.einstellungen;
import partyAndFriends.freunde.kommandos.jump;
import partyAndFriends.freunde.kommandos.list;
import partyAndFriends.freunde.kommandos.message;
import partyAndFriends.freunde.kommandos.remove;
import partyAndFriends.main.Main;
import partyAndFriends.utilities.ContainsIgnoreCase;
import partyAndFriends.utilities.MessagesYML;
import partyAndFriends.utilities.StringToArrayList;

/**
 * The /friend command class
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class friends extends Command {
	/**
	 * The Alias for the subcommands
	 */
	private ArrayList<ArrayList<String>> subCommandAlias = new ArrayList<>();

	/**
	 * Initials the object
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param friendAllias
	 *            The alias for the /friend command
	 */
	public friends(String[] friendAllias) {
		super("friend", Main.main.config.getString("Permissions.FriendPermission"), friendAllias);
		subCommandAlias.add(StringToArrayList.stringToArrayList(Main.main.config.getString("Aliases.ListAlias")));
		subCommandAlias.add(StringToArrayList.stringToArrayList(Main.main.config.getString("Aliases.ChatAlias")));
		subCommandAlias.add(StringToArrayList.stringToArrayList(Main.main.config.getString("Aliases.AddAlias")));
		subCommandAlias.add(StringToArrayList.stringToArrayList(Main.main.config.getString("Aliases.AcceptAlias")));
		subCommandAlias.add(StringToArrayList.stringToArrayList(Main.main.config.getString("Aliases.DenyAlias")));
		subCommandAlias.add(StringToArrayList.stringToArrayList(Main.main.config.getString("Aliases.RemoveAlias")));
		subCommandAlias.add(StringToArrayList.stringToArrayList(Main.main.config.getString("Aliases.SettingsAlias")));
		subCommandAlias.add(StringToArrayList.stringToArrayList(Main.main.config.getString("Aliases.jump")));
	}

	/**
	 * Executed on /friend
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param commandSender
	 *            The sender of the command
	 * @param args
	 *            The arguments
	 */
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (!(commandSender instanceof ProxiedPlayer)) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				commandSender.sendMessage(new TextComponent(Main.main.friendsPrefix + "You need to be a player!"));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					try {
						Main.main.messagesYml = MessagesYML.ladeMessages();
					} catch (IOException e) {
						e.printStackTrace();
					}
					commandSender.sendMessage(new TextComponent(Main.main.friendsPrefix + "Config reloaded!"));
				} else {
					commandSender.sendMessage(new TextComponent(Main.main.friendsPrefix + "Du must ein Spieler sein!"));
				}
			}
			return;
		}
		ProxiedPlayer player = (ProxiedPlayer) commandSender;
		if (args.length == 0) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent(
						"§8§m-------------------" + ChatColor.RESET + "§8[§5§lFriends§8]§m-------------------"));
				if (!Main.main.config.getString("General.DisableCommand.Friends.List").equalsIgnoreCase("true")) {
					player.sendMessage(new TextComponent(
							"§8/§5friend list" + ChatColor.RESET + " §8- §7Lists §7all §7of §7your §7friends"));
				}
				if (!Main.main.config.getString("General.DisableCommand.Friends.MSG").equalsIgnoreCase("true")) {
					player.sendMessage(new TextComponent("§8/§5friend §5msg §5[name §5of §5the §5friend] §5[message]"
							+ ChatColor.RESET + " §8- §7send §7a §7friend §7a §7message"));
				}
				player.sendMessage(new TextComponent("§8/§5friend §5add §5[name §5of §5the §5player]" + ChatColor.RESET
						+ " §8- §7Add §7a §7friend"));
				player.sendMessage(new TextComponent("§8/§5friend §5accept §5[name §5of §5the §5player]"
						+ ChatColor.RESET + " §8- §7accept §7a §7friend request"));
				player.sendMessage(new TextComponent("§8/§5friend §5deny §5[name §5of §5the §5player]" + ChatColor.RESET
						+ " §8- §7deny §7a §7friend §7request"));
				player.sendMessage(new TextComponent("§8/§5friend §5remove §5[name §5of §5the §5friend]"
						+ ChatColor.RESET + " §8- §7removes §7a §7friend"));
				if (!Main.main.config.getString("General.DisableCommand.Friends.Settings").equalsIgnoreCase("true")) {
					player.sendMessage(new TextComponent("§8/§5friend §5settings " + ChatColor.RESET
							+ "§8- §7settings §7of §7the §7party- §7and §7friendsystem"));
				}
				if (!Main.main.config.getString("General.DisableCommand.Friends.Jump").equalsIgnoreCase("true")) {
					player.sendMessage(new TextComponent("§8/§5friend §5jump [name of the §5friend]" + ChatColor.RESET
							+ "§8- §7Jump §7to §7a §7friend"));
				}
				player.sendMessage(new TextComponent("§8§m-----------------------------------------------"));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					player.sendMessage(new TextComponent(Main.main.messagesYml.getString("Friends.General.HelpBegin")));
					if (!Main.main.config.getString("General.DisableCommand.Friends.List").equalsIgnoreCase("true")) {
						player.sendMessage(
								new TextComponent(Main.main.messagesYml.getString("Friends.CommandUsage.List")));
					}
					if (!Main.main.config.getString("General.DisableCommand.Friends.MSG").equalsIgnoreCase("true")) {
						player.sendMessage(
								new TextComponent(Main.main.messagesYml.getString("Friends.CommandUsage.MSG")));
					}
					player.sendMessage(new TextComponent(Main.main.messagesYml.getString("Friends.CommandUsage.ADD")));
					player.sendMessage(
							new TextComponent(Main.main.messagesYml.getString("Friends.CommandUsage.Accept")));
					player.sendMessage(new TextComponent(Main.main.messagesYml.getString("Friends.CommandUsage.Deny")));
					player.sendMessage(
							new TextComponent(Main.main.messagesYml.getString("Friends.CommandUsage.Remove")));
					if (!Main.main.config.getString("General.DisableCommand.Friends.Settings")
							.equalsIgnoreCase("true")) {
						player.sendMessage(
								new TextComponent(Main.main.messagesYml.getString("Friends.CommandUsage.Settings")));
					}
					player.sendMessage(new TextComponent(Main.main.messagesYml.getString("Friends.CommandUsage.Jump")));
					if (!Main.main.config.getString("General.DisableCommand.Friends.Jump").equalsIgnoreCase("true")) {
						player.sendMessage(
								new TextComponent(Main.main.messagesYml.getString("Friends.General.HelpEnd")));
					}
				} else {
					player.sendMessage(new TextComponent(
							"§8§m-------------------" + ChatColor.RESET + "§8[§5§lFriends§8]§m-------------------"));
					if (!Main.main.config.getString("General.DisableCommand.Friends.List").equalsIgnoreCase("true")) {
						player.sendMessage(new TextComponent(
								"§8/§5friend list" + ChatColor.RESET + " §8- §7Listet §7deine §7Freunde §7auf"));
					}
					if (!Main.main.config.getString("General.DisableCommand.Friends.MSG").equalsIgnoreCase("true")) {
						player.sendMessage(new TextComponent("§8/§5friend §5msg §5[Name §5des §5Freundes] §5[Nachricht]"
								+ ChatColor.RESET + " §8- §7schickt §7einem §7Freund §7eine §7Private Nachricht"));
					}
					player.sendMessage(new TextComponent("§8/§5friend §5add §5[Name §5des §5Spielers]" + ChatColor.RESET
							+ " §8- §7Fügt §7einen §7Freund §7hinzu"));
					player.sendMessage(new TextComponent("§8/§5friend §5accept §5[Name §5des §5Spielers]"
							+ ChatColor.RESET + " §8- §7Akzeptiert §7eine §7Freundschaftsanfrage"));
					player.sendMessage(new TextComponent("§8/§5friend §5deny §5[Name §5des §5Spielers]"
							+ ChatColor.RESET + " §8- §7Lehnt eine §7Freundschaftsanfrage §7ab"));
					player.sendMessage(new TextComponent("§8/§5friend §5remove §5[Name §5des §5Spielers]"
							+ ChatColor.RESET + " §8- §7Entfernt §7einen §7Freund"));
					if (!Main.main.config.getString("General.DisableCommand.Friends.Settings")
							.equalsIgnoreCase("true")) {
						player.sendMessage(new TextComponent("§8/§5friend §5settings " + ChatColor.RESET
								+ "§8- §7Einstellungen §7des §7Party- §7und §7Freundsystems"));
					}
					if (!Main.main.config.getString("General.DisableCommand.Friends.Jump").equalsIgnoreCase("true")) {
						player.sendMessage(new TextComponent("§8/§5friend §5jump [Name des Freundes]" + ChatColor.RESET
								+ "§8- §7Zu §7einem §7Freund §7springen"));
					}
					player.sendMessage(new TextComponent("§8§m-----------------------------------------------"));
				}
			}
			return;
		}
		if (!Main.main.config.getString("General.DisableCommand.Friends.List").equalsIgnoreCase("true")) {
			if (args[0].equalsIgnoreCase("list")
					|| ContainsIgnoreCase.containsIgnoreCase(subCommandAlias.get(0), args[0])) {
				try {
					list.auflisten(player, args, Main.main);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		if (!Main.main.config.getString("General.DisableCommand.Friends.MSG").equalsIgnoreCase("true")) {
			if (args[0].equalsIgnoreCase("msg") || args[0].equalsIgnoreCase("message")
					|| ContainsIgnoreCase.containsIgnoreCase(subCommandAlias.get(1), args[0])) {
				try {
					message.callMessageSend(player, args, Main.main);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		if (args[0].equalsIgnoreCase("add") || ContainsIgnoreCase.containsIgnoreCase(subCommandAlias.get(2), args[0])) {
			try {
				add.hinzufuegen(player, args);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (args[0].equalsIgnoreCase("accept")
				|| ContainsIgnoreCase.containsIgnoreCase(subCommandAlias.get(3), args[0])) {
			try {
				accept.akzeptieren(player, args);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (args[0].equalsIgnoreCase("deny")
				|| ContainsIgnoreCase.containsIgnoreCase(subCommandAlias.get(4), args[0])) {
			try {
				deny.ablehnen(player, args);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (args[0].equalsIgnoreCase("remove")
				|| ContainsIgnoreCase.containsIgnoreCase(subCommandAlias.get(5), args[0])) {
			try {
				remove.entfernen(player, args);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (!Main.main.config.getString("General.DisableCommand.Friends.Settings").equalsIgnoreCase("true")) {
			if (args[0].equalsIgnoreCase("einstellungen") || args[0].equalsIgnoreCase("einstellung")
					|| args[0].equalsIgnoreCase("setting") || args[0].equalsIgnoreCase("settings")
					|| ContainsIgnoreCase.containsIgnoreCase(subCommandAlias.get(6), args[0])) {
				try {
					einstellungen.settings(player, args);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		if (!Main.main.config.getString("General.DisableCommand.Friends.Jump").equalsIgnoreCase("true")) {
			if (args[0].equalsIgnoreCase("jump")
					|| ContainsIgnoreCase.containsIgnoreCase(subCommandAlias.get(7), args[0])) {
				try {
					jump.springen(player, args);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		if (Main.main.language.equalsIgnoreCase("english")) {
			player.sendMessage(
					new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + " §7The §7Command §7doesn´t §7exist."));
		} else {
			if (Main.main.language.equalsIgnoreCase("own")) {
				player.sendMessage(new TextComponent(
						Main.main.friendsPrefix + Main.main.messagesYml.getString("Friends.General.CommandNotFound")));
			} else {
				player.sendMessage(new TextComponent(
						"§8[§5§lFriends§8]" + ChatColor.RESET + " §7Das §7Kommando §7existiert §7nicht."));
			}
		}
	}
}
