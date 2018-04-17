package de.simonsator.partyandfriends.utilities;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public abstract class SubCommand implements Comparable<SubCommand> {
	public final TextComponent HELP;
	protected final String PREFIX;
	private final ArrayList<String> commands;
	private final int PRIORITY;
	private final String PERMISSION;

	protected SubCommand(String[] pCommands, int pPriority, TextComponent pHelp, String pPrefix) {
		HELP = pHelp;
		PRIORITY = pPriority;
		PREFIX = pPrefix;
		PERMISSION = null;
		commands = new ArrayList<>(pCommands.length);
		for (String command : pCommands)
			commands.add(command.toLowerCase());
	}

	protected SubCommand(String[] pCommands, int pPriority, String pHelp, String pPrefix) {
		HELP = new TextComponent(pHelp);
		PRIORITY = pPriority;
		PREFIX = pPrefix;
		PERMISSION = null;
		commands = new ArrayList<>(pCommands.length);
		for (String command : pCommands)
			commands.add(command.toLowerCase());
	}

	/**
	 * @param pCommands   The names of this command
	 * @param pPriority   The priority of this command (the command with the lowest priority is listed first)
	 * @param pHelp       The help message. It will be outprinted when the command is listed and when {@link #printOutHelp(OnlinePAFPlayer, String)} printOutHelp} is called.
	 * @param pPrefix     The prefix of this command (e.g. [Friends] or [Party])
	 * @param pPermission The permission which is needed to execute this command. If no Permission is needed set it to null.
	 */
	protected SubCommand(List<String> pCommands, int pPriority, String pHelp, String pPrefix, String pPermission) {
		HELP = new TextComponent(pHelp);
		PRIORITY = pPriority;
		PREFIX = pPrefix;
		PERMISSION = pPermission;
		commands = new ArrayList<>(pCommands.size());
		for (String command : pCommands)
			commands.add(command.toLowerCase());
	}

	public boolean isApplicable(String pCommand) {
		return commands.contains(pCommand.toLowerCase());
	}

	public boolean isApplicable(OnlinePAFPlayer pPlayer, String pCommand) {
		return isApplicable(pCommand) && hasPermission(pPlayer);
	}

	public void sendError(OnlinePAFPlayer pPlayer, TextComponent pMessage) {
		pPlayer.sendMessage(pMessage);
		pPlayer.sendMessage(HELP);
	}

	public void sendError(OnlinePAFPlayer pPlayer, String pIdentifier) {
		sendError(pPlayer, new TextComponent(PREFIX + Main.getInstance().getMessages().getString(pIdentifier)));
	}

	public abstract void onCommand(OnlinePAFPlayer pPlayer, String[] args);

	@Override
	public int compareTo(SubCommand o) {
		return Integer.compare(PRIORITY, o.PRIORITY);
	}

	public String getCommandName() {
		return commands.get(0);
	}

	public ArrayList<String> getCommandNames() {
		return commands;
	}

	public void printOutHelp(OnlinePAFPlayer pPlayer, String pCommandName) {
		pPlayer.sendMessage(HELP.getText());
	}

	public boolean hasPermission(OnlinePAFPlayer pPlayer) {
		return pPlayer.hasPermission(PERMISSION);
	}
}
