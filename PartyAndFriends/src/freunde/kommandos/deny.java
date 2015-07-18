package freunde.kommandos;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.StringTokenizer;
import mySql.mySql;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class deny {

	public static void ablehnen(ProxiedPlayer player, String[] args, mySql verbindung, String language)
			throws SQLException {
		if (args.length == 2) {

			int idAbfrage = verbindung.getIDByPlayerName(args[1]);
			String senderName = player.getName();
			int idSender = verbindung.getIDByPlayerName(senderName);
			String zuTrennen = verbindung.getAnfragen(idSender);
			int[] anfragenID = new int[0];
			StringTokenizer st = new StringTokenizer(zuTrennen, "|");
			while (st.hasMoreTokens()) {
				Object newArray = Array.newInstance(anfragenID.getClass().getComponentType(),
						Array.getLength(anfragenID) + 1);
				System.arraycopy(anfragenID, 0, newArray, 0, Array.getLength(anfragenID));
				anfragenID = (int[]) newArray;
				anfragenID[Array.getLength(anfragenID) - 1] = Integer.parseInt(st.nextToken());
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
				verbindung.spielerAblehnen(idSender, idAbfrage);
				if (language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
							+ " §7You have deny the friend request of §e" + args[1] + "."));
				} else {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
							+ " §7Du hast die Anfrage von §e" + args[1] + " §7abglehnt"));
				}
			} else {
				if (language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
							+ "§7You didn´t recive a §7friend §7request §7from §e" + args[1] + "§7."));
				} else {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + "§7Du hast von §e"
							+ args[1] + "§7 keine §7Freundschaftsanfrage §7erhalten"));
				}
			}
		} else {
			if (args.length < 2) {
				if (language.equalsIgnoreCase("english")) {
					player.sendMessage(
							new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + "§7 You need to give a player"));
					player.sendMessage(new TextComponent("§8/§5friend §5deny §5[name §5of §5the §5player]"
							+ ChatColor.RESET + " §8- §7deny §7a §7friendrequest"));
				} else {
					player.sendMessage(new TextComponent(
							"§8[§5§lFriends§8]" + ChatColor.RESET + "§7 Du musst einen Spieler angeben"));
					player.sendMessage(new TextComponent("§8/§5friends §5deny §5[Name §5des §5Spielers]"
							+ ChatColor.RESET + " §8- §7Lehnt eine §7Freundschaftsanfrage §7ab"));
				}
			} else {
				if (language.equalsIgnoreCase("english")) {
					player.sendMessage(
							new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + "§7 Too many arguments"));
					player.sendMessage(new TextComponent("§8/§5friend §5deny §5[name §5of §5the §5player]"
							+ ChatColor.RESET + " §8- §7deny §7a §7friendrequest"));
				} else {
					player.sendMessage(
							new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + "§7 Zu viele Argumente"));
					player.sendMessage(new TextComponent("§8/§5friends §5deny §5[Name §5des §5Spielers]"
							+ ChatColor.RESET + " §8- §7Lehnt eine §7Freundschaftsanfrage §7ab"));
				}
			}
		}
	}

}
