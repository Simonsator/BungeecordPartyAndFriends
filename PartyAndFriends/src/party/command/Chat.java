package party.command;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import party.PartyManager;
import party.PlayerParty;

public class Chat extends SubCommand {
	private String chatPrefix = "§7[§5PartyChat§7] ";
	private String language;

	public Chat(String chatAllias, String languageOverGive) {
		super("Sendet allen Spieler in der Party eine §7Nachicht", "",
				new String[] { "chat", "msg", "message", chatAllias });
		language = languageOverGive;
	}

	public void onCommand(ProxiedPlayer p, String[] args) {
		if (PartyManager.getParty(p) != null) {
			if (args.length > 0) {
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
					p.sendMessage(new TextComponent(chatPrefix + "§5You need to give a message"));
				} else {
					p.sendMessage(new TextComponent(chatPrefix + "§5Du musst eine Nachricht eingeben"));
				}
			}
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
