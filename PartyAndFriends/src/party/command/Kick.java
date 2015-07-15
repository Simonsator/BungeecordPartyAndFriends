package party.command;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import party.Party;
import party.PartyManager;
import party.PlayerParty;

public class Kick extends SubCommand {
	public Kick() {
		super("Kicke einen Spieler aus deiner Party", "<Name>",
				new String[] { "kick" });
	}

	public void onCommand(ProxiedPlayer p, String[] args) {
		if (args.length == 0) {
			p.sendMessage(new TextComponent(Party.prefix
					+ "§cBitte §cgib §ceinen §cSpieler §can."));
			return;
		}
		if (PartyManager.getParty(p) == null) {
			p.sendMessage(new TextComponent(Party.prefix
					+ "§cDu §cbist §cin §ckeiner §cParty."));
			return;
		}
		PlayerParty party = PartyManager.getParty(p);
		if (!party.isleader(p)) {
			p.sendMessage(new TextComponent(Party.prefix
					+ "§cDu §cbist §cnicht §cder §cParty §cLeader."));
			return;
		}
		ProxiedPlayer pl = BungeeCord.getInstance().getPlayer(args[0]);
		if (pl == null) {
			p.sendMessage(new TextComponent(Party.prefix
					+ "§cDieser §cSpieler §cist §cnicht §conline."));
			return;
		}
		if (party.removePlayer(pl)) {
			p.sendMessage(new TextComponent(Party.prefix
					+ "§bDu §bhast §bden §bSpieler §6" + pl.getName()
					+ " §baus §bdeiner §bParty §bgekickt."));
			for (ProxiedPlayer pp : party.getPlayer()) {
				pp.sendMessage(new TextComponent(Party.prefix
						+ "§bDer §bSpieler §6" + pl.getName()
						+ " §bwurde §baus §bder §bParty §bgekickt."));
			}
			pl.sendMessage(new TextComponent(Party.prefix
					+ "§bDu §bwurdest §baus §bder §bParty §bgekickt."));
			return;
		}
		p.sendMessage(new TextComponent(
				Party.prefix
						+ "§cDu §ckonntest §cden §cSpieler §cnicht §caus §cdeiner §cParty §ckicken."));
	}
}
