package party.command;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import party.Party;
import party.PartyManager;
import party.PlayerParty;

public class Chat extends SubCommand {
	private String chatPrefix = "§7[§5PartyChat§7] ";
	private String language;
	private Party main;
	private String farbe = "§7";

	public Chat(String chatAllias, String languageOverGive, Party party) {
		super("Sendet allen Spieler in der Party eine §7Nachicht", "",
				new String[] { "chat", "msg", "message", chatAllias });
		language = languageOverGive;
		main = party;
		if (language.equalsIgnoreCase("own")) {
			File file = new File(main.getDataFolder().getPath(), "Messages.yml");
			Configuration messagesYml = null;
			try {
				messagesYml = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			chatPrefix = messagesYml.getString("Party.Command.Chat.Prefix");
			farbe = messagesYml.getString("Party.Command.Chat.ContentColor");
		}
	}

	public void onCommand(ProxiedPlayer p, String[] args) {
		File file = new File(main.getDataFolder().getPath(), "Messages.yml");
		Configuration messagesYml = null;
		try {
			messagesYml = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (PartyManager.getParty(p) != null) {
			if (args.length > 0) {
				PlayerParty party = PartyManager.getParty(p);
				String text = "";
				for (String arg : args) {
					text += " " + farbe + arg;
				}
				for (ProxiedPlayer player : party.getPlayer()) {
					if (language.equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(
								chatPrefix + messagesYml.getString("Party.Command.Chat.PartyChatOutput")
										.replace("[SENDERNAME]", p.getName()).replace("[MESSAGE_CONTENT]", text)));
					} else {
						player.sendMessage(new TextComponent(chatPrefix + "§e" + p.getName() + "§5:" + text));
					}
				}
				if (language.equalsIgnoreCase("own")) {
					party.getleader()
							.sendMessage(new TextComponent(
									chatPrefix + messagesYml.getString("Party.Command.Chat.PartyChatOutput")
											.replace("[SENDERNAME]", p.getName()).replace("[MESSAGE_CONTENT]", text)));
				} else {
					party.getleader().sendMessage(new TextComponent(chatPrefix + "§e" + p.getName() + "§5:" + text));
				}
			} else {
				if (language.equalsIgnoreCase("english")) {
					p.sendMessage(new TextComponent(chatPrefix + "§5You need to give a message"));
				} else {
					if (language.equalsIgnoreCase("own")) {
						p.sendMessage(new TextComponent(
								chatPrefix + messagesYml.getString("Party.Command.Chat.ErrorNoMessage")));
					} else {
						p.sendMessage(new TextComponent(chatPrefix + "§5Du musst eine Nachricht eingeben"));
					}
				}
			}
		} else {
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(chatPrefix + "§5You need to be in a party"));
			} else {
				if (language.equalsIgnoreCase("own")) {
					p.sendMessage(
							new TextComponent(chatPrefix + messagesYml.getString("Party.Command.Chat.ErrorNoParty")));
				} else {
					p.sendMessage(new TextComponent(chatPrefix + "§5Du musst in einer Party sein"));
				}
			}
		}
	}
}
