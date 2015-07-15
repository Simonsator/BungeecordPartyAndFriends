package freunde;

import java.sql.SQLException;

import mySql.mySql;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class nachricht {
	public static void senden(ProxiedPlayer player, String[] args,
			mySql verbindung, int typ) throws SQLException {
		if (typ == 0) {
			if (args.length > 2) {
				ProxiedPlayer angeschrieben = BungeeCord.getInstance()
						.getPlayer(args[1]);
				if (angeschrieben == null) {
					player.sendMessage(new TextComponent(
							"§8[§5§lFriends§8]"
									+ ChatColor.RESET
									+ " §7Der §7Spieler §e"
									+ args[0]
									+ " §7ist §7nicht §7Online §7oder §7du §7bist §7nicht §7mit §7ihm §7befreundet"));
					return;
				}
				if (angeschrieben.equals(player)) {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]"
							+ ChatColor.RESET
							+ " §7Du kannst dich nicht selber anschreiben."));
					player.sendMessage(new TextComponent(
							"§8/§5friends §5msg §5[Name §5des §5Freundes] §5[Nachricht]"));
					return;
				}
				if (verbindung.istBefreundetMit(player, angeschrieben)) {
					nachrichtSenden(args, 0, angeschrieben, player);
				} else {
					player.sendMessage(new TextComponent(
							"§8[§5§lFriends§8]"
									+ ChatColor.RESET
									+ " §7Der §7Spieler §e"
									+ args[1]
									+ " §7ist §7nicht §7Online §7oder §7du §7bist §7nicht §7mit §7ihm §7befreundet"));
					return;
				}
			} else {
				player.sendMessage(new TextComponent(
						"§8[§5§lFriends§8]"
								+ ChatColor.RESET
								+ " §7Du §7musst §7einen §7Spieler §7und §7eine §7Nachricht §7angeben."));
				player.sendMessage(new TextComponent(
						"§8/§5friend §5msg §5[Name §5des §5Freundes] §5[Nachricht]"));
			}
		} else {
			if (args.length > 1) {
				ProxiedPlayer angeschrieben = BungeeCord.getInstance()
						.getPlayer(args[0]);
				if (angeschrieben == null) {
					player.sendMessage(new TextComponent(
							"§8[§5§lFriends§8]"
									+ ChatColor.RESET
									+ " §7Der §7Spieler §e"
									+ args[1]
									+ " §7ist §7nicht §7Online §7oder §7du §7bist §7nicht §7mit §7ihm §7befreundet"));
					return;
				}
				if (angeschrieben.equals(player)) {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]"
							+ ChatColor.RESET
							+ " §7Du kannst dich nicht selber anschreiben."));
					player.sendMessage(new TextComponent(
							"§8/§5msg §5[Name §5des §5Freundes] §5[Nachricht]"));
					return;
				}
				if (verbindung.istBefreundetMit(player, angeschrieben)) {
					nachrichtSenden(args, 1, angeschrieben, player);
				} else {
					player.sendMessage(new TextComponent(
							"§8[§5§lFriends§8]"
									+ ChatColor.RESET
									+ " §7Der §7Spieler §e"
									+ args[0]
									+ " §7ist §7nicht §7Online §7oder §7du §7bist §7nicht §7mit §7ihm §7befreundet"));
					return;
				}
			} else {
				player.sendMessage(new TextComponent(
						"§8[§5§lFriends§8]"
								+ ChatColor.RESET
								+ " §7Du §7musst §7einen §7Spieler §7und §7eine §7Nachricht §7angeben."));
				player.sendMessage(new TextComponent(
						"§8/§5msg §5[Name §5des §5Freundes] §5[Nachricht]"));
			}
		}
	}

	private static void nachrichtSenden(String[] args, int typ,
			ProxiedPlayer angeschrieben, ProxiedPlayer player) {
		int durchlauf = 2;
		if (typ == 1) {
			durchlauf = 1;
		}
		String Inhalt = "";
		while (durchlauf < args.length) {
			Inhalt = Inhalt + " §7" + args[durchlauf];
			durchlauf++;
		}
		angeschrieben.sendMessage(new TextComponent("§8[§5§lFriends§8]"
				+ ChatColor.RESET + " §e" + player.getName() + "§5-> §e"
				+ angeschrieben.getDisplayName() + "§7:§7" + Inhalt));
		player.sendMessage(new TextComponent("§8[§5§lFriends§8]"
				+ ChatColor.RESET + " §e" + player.getName() + "§5-> §e"
				+ angeschrieben.getDisplayName() + "§7:§7" + Inhalt));
	}
}
