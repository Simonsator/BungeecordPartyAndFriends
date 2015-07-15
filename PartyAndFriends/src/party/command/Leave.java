package party.command;

import java.util.List;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import party.Party;
import party.PartyManager;
import party.PlayerParty;

public class Leave extends SubCommand {
	public Leave() {
		super("Verlasse deine Party", "", new String[] { "leave" });
	}

	public void onCommand(ProxiedPlayer p, String[] args) {
		if (PartyManager.getParty(p) == null) {
			p.sendMessage(new TextComponent(Party.prefix + "§cDu §cbist §cin §ckeiner §cParty."));
			return;
		}
		PlayerParty party = PartyManager.getParty(p);
		if (party.isleader(p)) {
			List<ProxiedPlayer> liste = party.getPlayer();
			if (liste.size() > 1) {
				ProxiedPlayer newLeader = liste.get(0);
				for (ProxiedPlayer pn : party.getPlayer()) {
					pn.sendMessage(new TextComponent(Party.prefix
							+ "§bDer §bLeader §bhat §bdie §bParty §bverlassen. §bDer §bneue §bLeader §bist §e"
							+ newLeader.getName() + "."));
				}
				party.setLeader(newLeader);
				liste.remove(newLeader);
				party.setPlayer(liste);
				p.sendMessage(new TextComponent(Party.prefix + "§bDu §bhast §bdeine §bParty §bverlassen."));
				return;
			}
			for (ProxiedPlayer pp : party.getPlayer()) {
				pp.sendMessage(new TextComponent(Party.prefix + "§bDer §bLeader §bhat §bdie §bParty §baufgelöst."));
			}
			p.sendMessage(new TextComponent(Party.prefix + "§bDu §bhast §bdeine §bParty §bverlassen."));
			PartyManager.deleteParty(party);
			return;
		}
		if (party.removePlayer(p)) {
			p.sendMessage(new TextComponent(Party.prefix + "§bDu §bhast §bdie §bParty §bverlassen."));
			for (ProxiedPlayer pp : party.getPlayer()) {
				pp.sendMessage(new TextComponent(
						Party.prefix + "§bDer §bSpieler §6" + p.getName() + " §bhat §bdie §bParty §bverlassen."));
			}
			party.getleader().sendMessage(new TextComponent(
					Party.prefix + "§bDer §bSpieler §6" + p.getName() + " §bhat §bdie §bParty §bverlassen."));
			return;
		}
		p.sendMessage(new TextComponent(Party.prefix + "§bDu §bkonntest §bdie §bParty §bnicht §bverlassen."));
	}
}
