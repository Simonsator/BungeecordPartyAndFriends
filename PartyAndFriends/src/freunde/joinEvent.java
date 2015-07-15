package freunde;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.StringTokenizer;
import freunde.spielerUUID;
import mySql.mySql;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class joinEvent implements Listener {
	spielerUUID infoplatz = new spielerUUID();
	mySql verbindung;

	public joinEvent(mySql pVerbindung) {
		verbindung = pVerbindung;
	}

	@EventHandler
	public void onPostLogin(PostLoginEvent event) throws SQLException {
		ProxiedPlayer spieler = event.getPlayer();
		String nameDesSpielers = spieler.getName();
		String uuid = spieler.getUniqueId() + "";
		int id = verbindung.ID(uuid);
		if (id == -1) {
			verbindung.erstesmal(spieler, nameDesSpielers, uuid);
			return;
		} else {
			if (nameDesSpielers != verbindung.getNameDesSpielers(id)) {
				verbindung.updateSpielerName(id, nameDesSpielers);
			}
		}
		String freundeAusgeben = verbindung.freundeAusgeben(uuid);
		String anfragen = verbindung.getAnfragen(id);
		if (freundeAusgeben.equals("") && anfragen.equals("")) {
			return;
		}
		boolean keineFreunde = false;
		if (freundeAusgeben.equals("")) {
			keineFreunde = true;
		}
		boolean keineAnfragen = false;
		if (anfragen.equals("")) {
			keineAnfragen = true;
		}
		if (keineFreunde == false) {
			int[] freundeArrayID = new int[0];
			StringTokenizer st = new StringTokenizer(freundeAusgeben, "|");
			while (st.hasMoreTokens()) {
				Object newArray = Array.newInstance(freundeArrayID.getClass()
						.getComponentType(),
						Array.getLength(freundeArrayID) + 1);
				System.arraycopy(freundeArrayID, 0, newArray, 0,
						Array.getLength(freundeArrayID));
				freundeArrayID = (int[]) newArray;
				freundeArrayID[Array.getLength(freundeArrayID) - 1] = Integer
						.parseInt(st.nextToken());
			}
			int i = 0;
			String befreundeterSpieler;
			while (freundeArrayID.length > i) {
				befreundeterSpieler = verbindung
						.getNameDesSpielers(freundeArrayID[i]);
				ProxiedPlayer freundGeladen = BungeeCord.getInstance()
						.getPlayer(befreundeterSpieler);
				if (freundGeladen != null) {
					freundGeladen.sendMessage(new TextComponent(
							"§8[§5§lFriends§8]" + ChatColor.RESET
									+ " §7Der Freund §e" + nameDesSpielers
									+ "§7 ist §7nun §aOnline."));
				}
				i++;
			}
		}
		if (keineAnfragen == false) {
			StringTokenizer stnnnn = new StringTokenizer(anfragen, "|");
			String Inhalt = "";
			while (stnnnn.hasMoreTokens()) {
				Inhalt = Inhalt
						+ " §e"
						+ verbindung.getNameDesSpielers(Integer.parseInt(stnnnn
								.nextToken())) + "§7,";
			}
			String Inhaltb = Inhalt.substring(0, Inhalt.length() - 1);
			spieler.sendMessage(new TextComponent(
					"§8[§5§lFriends§8]"
							+ ChatColor.RESET
							+ " §7Freundschaftsanfragen §7stehen §7von §7den §7folgenden §7Spielern §7aus:"
							+ Inhaltb));
		}
	}
}
