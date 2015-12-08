/**
 * The /p command
 * 
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.party.command;

import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.PartyManager;
import de.simonsator.partyandfriends.party.PlayerParty;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

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
		super("p", Main.getInstance().getConfig().getString("Permissions.PartyPermission"), alias);
		if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
			chatPrefix = Main.getInstance().getMessagesYml().getString("Party.Command.Chat.Prefix");
			farbe = Main.getInstance().getMessagesYml().getString("Party.Command.Chat.ContentColor");
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
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				arg0.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "You need to be a player!"));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					arg0.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "You need to be a player!"));
				} else {
					arg0.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "Du must ein Spieler sein!"));
				}
			}
			return;
		}
		ProxiedPlayer p = (ProxiedPlayer) arg0;
		if (args.length == 0) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(chatPrefix + "§5You need to give a message"));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(
							chatPrefix + Main.getInstance().getMessagesYml().getString("Party.Command.Chat.ErrorNoMessage")));
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
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(
								chatPrefix + Main.getInstance().getMessagesYml().getString("Party.Command.Chat.PartyChatOutput")
										.replace("[SENDERNAME]", p.getName()).replace("[MESSAGE_CONTENT]", text)));
					} else {
						player.sendMessage(new TextComponent(chatPrefix + "§e" + p.getName() + "§5:" + text));
					}
				}
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					party.getleader()
							.sendMessage(new TextComponent(
									chatPrefix + Main.getInstance().getMessagesYml().getString("Party.Command.Chat.PartyChatOutput")
											.replace("[SENDERNAME]", p.getName()).replace("[MESSAGE_CONTENT]", text)));
				} else {
					party.getleader().sendMessage(new TextComponent(chatPrefix + "§e" + p.getName() + "§5:" + text));
				}
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					p.sendMessage(new TextComponent(chatPrefix + "§5You need to be in a party"));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						p.sendMessage(new TextComponent(
								chatPrefix + Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorNoParty")));
					} else {
						p.sendMessage(new TextComponent(chatPrefix + "§5Du musst in einer Party sein"));
					}
				}
			}
		}

	}

}
