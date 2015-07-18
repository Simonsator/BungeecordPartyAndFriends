package party.command;

import java.util.List;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import party.Party;
import party.PartyManager;
import party.PlayerParty;

public class Leave extends SubCommand {
	private String language;

	public Leave(String leaveAllias, String languageOverGive) {
		super("Verlasse deine Party", "", new String[] { "leave", leaveAllias });
		language = languageOverGive;
	}

	public void onCommand(ProxiedPlayer p, String[] args) {
		if (PartyManager.getParty(p) == null) {
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Party.prefix + "§cYou §care §cnot §cin §ca §cparty."));
			} else {
				p.sendMessage(new TextComponent(Party.prefix + "§cDu §cbist §cin §ckeiner §cParty."));
			}
			return;
		}
		PlayerParty party = PartyManager.getParty(p);
		if (party.isleader(p)) {
			List<ProxiedPlayer> liste = party.getPlayer();
			if (liste.size() > 1) {
				ProxiedPlayer newLeader = liste.get(0);
				for (ProxiedPlayer pn : party.getPlayer()) {
					if (language.equalsIgnoreCase("english")) {
						pn.sendMessage(new TextComponent(
								Party.prefix + "§bThe §bLeader §bhas §bleft §bthe §Party. §bThe §bnew §bLeader §bis §e"
										+ newLeader.getName() + "."));
					} else {
						pn.sendMessage(new TextComponent(Party.prefix
								+ "§bDer §bLeader §bhat §bdie §bParty §bverlassen. §bDer §bneue §bLeader §bist §e"
								+ newLeader.getName() + "."));
					}
				}
				party.setLeader(newLeader);
				liste.remove(newLeader);
				party.setPlayer(liste);
				if (language.equalsIgnoreCase("english")) {
					p.sendMessage(new TextComponent(Party.prefix + "§bYou §bleft §byour §bparty."));
				} else {
					p.sendMessage(new TextComponent(Party.prefix + "§bDu §bhast §bdeine §bParty §bverlassen."));
				}
				return;
			}
			for (ProxiedPlayer pp : party.getPlayer()) {
				if (language.equalsIgnoreCase("english")) {
					pp.sendMessage(new TextComponent(
							Party.prefix + "§5The §5party §5was §5dissolved §5because §5of §5to §5less §5players."));
				} else {
					pp.sendMessage(new TextComponent(
							Party.prefix + "§5Die §5Party §5wurde §5wegen §5zu §5wenig §5Mitgliedern §5aufgelöst."));
				}
			}
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Party.prefix + "§bYou §bleft §byour §bparty."));
			} else {
				p.sendMessage(new TextComponent(Party.prefix + "§bDu §bhast §bdeine §bParty §bverlassen."));
			}
			PartyManager.deleteParty(party);
			return;
		}
		if (party.removePlayer(p)) {
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Party.prefix + "§bYou §bleft §byour §bparty."));
			} else {
				p.sendMessage(new TextComponent(Party.prefix + "§bDu §bhast §bdeine §bParty §bverlassen."));
			}
			for (ProxiedPlayer pp : party.getPlayer()) {
				if (language.equalsIgnoreCase("english")) {
					pp.sendMessage(new TextComponent(
							Party.prefix + "§bThe §bplayer §6" + p.getName() + " §bhas §bleft §bthe §bparty."));
				} else {
					pp.sendMessage(new TextComponent(
							Party.prefix + "§bDer §bSpieler §6" + p.getName() + " §bhat §bdie §bParty §bverlassen."));
				}
			}
			if (language.equalsIgnoreCase("english")) {
				party.getleader().sendMessage(new TextComponent(
						Party.prefix + "§bThe §bplayer §6" + p.getName() + " §bhas §bleft §bthe §bparty."));
			} else {
				party.getleader().sendMessage(new TextComponent(
						Party.prefix + "§bDer §bSpieler §6" + p.getName() + " §bhat §bdie §bParty §bverlassen."));
			}
			return;
		}
		p.sendMessage(new TextComponent(Party.prefix + "§bDu §bkonntest §bdie §bParty §bnicht §bverlassen."));
	}
}
