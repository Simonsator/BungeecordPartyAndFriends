package party.command;

import java.util.List;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import party.Party;
import party.PartyManager;
import party.PlayerParty;

public class Leader extends SubCommand {

	public Leader() {
		super("§7Macht §7einen §7anderen §7Spieler §7zum §7Leader", "<Name>",
				new String[] { "leader", "setleader" });
	}

	@Override
	public void onCommand(ProxiedPlayer p, String[] agrs) {
		if (agrs.length == 0) {
			p.sendMessage(new TextComponent(
					Party.prefix
							+ "§7Du §7musst §7einen §7Spieler §7angeben, §7der §7der §7neue §7Leader §7sein §7soll."));
			return;
		}
		ProxiedPlayer player = BungeeCord.getInstance().getPlayer(agrs[0]);
		if(player==null)
		{
			p.sendMessage(new TextComponent(Party.prefix + "§cDer §cSpieler "
					+ agrs[0] + " §cist §cnicht §cin §cder §cParty."));
			return;
		}
		if (PartyManager.getParty(p) == null) {
			p.sendMessage(new TextComponent(Party.prefix
					+ "§7Du §7bist §7in §7keiner §7Party"));
			return;
		}
		PlayerParty party = PartyManager.getParty(p);
		if (!party.isleader(p)) {
			p.sendMessage(new TextComponent(Party.prefix
					+ "§cDu §cbist §cnicht §cder §cParty §cLeader."));
			return;
		}
		if (player.equals(p)) {
			p.sendMessage(new TextComponent(
					Party.prefix
							+ "§7Du §7kannst §7dich §7nicht §7selber §7zum §7neuen §7Party §7Leiter §7machen"));
			return;
		}
		if (!party.getPlayer().contains(player)) {
			p.sendMessage(new TextComponent(Party.prefix + "§cDer §cSpieler "
					+ agrs[0] + " §cist §cnicht §cin §cder §cParty."));
			return;
		}
		List<ProxiedPlayer> liste = party.getPlayer();
		liste.add(p);
		liste.remove(player);
		party.setPlayer(liste);
		party.setLeader(player);
		for (ProxiedPlayer playern : BungeeCord.getInstance().getPlayers()) {
			if (party.isinParty(playern)) {
				playern.sendMessage(new TextComponent(Party.prefix
						+ "§7Der §7neue §7Party §7Leiter §7ist §6" + player.getName()));
			}
		}
	}
}
