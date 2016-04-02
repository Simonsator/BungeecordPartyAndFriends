/**
 * The class with the join event
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.main.listener;

import java.sql.SQLException;
import java.util.ArrayList;

import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * The class with the join event
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class JoinEvent implements Listener {
	/**
	 * The color of the friend requests.
	 */
	String farbe = "§e";
	/**
	 * The color for the comma in the list of received friend requests.
	 */
	String farbeComma = "§7";

	/**
	 * Initialize the object
	 */
	public JoinEvent() {
		if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
			farbe = Main.getInstance().getMessagesYml().getString("Friends.General.RequestInfoOnJoinColor");
			farbeComma = Main.getInstance().getMessagesYml().getString("Friends.General.RequestInfoOnJoinColorComma");
		}
	}

	/**
	 * Will be execute if somebody logs in into server
	 * 
	 * @param event
	 *            The event
	 * @throws SQLException
	 *             Throws a SQLException
	 */
	@EventHandler
	public void onPostLogin(final PostLoginEvent event) throws SQLException {
		Main.getInstance().getProxy().getScheduler().runAsync(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				jemandHatSichEingelogt(event);
			}
		});
	}

	private void jemandHatSichEingelogt(PostLoginEvent event) {
		ProxiedPlayer spieler = event.getPlayer();
		int id = Main.getInstance().getConnection().getIDByUUID(spieler.getUniqueId() + "");
		if (id == -1) {
			Main.getInstance().getConnection().firstJoin(spieler);
			return;
		} else {
			if (spieler.getName() != Main.getInstance().getConnection().getPlayerName(id)) {
				Main.getInstance().getConnection().updatePlayerName(id, spieler.getName());
			}
		}
		int[] freundeArrayID = Main.getInstance().getConnection().getFriendsAsArray(id);
		ArrayList<String> anfragen = Main.getInstance().getConnection().getRequestsAsArrayList(id);
		if (freundeArrayID.length == 0 && anfragen.size() == 0) {
			return;
		}
		boolean keineFreunde = false;
		if (freundeArrayID.length == 0) {
			keineFreunde = true;
		}
		boolean keineAnfragen = false;
		if (anfragen.size() == 0) {
			keineAnfragen = true;
		}
		if (Main.getInstance().getConnection().querySettings(spieler)[3] == 1) {
			keineFreunde = true;
		}
		if (keineFreunde == false) {
			for (int i = 0; i < freundeArrayID.length; i++) {
				String befreundeterSpieler = Main.getInstance().getConnection().getPlayerName(freundeArrayID[i]);
				ProxiedPlayer freundGeladen = ProxyServer.getInstance().getPlayer(befreundeterSpieler);
				if (freundGeladen != null) {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						freundGeladen.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
								+ " §7The friend §e" + spieler.getDisplayName() + "§7 is §7now §aonline."));
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							freundGeladen.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
									+ Main.getInstance().getMessagesYml().getString("Friends.General.PlayerIsNowOnline")
											.replace("[PLAYER]", spieler.getDisplayName())));
						} else {
							freundGeladen.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
									+ " §7Der Freund §e" + spieler.getDisplayName() + "§7 ist §7nun §aOnline."));
						}
					}
				}
			}
		}
		if (keineAnfragen == false) {
			String Inhalt = "";
			for (String geradeZuBearbeiten : anfragen) {
				Inhalt = Inhalt + farbe + geradeZuBearbeiten + farbeComma + ",";
			}
			Inhalt = Inhalt.substring(0, Inhalt.length() - 1);
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				spieler.sendMessage(new TextComponent(
						"§8[§5§lFriends§8]" + ChatColor.RESET + " §7You §7have §7friend §7requests §7from:" + Inhalt));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					spieler.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
							+ Main.getInstance().getMessagesYml().getString("Friends.General.RequestInfoOnJoin")
									.replace("[FRIENDREQUESTS]", Inhalt)));
				} else {
					spieler.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
							+ " §7Freundschaftsanfragen §7stehen §7von §7den §7folgenden §7Spielern §7aus:" + Inhalt));
				}
			}
		}
	}
}
