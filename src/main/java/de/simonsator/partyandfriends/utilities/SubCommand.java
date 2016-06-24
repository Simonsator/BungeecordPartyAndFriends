package de.simonsator.partyandfriends.utilities;

import de.simonsator.partyandfriends.pafplayers.OnlinePAFPlayer;

import java.util.ArrayList;

public abstract class SubCommand implements Comparable<SubCommand> {
	private ArrayList<String> commands = new ArrayList<>();
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
		return commands.contains(pCommand.toLowerCase());
	}

	public abstract void onCommand(OnlinePAFPlayer pPlayer, String[] args);

	public String getHelp() {
		return help;
	}

	public int getPriority() {
		return priority;
	}

	@Override
	public int compareTo(SubCommand o) {
		return ((Integer) getPriority()).compareTo(o.getPriority());
	}
}
