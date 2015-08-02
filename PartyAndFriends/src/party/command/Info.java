package party.command;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import party.Party;
import party.PartyManager;
import party.PlayerParty;

public class Info extends SubCommand {
	private String language;
	private Party haupt;

	public Info(String infoAllias, String languageOverGive, Party main) {
		super("Liste alle Spieler in deiner Party", "", new String[] { "list", infoAllias });
		language = languageOverGive;
		haupt = main;
	}

	public void onCommand(ProxiedPlayer p, String[] args) {
		if (PartyManager.getParty(p) == null) {
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Party.prefix + "§cYou §care §cnot §cin §ca §cparty."));
			} else {
				if (language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(haupt.messagesYml.getString("Party.Command.General.ErrorNoParty")));
				} else {
					p.sendMessage(new TextComponent(Party.prefix + "§cDu §cbist §cin §ckeiner §cParty."));
				}
			}
			return;
		}
		PlayerParty party = PartyManager.getParty(p);
		String leader = "";
		String players = "";
		if (language.equalsIgnoreCase("own")) {
			leader = haupt.messagesYml.getString("Party.Command.Info.Leader");
			players = haupt.messagesYml.getString("Party.Command.Info.Players");
		} else {
			leader = "§3Leader§7: §5" + party.getleader().getName();
			players = "§8Players§7: §b";
		}
		if (!party.getPlayer().isEmpty()) {
			for (ProxiedPlayer pp : party.getPlayer()) {
				players = players + pp.getName() + "§7, §b";
			}
			players = players.substring(0, players.lastIndexOf("§7, §b"));
		} else {
			players = players + "Leer";
		}
		p.sendMessage(new TextComponent("§8§m----------[§5§lPARTY§8]§m----------"));

		p.sendMessage(new TextComponent(Party.prefix + leader));
		p.sendMessage(new TextComponent(Party.prefix + players));

		p.sendMessage(new TextComponent("§8§m-----------------------------------"));
	}
}
