package party.listener;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

import mySql.mySql;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import party.Party;
import party.PartyManager;
import party.PlayerParty;

public class PlayerDisconnectListener implements Listener {
	private mySql verbindung;
	private String language;

	public PlayerDisconnectListener(mySql pVerbindung, String languageOverGive) {
		verbindung = pVerbindung;
		language = languageOverGive;
	}

	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent e) throws SQLException {
		ProxiedPlayer player = e.getPlayer();
		if (PartyManager.getParty(player) != null) {
			PlayerParty party = PartyManager.getParty(player);
			if (party.isleader(player)) {
				List<ProxiedPlayer> liste = party.getPlayer();
				if (liste.size() > 1) {
					ProxiedPlayer newLeader = liste.get(0);
					for (ProxiedPlayer p : party.getPlayer()) {
						if (language.equalsIgnoreCase("english")) {
							p.sendMessage(new TextComponent(Party.prefix
									+ "§bThe §bLeader §bhas §bleft §bthe §Party. §bThe §bnew §bLeader §bis §e"
									+ newLeader.getName() + "."));
						} else {
							p.sendMessage(new TextComponent(Party.prefix
									+ "§bDer §bLeader §bhat §bdie §bParty §bverlassen. §bDer §bneue §bLeader §bist §e"
									+ newLeader.getName() + "."));
						}
					}
					party.setLeader(newLeader);
					liste.remove(newLeader);
					party.setPlayer(liste);
				} else {
					for (ProxiedPlayer p : party.getPlayer()) {
						if (language.equalsIgnoreCase("english")) {
							p.sendMessage(new TextComponent(Party.prefix
									+ "§5The §5party §5was §5dissolved §5because §5of §5to §5less §5players."));
						} else {
							p.sendMessage(new TextComponent(Party.prefix
									+ "§5Die §5Party §5wurde §5wegen §5zu §5wenig §5Mitgliedern §5aufgelöst."));
						}
					}
					PartyManager.deleteParty(party);
				}
			} else {
				party.removePlayer(player);
				for (ProxiedPlayer p : party.getPlayer()) {
					if (language.equalsIgnoreCase("english")) {
						p.sendMessage(new TextComponent(Party.prefix + "§bThe §bLeader §bhas §bleft §bthe §Party."));
					} else {
						p.sendMessage(
								new TextComponent(Party.prefix + "§bDer §bLeader §bhat §bdie §bParty §bverlassen"));
					}
				}
				if (language.equalsIgnoreCase("english")) {
					party.getleader()
							.sendMessage(new TextComponent(Party.prefix + "§bThe §bLeader §bhas §bleft §bthe §Party."));
				} else {
					party.getleader().sendMessage(
							new TextComponent(Party.prefix + "§bDer §bLeader §bhat §bdie §bParty §bverlassen"));
				}
			}
		}
		ProxiedPlayer spieler = e.getPlayer();
		String nameDesSpielers = spieler.getName();
		String uuid = spieler.getUniqueId() + "";
		String freundeAusgeben = verbindung.freundeAusgeben(uuid);
		if (freundeAusgeben.equals("")) {
			return;
		}
		int[] freundeArrayID = new int[0];
		StringTokenizer st = new StringTokenizer(freundeAusgeben, "|");
		while (st.hasMoreTokens()) {
			Object newArray = Array.newInstance(freundeArrayID.getClass().getComponentType(),
					Array.getLength(freundeArrayID) + 1);
			System.arraycopy(freundeArrayID, 0, newArray, 0, Array.getLength(freundeArrayID));
			freundeArrayID = (int[]) newArray;
			freundeArrayID[Array.getLength(freundeArrayID) - 1] = Integer.parseInt(st.nextToken());
		}
		int i = 0;
		String befreundeterSpieler;
		while (freundeArrayID.length > i) {
			befreundeterSpieler = verbindung.getNameDesSpielers(freundeArrayID[i]);
			ProxiedPlayer freundGeladen = BungeeCord.getInstance().getPlayer(befreundeterSpieler);
			if (freundGeladen != null) {
				if (language.equalsIgnoreCase("english")) {
					freundGeladen.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
							+ " §7Your friend §e" + nameDesSpielers + " §7is §7now §coffline."));
				} else {
					freundGeladen.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
							+ " §7Der Freund §e" + nameDesSpielers + " §7ist §7nun §cOffline."));
				}
			}
			i++;
		}
	}
}