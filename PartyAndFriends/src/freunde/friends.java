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
	private String acceptAliasc;
	private String addAliasc;
	private String denyAliasc;
	private String settingsAliasc;
	private String jumpAliasc;
	private String listAliasc;
	private String removeAliasc;
	private String friendsAliasMsgc;
	private String language;

	public friends(mySql pVerbindung, String friendsPermission, String friendAllias, String friendsAliasMsg,
			String acceptAlias, String addAlias, String denyAlias, String settingsAlias, String jumpAlias,
			String listAlias, String removeAlias, String languageOvergive) {
		super("friend", friendsPermission, new String[] { "friends", friendAllias });
		verbindung = pVerbindung;
		friendsAliasMsgc = friendsAliasMsg;
		acceptAliasc = acceptAlias;
		addAliasc = addAlias;
		denyAliasc = denyAlias;
		settingsAliasc = settingsAlias;
		jumpAliasc = jumpAlias;
		listAliasc = listAlias;
		removeAliasc = removeAlias;
		language = languageOvergive;
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (!(commandSender instanceof ProxiedPlayer)) {
			commandSender.sendMessage(new TextComponent("§8[§5§lFriends§8]" + " Du must ein Spieler sein!"));
			return;
		}
		ProxiedPlayer player = (ProxiedPlayer) commandSender;
		if (args.length == 0) {
			player.sendMessage(new TextComponent(
					"§8§m-------------------" + ChatColor.RESET + "§8[§5§lFriends§8]§m-------------------"));
			if (language.equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent(
						"§8/§5friend list" + ChatColor.RESET + " §8- §7Lists §7all §7of §7your §7friends"));
				player.sendMessage(new TextComponent("§8/§5friend §5msg §5[name §5of §5the §5friend] §5[message]"
						+ ChatColor.RESET + " §8- §7send §7a §7friend §7a §7message"));
				player.sendMessage(new TextComponent("§8/§5friend §5add §5[name §5of §5the §5player]" + ChatColor.RESET
						+ " §8- §7Add §7a §7friend"));
				player.sendMessage(new TextComponent("§8/§5friend §5accept §5[name §5of §5the §5player]"
						+ ChatColor.RESET + " §8- §7accept §7a §7friend request"));
				player.sendMessage(new TextComponent("§8/§5friend §5deny §5[name §5of §5the §5player]" + ChatColor.RESET
						+ " §8- §7deny §7a §7friend §7request"));
				player.sendMessage(new TextComponent("§8/§5friend §5remove §5[name §5of §5the §5friend]"
						+ ChatColor.RESET + " §8- §7removes §7a §7friend"));
				player.sendMessage(new TextComponent("§8/§5friend §5settings " + ChatColor.RESET
						+ "§8- §7settings §7of §7the §7party- §7und §7friendsystem"));
				player.sendMessage(new TextComponent("§8/§5friend §5jump [name of the §5friend]" + ChatColor.RESET
						+ "§8- §7Jump §7to §7a §7friend"));

			} else {
				player.sendMessage(new TextComponent(
						"§8/§5friend list" + ChatColor.RESET + " §8- §7Listet §7deine §7Freunde §7auf"));
				player.sendMessage(new TextComponent("§8/§5friend §5msg §5[Name §5des §5Freundes] §5[Nachricht]"
						+ ChatColor.RESET + " §8- §7schieckt §7einem §7Freund §7eine §7Private Nachricht"));
				player.sendMessage(new TextComponent("§8/§5friend §5add §5[Name §5des §5Spielers]" + ChatColor.RESET
						+ " §8- §7Fügt §7einen §7Freund §7hinzu"));
				player.sendMessage(new TextComponent("§8/§5friend §5accept §5[Name §5des §5Spielers]" + ChatColor.RESET
						+ " §8- §7akzeptiert §7eine §7Freundschaftsanfrage"));
				player.sendMessage(new TextComponent("§8/§5friend §5deny §5[Name §5des §5Spielers]" + ChatColor.RESET
						+ " §8- §7Lehnt eine §7Freundschaftsanfrage §7ab"));
				player.sendMessage(new TextComponent("§8/§5friend §5remove §5[Name §5des §5Spielers]" + ChatColor.RESET
						+ " §8- §7entfernt §7einen §7Freund"));
				player.sendMessage(new TextComponent("§8/§5friend §5settings " + ChatColor.RESET
						+ "§8- §7Einstellungen §7des §7Party- §7und §7Freundsystems"));
				player.sendMessage(new TextComponent("§8/§5friend §5jump [Name des Freundes]" + ChatColor.RESET
						+ "§8- §7Zu §7einem §7Freund §7springen"));
			}
			player.sendMessage(new TextComponent("§8§m-----------------------------------------------"));
			return;
		}
		if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase(listAliasc)) {
			try {
				list.auflisten(player, args, verbindung, language);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (args[0].equalsIgnoreCase("msg") || args[0].equalsIgnoreCase("message")
				|| args[0].equalsIgnoreCase(friendsAliasMsgc)) {
			try {
				message.nachricht(player, args, verbindung, language);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase(addAliasc)) {
			try {
				add.hinzufuegen(player, args, verbindung, language);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase(acceptAliasc)) {
			try {
				accept.akzeptieren(player, args, verbindung, language);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (args[0].equalsIgnoreCase("deny") || args[0].equalsIgnoreCase(denyAliasc)) {
			try {
				deny.ablehnen(player, args, verbindung, language);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase(removeAliasc)) {
			try {
				remove.entfernen(player, args, verbindung, language);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (args[0].equalsIgnoreCase("einstellungen") || args[0].equalsIgnoreCase("einstellung")
				|| args[0].equalsIgnoreCase("setting") || args[0].equalsIgnoreCase("settings")
				|| args[0].equalsIgnoreCase(settingsAliasc)) {
			try {
				einstellungen.settings(player, args, verbindung, language);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (args[0].equalsIgnoreCase("jump") || args[0].equalsIgnoreCase(jumpAliasc)) {
			try {
				jump.springen(player, args, verbindung, language);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		if (language.equalsIgnoreCase("english")) {
			player.sendMessage(
					new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET + " §The §7Command §7doesn´t §7exist."));
		} else {
			player.sendMessage(new TextComponent(
					"§8[§5§lFriends§8]" + ChatColor.RESET + " §7Das §7Kommando §7existiert §7nicht."));
		}
	}
}
