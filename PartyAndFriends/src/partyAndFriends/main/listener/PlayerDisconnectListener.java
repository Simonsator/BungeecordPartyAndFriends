/**
 * The class with the PlayerDisconnectEvent event.
 * @author Simonsator
 * @version 1.0.0
 */
package partyAndFriends.main.listener;

import java.sql.SQLException;
import java.util.List;
import partyAndFriends.main.Main;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import partyAndFriends.party.PartyManager;
import partyAndFriends.party.PlayerParty;

/**
 * The class with the PlayerDisconnectEvent event.
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class PlayerDisconnectListener implements Listener {
	/**
	 * The Main.main class
	 */
	/**
	 * Will be executed on player disconnect
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param e
	 *            The disconnect event
	 * @throws SQLException
	 *             Can throw a {@link SQLException}
	 */
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
						if (Main.main.language.equalsIgnoreCase("english")) {
							p.sendMessage(new TextComponent(Main.main.partyPrefix
									+ "§bThe §bLeader §bhas §bleft §bthe §bParty. §bThe §bnew §bLeader §bis §e"
									+ newLeader.getDisplayName() + "."));
						} else {
							if (Main.main.language.equalsIgnoreCase("own")) {
								p.sendMessage(new TextComponent(Main.main.partyPrefix
										+ Main.main.messagesYml.getString("Party.Command.Leave.NewLeaderIs")
												.replace("[NEWLEADER]", newLeader.getDisplayName())));
							} else {
								p.sendMessage(new TextComponent(Main.main.partyPrefix
										+ "§bDer §bLeader §bhat §bdie §bParty §bverlassen. §bDer §bneue §bLeader §bist §e"
										+ newLeader.getDisplayName() + "."));
							}
						}
					}
					party.setLeader(newLeader);
					liste.remove(newLeader);
					party.setPlayer(liste);
				} else {
					for (ProxiedPlayer p : party.getPlayer()) {
						if (Main.main.language.equalsIgnoreCase("english")) {
							p.sendMessage(new TextComponent(Main.main.partyPrefix
									+ "§5The §5party §5was §5dissolved §5because §5of §5to §5less §5players."));
						} else {
							if (Main.main.language.equalsIgnoreCase("own")) {
								p.sendMessage(new TextComponent(Main.main.partyPrefix + Main.main.messagesYml
										.getString("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers")));
							} else {
								p.sendMessage(new TextComponent(Main.main.partyPrefix
										+ "§5Die §5Party §5wurde §5wegen §5zu §5wenig §5Mitgliedern §5aufgelöst."));
							}
						}
					}
					PartyManager.deleteParty(party);
				}
			} else {
				party.removePlayer(player);
				for (ProxiedPlayer p : party.getPlayer()) {
					if (Main.main.language.equalsIgnoreCase("english")) {
						p.sendMessage(new TextComponent(Main.main.partyPrefix + "§bThe §bplayer §6"
								+ player.getDisplayName() + " §bhas §bleft §bthe §bparty."));
					} else {
						if (Main.main.language.equalsIgnoreCase("own")) {
							p.sendMessage(new TextComponent(Main.main.partyPrefix
									+ Main.main.messagesYml.getString("Party.Command.General.PlayerHasLeftTheParty")
											.replace("[PLAYER]", player.getDisplayName())));
						} else {
							p.sendMessage(new TextComponent(Main.main.partyPrefix + "§bDer §bSpieler §6"
									+ player.getDisplayName() + " §bhat §bdie §bParty §bverlassen."));
						}
					}
				}

				if (Main.main.language.equalsIgnoreCase("english")) {
					party.getleader().sendMessage(new TextComponent(Main.main.partyPrefix + "§bThe §bplayer §6"
							+ player.getDisplayName() + " §bhas §bleft §bthe §bparty."));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						party.getleader()
								.sendMessage(new TextComponent(Main.main.partyPrefix
										+ Main.main.messagesYml.getString("Party.Command.General.PlayerHasLeftTheParty")
												.replace("[PLAYER]", player.getDisplayName())));
					} else {
						party.getleader().sendMessage(new TextComponent(Main.main.partyPrefix + "§bDer §bSpieler §6"
								+ player.getDisplayName() + " §bhat §bdie §bParty §bverlassen."));
					}
				}
				List<ProxiedPlayer> liste = party.getPlayer();
				if (liste.size() == 1) {
					if (Main.main.language.equalsIgnoreCase("english")) {
						party.getleader().sendMessage(new TextComponent(Main.main.partyPrefix
								+ "§5The §5party §5was §5dissolved §5because §5of §5to §5less §5players."));
					} else {
						if (Main.main.language.equalsIgnoreCase("own")) {
							party.getleader()
									.sendMessage(new TextComponent(Main.main.partyPrefix + Main.main.messagesYml
											.getString("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers")));
						} else {
							party.getleader().sendMessage(new TextComponent(Main.main.partyPrefix
									+ "§5Die §5Party §5wurde §5wegen §5zu §5wenig §5Mitgliedern §5aufgelöst."));
						}
					}
				}
			}
		}
		String freundeAusgeben = Main.main.verbindung.freundeAusgeben(player.getUniqueId() + "");
		if (freundeAusgeben.equals("")) {
			return;
		}
		int[] freundeArrayID = Main.main.verbindung
				.getFreundeArray(Main.main.verbindung.getIDByPlayerName(player.getName()));
		for (int i = 0; i < freundeArrayID.length; i++) {
			String befreundeterSpieler = Main.main.verbindung.getNameDesSpielers(freundeArrayID[i]);
			ProxiedPlayer freundGeladen = BungeeCord.getInstance().getPlayer(befreundeterSpieler);
			if (freundGeladen != null) {
				if (Main.main.language.equalsIgnoreCase("english")) {
					freundGeladen.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
							+ " §7Your friend §e" + player.getDisplayName() + " §7is §7now §coffline."));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						freundGeladen.sendMessage(new TextComponent(Main.main.friendsPrefix
								+ Main.main.messagesYml.getString("Friends.General.PlayerIsNowOffline")
										.replace("[PLAYER]", player.getDisplayName())));
					} else {
						freundGeladen.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
								+ " §7Der Freund §e" + player.getDisplayName() + " §7ist §7nun §cOffline."));
					}
				}
			}
		}
	}
}