package party.command;

import java.sql.SQLException;

import mySql.mySql;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import party.Party;
import party.PartyManager;
import party.PlayerParty;

public class Invite extends SubCommand {
	mySql verbindung;

	public Invite(mySql verbindungl) {
		super("Lade §7einen §7Spieler §7in §7deine §7Party §7ein", "<Name>",
				new String[] { "invite" });
		verbindung = verbindungl;
	}

	public void onCommand(ProxiedPlayer p, String[] args) {
		if (args.length == 0) {
			p.sendMessage(new TextComponent(Party.prefix
					+ "§cDu §cmusst §ceinen §cSpieler §cangeben."));
			return;
		}
		if (PartyManager.getParty(p) == null) {
			PartyManager.createParty(p);
		}
		PlayerParty party = PartyManager.getParty(p);
		if (!party.isleader(p)) {
			p.sendMessage(new TextComponent(Party.prefix
					+ "§cDu §cbist §cnicht §cder §cParty §cLeader."));
			return;
		}
		try {
			if (verbindung.erlaubtPartyAnfragen(args[0]) == false) {
				p.sendMessage(new TextComponent(
						Party.prefix
								+ "§cDu §ckannst §cdiesen §cSpieler §cnicht §cin §cdeine §cParty §ceinladen."));
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ProxiedPlayer pl = BungeeCord.getInstance().getPlayer(args[0]);
		if (pl == null) {
			p.sendMessage(new TextComponent(Party.prefix
					+ "§cDieser §cSpieler §cist §cnicht §conline."));
			return;
		}
		if (pl.equals(p)) {
			p.sendMessage(new TextComponent(Party.prefix
					+ "§7Du §7darfst §7dich §7nicht §7selber §7einladen."));
			return;
		}
		if (PartyManager.getParty(pl) != null) {
			p.sendMessage(new TextComponent(
					Party.prefix
							+ "§cDieser §cDer §cSpieler §cist §cbereits §cin §ceiner §cParty."));
			return;
		}

		party.invite(pl);

		p.sendMessage(new TextComponent(Party.prefix + "§bDu §bhast §6"
				+ pl.getName() + " §bin §bdeine §bParty §beingeladen."));
	}
}
