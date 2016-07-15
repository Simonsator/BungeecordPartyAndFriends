package de.simonsator.partyandfriends.utilities;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;

public abstract class SubCommand implements Comparable<SubCommand> {
	private final ArrayList<String> commands = new ArrayList<>();
	public final TextComponent HELP;
	private final int PRIORITY;

	protected SubCommand(String[] pCommands, int pPriority, TextComponent pHelp) {
		HELP = pHelp;
		PRIORITY = pPriority;
		for (String command : pCommands) {
			commands.add(command.toLowerCase());
		}
	}

	public boolean isApplicable(String pCommand) {
		return commands.contains(pCommand.toLowerCase());
	}

	protected void sendError(OnlinePAFPlayer pPlayer, TextComponent pMessage) {
		pPlayer.sendMessage(pMessage);
		pPlayer.sendMessage(HELP);
	}

	public abstract void onCommand(OnlinePAFPlayer pPlayer, String[] args);


	@Override
	public int compareTo(SubCommand o) {
		return ((Integer) PRIORITY).compareTo(o.PRIORITY);
	}
}
