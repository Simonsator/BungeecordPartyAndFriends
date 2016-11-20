package de.simonsator.partyandfriends.api;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.utilities.SubCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.Collections;

import static de.simonsator.partyandfriends.main.Main.getPlayerManager;

public abstract class TopCommand<T extends SubCommand> extends Command {
	protected final ArrayList<T> subCommands = new ArrayList<>();
	private final String PREFIX;

	protected TopCommand(String[] pCommandNames, String pPermission, String pPrefix) {
		super(pCommandNames[0], pPermission, pCommandNames);
		PREFIX = pPrefix;
	}

	public static boolean isPlayer(CommandSender pCommandSender) {
		if (!(pCommandSender instanceof ProxiedPlayer)) {
			Main.getInstance().reload();
			pCommandSender.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + "Party and Friends was reloaded. " +
					"Anyway it is recommended to restart the bungeecord completely because it can be that " +
					"not all features were reloaded/were right reloaded."));
			return false;
		}
		return true;
	}

	@Override
	public void execute(CommandSender pCommandSender, String[] args) {
		if (isPlayer(pCommandSender))
			onCommand(getPlayerManager().getPlayer((ProxiedPlayer) pCommandSender), args);
	}

	protected abstract void onCommand(OnlinePAFPlayer pPlayer, String args[]);

	public void addCommand(T pCommand) {
		subCommands.add(pCommand);
		sort();
	}

	protected void sort() {
		Collections.sort(subCommands);
	}

	public SubCommand getSubCommand(Class<? extends SubCommand> aClass) {
		for (SubCommand subCommand : subCommands)
			if (subCommand.getClass().equals(aClass))
				return subCommand;
		return null;
	}

	public String getPrefix() {
		return PREFIX;
	}
}
