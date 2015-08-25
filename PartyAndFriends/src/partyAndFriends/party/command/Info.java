/**
 * This class will be executed on /party list
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
		super(StringToArray.stringToArray(Main.main.config.getString("Aliases.ListAlias")));
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
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.main.partyPrefix + "§cYou §care §cnot §cin §ca §cparty."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(
							new TextComponent(Main.main.messagesYml.getString("Party.Command.General.ErrorNoParty")));
				} else {
					p.sendMessage(new TextComponent(Main.main.partyPrefix + "§cDu §cbist §cin §ckeiner §cParty."));
				}
			}
			return;
		}
		PlayerParty party = PartyManager.getParty(p);
		String leader = "";
		String players = "";
		if (Main.main.language.equalsIgnoreCase("own")) {
			leader = Main.main.messagesYml.getString("Party.Command.Info.Leader").replace("[LEADER]",
					party.getleader().getDisplayName());
			players = Main.main.messagesYml.getString("Party.Command.Info.Players");
		} else {
			leader = "§3Leader§7: §5" + party.getleader().getDisplayName();
			players = "§8Players§7: §b";
		}
		if (!party.getPlayer().isEmpty()) {
			for (ProxiedPlayer pp : party.getPlayer()) {
				if (Main.main.language.equalsIgnoreCase("own")) {
					players = players + pp.getDisplayName()
							+ Main.main.messagesYml.getString("Party.Command.Info.PlayersCut");
				} else {
					players = players + pp.getDisplayName() + "§7, §b";
				}
			}
			players = players.substring(0, players.lastIndexOf("§7, §b"));
		} else {
			if (Main.main.language.equalsIgnoreCase("english")) {
				players = players + "empty";
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					players = players + Main.main.messagesYml.getString("Party.Command.Info.Empty");
				} else {
					players = players + "Leer";
				}
			}
		}
		if (Main.main.language.equalsIgnoreCase("own")) {
			p.sendMessage(new TextComponent(Main.main.messagesYml.getString("Party.General.HelpBegin")));
		} else {
			p.sendMessage(new TextComponent("§8§m----------[§5§lPARTY§8]§m----------"));
		}
		p.sendMessage(new TextComponent(Main.main.partyPrefix + leader));
		p.sendMessage(new TextComponent(Main.main.partyPrefix + players));
		if (Main.main.language.equalsIgnoreCase("own")) {
			p.sendMessage(new TextComponent(Main.main.messagesYml.getString("Party.General.HelpEnd")));
		} else {
			p.sendMessage(new TextComponent("§8§m-----------------------------------"));
		}
	}
}
