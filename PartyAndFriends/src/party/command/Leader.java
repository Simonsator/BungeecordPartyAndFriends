package party.command;

import java.util.List;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import party.Party;
import party.PartyManager;
import party.PlayerParty;

public class Leader extends SubCommand {
	private String language;

	public Leader(String leaderAllias, String languageOverGive) {
		super("§7Macht §7einen §7anderen §7Spieler §7zum §7Leader", "<Name>",
				new String[] { "leader", "setleader", leaderAllias });
		language = languageOverGive;
	}

	@Override
	public void onCommand(ProxiedPlayer p, String[] agrs) {
		if (agrs.length == 0) {
			p.sendMessage(new TextComponent(Party.prefix
					+ "§7Du §7musst §7einen §7Spieler §7angeben, §7der §7der §7neue §7Leader §7sein §7soll."));
			return;
		}
		ProxiedPlayer player = BungeeCord.getInstance().getPlayer(agrs[0]);
		if (player == null) {
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Party.prefix + "§cThe §cplayer " + agrs[0] + " §cis §cnot §cin §cthe §cParty."));
			} else {
				p.sendMessage(new TextComponent(
						Party.prefix + "§cDer §cSpieler " + agrs[0] + " §cist §cnicht §cin §cder §cParty."));
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
		if (player.equals(p)) {
			p.sendMessage(new TextComponent(
					Party.prefix + "§7Du §7kannst §7dich §7nicht §7selber §7zum §7neuen §7Party §7Leiter §7machen"));
			return;
		}
		if (!party.getPlayer().contains(player)) {
			if (language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Party.prefix + "§cThe §cplayer " + agrs[0] + " §cis §cnot §cin §cthe §cParty."));
			} else {
				p.sendMessage(new TextComponent(
						Party.prefix + "§cDer §cSpieler " + agrs[0] + " §cist §cnicht §cin §cder §cParty."));
			}
			return;
		}
		List<ProxiedPlayer> liste = party.getPlayer();
		liste.add(p);
		liste.remove(player);
		party.setPlayer(liste);
		party.setLeader(player);
		for (ProxiedPlayer playern : BungeeCord.getInstance().getPlayers()) {
			if (party.isinParty(playern)) {
				if (language.equalsIgnoreCase("english")) {
					playern.sendMessage(new TextComponent(
							Party.prefix + "§7The §7new §7party §7leader §7is §6" + player.getName()));
				} else {
					playern.sendMessage(new TextComponent(
							Party.prefix + "§7Der §7neue §7Party §7Leiter §7ist §6" + player.getName()));
				}
			}
		}
	}
}
