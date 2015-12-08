/**
 * The /party command
 * 
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.party.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.PartyManager;
import de.simonsator.partyandfriends.party.PlayerParty;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * The /party command
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class PartyCommand extends Command {
	/**
	 * A list of the command classes
	 */
	private List<SubCommand> cmds;

	/**
	 * Initials the object
	 * 
	 * @param allias
	 *            The alias for the command
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public PartyCommand(String[] allias) {
		super("Party", Main.getInstance().getConfig().getString("Permissions.PartyPermission"), allias);
		cmds = new ArrayList<SubCommand>();
		cmds.add(new Join());
		cmds.add(new Invite());
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Kick").equalsIgnoreCase("true")) {
			cmds.add(new Kick());
		}
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Info").equalsIgnoreCase("true")) {
			cmds.add(new Info());
		}
		cmds.add(new Leave());
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Chat").equalsIgnoreCase("true")) {
			cmds.add(new Chat());
		}
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Leader").equalsIgnoreCase("true")) {
			cmds.add(new Leader());
		}
	}

	/**
	 * Will be executed on /party
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param sender
	 *            The command sender
	 * @param args
	 *            The arguments
	 */
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ProxiedPlayer)) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
				Main.getInstance().loadConfiguration();
				sender.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "Config and MessagesYML reloaded!"));
			} else {
				Main.getInstance().loadConfiguration();
				sender.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "Config reloaded"));
			}
			return;
		}
		ProxiedPlayer player = (ProxiedPlayer) sender;
		if (args.length == 0) {
			PlayerParty party = PartyManager.getParty(player);
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent(
						"§8§m-------------------" + ChatColor.RESET + "§8[§5§lParty§8]§m-------------------"));
				player.sendMessage(new TextComponent("§8/§5Party " + "join <Name>" + " §8- §7Join §7a §7party"));
				player.sendMessage(new TextComponent(
						"§8/§5Party " + "invite <player>" + " §8- §7Invite §7a §7player §7into §7your §7Party"));
				if (party != null) {
					if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Info").equalsIgnoreCase("true")) {
						player.sendMessage(new TextComponent(
								"§8/§5Party " + "list" + " §8- §7List §7all §7players §7who §7are §7in §7the §7party"));
					}
					if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Chat").equalsIgnoreCase("true")) {
						player.sendMessage(new TextComponent("§8/§5Party " + "chat <message>"
								+ " §8- §7Send §7all §7players §7in §7the §7party §7a §7message"));
					}
					player.sendMessage(new TextComponent("§8/§5Party " + "leave" + " §8- §7Leave the party"));
					if (party.isleader(player)) {
						if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Kick").equalsIgnoreCase("true")) {
							player.sendMessage(new TextComponent("§8/§5Party " + "kick <player>"
									+ " §8- §7Kicks §7a §7player §7out §7of §7the §7party"));
						}
						if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Leader")
								.equalsIgnoreCase("true")) {
							player.sendMessage(new TextComponent("§8/§5Party " + "leader §5<player>"
									+ " §8- §7Makes §7another §7player §7to §7the §7party §7leader"));
						}
					}
				}
				player.sendMessage(new TextComponent("§8§m---------------------------------------------"));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					player.sendMessage(new TextComponent(Main.getInstance().getMessagesYml().getString("Party.General.HelpBegin")));
					player.sendMessage(new TextComponent(Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Join")));
					player.sendMessage(new TextComponent(Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Invite")));
					if (party != null) {
						if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Info").equalsIgnoreCase("true")) {
							player.sendMessage(
									new TextComponent(Main.getInstance().getMessagesYml().getString("Party.CommandUsage.List")));
						}
						if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Chat").equalsIgnoreCase("true")) {
							player.sendMessage(
									new TextComponent(Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Chat")));
						}
						player.sendMessage(
								new TextComponent(Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Leave")));
						if (party.isleader(player)) {
							if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Kick")
									.equalsIgnoreCase("true")) {
								player.sendMessage(
										new TextComponent(Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Kick")));
							}
							if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Leader")
									.equalsIgnoreCase("true")) {
								player.sendMessage(new TextComponent(
										Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Leader")));
							}
						}
					}
					player.sendMessage(new TextComponent(Main.getInstance().getMessagesYml().getString("Party.General.HelpEnd")));
				} else {
					player.sendMessage(new TextComponent(
							"§8§m-------------------" + ChatColor.RESET + "§8[§5§lParty§8]§m-------------------"));
					player.sendMessage(
							new TextComponent("§8/§5Party " + "join <Name>" + " §8- §7Trete einer Party bei"));
					player.sendMessage(new TextComponent("§8/§5Party " + "invite <Name>"
							+ " §8- §7Lade §7einen §7Spieler §7in §7deine §7Party §7ein"));
					if (party != null) {
						if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Info").equalsIgnoreCase("true")) {
							player.sendMessage(new TextComponent(
									"§8/§5Party " + "list" + " §8- §7Listet alle Spieler in der Party auf"));
						}
						if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Chat").equalsIgnoreCase("true")) {
							player.sendMessage(new TextComponent("§8/§5Party " + "chat <Nachricht>"
									+ " §8- §7Sendet allen Spieler in der Party §7eine §7Nachicht"));
						}
						player.sendMessage(new TextComponent("§8/§5Party " + "leave" + " §8- §7Verlässt die Party"));
						if (party.isleader(player)) {
							if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Kick")
									.equalsIgnoreCase("true")) {
								player.sendMessage(new TextComponent(
										"§8/§5Party " + "kick <Spieler>" + " §8- §7Kickt einen Spieler aus der Party"));
							}
							if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Leader")
									.equalsIgnoreCase("true")) {
								player.sendMessage(new TextComponent("§8/§5Party " + "leader §5<Spieler>"
										+ " §8- §7Macht einen anderen Spieler zum Leiter"));
							}
						}
					}
					player.sendMessage(new TextComponent("§8§m---------------------------------------------"));
				}
			}
			return;
		}
		SubCommand sc = getCommand(args[0]);

		if (sc == null) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cThis command doesn´t exist!"));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					player.sendMessage(new TextComponent(
							Main.getInstance().getPartyPrefix() + Main.getInstance().getMessagesYml().getString("Party.Error.CommandNotFound")));
				} else {
					player.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cDieser Befehl Existiert nicht!"));
				}
			}
			return;
		}
		Vector<String> a = new Vector<String>(Arrays.asList(args));
		a.remove(0);
		args = a.toArray(new String[a.size()]);
		sc.onCommand(player, args);
		return;
	}

	public String aliases(SubCommand sc) {
		String fin = "";

		for (String a : sc.getAliases()) {
			fin += a + " | ";
		}
		return fin.substring(0, fin.lastIndexOf(" | "));
	}

	public SubCommand getCommand(String name) {
		for (SubCommand c : cmds) {
			if (c.getClass().getSimpleName().equalsIgnoreCase(name))
				return c;
			for (String alias : c.getAliases())
				if (alias.equalsIgnoreCase(name))
					return c;
		}
		return null;
	}
}
