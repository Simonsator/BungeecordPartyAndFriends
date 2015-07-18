package party.command;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import party.Party;
import party.PartyManager;
import party.PlayerParty;

public class Join extends SubCommand {
	private String language;

	public Join(String joinAllias, String languageOverGive) {
		super("Trete einer Party bei", "<Name>", new String[] { "join", joinAllias });
		language = languageOverGive;
	}

	public void onCommand(ProxiedPlayer p, String[] args) {
		partyBeitreten(p, args);
	}

	public void partyBeitreten(ProxiedPlayer p, String[] args) {
		if (args.length == 0) {
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Party.prefix + "§cYou §cneed §cto §cgive §ca §cplayer."));
			} else {
				p.sendMessage(new TextComponent(Party.prefix + "§cDu §cmusst §ceinen §cSpieler §cangeben."));
			}
			return;
		}
		if (PartyManager.getParty(p) != null) {
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Party.prefix
						+ "§cYou §care §calready §cin §ca §cparty. §cUse §6/party leave §cto §cleave §cthis §cparty."));
			} else {
				p.sendMessage(new TextComponent(Party.prefix
						+ "§cDu §cbist §cbereits §cin §ceiner §cParty. §cNutze §6/party leave §cum §cdiese §cParty §czu §cverlassen."));
			}
			return;
		}
		ProxiedPlayer pl = BungeeCord.getInstance().getPlayer(args[0]);
		if (pl == null) {
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Party.prefix + "§cThis §cplayer §cis §cnot §conline."));
			} else {
				p.sendMessage(new TextComponent(Party.prefix + "§cDieser §cSpieler §cist §cnicht §conline."));
			}
			return;
		}
		if (PartyManager.getParty(pl) == null) {
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Party.prefix + "§ccThis §cplayer §chas §cno §cparty."));
			} else {
				p.sendMessage(new TextComponent(Party.prefix + "§cDieser §cSpieler §chat §ckeine §cParty."));
			}
			return;
		}
		PlayerParty party = PartyManager.getParty(pl);
		if (party.addPlayer(p)) {
			for (ProxiedPlayer pp : party.getPlayer()) {
				if (language.equalsIgnoreCase("english")) {
					pp.sendMessage(new TextComponent(
							Party.prefix + "§bThe §bplayer §6" + p.getName() + " §bjoined §bthe §bparty."));
				} else {
					pp.sendMessage(new TextComponent(
							Party.prefix + "§bDer §bSpieler §6" + p.getName() + " §bist §bder §bParty §bbeigetreten."));
				}
			}
			if (language.equalsIgnoreCase("english")) {
				party.getleader().sendMessage(new TextComponent(
						Party.prefix + "§bThe §bplayer §6" + p.getName() + " §bjoined §bthe §bparty."));
			} else {
				party.getleader().sendMessage(new TextComponent(
						Party.prefix + "§bDer §bSpieler §6" + p.getName() + " §bist §bder §bParty §bbeigetreten."));
			}
		} else {
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Party.prefix + "§cYou §can´t §cjoin §cthis §cparty."));
			} else {
				p.sendMessage(new TextComponent(Party.prefix + "§cDu §ckannst §cder §cParty §cnicht §cbeitreten."));
			}
			return;
		}
	}
}
