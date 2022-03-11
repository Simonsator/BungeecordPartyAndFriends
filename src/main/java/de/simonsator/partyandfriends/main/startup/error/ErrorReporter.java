package de.simonsator.partyandfriends.main.startup.error;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public interface ErrorReporter {
	default void reportError(CommandSender pSender, BootErrorType pBootErrorType) {
		switch (pBootErrorType) {
			case MYSQL_CONNECTION_PROBLEM:
				pSender.sendMessage(TextComponent.fromLegacyText("§cParty and Friends was either not able to connect to the MySQL database or to login into the MySQL database. " +
						"Please correct your MySQL data in the config.yml. If you need further help contact Simonsator via Discord (@Simonsator#5834), PM him (https://www.spigotmc.org/conversations/add?to=simonsator ) or write an email to him (support@simonsator.de). Please don't forget to send him the Proxy.Log.0 file (bungeecord log file). Also please don't write a bad review without giving him 24 hours time to fix the problem."));
				break;
			case TOO_OLD_VERSION:
				pSender.sendMessage(TextComponent.fromLegacyText("§cYour BungeeCord is too old to run Party and Friends correctly. Please update your bungeecord, by downloading the newest version from https://ci.md-5.net/job/BungeeCord/ or if you need 1.7 support download this modified version of BungeeCord https://github.com/HexagonMC/BungeeCord/releases"));
				break;
			case SHA_ENCRYPTED_PASSWORD:
				pSender.sendMessage(TextComponent.fromLegacyText("§cBungeecord does not support support SHA encrypted MySQL passwords till now, since it is using an old MySQL driver. Please use the command \"ALTER USER 'yourusername'@'localhost' IDENTIFIED WITH mysql_native_password BY 'youpassword';\" in MySQL to fix this issue. Or update your BungeeCord version."));
				break;
			case MISSING_PERMISSION_REFERENCE_COMMAND:
				pSender.sendMessage(TextComponent.fromLegacyText("§cYour MySQL user does not have the permission to execute the reference Command which is required to create the tables for the plugin. If you need further help contact Simonsator via via Discord (@Simonsator#5834), PM him (https://www.spigotmc.org/conversations/add?to=simonsator ) or write an email to him (support@simonsator.de). Please don't forget to send him the Proxy.Log.0 file (bungeecord log file). Also please don't write a bad review without giving him 24 hours time to fix the problem."));
				break;
			default:
				pSender.sendMessage(TextComponent.fromLegacyText("§cParty and Friends was not able to boot up correctly."));
				break;
		}
		pSender.sendMessage(TextComponent.fromLegacyText("§cIf you need further help contact Simonsator via Discord (@Simonsator#5834), PM him (https://www.spigotmc.org/conversations/add?to=simonsator) or write an email to him (support@simonsator.de). Please don't forget to send him the Proxy.Log.0 file (bungeecord log file)."));
	}
}
