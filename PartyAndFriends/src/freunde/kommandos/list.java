package freunde.kommandos;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.StringTokenizer;

import mySql.mySql;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class list {
	public static void auflisten(ProxiedPlayer p, String[] args,
			mySql verbindung) throws SQLException {
		String uuid = p.getUniqueId() + "";
		String freundeAusgeben;
		freundeAusgeben = verbindung.freundeAusgeben(uuid);
		if (freundeAusgeben.equals("")) {
			p.sendMessage(new TextComponent("§8[§5§lFriends§8]"
					+ ChatColor.RESET
					+ " §7Du hast noch keine Freunde hinzugefügt."));
		} else {
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
			String[] freundeName = new String[freundeArrayID.length];
			String freundeZusammen = "";
			while (freundeArrayID.length > i) {
				freundeName[i] = verbindung
						.getNameDesSpielers(freundeArrayID[i]);
				ProxiedPlayer freundGeladen = BungeeCord.getInstance()
						.getPlayer(freundeName[i]);
				String zusatz;
				ChatColor farbe;
				if (freundGeladen == null) {
					zusatz = "(offline)";
					farbe = ChatColor.RED;
				} else {
					zusatz = "(online)";
					farbe = ChatColor.GREEN;
				}
				String komma;
				if (i > 0) {
					komma = "§7, ";
				} else {
					komma = " ";
				}
				freundeZusammen = freundeZusammen + komma + farbe
						+ verbindung.getNameDesSpielers(freundeArrayID[i])
						+ zusatz + "§7";
				i++;
			}
			p.sendMessage(new TextComponent("§8[§5§lFriends§8]"
					+ ChatColor.RESET + " §7Dies §7sind §7deine §7Freunde:"
					+ freundeZusammen));
		}
	}
}
