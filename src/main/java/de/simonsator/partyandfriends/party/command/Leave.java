/**
 * The class which will be executed on /party leave
 * 
 * @author Simonsator
 * @version 1.0.0
 *
 */
package de.simonsator.partyandfriends.party.command;

import java.util.ArrayList;

import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.PartyManager;
import de.simonsator.partyandfriends.party.PlayerParty;
import de.simonsator.partyandfriends.utilities.StringToArray;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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
		super(StringToArray.stringToArray(Main.getInstance().getConfig().getString("Aliases.leaveAlias")));
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
		PlayerParty party = PartyManager.getParty(p);
		if (party == null) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cYou §care §cnot §cin §ca §cparty."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorNoParty")));
				} else {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cDu §cbist §cin §ckeiner §cParty."));
				}
			}
			return;
		}
		if (party.isleader(p)) {
			ArrayList<ProxiedPlayer> liste = party.getPlayer();
			if (liste.size() > 1) {
				ProxiedPlayer newLeader = liste.get(0);
				for (ProxiedPlayer pn : party.getPlayer()) {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						pn.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
								+ "§bThe §bLeader §bhas §bleft §bthe §bParty. §bThe §bnew §bLeader §bis §e"
								+ newLeader.getDisplayName() + "."));
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							pn.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
									+ Main.getInstance().getMessagesYml().getString("Party.Command.Leave.NewLeaderIs")
											.replace("[NEWLEADER]", newLeader.getDisplayName())));
						} else {
							pn.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
									+ "§bDer §bLeader §bhat §bdie §bParty §bverlassen. §bDer §bneue §bLeader §bist §e"
									+ newLeader.getDisplayName() + "."));
						}
					}
				}
				party.setLeader(newLeader);
				liste.remove(newLeader);
				party.setPlayer(liste);
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§bYou §bleft §byour §bparty."));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
								+ Main.getInstance().getMessagesYml().getString("Party.Command.Leave.YouLeftTheParty")));
					} else {
						p.sendMessage(
								new TextComponent(Main.getInstance().getPartyPrefix() + "§bDu §bhast §bdeine §bParty §bverlassen."));
					}
				}
				return;
			}
			for (ProxiedPlayer pp : party.getPlayer()) {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					pp.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ "§5The §5party §5was §5dissolved §5because §5of §5to §5less §5players."));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						pp.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance().getMessagesYml()
								.getString("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers")));
					} else {
						pp.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
								+ "§5Die §5Party §5wurde §5wegen §5zu §5wenig §5Mitgliedern §5aufgelöst."));
					}
				}
			}
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§bYou §bleft §byour §bparty."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.Leave.YouLeftTheParty")));
				} else {
					p.sendMessage(
							new TextComponent(Main.getInstance().getPartyPrefix() + "§bDu §bhast §bdeine §bParty §bverlassen."));
				}
			}
			PartyManager.deleteParty(party);
			return;
		}
		if (party.removePlayer(p)) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§bYou §bleft §byour §bparty."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.Leave.YouLeftTheParty")));
				} else {
					p.sendMessage(
							new TextComponent(Main.getInstance().getPartyPrefix() + "§bDu §bhast §bdeine §bParty §bverlassen."));
				}
			}
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				party.getleader().sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§bThe §bplayer §6"
						+ p.getDisplayName() + " §bhas §bleft §bthe §bparty."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					party.getleader()
							.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
									+ Main.getInstance().getMessagesYml().getString("Party.Command.General.PlayerHasLeftTheParty")
											.replace("[PLAYER]", p.getDisplayName())));
				} else {
					party.getleader().sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§bDer §bSpieler §6"
							+ p.getDisplayName() + " §bhat §bdie §bParty §bverlassen."));
				}
			}
			ArrayList<ProxiedPlayer> liste = party.getPlayer();
			if (liste.isEmpty() && party.inviteListSize() == 0) {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					party.getleader().sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ "§5The §5party §5was §5dissolved §5because §5of §5to §5less §5players."));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						party.getleader().sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance().getMessagesYml()
								.getString("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers")));
					} else {
						party.getleader().sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
								+ "§5Die §5Party §5wurde §5wegen §5zu §5wenig §5Mitgliedern §5aufgelöst."));
					}
				}
				PartyManager.deleteParty(party);
				return;
			}
			return;
		}
		p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§bDu §bkonntest §bdie §bParty §bnicht §bverlassen."));
	}
}
