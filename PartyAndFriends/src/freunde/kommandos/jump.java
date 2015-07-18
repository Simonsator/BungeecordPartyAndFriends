package freunde.kommandos;

import java.sql.SQLException;

import mySql.mySql;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class jump {
	public static void springen(ProxiedPlayer player, String[] args, mySql verbindung, String language)
			throws SQLException {
		if (args.length > 1) {
			ProxiedPlayer freund = BungeeCord.getInstance().getPlayer(args[1]);

			if (freund == null) {
				if (language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + " §7The Player §e"
							+ args[0] + " §7is §7not §7online §7or §7you §7bare §7not §7a §7friend §7of §7him"));
				} else {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + " §7Der Spieler §e" + args[1]
							+ " §7ist §7nicht §7Online §7oder §7du §7bist §7nicht §7mit §7ihm §7befreundet"));
				}
				return;
			}
			if (verbindung.istBefreundetMit(player, freund)) {
				ServerInfo zuJoinen = freund.getServer().getInfo();
				if (zuJoinen.equals(player.getServer().getInfo())) {
					if (language.equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(
								"§8[§5§lFriends§8]" + " §7You §7bare §7already §7on §7this §7server"));
					} else {
						player.sendMessage(new TextComponent(
								"§8[§5§lFriends§8]" + " §7Du §7bist §7bereits §7auf §7diesem §7Server"));
					}
					return;
				}
				player.connect(zuJoinen);
				if (language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]"
							+ " §7Now §7you §7are §7on §7the §7same §7server, §7like §7the §7player §e" + args[1]));
				} else {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]"
							+ " §7Du §7bist §7jetzt §7auf §7dem §7gleichen §7Server, §7wie §7der §7Spieler §e"
							+ args[1]));
				}
			} else {
				if (language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + " §7The Player §e"
							+ args[0] + " §7is §7not §7online §7or §7you §7bare §7not §7a §7friend §7of §7him"));
				} else {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + " §7Der Spieler §e" + args[1]
							+ " §7ist §7nicht §7Online §7oder §7du §7bist §7nicht §7mit §7ihm §7befreundet"));
				}
			}
		} else {
			if (language.equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + " §7You §7need §7to §7give §7a §7friend"));
				player.sendMessage(new TextComponent("§8/§5friend §5jump [name of the §7friend]" + ChatColor.RESET
						+ "§8- §7Jump §7to §7a §7friend"));
			} else {
				player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + " §7Du §7musst §7einen §7Freund §7angeben"));
				player.sendMessage(new TextComponent("§8/§5friend §5jump [Name des Freundes]" + ChatColor.RESET
						+ "§8- §7Zu §7einem §7Freund §7springen"));
			}
		}
	}
}
