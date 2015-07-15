package freunde.kommandos;

//import java.lang.reflect.Array;
import java.sql.SQLException;
//import java.util.StringTokenizer;

import mySql.mySql;
import freunde.nachricht;
/*import net.md_5.bungee.BungeeCord;
 import net.md_5.bungee.api.ChatColor;
 import net.md_5.bungee.api.chat.TextComponent;*/
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class message {

	public static void nachricht(ProxiedPlayer player, String[] args,
			mySql verbindung) throws SQLException {
		nachrichtSenden(player, args, verbindung);
	}

	public static void nachrichtSenden(ProxiedPlayer player, String[] args,
			mySql verbindung) throws SQLException {
		nachricht.senden(player, args, verbindung, 0);
		/*
		 * if (args.length > 2) { ProxiedPlayer angeschrieben =
		 * BungeeCord.getInstance().getPlayer( args[1]); if (angeschrieben !=
		 * null) { boolean befreundetMit = false; String uuid =
		 * angeschrieben.getUniqueId() + ""; String freundeAusgeben =
		 * verbindung.freundeAusgeben(uuid); if (freundeAusgeben.equals("")) {
		 * return; } if (args[1].equalsIgnoreCase(player.getName())) {
		 * player.sendMessage(new TextComponent("§8[§5§lFriends§8]" +
		 * ChatColor.RESET + " §7Du kannst dich nicht selber anschreiben."));
		 * player.sendMessage(new TextComponent(
		 * "§8/§5friends §5msg §5[Name §5des §5Freundes] §5[Nachricht]"));
		 * return; } int[] freundeArrayID = new int[0]; StringTokenizer st = new
		 * StringTokenizer(freundeAusgeben, "|"); while (st.hasMoreTokens()) {
		 * Object newArray = Array.newInstance(freundeArrayID
		 * .getClass().getComponentType(), Array .getLength(freundeArrayID) +
		 * 1); System.arraycopy(freundeArrayID, 0, newArray, 0,
		 * Array.getLength(freundeArrayID)); freundeArrayID = (int[]) newArray;
		 * freundeArrayID[Array.getLength(freundeArrayID) - 1] = Integer
		 * .parseInt(st.nextToken()); } int i = 0; String befreundeterSpieler;
		 * while (freundeArrayID.length > i) { befreundeterSpieler = verbindung
		 * .getNameDesSpielers(freundeArrayID[i]); ProxiedPlayer freundGeladen =
		 * BungeeCord.getInstance() .getPlayer(befreundeterSpieler); if
		 * (freundGeladen != null &&
		 * freundGeladen.getDisplayName().equalsIgnoreCase( args[i])) {
		 * befreundetMit = true; } i++; } if (befreundetMit == true) { if
		 * (args.length > 0) {
		 * 
		 * int durchlauf = 1; String Inhalt = ""; while (durchlauf <
		 * args.length) { Inhalt = Inhalt + " §7" + args[durchlauf];
		 * durchlauf++; } angeschrieben.sendMessage(new TextComponent(
		 * "§8[§5§lFriends§8]" + ChatColor.RESET + " §e" + player.getName() +
		 * "§5-> §e" + args[1] + ": §7" + Inhalt)); player.sendMessage(new
		 * TextComponent( "§8[§5§lFriends§8]" + ChatColor.RESET + " §5" +
		 * player.getName() + "§5-> §e" + args[1] + ": §7" + Inhalt)); } } else
		 * { player.sendMessage(new TextComponent("§8[§5§lFriends§8]" +
		 * ChatColor.RESET + " §7Du bist nicht mit §e" + args[1] +
		 * " §7befreundet")); } } else { player.sendMessage(new
		 * TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET +
		 * " §7Der Spieler §e" + args[1] + " §7ist §7nicht §7Online.")); } }
		 * else { player.sendMessage(new TextComponent( "§8[§5§lFriends§8]" +
		 * ChatColor.RESET +
		 * " §7Du §7musst §7einen §7Spieler §7und §7eine §7Nachricht §7angeben."
		 * )); player.sendMessage(new TextComponent(
		 * "§8/§5friends §5msg §5[Name §5des §5Freundes] §5[Nachricht]")); }
		 */
	}
}
