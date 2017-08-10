package de.simonsator.partyandfriends.main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * @author simonbrungs
 * @version 1.0.0 07.08.17
 */
public class BootErrorCommand extends Command {
	public BootErrorCommand(String name, String permission, String... aliases) {
		super(name, permission, aliases);
	}

	public BootErrorCommand(String[] names) {
		super(names[0], "", names);
	}

	@Override
	public void execute(CommandSender pSender, String[] args) {
		pSender.sendMessage("§cParty and Friends was either not able to connect to the MySQL database or to login into the MySQL database. " +
				"Please correct your MySQL data in the config.yml. If you need further help contact Simonsator via Skype (live:00pflaume), PM him (https://www.spigotmc.org/conversations/add?to=simonsator ) or write an email to him (support@simonsator.de). Please don't forget to send him the Proxy.Log.0 file (bungeecord log file). Also please don't write a bad review without giving him 24 hours time to fix the problem.");
	}
}
