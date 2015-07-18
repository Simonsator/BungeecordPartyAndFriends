package freunde;

import java.sql.SQLException;

import mySql.mySql;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class nachricht {
	public static void senden(ProxiedPlayer player, String[] args, mySql verbindung, int typ, String language)
			throws SQLException {
		if (typ == 0) {
			if (args.length > 2) {
				ProxiedPlayer angeschrieben = BungeeCord.getInstance().getPlayer(args[1]);
				if (angeschrieben == null) {
					if (language.equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + " §7The Player §e"
								+ args[0] + " §7is §7not §7online §7or §7you §7bare §7not §7a §7friend §7of §7him"));
					} else {
						player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
								+ " §7Der §7Spieler §e" + args[0]
								+ " §7ist §7nicht §7Online §7oder §7du §7bist §7nicht §7mit §7ihm §7befreundet"));
					}
					return;
				}
				if (angeschrieben.equals(player)) {
					if (language.equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(
								"§8[§5§lFriends§8]" + ChatColor.RESET + " §7You can §7not §7write §7to §7yourself."));
						player.sendMessage(
								new TextComponent("§8/§5friend §5msg §5[name §5of $5the §5friend] §5[message]"
										+ ChatColor.RESET + " §8- §7send §7a §7friend §7a §7message"));
					} else {
						player.sendMessage(new TextComponent(
								"§8[§5§lFriends§8]" + ChatColor.RESET + " §7Du kannst dich nicht selber anschreiben."));
						player.sendMessage(
								new TextComponent("§8/§5friends §5msg §5[Name §5des §5Freundes] §5[Nachricht]"));
					}
					return;
				}
				if (verbindung.istBefreundetMit(player, angeschrieben)) {
					nachrichtSenden(args, 0, angeschrieben, player);
				} else {
					if (language.equalsIgnoreCase("english")) {
						player.sendMessage(
								new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + " §7The §7player §e" + args[0]
										+ " §7is §7not §7online §7or §7you §7bare §7not §7a §7friend §7of §7him"));
					} else {
						player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
								+ " §7Der §7Spieler §e" + args[1]
								+ " §7ist §7nicht §7Online §7oder §7du §7bist §7nicht §7mit §7ihm §7befreundet"));
					}
					return;
				}
			} else {
				if (language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
							+ " §7You §7need §7to §7give §7a §7player §7and §7a §7message."));
					player.sendMessage(new TextComponent("§8/§5friend §5msg §5[name §5of $5the §5friend] §5[message]"
							+ ChatColor.RESET + " §8- §7send §7a §7friend §7a §7message"));
				} else {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
							+ " §7Du §7musst §7einen §7Spieler §7und §7eine §7Nachricht §7angeben."));
					player.sendMessage(new TextComponent("§8/§5friend §5msg §5[Name §5des §5Freundes] §5[Nachricht]"));
				}
			}
		} else {
			if (args.length > 1) {
				ProxiedPlayer angeschrieben = BungeeCord.getInstance().getPlayer(args[0]);
				if (angeschrieben == null) {
					if (language.equalsIgnoreCase("english")) {
						player.sendMessage(
								new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + " §7The §7player §e" + args[0]
										+ " §7is §7not §7online §7or §7you §7bare §7not §7a §7friend §7of §7him"));
					} else {
						player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
								+ " §7Der §7Spieler §e" + args[1]
								+ " §7ist §7nicht §7Online §7oder §7du §7bist §7nicht §7mit §7ihm §7befreundet"));
					}
					return;
				}
				if (angeschrieben.equals(player)) {
					if (language.equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(
								"§8[§5§lFriends§8]" + ChatColor.RESET + " §7You can not §7write §7to §7yourself."));
						player.sendMessage(
								new TextComponent("§8/§5friend §5msg §5[name §5of $5the §5friend] §5[message]"
										+ ChatColor.RESET + " §8- §7send §7a §7friend §7a §7message"));
					} else {
						player.sendMessage(new TextComponent(
								"§8[§5§lFriends§8]" + ChatColor.RESET + " §7Du kannst dich nicht selber anschreiben."));
						player.sendMessage(new TextComponent("§8/§5msg §5[Name §5des §5Freundes] §5[Nachricht]"));
					}
					return;
				}
				if (verbindung.istBefreundetMit(player, angeschrieben)) {
					nachrichtSenden(args, 1, angeschrieben, player);
				} else {
					if (language.equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + " §7The Player §e"
								+ args[0] + " §7is §7not §7online §7or §7you §7bare §7not §7a §7friend §7of §7him"));
					} else {
						player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
								+ " §7Der §7Spieler §e" + args[0]
								+ " §7ist §7nicht §7Online §7oder §7du §7bist §7nicht §7mit §7ihm §7befreundet"));
					}
					return;
				}
			} else {
				if (language.equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
							+ " §7You §7need §7to §7give §7a §7player §7and §7a §7message."));
					player.sendMessage(new TextComponent("§8/§5friend §5msg §5[name §5of $5the §5friend] §5[message]"
							+ ChatColor.RESET + " §8- §7send §7a §7friend §7a §7message"));
				} else {
					player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
							+ " §7Du §7musst §7einen §7Spieler §7und §7eine §7Nachricht §7angeben."));
					player.sendMessage(new TextComponent("§8/§5msg §5[Name §5des §5Freundes] §5[Nachricht]"));
				}
			}
		}
	}

	private static void nachrichtSenden(String[] args, int typ, ProxiedPlayer angeschrieben, ProxiedPlayer player) {
		int durchlauf = 2;
		if (typ == 1) {
			durchlauf = 1;
		}
		String Inhalt = "";
		while (durchlauf < args.length) {
			Inhalt = Inhalt + " §7" + args[durchlauf];
			durchlauf++;
		}
		angeschrieben.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + " §e" + player.getName()
				+ "§5-> §e" + angeschrieben.getDisplayName() + "§7:§7" + Inhalt));
		player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + " §e" + player.getName()
				+ "§5-> §e" + angeschrieben.getDisplayName() + "§7:§7" + Inhalt));
	}
}
