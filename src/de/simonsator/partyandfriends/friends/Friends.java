/**
 * The /friend command class
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.friends;

import java.util.ArrayList;

import de.simonsator.partyandfriends.friends.commands.Accept;
import de.simonsator.partyandfriends.friends.commands.Add;
import de.simonsator.partyandfriends.friends.commands.Deny;
import de.simonsator.partyandfriends.friends.commands.Jump;
import de.simonsator.partyandfriends.friends.commands.List;
import de.simonsator.partyandfriends.friends.commands.Message;
import de.simonsator.partyandfriends.friends.commands.Remove;
import de.simonsator.partyandfriends.friends.commands.Settings;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.utilities.ContainsIgnoreCase;
import de.simonsator.partyandfriends.utilities.StringToArrayList;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * The /friend command class
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Friends extends Command {
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
	public Friends(String[] friendAllias) {
		super("friend", Main.getInstance().getConfig().getString("Permissions.FriendPermission"), friendAllias);
		subCommandAlias.add(StringToArrayList.stringToArrayList(Main.getInstance().getConfig().getString("Aliases.ListAlias")));
		subCommandAlias.add(StringToArrayList.stringToArrayList(Main.getInstance().getConfig().getString("Aliases.ChatAlias")));
		subCommandAlias.add(StringToArrayList.stringToArrayList(Main.getInstance().getConfig().getString("Aliases.AddAlias")));
		subCommandAlias.add(StringToArrayList.stringToArrayList(Main.getInstance().getConfig().getString("Aliases.AcceptAlias")));
		subCommandAlias.add(StringToArrayList.stringToArrayList(Main.getInstance().getConfig().getString("Aliases.DenyAlias")));
		subCommandAlias.add(StringToArrayList.stringToArrayList(Main.getInstance().getConfig().getString("Aliases.RemoveAlias")));
		subCommandAlias.add(StringToArrayList.stringToArrayList(Main.getInstance().getConfig().getString("Aliases.JumpAlias")));
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
			if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
				Main.getInstance().loadConfiguration();
				commandSender
						.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + "Config and MessagesYML reloaded!"));
			} else {
				Main.getInstance().loadConfiguration();
				commandSender.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + "Config reloaded"));
			}
			return;
		}
		ProxiedPlayer player = (ProxiedPlayer) commandSender;
		if (args.length == 0) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent(
						"§8§m-------------------" + ChatColor.RESET + "§8[§5§lFriends§8]§m-------------------"));
				if (!Main.getInstance().getConfig().getString("General.DisableCommand.Friends.List").equalsIgnoreCase("true")) {
					player.sendMessage(new TextComponent(
							"§8/§5friend list" + ChatColor.RESET + " §8- §7Lists §7all §7of §7your §7friends"));
				}
				if (!Main.getInstance().getConfig().getString("General.DisableCommand.Friends.MSG").equalsIgnoreCase("true")) {
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
				if (!Main.getInstance().getConfig().getString("General.DisableCommand.Friends.Jump").equalsIgnoreCase("true")) {
					player.sendMessage(new TextComponent("§8/§5friend §5jump [name of the §5friend]" + ChatColor.RESET
							+ "§8- §7Jump §7to §7a §7friend"));
				}
				player.sendMessage(new TextComponent("§8§m-----------------------------------------------"));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					player.sendMessage(new TextComponent(Main.getInstance().getMessagesYml().getString("Friends.General.HelpBegin")));
					if (!Main.getInstance().getConfig().getString("General.DisableCommand.Friends.List").equalsIgnoreCase("true")) {
						player.sendMessage(
								new TextComponent(Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.List")));
					}
					if (!Main.getInstance().getConfig().getString("General.DisableCommand.Friends.MSG").equalsIgnoreCase("true")) {
						player.sendMessage(
								new TextComponent(Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.MSG")));
					}
					player.sendMessage(new TextComponent(Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.ADD")));
					player.sendMessage(
							new TextComponent(Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Accept")));
					player.sendMessage(new TextComponent(Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Deny")));
					player.sendMessage(
							new TextComponent(Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Remove")));
					if (!Main.getInstance().getConfig().getString("General.DisableCommand.Friends.Jump").equalsIgnoreCase("true")) {
						player.sendMessage(
								new TextComponent(Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Jump")));
					}
					player.sendMessage(new TextComponent(Main.getInstance().getMessagesYml().getString("Friends.General.HelpEnd")));
				} else {
					player.sendMessage(new TextComponent(
							"§8§m-------------------" + ChatColor.RESET + "§8[§5§lFriends§8]§m-------------------"));
					if (!Main.getInstance().getConfig().getString("General.DisableCommand.Friends.List").equalsIgnoreCase("true")) {
						player.sendMessage(new TextComponent(
								"§8/§5friend list" + ChatColor.RESET + " §8- §7Listet §7deine §7Freunde §7auf"));
					}
					if (!Main.getInstance().getConfig().getString("General.DisableCommand.Friends.MSG").equalsIgnoreCase("true")) {
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
					if (!Main.getInstance().getConfig().getString("General.DisableCommand.Friends.Jump").equalsIgnoreCase("true")) {
						player.sendMessage(new TextComponent("§8/§5friend §5jump [Name des Freundes]" + ChatColor.RESET
								+ "§8- §7Zu §7einem §7Freund §7springen"));
					}
					player.sendMessage(new TextComponent("§8§m-----------------------------------------------"));
				}
			}
			return;
		}
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Friends.List").equalsIgnoreCase("true")) {
			if (args[0].equalsIgnoreCase("list")
					|| ContainsIgnoreCase.containsIgnoreCase(subCommandAlias.get(0), args[0])) {
				List.list(player, args);
				return;
			}
		}
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Friends.MSG").equalsIgnoreCase("true")) {
			if (args[0].equalsIgnoreCase("msg") || args[0].equalsIgnoreCase("message")
					|| ContainsIgnoreCase.containsIgnoreCase(subCommandAlias.get(1), args[0])) {
				Message.callMessageSend(player, args, Main.getInstance());
				return;
			}
		}
		if (args[0].equalsIgnoreCase("add") || ContainsIgnoreCase.containsIgnoreCase(subCommandAlias.get(2), args[0])) {
			Add.add(player, args);
			return;
		}
		if (args[0].equalsIgnoreCase("accept")
				|| ContainsIgnoreCase.containsIgnoreCase(subCommandAlias.get(3), args[0])) {
			Accept.accept(player, args);
			return;
		}
		if (args[0].equalsIgnoreCase("deny")
				|| ContainsIgnoreCase.containsIgnoreCase(subCommandAlias.get(4), args[0])) {
			Deny.deny(player, args);
			return;
		}
		if (args[0].equalsIgnoreCase("remove")
				|| ContainsIgnoreCase.containsIgnoreCase(subCommandAlias.get(5), args[0])) {
			Remove.remove(player, args);
			return;
		}
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Friends.Settings").equalsIgnoreCase("true")) {
			if (args[0].equalsIgnoreCase("einstellungen") || args[0].equalsIgnoreCase("einstellung")
					|| args[0].equalsIgnoreCase("setting") || args[0].equalsIgnoreCase("settings")) {
				Settings.settings(player, args);
				return;
			}
		}
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Friends.Jump").equalsIgnoreCase("true")) {
			if (args[0].equalsIgnoreCase("jump")
					|| ContainsIgnoreCase.containsIgnoreCase(subCommandAlias.get(6), args[0])) {
				Jump.jump(player, args);
				return;
			}
		}
		if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
			player.sendMessage(
					new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + " §7The §7Command §7doesn´t §7exist."));
		} else {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
				player.sendMessage(new TextComponent(
						Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml().getString("Friends.General.CommandNotFound")));
			} else {
				player.sendMessage(new TextComponent(
						"§8[§5§lFriends§8]" + ChatColor.RESET + " §7Das §7Kommando §7existiert §7nicht."));
			}
		}
	}
}
