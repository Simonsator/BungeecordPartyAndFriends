package party.command;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import party.Party;
import party.PartyManager;
import party.PlayerParty;

public class Kick extends SubCommand {
	private String language;

	public Kick(String kickAllias, String languageOverGive) {
		super("Kicke einen Spieler aus deiner Party", "<Name>", new String[] { "kick", kickAllias });
		language = languageOverGive;
	}

	public void onCommand(ProxiedPlayer p, String[] args) {
		if (args.length == 0) {
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Party.prefix + "§cYou §cneed §cto §cgive §ca §cplayer."));
			} else {
				p.sendMessage(new TextComponent(Party.prefix + "§cDu §cmusst §ceinen §cSpieler §cangeben."));
			}
			return;
		}
		if (PartyManager.getParty(p) == null) {
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Party.prefix + "§cYou §care §cnot §cin §ca §cparty."));
			} else {
				p.sendMessage(new TextComponent(Party.prefix + "§cDu §cbist §cin §ckeiner §cParty."));
			}
			return;
		}
		PlayerParty party = PartyManager.getParty(p);
		if (!party.isleader(p)) {
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Party.prefix + "§cYou §cbare §cnot §cthe §cparty §cleader."));
			} else {
				p.sendMessage(new TextComponent(Party.prefix + "§cDu §cbist §cnicht §cder §cParty §cLeader."));
			}
			return;
		}
		ProxiedPlayer pl = BungeeCord.getInstance().getPlayer(args[0]);
		if (pl == null) {
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Party.prefix + "§cThe §cplayer " + args[0] + " §cis §cnot §cin §cthe §cParty."));
			} else {
				p.sendMessage(new TextComponent(
						Party.prefix + "§cDer §cSpieler " + args[0] + " §cist §cnicht §cin §cder §cParty."));
			}

			/*
			 * if (language.equalsIgnoreCase("english")) { p.sendMessage(new
			 * TextComponent(Party.prefix +
			 * "§cThis §cplayer §cis §cnot §conline.")); } else {
			 * p.sendMessage(new TextComponent(Party.prefix +
			 * "§cDieser §cSpieler §cist §cnicht §conline.")); }
			 */
			return;
		}
		if (!party.getPlayer().contains(pl)) {
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Party.prefix + "§cThe §cplayer " + args[0] + " §cis §cnot §cin §cthe §cParty."));
			} else {
				p.sendMessage(new TextComponent(
						Party.prefix + "§cDer §cSpieler " + args[0] + " §cist §cnicht §cin §cder §cParty."));
			}
			return;
		}
		if (party.removePlayer(pl))

		{
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Party.prefix + "§bYou §bhave §bkicked §bthe §bplayer §6" + pl.getName()
						+ " §bout §bof §bthe §bparty."));
			} else {
				p.sendMessage(new TextComponent(Party.prefix + "§bDu §bhast §bden §bSpieler §6" + pl.getName()
						+ " §baus §bdeiner §bParty §bgekickt."));
			}
			for (ProxiedPlayer pp : party.getPlayer()) {
				if (language.equalsIgnoreCase("english")) {
					pp.sendMessage(new TextComponent(Party.prefix + "§bThe §bplayer §6" + pl.getName()
							+ " §bwas §bkicked §bout §bof §bparty §bparty."));
				} else {
					pp.sendMessage(new TextComponent(Party.prefix + "§bDer §bSpieler §6" + pl.getName()
							+ " §bwurde §baus §bder §bParty §bgekickt."));
				}
			}
			if (language.equalsIgnoreCase("english")) {

			} else {
				pl.sendMessage(new TextComponent(Party.prefix + "§bYou §bhave §bbeen §bkicked §bout §bof §bparty."));
			}
			return;
		}

		if (language.equalsIgnoreCase("english"))

		{
			p.sendMessage(new TextComponent(
					Party.prefix + "§cYou §ccouldn´t §ckick §cthe §cplayer §cout §cof §cthe §cparty."));
		} else

		{
			p.sendMessage(new TextComponent(
					Party.prefix + "§cDu §ckonntest §cden §cSpieler §cnicht §caus §cdeiner §cParty §ckicken."));
		}
	}
}
