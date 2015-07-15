package freunde.kommandos;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.StringTokenizer;
import mySql.mySql;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.Chat;

public class add {

	public static void hinzufuegen(ProxiedPlayer player, String[] args,
			mySql verbindung) throws SQLException {
		if (args.length == 2) {
			int idAbfrage = verbindung.getIDByPlayerName(args[1]);
			if (idAbfrage == -1) {
				player.sendMessage(new TextComponent("§8[§5§lFriends§8]"
						+ ChatColor.RESET + "§7Der Spieler §e" + args[1]
						+ "§7 exestiert nicht"));
				return;
			}
			String senderName = player.getName();
			if (senderName.equalsIgnoreCase(args[1])) {
				player.sendMessage(new TextComponent(
						"§8[§5§lFriends§8]"
								+ ChatColor.RESET
								+ " §7Du kannst dir nicht selber eine §7Freundschaftsanfrage §7senden"));
				return;
			}
			int idSender = verbindung.getIDByPlayerName(senderName);
			String zuTrennen = verbindung.freundeAusgeben(idSender);
			int[] anfragenID = new int[0];
			StringTokenizer st = new StringTokenizer(zuTrennen, "|");
			while (st.hasMoreTokens()) {
				Object newArray = Array.newInstance(anfragenID.getClass()
						.getComponentType(), Array.getLength(anfragenID) + 1);
				System.arraycopy(anfragenID, 0, newArray, 0,
						Array.getLength(anfragenID));
				anfragenID = (int[]) newArray;
				anfragenID[Array.getLength(anfragenID) - 1] = Integer
						.parseInt(st.nextToken());
			}
			boolean gefunden = false;
			int i = 0;
			while (anfragenID.length > i && gefunden == false) {
				if (anfragenID[i] == idAbfrage) {
					gefunden = true;
				}
				i++;
			}
			if (gefunden == true) {
				player.sendMessage(new TextComponent("§8[§5§lFriends§8]"
						+ ChatColor.RESET + "§7 Du bist schon mit §e" + args[1]
						+ " §7befreundet"));
				return;
			} else {
				boolean gefundenBeiAnfragen = false;
				String anfragenDesAngefragtenZuTrennen = verbindung
						.getAnfragen(idAbfrage);
				int[] anfragenIDDesAngefragten = new int[0];
				StringTokenizer stn = new StringTokenizer(
						anfragenDesAngefragtenZuTrennen, "|");
				while (stn.hasMoreTokens()) {
					Object newArray = Array.newInstance(
							anfragenIDDesAngefragten.getClass()
									.getComponentType(), Array
									.getLength(anfragenIDDesAngefragten) + 1);
					System.arraycopy(anfragenIDDesAngefragten, 0, newArray, 0,
							Array.getLength(anfragenIDDesAngefragten));
					anfragenIDDesAngefragten = (int[]) newArray;
					anfragenIDDesAngefragten[Array
							.getLength(anfragenIDDesAngefragten) - 1] = Integer
							.parseInt(stn.nextToken());
				}
				int durchlauf = 0;
				while (anfragenIDDesAngefragten.length > durchlauf
						&& gefundenBeiAnfragen == false) {
					if (anfragenIDDesAngefragten[durchlauf] == idSender) {
						gefundenBeiAnfragen = true;
					}
					durchlauf++;
				}
				if (gefundenBeiAnfragen == true) {
					player.sendMessage(new TextComponent(
							"§8[§5§lFriends§8]"
									+ ChatColor.RESET
									+ "§7 Du hast dem Spieler §e"
									+ args[1]
									+ " §7schon §7eine §7Freundschaftsanfrage §7gesendet."));
					return;
				}
				boolean gefundenBeiAnfragenVonSelbst = false;
				String anfragenDesAngefragtenVonSelbstZuTrennen = verbindung
						.getAnfragen(idSender);
				int[] anfragenIDDesAngefragtenVonSelbst = new int[0];
				StringTokenizer stnn = new StringTokenizer(
						anfragenDesAngefragtenVonSelbstZuTrennen, "|");
				while (stnn.hasMoreTokens()) {
					Object newArray = Array
							.newInstance(
									anfragenIDDesAngefragtenVonSelbst
											.getClass().getComponentType(),
									Array.getLength(anfragenIDDesAngefragtenVonSelbst) + 1);
					System.arraycopy(anfragenIDDesAngefragtenVonSelbst, 0,
							newArray, 0,
							Array.getLength(anfragenIDDesAngefragtenVonSelbst));
					anfragenIDDesAngefragtenVonSelbst = (int[]) newArray;
					anfragenIDDesAngefragtenVonSelbst[Array
							.getLength(anfragenIDDesAngefragtenVonSelbst) - 1] = Integer
							.parseInt(stnn.nextToken());
				}
				int durchlaufn = 0;
				while (anfragenIDDesAngefragtenVonSelbst.length > durchlaufn
						&& gefundenBeiAnfragenVonSelbst == false) {
					if (anfragenIDDesAngefragtenVonSelbst[durchlaufn] == idAbfrage) {
						gefundenBeiAnfragenVonSelbst = true;
					}
					durchlaufn++;
				}
				if (gefundenBeiAnfragenVonSelbst == true) {
					player.sendMessage(new TextComponent(
							"§8[§5§lFriends§8]"
									+ ChatColor.RESET
									+ " §7Der Spieler §e"
									+ args[1]
									+ " §7hat §7dir §7schon §7eine §7Freundschaftsanfrage §7gesendet."));
					String zuschreiben = "§8[§5§lFriends§8]" + ChatColor.RESET
							+ " §7Nimm sie mit §6/friend accept " + args[1]
							+ " §7an";
					String command = "/friends accept " + args[1];
					String jsoncode = "{'text':'"
							+ zuschreiben
							+ "', 'clickEvent':{'action':'run_command','value':'"
							+ command
							+ "'},'hoverEvent':{'action':'show_text','value':'Hier klicken um die Freundschaftsanfrage anzunehmen'}}";
					player.unsafe().sendPacket(new Chat(jsoncode));
					return;
				}
			}
			if (verbindung.erlaubtAnfragen(idAbfrage)) {
				player.sendMessage(new TextComponent("§8[§5§lFriends§8]"
						+ ChatColor.RESET + " §7Du §7kannst §7dem §7Spieler §e"
						+ args[1] + " §7keine §7Freundschaftsanfrage §7senden"));
				return;
			}
			verbindung.sendFreundschaftsAnfrage(idSender, idAbfrage);
			ProxiedPlayer empfaenger = BungeeCord.getInstance().getPlayer(
					args[1]);
			if (empfaenger != null) {
				empfaenger.sendMessage(new TextComponent("§8[§5§lFriends§8]"
						+ ChatColor.RESET
						+ "§7 Du hast eine Freundschafftsanfrage von §e"
						+ senderName + " §7erhalten"));
				String zuschreiben = "§7 Nimm die Freundschafftsanfrage mit /friends accept §e"
						+ senderName + " §7an.";
				String command = "/friends accept " + senderName;
				String jsoncode = "{'text':'"
						+ zuschreiben
						+ "', 'clickEvent':{'action':'run_command','value':'"
						+ command
						+ "'},'hoverEvent':{'action':'show_text','value':'Hier klicken um die Freundschaftsanfrage anzunehmen'}}";
				empfaenger.unsafe().sendPacket(new Chat(jsoncode));
			}
			player.sendMessage(new TextComponent("§8[§5§lFriends§8]"
					+ ChatColor.RESET + "§7 Dem Spieler §e" + args[1]
					+ "§7 wurde §7eine §7Freundschafftsanfrage §7gesendet"));
			return;
		}
		if (args.length < 2) {
			player.sendMessage(new TextComponent("§8[§5§lFriends§8]"
					+ ChatColor.RESET + " §7Du musst einen Spieler angeben"));
			player.sendMessage(new TextComponent(
					"§8/§5friends add [Name des Spielers]" + ChatColor.RESET
							+ " §8- §7Fügt einen Freund hinzu"));
			return;
		}
		if (args.length > 2) {
			player.sendMessage(new TextComponent("§8[§5§lFriends§8]"
					+ ChatColor.RESET + "§7 Zu viele Argumente"));
			player.sendMessage(new TextComponent(
					"§8/§5friends add [Name des Spielers]" + ChatColor.RESET
							+ " §8- §7Fügt einen Freund hinzu"));
			return;
		}
	}
}
