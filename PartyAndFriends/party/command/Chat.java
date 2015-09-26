/**
 * The /party chat command
 * 
 * @author Simonsator
 * @version 1.0.0
 */
package partyAndFriends.party.command;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import partyAndFriends.main.Main;
import partyAndFriends.party.PartyManager;
import partyAndFriends.party.PlayerParty;
import partyAndFriends.utilities.StringToArray;

/**
 * The /party chat command
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Chat extends SubCommand {
	/**
	 * The chat prefix
	 */
	private String chatPrefix = "§7[§5PartyChat§7] ";
	/**
	 * The color of the message
	 */
	private String farbe = "§7";

	/**
	 * Initials the object
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public Chat() {
		super(StringToArray.stringToArray(Main.main.config.getString("Aliases.ChatAlias")));
		if (Main.main.language.equalsIgnoreCase("own")) {
			chatPrefix = Main.main.messagesYml.getString("Party.Command.Chat.Prefix");
			farbe = Main.main.messagesYml.getString("Party.Command.Chat.ContentColor");
		}
	}

	/**
	 * Will be executed on /party chat
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param p
	 *            The player
	 * @param args
	 *            The arguments
	 */
	public void onCommand(ProxiedPlayer p, String[] args) {
		if (PartyManager.getParty(p) != null) {
			if (args.length > 0) {
				PlayerParty party = PartyManager.getParty(p);
				String text = "";
				for (String arg : args) {
					text += " " + farbe + arg;
				}
				for (ProxiedPlayer player : party.getPlayer()) {
					if (Main.main.language.equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(chatPrefix + Main.main.messagesYml
								.getString("Party.Command.Chat.PartyChatOutput")
								.replace("[SENDERNAME]", p.getDisplayName()).replace("[MESSAGE_CONTENT]", text)));
					} else {
						player.sendMessage(new TextComponent(chatPrefix + "§e" + p.getDisplayName() + "§5:" + text));
					}
				}
				if (Main.main.language.equalsIgnoreCase("own")) {
					party.getleader()
							.sendMessage(new TextComponent(chatPrefix + Main.main.messagesYml
									.getString("Party.Command.Chat.PartyChatOutput")
									.replace("[SENDERNAME]", p.getDisplayName()).replace("[MESSAGE_CONTENT]", text)));
				} else {
					party.getleader()
							.sendMessage(new TextComponent(chatPrefix + "§e" + p.getDisplayName() + "§5:" + text));
				}
			} else {
				if (Main.main.language.equalsIgnoreCase("english")) {
					p.sendMessage(new TextComponent(chatPrefix + "§5You need to give a message"));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						p.sendMessage(new TextComponent(
								chatPrefix + Main.main.messagesYml.getString("Party.Command.Chat.ErrorNoMessage")));
					} else {
						p.sendMessage(new TextComponent(chatPrefix + "§5Du musst eine Nachricht eingeben"));
					}
				}
			}
		} else {
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(chatPrefix + "§5You need to be in a party"));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(
							chatPrefix + Main.main.messagesYml.getString("Party.Command.General.ErrorNoParty")));
				} else {
					p.sendMessage(new TextComponent(chatPrefix + "§5Du musst in einer Party sein"));
				}
			}
		}
	}
}
