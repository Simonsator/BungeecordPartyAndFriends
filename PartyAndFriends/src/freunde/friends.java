package freunde;

import java.sql.SQLException;

import freunde.kommandos.accept;
import freunde.kommandos.add;
import freunde.kommandos.deny;
import freunde.kommandos.einstellungen;
import freunde.kommandos.jump;
import freunde.kommandos.list;
import freunde.kommandos.message;
import freunde.kommandos.remove;
import mySql.mySql;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class friends extends Command {
	mySql verbindung;

	public friends(mySql pVerbindung) {
		super("friends", null, new String[] { "friend" });
		verbindung = pVerbindung;
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (!(commandSender instanceof ProxiedPlayer)) {
			commandSender.sendMessage(new TextComponent("§8[§5§lFriends§8]"
					+ " Du must ein Spieler sein!"));
			return;
		}
		ProxiedPlayer player = (ProxiedPlayer) commandSender;
		if (args.length == 0) {
			player.sendMessage(new TextComponent("§8§m-------------------"
					+ ChatColor.RESET
					+ "§8[§5§lFriends§8]§m-------------------"));
			player.sendMessage(new TextComponent("§8/§5friend list"
					+ ChatColor.RESET + " §8- §7Listet §7deine §7Freunde §7auf"));
			player.sendMessage(new TextComponent(
					"§8/§5friend §5msg §5[Name §5des §5Freundes] §5[Nachricht]"
							+ ChatColor.RESET
							+ " §8- §7schieckt §7einem §7Freund §7eine §7Private Nachricht"));
			player.sendMessage(new TextComponent(
					"§8/§5friend §5add §5[Name §5des §5Spielers]"
							+ ChatColor.RESET
							+ " §8- §7Fügt §7einen §7Freund §7hinzu"));
			player.sendMessage(new TextComponent(
					"§8/§5friend §5accept §5[Name §5des §5Spielers]"
							+ ChatColor.RESET
							+ " §8- §7akzeptiert §7eine §7Freundschaftsanfrage"));
			player.sendMessage(new TextComponent(
					"§8/§5friend §5deny §5[Name §5des §5Spielers]"
							+ ChatColor.RESET
							+ " §8- §7Lehnt eine §7Freundschaftsanfrage §7ab"));
			player.sendMessage(new TextComponent(
					"§8/§5friend §5remove §5[Name §5des §5Spielers]"
							+ ChatColor.RESET
							+ " §8- §7entfernt §7einen §7Freund"));
			player.sendMessage(new TextComponent(
					"§8/§5friend §5settings "
							+ ChatColor.RESET
							+ "§8- §7Einstellungen §7des §7Party- §7und §7Freundsystems"));
			player.sendMessage(new TextComponent(
					"§8/§5friend §5jump [Name des Freundes]" + ChatColor.RESET
							+ "§8- §7Zu §7einem §7Freund §7springen"));
			player.sendMessage(new TextComponent(
					"§8§m-----------------------------------------------"));
			return;
		}
		if (args[0].equalsIgnoreCase("list")) {
			try {
				list.auflisten(player, args, verbindung);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (args[0].equalsIgnoreCase("msg")
				|| args[0].equalsIgnoreCase("message")) {
			try {
				message.nachricht(player, args, verbindung);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (args[0].equalsIgnoreCase("add")) {
			try {
				add.hinzufuegen(player, args, verbindung);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (args[0].equalsIgnoreCase("accept")) {
			try {
				accept.akzeptieren(player, args, verbindung);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (args[0].equalsIgnoreCase("deny")) {
			try {
				deny.ablehnen(player, args, verbindung);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (args[0].equalsIgnoreCase("remove")) {
			try {
				remove.entfernen(player, args, verbindung);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (args[0].equalsIgnoreCase("einstellungen")
				|| args[0].equalsIgnoreCase("einstellung")
				|| args[0].equalsIgnoreCase("setting")
				|| args[0].equalsIgnoreCase("settings")) {
			try {
				einstellungen.settings(player, args, verbindung);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (args[0].equalsIgnoreCase("jump")) {
			try {
				jump.springen(player, args, verbindung);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		player.sendMessage(new TextComponent("§8[§5§lFriends§8]"
				+ ChatColor.RESET + " §7Das §7Kommando §7existiert §7nicht."));
	}
}
