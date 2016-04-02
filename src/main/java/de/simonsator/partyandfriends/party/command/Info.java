/**
 * This class will be executed on /party list
 * 
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.party.command;

import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.PartyManager;
import de.simonsator.partyandfriends.party.PlayerParty;
import de.simonsator.partyandfriends.utilities.StringToArray;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * This class will be executed on /party list
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Info extends SubCommand {
	/**
	 * Initials the object
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public Info() {
		super(StringToArray.stringToArray(Main.getInstance().getConfig().getString("Aliases.ListAlias")));
	}

	/**
	 * This method will be executed on /party list
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param p
	 *            The player
	 * @param args
	 *            The arguments
	 */
	public void onCommand(ProxiedPlayer p, String[] args) {
		if (PartyManager.getParty(p) == null) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cYou §care §cnot §cin §ca §cparty."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(
							new TextComponent(Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorNoParty")));
				} else {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cDu §cbist §cin §ckeiner §cParty."));
				}
			}
			return;
		}
		PlayerParty party = PartyManager.getParty(p);
		String leader = "";
		String players = "";
		if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
			leader = Main.getInstance().getMessagesYml().getString("Party.Command.Info.Leader").replace("[LEADER]",
					party.getleader().getDisplayName());
			players = Main.getInstance().getMessagesYml().getString("Party.Command.Info.Players");
		} else {
			leader = "§3Leader§7: §5" + party.getleader().getDisplayName();
			players = "§8Players§7: §b";
		}
		if (!party.getPlayer().isEmpty()) {
			for (ProxiedPlayer pp : party.getPlayer()) {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					players = players + pp.getDisplayName()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.Info.PlayersCut");
				} else {
					players = players + pp.getDisplayName() + "§7, §b";
				}
			}
			players = players.substring(0, players.lastIndexOf("§7, §b"));
		} else {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				players = players + "empty";
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					players = players + Main.getInstance().getMessagesYml().getString("Party.Command.Info.Empty");
				} else {
					players = players + "Leer";
				}
			}
		}
		if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
			p.sendMessage(new TextComponent(Main.getInstance().getMessagesYml().getString("Party.General.HelpBegin")));
		} else {
			p.sendMessage(new TextComponent("§8§m----------[§5§lPARTY§8]§m----------"));
		}
		p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + leader));
		p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + players));
		if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
			p.sendMessage(new TextComponent(Main.getInstance().getMessagesYml().getString("Party.General.HelpEnd")));
		} else {
			p.sendMessage(new TextComponent("§8§m-----------------------------------"));
		}
	}
}
