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
	private mySql verbindung;
	private String language;
	private int maxPlayersInParty;

	public Invite(mySql verbindungl, String inviteAllias, String languageOverGive, int maxPlayersInPartyOverGive) {
		super("Lade §7einen §7Spieler §7in §7deine §7Party §7ein", "<Name>", new String[] { "invite", inviteAllias });
		verbindung = verbindungl;
		language = languageOverGive;
		maxPlayersInParty = maxPlayersInPartyOverGive;
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
		ProxiedPlayer pl = BungeeCord.getInstance().getPlayer(args[0]);
		if (pl == null)

		{
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Party.prefix + "§cThis §cplayer §cis §cnot §conline."));
			} else {
				p.sendMessage(new TextComponent(Party.prefix + "§cDieser §cSpieler §cist §cnicht §conline."));
			}
			return;
		}
		if (pl.equals(p))

		{
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(
						new TextComponent(Party.prefix + "§7You §7are §7not §7allowed §7to §7invite §7yourself."));
			} else {
				p.sendMessage(new TextComponent(Party.prefix + "§7Du §7darfst §7dich §7nicht §7selber §7einladen."));
			}
			return;
		}
		if (PartyManager.getParty(p) == null) {
			PartyManager.createParty(p);
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
		try {
			if (verbindung.erlaubtPartyAnfragen(args[0]) == false) {
				if (language.equalsIgnoreCase("english")) {
					p.sendMessage(new TextComponent(
							Party.prefix + "§cYou §ccan´t §cinvite §cthis §cplayer §cinto §cyour §cParty."));
				} else {
					p.sendMessage(new TextComponent(Party.prefix
							+ "§cDu §ckannst §cdiesen §cSpieler §cnicht §cin §cdeine §cParty §ceinladen."));
				}
				return;
			}
		} catch (

		SQLException e)

		{
			e.printStackTrace();
		}

		if (PartyManager.getParty(pl) != null)

		{
			if (language.equalsIgnoreCase("english")) {
				new TextComponent(Party.prefix + "§cThis §cplayer §cis §calready §cin §cyour §cparty.");
			} else {
				p.sendMessage(new TextComponent(
						Party.prefix + "§cDieser §cDer §cSpieler §cist §cbereits §cin §ceiner §cParty."));
			}
			return;
		}
		if (party.containsPlayer(pl)) {
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Party.prefix + "§cThe §cplayer §e" + pl.getDisplayName()
						+ "§cis §calready §cinvited §cinto §cyour §cparty."));
			} else {
				p.sendMessage(new TextComponent(Party.prefix + "§cDer §cSpieler §e" + pl.getDisplayName()
						+ " §cist §cschon §cin §cdie §cParty §ceingeladen."));
			}
			return;
		}
		if (maxPlayersInParty > 1) {
			if (maxPlayersInParty < party.getPlayer().size() + party.inviteListSize() + 2) {
				if (language.equalsIgnoreCase("english")) {
					p.sendMessage(new TextComponent(
							Party.prefix + "§cThe §cMax §csize §cof §ca §cparty §cis §c" + maxPlayersInParty + "§c."));
				} else {
					p.sendMessage(new TextComponent(Party.prefix
							+ "§cDie §cMaximale §cgröße §cfür §ceine §cParty §cist §c" + maxPlayersInParty));
				}
				return;
			}
		}

		party.invite(pl, language);
		if (language.equalsIgnoreCase("english")) {
			p.sendMessage(
					new TextComponent(Party.prefix + "§6" + pl.getName() + " §bwas §binvited §bto §byour §bparty."));
		} else {
			p.sendMessage(new TextComponent(
					Party.prefix + "§bDu §bhast §6" + pl.getName() + " §bin §bdeine §bParty §beingeladen."));
		}
	}
}
