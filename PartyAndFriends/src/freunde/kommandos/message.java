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

	public static void nachricht(ProxiedPlayer player, String[] args, mySql verbindung, String language)
			throws SQLException {
		nachrichtSenden(player, args, verbindung, language);
	}

	public static void nachrichtSenden(ProxiedPlayer player, String[] args, mySql verbindung, String language)
			throws SQLException {
		nachricht.senden(player, args, verbindung, 0, language);
	}
}
