package freunde.kommandos;

import java.sql.SQLException;

import mySql.mySql;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.Chat;

public class einstellungen {

	public static void settings(ProxiedPlayer player, String[] args, mySql verbindung, String language)
			throws SQLException {
		if (args.length > 1) {
			if (args[1].equalsIgnoreCase("party")) {
				int wertJetzt = verbindung.einstellungenSetzen(player, 0);
				if (wertJetzt == 0) {
					if (language.equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
								+ " §7Now §7you §7can §7get §7invited §7by §aevery §7player §7into §7his §7Party."));
					} else {
						player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
								+ " §7Du §7kannst §7jetzt §7von §ajedem §7Spieler §7in §7eine §7Party §7eingeladen §7werden"));
					}
				} else {
					if (language.equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
								+ " §7Now §7you §7can §7get §7invited §conly §7by §7by your friends §7into §7their §7Party."));
					} else {
						player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
								+ " §7Du §7kannst §7jetzt §cnur §7noch §7von §7deinen §7Freunden §7in §7eine §7Party §7eingeladen §7werden"));
					}
				}
				return;
			}
			if (args[1].equalsIgnoreCase("Freundschaftsanfragen") || args[1].equalsIgnoreCase("friendrequests")) {
				int wertJetzt = verbindung.einstellungenSetzen(player, 1);
				if (wertJetzt == 0) {
					if (language.equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
								+ " §7Now §7you §7are §cnot §7gone §7recive §7friend §7requests §7anymore"));
					} else {
						player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
								+ " §7Du §7kannst §7jetzt §ckeine §7Freundschaftsanfragen §7mehr §7erhalten"));
					}
				} else {
					if (language.equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
								+ " §7Now §7you §7are §agone §7recive §7friend §7requests §7from §7everyone"));
					} else {
						player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
								+ " §7Du §7kannst §7jetzt §7von §ajedem §7Freundschaftsanfragen §7erhalten"));
					}
				}
				return;
			}
		}
		int[] abgefragteEinstellung = verbindung.einstellungenAbfragen(player);
		if (abgefragteEinstellung[0] == 0) {
			if (language.equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
						+ " §7At §7the moment §7you §7are §cnot §7gone §7recive §7friend §7request"));
			} else {
				player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
						+ " §7Momentan §7können §7dir §ckeine §7Freundschaftsanfragen §7gesendet §7werden"));
			}

		} else {
			if (language.equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
						+ " §7At §7the moment §7you §7are §7gone §7recive §7friend §7requests §7from §aeveryone"));
			} else {
				player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
						+ " §7Momentan §7können §7dir §7von §ajedem §7Freundschaftsanfragen §7gesendet §7werden"));
			}
		}
		String jsoncode = "";
		String zuschreiben;
		String command;
		if (language.equalsIgnoreCase("english")) {
			zuschreiben = "§8[§5§lFriends§8]" + ChatColor.RESET
					+ " §7Change §7this §7setting §7with §6/friend §6settings §6friendrequests";
			command = "/friends settings friendrequest";
			jsoncode = "{'text':'" + zuschreiben + "', 'clickEvent':{'action':'run_command','value':'" + command
					+ "'},'hoverEvent':{'action':'show_text','value':'Click here to change this setting.'}}";
		} else {
			zuschreiben = "§8[§5§lFriends§8]" + ChatColor.RESET
					+ " §7Ändere §7diese §7Einstellung §7mit §6/friend §6settings §6Freundschaftsanfragen";
			command = "/friends settings Freundschaftsanfragen";
			jsoncode = "{'text':'" + zuschreiben + "', 'clickEvent':{'action':'run_command','value':'" + command
					+ "'},'hoverEvent':{'action':'show_text','value':'Hier klicken um die Einstellung zu ändern.'}}";
		}
		player.unsafe().sendPacket(new Chat(jsoncode));
		player.sendMessage(new TextComponent("§8§m-----------------------------------------------"));
		if (abgefragteEinstellung[1] == 0) {
			if (language.equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
						+ " §7At §7the moment §7you §7can §7get §7invited §7by §aevery §7player §7into §7his §7Party."));
			} else {
				player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
						+ " §7Momentan §7können §7dir §7Party §7Einladungen §7von §ajedem §7gesendet §7werden §7gesendet §7werden"));
			}
		} else {
			if (language.equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
						+ " §7At §7the moment §7you §7can §7get §7invited §aonly §7by §7by your friends §7into §7their §7Party."));
				zuschreiben = "§8[§5§lFriends§8]" + ChatColor.RESET
						+ " §7Change §7this §7setting §7with §6/friend §6settings §6Party";
				command = "/friends settings party";
				jsoncode = "{'text':'" + zuschreiben + "', 'clickEvent':{'action':'run_command','value':'" + command
						+ "'},'hoverEvent':{'action':'show_text','value':'Click here to change this setting.'}}";
			} else {
				player.sendMessage(new TextComponent("§8[§5§lFriends§8]" + ChatColor.RESET
						+ " §7Momentan §7können §7dir §cnur §7Party §7Einladungen §7von §7Freunden §7gesendet §7werden"));
				zuschreiben = "§8[§5§lFriends§8]" + ChatColor.RESET
						+ " §7Ändere §7diese §7Einstellung §7mit §6/friend §6settings §6Party";
				command = "/friends settings party";
				jsoncode = "{'text':'" + zuschreiben + "', 'clickEvent':{'action':'run_command','value':'" + command
						+ "'},'hoverEvent':{'action':'show_text','value':'Hier klicken um die Einstellung zu ändern.'}}";
			}
		}
		player.unsafe().sendPacket(new Chat(jsoncode));
	}
}
