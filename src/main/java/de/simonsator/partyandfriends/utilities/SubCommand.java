package de.simonsator.partyandfriends.utilities;

import java.util.ArrayList;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public abstract class SubCommand {
	protected ArrayList<String> commands = new ArrayList<>();
	private String help;
	private int priority;

	public SubCommand(String[] pCommands, int pPriority, String pHelp) {
		for (String command : pCommands) {
			commands.add(command.toLowerCase());
		}
		help = pHelp;
		priority = pPriority;
	}

	public boolean isApplicable(String pCommand) {
		pCommand = pCommand.toLowerCase();
		if (commands.contains(pCommand))
			return true;
		return false;
	}

	public abstract void onCommand(ProxiedPlayer pPlayer, String[] args);

	public String getHelp() {
		return help;
	}

	public int getPriority() {
		return priority;
	}
}
