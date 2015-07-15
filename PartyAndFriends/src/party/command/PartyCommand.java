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

	public PartyCommand(mySql verbindung) {
		super("Party");
		cmds = new ArrayList<SubCommand>();
		cmds.add(new Join());
		cmds.add(new Invite(verbindung));
		cmds.add(new Kick());
		cmds.add(new Info());
		cmds.add(new Leave());
		cmds.add(new Chat());
		cmds.add(new Leader());
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage(new TextComponent(Party.prefix
					+ "Du must ein Spieler sein!"));
			return;
		}
		ProxiedPlayer player = (ProxiedPlayer) sender;
		if (args.length == 0) {
			PlayerParty party = PartyManager.getParty(player);
			player.sendMessage(new TextComponent("§8§m-------------------"
					+ ChatColor.RESET + "§8[§5§lParty§8]§m-------------------"));
			player.sendMessage(new TextComponent("§8/§5Party " + "join <Name>"
					+ " §8- §7Trete einer Party bei"));
			player.sendMessage(new TextComponent(
					"§8/§5Party "
							+ "invite <Name>"
							+ " §8- §7Lade §7einen §7Spieler §7in §7deine §7Party §7ein"));
			if (party != null) {
				player.sendMessage(new TextComponent("§8/§5Party " + "list"
						+ " §8- §7Listet alle Spieler in der Party auf"));
				player.sendMessage(new TextComponent(
						"§8/§5Party "
								+ "chat <Nachricht>"
								+ " §8- §7Sendet allen Spieler in der Party §7eine §7Nachicht"));
				player.sendMessage(new TextComponent("§8/§5Party " + "leave"
						+ " §8- §7Verlässt die Party"));
				if (party.isleader(player)) {
					player.sendMessage(new TextComponent("§8/§5Party "
							+ "kick <Spieler>"
							+ " §8- §7Kickt einen Spieler aus der Party"));
					player.sendMessage(new TextComponent("§8/§5Party "
							+ "leader"
							+ " §8- §7Macht einen anderen Spieler zum Leiter"));
				}
			}
			player.sendMessage(new TextComponent(
					"§8§m---------------------------------------------"));
			return;
		}
		SubCommand sc = getCommand(args[0]);

		if (sc == null) {
			player.sendMessage(new TextComponent(Party.prefix
					+ "§cDieser Befehl Existiert nicht!"));
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
