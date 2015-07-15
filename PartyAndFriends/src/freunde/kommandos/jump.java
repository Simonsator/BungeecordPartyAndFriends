package freunde.kommandos;

import java.sql.SQLException;

import mySql.mySql;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class jump {
	public static void springen(ProxiedPlayer player, String[] args,
			mySql verbindung) throws SQLException {
		if (args.length > 1) {
			ProxiedPlayer freund = BungeeCord.getInstance().getPlayer(args[1]);
			
			if (freund == null) {
				player.sendMessage(new TextComponent(
						"§8[§5§lFriends§8]"
								+ " §7Der Spieler §e"
								+ args[1]
								+ " §7ist §7nicht §7Online §7oder §7du §7bist §7nicht §7mit §7ihm §7befreundet"));
				return;
			}
			if (verbindung.istBefreundetMit(player, freund)) {
				ServerInfo zuJoinen = freund.getServer().getInfo();
				if (zuJoinen.equals(player.getServer().getInfo())) {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]"
							+ " §7Du §7bist §7bereits §7auf §7diesem §7Server"));
					return;
				}
				player.connect(zuJoinen);
				player.sendMessage(new TextComponent(
						"§8[§5§lFriends§8]"
								+ " §7Du §7bist §7jetzt §7auf §7dem §7gleichen §7Server, §7wie §7der §7Spieler §e"
								+ args[1]));
			} else {
				player.sendMessage(new TextComponent(
						"§8[§5§lFriends§8]"
								+ " §7Der Spieler §e"
								+ args[1]
								+ " §7ist §7nicht §7Online §7oder §7du §7bist §7nicht §7mit §7ihm §7befreundet"));
			}
		} else {
			player.sendMessage(new TextComponent("§8[§5§lFriends§8]"
					+ " §7Du §7musst §7einen §7Freund §7angeben"));
			player.sendMessage(new TextComponent(
					"§8/§5friend §5jump [Name des Freundes]" + ChatColor.RESET
							+ "§8- §7Zu §7einem §7Freund §7springen"));
		}
	}
}
