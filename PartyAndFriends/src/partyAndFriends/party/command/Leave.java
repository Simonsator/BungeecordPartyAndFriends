/**
 * The class which will be executed on /party leave
 * 
 * @author Simonsator
 * @version 1.0.0
 *
 */
package partyAndFriends.party.command;

import java.util.ArrayList;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import partyAndFriends.main.Main;
import partyAndFriends.party.PartyManager;
import partyAndFriends.party.PlayerParty;
import partyAndFriends.utilities.StringToArray;

/**
 * The class which will be executed on /party leave
 * 
 * @author Simonsator
 * @version 1.0.0
 *
 */
public class Leave extends SubCommand {
	/**
	 * Initials the object
	 */
	public Leave() {
		super(StringToArray.stringToArray(Main.main.config.getString("Aliases.leaveAlias")));
	}

	/**
	 * Will be executed on /party leave
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
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.General.ErrorNoParty")));
				} else {
					p.sendMessage(new TextComponent(Main.main.partyPrefix + "§cDu §cbist §cin §ckeiner §cParty."));
				}
			}
			return;
		}
		PlayerParty party = PartyManager.getParty(p);
		if (party.isleader(p)) {
			ArrayList<ProxiedPlayer> liste = party.getPlayer();
			if (liste.size() > 1) {
				ProxiedPlayer newLeader = liste.get(0);
				for (ProxiedPlayer pn : party.getPlayer()) {
					if (Main.main.language.equalsIgnoreCase("english")) {
						pn.sendMessage(new TextComponent(Main.main.partyPrefix
								+ "§bThe §bLeader §bhas §bleft §bthe §bParty. §bThe §bnew §bLeader §bis §e"
								+ newLeader.getDisplayName() + "."));
					} else {
						if (Main.main.language.equalsIgnoreCase("own")) {
							pn.sendMessage(new TextComponent(Main.main.partyPrefix
									+ Main.main.messagesYml.getString("Party.Command.Leave.NewLeaderIs")
											.replace("[NEWLEADER]", newLeader.getDisplayName())));
						} else {
							pn.sendMessage(new TextComponent(Main.main.partyPrefix
									+ "§bDer §bLeader §bhat §bdie §bParty §bverlassen. §bDer §bneue §bLeader §bist §e"
									+ newLeader.getDisplayName() + "."));
						}
					}
				}
				party.setLeader(newLeader);
				liste.remove(newLeader);
				party.setPlayer(liste);
				if (Main.main.language.equalsIgnoreCase("english")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix + "§bYou §bleft §byour §bparty."));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						p.sendMessage(new TextComponent(Main.main.partyPrefix
								+ Main.main.messagesYml.getString("Party.Command.Leave.YouLeftTheParty")));
					} else {
						p.sendMessage(
								new TextComponent(Main.main.partyPrefix + "§bDu §bhast §bdeine §bParty §bverlassen."));
					}
				}
				return;
			}
			for (ProxiedPlayer pp : party.getPlayer()) {
				if (Main.main.language.equalsIgnoreCase("english")) {
					pp.sendMessage(new TextComponent(Main.main.partyPrefix
							+ "§5The §5party §5was §5dissolved §5because §5of §5to §5less §5players."));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						pp.sendMessage(new TextComponent(Main.main.partyPrefix + Main.main.messagesYml
								.getString("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers")));
					} else {
						pp.sendMessage(new TextComponent(Main.main.partyPrefix
								+ "§5Die §5Party §5wurde §5wegen §5zu §5wenig §5Mitgliedern §5aufgelöst."));
					}
				}
			}
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.main.partyPrefix + "§bYou §bleft §byour §bparty."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.Leave.YouLeftTheParty")));
				} else {
					p.sendMessage(
							new TextComponent(Main.main.partyPrefix + "§bDu §bhast §bdeine §bParty §bverlassen."));
				}
			}
			PartyManager.deleteParty(party);
			return;
		}
		if (party.removePlayer(p)) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.main.partyPrefix + "§bYou §bleft §byour §bparty."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.Leave.YouLeftTheParty")));
				} else {
					p.sendMessage(
							new TextComponent(Main.main.partyPrefix + "§bDu §bhast §bdeine §bParty §bverlassen."));
				}
			}
			for (ProxiedPlayer pp : party.getPlayer()) {
				if (Main.main.language.equalsIgnoreCase("english")) {
					pp.sendMessage(new TextComponent(Main.main.partyPrefix + "§bThe §bplayer §6" + p.getDisplayName()
							+ " §bhas §bleft §bthe §bparty."));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						pp.sendMessage(new TextComponent(Main.main.partyPrefix
								+ Main.main.messagesYml.getString("Party.Command.General.PlayerHasLeftTheParty")
										.replace("[PLAYER]", p.getDisplayName())));
					} else {
						pp.sendMessage(new TextComponent(Main.main.partyPrefix + "§bDer §bSpieler §6"
								+ p.getDisplayName() + " §bhat §bdie §bParty §bverlassen."));
					}
				}
			}
			if (Main.main.language.equalsIgnoreCase("english")) {
				party.getleader().sendMessage(new TextComponent(Main.main.partyPrefix + "§bThe §bplayer §6"
						+ p.getDisplayName() + " §bhas §bleft §bthe §bparty."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					party.getleader()
							.sendMessage(new TextComponent(Main.main.partyPrefix
									+ Main.main.messagesYml.getString("Party.Command.General.PlayerHasLeftTheParty")
											.replace("[PLAYER]", p.getDisplayName())));
				} else {
					party.getleader().sendMessage(new TextComponent(Main.main.partyPrefix + "§bDer §bSpieler §6"
							+ p.getDisplayName() + " §bhat §bdie §bParty §bverlassen."));
				}
			}
			return;
		}
		p.sendMessage(new TextComponent(Main.main.partyPrefix + "§bDu §bkonntest §bdie §bParty §bnicht §bverlassen."));
	}
}
