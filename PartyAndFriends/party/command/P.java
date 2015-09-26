/**
 * The /p command
 * 
 * @author Simonsator
 * @version 1.0.0
 */
package partyAndFriends.party.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import partyAndFriends.main.Main;
import partyAndFriends.party.PartyManager;
import partyAndFriends.party.PlayerParty;

/**
 * The /p command
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class P extends Command {
	/**
	 * The chat prefix
	 */
	private String chatPrefix = "§7[§5PartyChat§7] ";
	/**
	 * The chat color
	 */
	private String farbe = "§7";

	/**
	 * Initials the object
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param alias
	 *            The alias for this command
	 */
	public P(String[] alias) {
		super("p", Main.main.config.getString("Permissions.PartyPermission"), alias);
		if (Main.main.language.equalsIgnoreCase("own")) {
			chatPrefix = Main.main.messagesYml.getString("Party.Command.Chat.Prefix");
			farbe = Main.main.messagesYml.getString("Party.Command.Chat.ContentColor");
		}
	}

	/**
	 * Will be executed on /p command
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param arg0
	 *            The command sender
	 * @param args
	 *            The arguments
	 */
	@Override
	public void execute(CommandSender arg0, String[] args) {
		if (!(arg0 instanceof ProxiedPlayer)) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				arg0.sendMessage(new TextComponent(Main.main.partyPrefix + "You need to be a player!"));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					arg0.sendMessage(new TextComponent(Main.main.partyPrefix + "You need to be a player!"));
				} else {
					arg0.sendMessage(new TextComponent(Main.main.partyPrefix + "Du must ein Spieler sein!"));
				}
			}
			return;
		}
		ProxiedPlayer p = (ProxiedPlayer) arg0;
		if (args.length == 0) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(chatPrefix + "§5You need to give a message"));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(
							chatPrefix + Main.main.messagesYml.getString("Party.Command.Chat.ErrorNoMessage")));
				} else {
					p.sendMessage(new TextComponent(chatPrefix + "§5Du musst eine Nachricht eingeben"));
				}
				return;
			}
		} else {
			if (PartyManager.getParty(p) != null) {
				PlayerParty party = PartyManager.getParty(p);
				String text = "";
				for (String arg : args) {
					text += " " + farbe + arg;
				}
				for (ProxiedPlayer player : party.getPlayer()) {
					if (Main.main.language.equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(
								chatPrefix + Main.main.messagesYml.getString("Party.Command.Chat.PartyChatOutput")
										.replace("[SENDERNAME]", p.getName()).replace("[MESSAGE_CONTENT]", text)));
					} else {
						player.sendMessage(new TextComponent(chatPrefix + "§e" + p.getName() + "§5:" + text));
					}
				}
				if (Main.main.language.equalsIgnoreCase("own")) {
					party.getleader()
							.sendMessage(new TextComponent(
									chatPrefix + Main.main.messagesYml.getString("Party.Command.Chat.PartyChatOutput")
											.replace("[SENDERNAME]", p.getName()).replace("[MESSAGE_CONTENT]", text)));
				} else {
					party.getleader().sendMessage(new TextComponent(chatPrefix + "§e" + p.getName() + "§5:" + text));
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

}
