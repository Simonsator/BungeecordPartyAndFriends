package freunde.kommandos;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.StringTokenizer;

import mySql.mySql;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class accept {

	public static void akzeptieren(ProxiedPlayer player, String[] args, mySql verbindung, String language)
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
				verbindung.spielerHinzufuegen(idSender, idAbfrage);
				if (language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + " §7You and §e"
							+ args[1] + " §7are §7now §7friends"));
				} else {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
							+ " §7Du bist jetzt mit §e" + args[1] + " §7befreundet"));
				}
				ProxiedPlayer befreundet = BungeeCord.getInstance().getPlayer(args[1]);
				if (befreundet != null) {
					if (language.equalsIgnoreCase("english")) {
						befreundet.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + " §7You and §e"
								+ senderName + " §7are §7now §7friends"));
						befreundet.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
								+ " §7The friend §e" + senderName + "§7 is §7now §aonline."));
						player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + " §7The friend §e"
								+ args[0] + "§7 is §7now §aonline."));
					} else {
						befreundet.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
								+ " §7Du bist jetzt mit §e" + senderName + " §7befreundet"));
						befreundet.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + " §e"
								+ senderName + "§7 ist §7jetzt §aOnline"));
						player.sendMessage(new TextComponent(
								"§8[§5§lFriends§8]" + ChatColor.RESET + " §e" + args[1] + "§7 ist §7jetzt §aOnline"));
					}
				}
				return;
			} else {
				if (language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
							+ "§7You didn´t recive a §7friend §7request §7from §e" + args[1] + "§7."));
				} else {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
							+ "§7 Du hast keine §7Freundschaftsanfrage von §e" + args[1] + " §7keine §7erhalten"));
				}
			}
		} else {
			if (args.length < 2) {
				if (language.equalsIgnoreCase("english")) {
					player.sendMessage(
							new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + "§7 Too many arguments"));
					player.sendMessage(new TextComponent("§8/§5friend §5accept §5[name §5of §5the §5player]"
							+ ChatColor.RESET + " §8- §7accept §7a §7friend request"));
				} else {
					player.sendMessage(new TextComponent(
							"§8[§5§lFriends§8]" + ChatColor.RESET + "§7 Du musst einen Spieler angeben"));
					player.sendMessage(new TextComponent("§8/§5friends accept [Name des Spielers]" + ChatColor.RESET
							+ " §8- §7akzeptiert eine §7Freundschaftsanfrage"));
				}
			} else {
				if (language.equalsIgnoreCase("english")) {
					player.sendMessage(
							new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + "§7 Too many arguments"));
					player.sendMessage(new TextComponent("§8/§5friend §5accept §5[name §5of §5the §5player]"
							+ ChatColor.RESET + " §8- §7accept §7a §7friend request"));
				} else {
					player.sendMessage(
							new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + "§7Zu viele Argumente"));
					player.sendMessage(new TextComponent("§8/§5friends accept [Name des Spielers]" + ChatColor.RESET
							+ " §8- §7akzeptiert eine §7Freundschaftsanfrage"));
				}
			}
		}
	}
}
