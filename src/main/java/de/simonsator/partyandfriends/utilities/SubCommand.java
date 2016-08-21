package de.simonsator.partyandfriends.utilities;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;

public abstract class SubCommand implements Comparable<SubCommand> {
	private final ArrayList<String> commands = new ArrayList<>();
	public final TextComponent HELP;
	private final int PRIORITY;
	protected final String PREFIX;

	protected SubCommand(String[] pCommands, int pPriority, TextComponent pHelp, String pPrefix) {
		HELP = pHelp;
		PRIORITY = pPriority;
		PREFIX = pPrefix;
		for (String command : pCommands)
			commands.add(command.toLowerCase());
	}

	protected SubCommand(String[] pCommands, int pPriority, String pHelp, String pPrefix) {
		HELP = new TextComponent(pHelp);
		PRIORITY = pPriority;
		PREFIX = pPrefix;
		for (String command : pCommands)
			commands.add(command.toLowerCase());
	}

	public boolean isApplicable(String pCommand) {
		return commands.contains(pCommand.toLowerCase());
	}

	protected void sendError(OnlinePAFPlayer pPlayer, TextComponent pMessage) {
		pPlayer.sendMessage(pMessage);
		pPlayer.sendMessage(HELP);
	}

	protected void sendError(OnlinePAFPlayer pPlayer, String pIdentifier) {
		sendError(pPlayer, new TextComponent(PREFIX + Main.getInstance().getMessagesYml().getString(pIdentifier)));
	}

	public abstract void onCommand(OnlinePAFPlayer pPlayer, String[] args);


	@Override
	public int compareTo(SubCommand o) {
		return ((Integer) PRIORITY).compareTo(o.PRIORITY);
	}
}
