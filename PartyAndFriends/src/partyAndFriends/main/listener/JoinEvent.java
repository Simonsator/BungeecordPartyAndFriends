/**
 * The class with the join event
 * @author Simonsator
 * @version 1.0.0
 */
package partyAndFriends.main.listener;

import java.sql.SQLException;
import java.util.ArrayList;
import partyAndFriends.main.Main;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
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
		if (Main.main.language.equalsIgnoreCase("own")) {
			farbe = Main.main.messagesYml.getString("Friends.General.RequestInfoOnJoinColor");
			farbeComma = Main.main.messagesYml.getString("Friends.General.RequestInfoOnJoinColorComma");
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
	public void onPostLogin(PostLoginEvent event) throws SQLException {
		ProxiedPlayer spieler = event.getPlayer();
		int id = Main.main.verbindung.ID(spieler.getUniqueId() + "");
		if (id == -1) {
			Main.main.verbindung.erstesmal(spieler);
			return;
		} else {
			if (spieler.getName() != Main.main.verbindung.getNameDesSpielers(id)) {
				Main.main.verbindung.updateSpielerName(id, spieler.getName());
			}
		}
		int[] freundeArrayID = Main.main.verbindung.getFreundeArray(id);
		ArrayList<String> anfragen = Main.main.verbindung.getAnfragenArrayList(id);
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
		if (Main.main.verbindung.einstellungenAbfragen(spieler)[3] == 1) {
			keineFreunde = true;
		}
		if (keineFreunde == false) {
			for (int i = 0; i < freundeArrayID.length; i++) {
				String befreundeterSpieler = Main.main.verbindung.getNameDesSpielers(freundeArrayID[i]);
				ProxiedPlayer freundGeladen = BungeeCord.getInstance().getPlayer(befreundeterSpieler);
				if (freundGeladen != null) {
					if (Main.main.language.equalsIgnoreCase("english")) {
						freundGeladen.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
								+ " §7The friend §e" + spieler.getDisplayName() + "§7 is §7now §aonline."));
					} else {
						if (Main.main.language.equalsIgnoreCase("own")) {
							freundGeladen.sendMessage(new TextComponent(Main.main.friendsPrefix
									+ Main.main.messagesYml.getString("Friends.General.PlayerIsNowOnline")
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
			if (Main.main.language.equalsIgnoreCase("english")) {
				spieler.sendMessage(new TextComponent(
						"§8[§5§lFriends§8]" + ChatColor.RESET + " §7You §7have §7friend §7requests §7from:" + Inhalt));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					spieler.sendMessage(new TextComponent(Main.main.friendsPrefix + Main.main.messagesYml
							.getString("Friends.General.RequestInfoOnJoin").replace("[FRIENDREQUESTS]", Inhalt)));
				} else {
					spieler.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
							+ " §7Freundschaftsanfragen §7stehen §7von §7den §7folgenden §7Spielern §7aus:" + Inhalt));
				}
			}
		}
	}
}
