package party.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import mySql.mySql;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import party.Party;
import party.PartyManager;
import party.PlayerParty;

public class PartyCommand extends Command {

	private List<SubCommand> cmds;

	private String language;

	public PartyCommand(mySql verbindung, String allias, String joinAllias, String inviteAllias, String kickAllias,
			String infoAllias, String leaveAllias, String chatAllias, String leaderAllias, String languageOverGive) {
		super("Party", "", allias);
		language = languageOverGive;
		cmds = new ArrayList<SubCommand>();
		cmds.add(new Join(joinAllias, languageOverGive));
		cmds.add(new Invite(verbindung, inviteAllias, languageOverGive));
		cmds.add(new Kick(kickAllias, languageOverGive));
		cmds.add(new Info(infoAllias, languageOverGive));
		cmds.add(new Leave(leaveAllias, languageOverGive));
		cmds.add(new Chat(chatAllias, languageOverGive));
		cmds.add(new Leader(leaderAllias, languageOverGive));
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ProxiedPlayer)) {
			if (language.equalsIgnoreCase("english")) {
				sender.sendMessage(new TextComponent(Party.prefix + "You need to be a player!"));
			} else {
				sender.sendMessage(new TextComponent(Party.prefix + "Du must ein Spieler sein!"));
			}
			return;
		}
		ProxiedPlayer player = (ProxiedPlayer) sender;
		if (args.length == 0) {
			PlayerParty party = PartyManager.getParty(player);
			player.sendMessage(new TextComponent(
					"§8§m-------------------" + ChatColor.RESET + "§8[§5§lParty§8]§m-------------------"));
			if (language.equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent("§8/§5Party " + "join <Name>" + " §8- §7Join §7a §7party"));
				player.sendMessage(new TextComponent(
						"§8/§5Party " + "invite <player>" + " §8- §7Invite §7a §7player §7into §7your §7Party"));
				if (party != null) {
					player.sendMessage(new TextComponent(
							"§8/§5Party " + "list" + " §8- §7List §7all §7players §7who §7are §7in §7the §7party"));
					player.sendMessage(new TextComponent("§8/§5Party " + "chat <message>"
							+ " §8- §7Send §7all §7players §7in §7the §7party §7a §7message"));
					player.sendMessage(new TextComponent("§8/§5Party " + "leave" + " §8- §7Leave the party"));
					if (party.isleader(player)) {
						player.sendMessage(new TextComponent("§8/§5Party " + "kick <player>"
								+ " §8- §7Kicks §7a §7player §7out §7of §7the §7party"));
						player.sendMessage(new TextComponent("§8/§5Party " + "leader §5<player>"
								+ " §8- §7Makes §7another §7player §7to §7the §7party §7leader"));
					}
				}
			} else {
				player.sendMessage(new TextComponent("§8/§5Party " + "join <Name>" + " §8- §7Trete einer Party bei"));
				player.sendMessage(new TextComponent(
						"§8/§5Party " + "invite <Name>" + " §8- §7Lade §7einen §7Spieler §7in §7deine §7Party §7ein"));
				if (party != null) {
					player.sendMessage(
							new TextComponent("§8/§5Party " + "list" + " §8- §7Listet alle Spieler in der Party auf"));
					player.sendMessage(new TextComponent("§8/§5Party " + "chat <Nachricht>"
							+ " §8- §7Sendet allen Spieler in der Party §7eine §7Nachicht"));
					player.sendMessage(new TextComponent("§8/§5Party " + "leave" + " §8- §7Verlässt die Party"));
					if (party.isleader(player)) {
						player.sendMessage(new TextComponent(
								"§8/§5Party " + "kick <Spieler>" + " §8- §7Kickt einen Spieler aus der Party"));
						player.sendMessage(new TextComponent("§8/§5Party " + "leader §5<Spieler>"
								+ " §8- §7Macht einen anderen Spieler zum Leiter"));

					}
				}
			}
			player.sendMessage(new TextComponent("§8§m---------------------------------------------"));
			return;
		}
		SubCommand sc = getCommand(args[0]);

		if (sc == null) {
			if (language.equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent(Party.prefix + "§cThis command doesn´t exist!"));
			} else {
				player.sendMessage(new TextComponent(Party.prefix + "§cDieser Befehl Existiert nicht!"));
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
