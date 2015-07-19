package party.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import party.PartyManager;
import party.PlayerParty;

public class P extends Command {
	private String language;
	private String chatPrefix = "§7[§5PartyChat§7] ";

	public P(String alias, String languageovergive, String partyPermission)

	{
		super("p", partyPermission, new String[] { "pc", "pmsg", alias });
		language = languageovergive;
	}

	@Override
	public void execute(CommandSender arg0, String[] args) {
		ProxiedPlayer p = (ProxiedPlayer) arg0;
		if (args.length == 0) {
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(chatPrefix + "§5You need to give a message"));
			} else {
				p.sendMessage(new TextComponent(chatPrefix + "§5Du musst eine Nachricht eingeben"));
			}
			return;
		} else {
			if (PartyManager.getParty(p) != null) {
				PlayerParty party = PartyManager.getParty(p);
				String text = "";
				for (String arg : args) {
					text += " §7" + arg;
				}
				for (ProxiedPlayer player : party.getPlayer()) {
					player.sendMessage(new TextComponent(chatPrefix + "§e" + p.getName() + "§5:" + text));
				}
				party.getleader().sendMessage(new TextComponent(chatPrefix + "§e" + p.getName() + "§5:" + text));
			} else {
				if (language.equalsIgnoreCase("english")) {
					p.sendMessage(new TextComponent(chatPrefix + "§5You need to be in a party"));
				} else {
					if (language.equalsIgnoreCase("own")) {

					} else {
						p.sendMessage(new TextComponent(chatPrefix + "§5Du musst in einer Party sein"));
					}
				}
			}
		}

	}

}
