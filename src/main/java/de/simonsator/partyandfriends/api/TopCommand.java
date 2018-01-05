package de.simonsator.partyandfriends.api;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.utilities.SubCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a TopCommand like /friend, /party or /command.
 * The top command decides if and which subcommand should be executed.
 * Also if no subcommand should be executed it outputs the help of the subcommands.
 *
 * @param <T> The type of subcommands this class should use
 *            {@link de.simonsator.partyandfriends.utilities.SubCommand}
 */
public abstract class TopCommand<T extends SubCommand> extends Command implements Listener {
	/**
	 * Contains all subcommands of the TopCommand
	 */
	protected final ArrayList<T> subCommands = new ArrayList<>();
	/**
	 * The prefix which gets returned by the method {@link #getPrefix()}
	 */
	private final String PREFIX;

	/**
	 * @param pCommandNames The command name and the different aliases of this command.
	 *                      By these names the method can be called.
	 * @param pPermission   The permission which is needed to execute this command.
	 *                      If it is blank no permission is needed to execute this command.
	 * @param pPrefix       The prefix which should be used by all subcommands.
	 *                      The prefix gets returned by the method {@link #getPrefix()}.
	 */
	protected TopCommand(String[] pCommandNames, String pPermission, String pPrefix) {
		super(pCommandNames[0], pPermission, pCommandNames);
		PREFIX = pPrefix;
	}

	/**
	 * @param pCommandSender The sender of the command.
	 * @return Returns true if the sender of the command is a player. If not it returns false and reloads the plugin.
	 */
	public static boolean isPlayer(CommandSender pCommandSender) {
		if (!(pCommandSender instanceof ProxiedPlayer)) {
			Main.getInstance().reload();
			pCommandSender.sendMessage((Friends.getInstance().getPrefix() + "Party and Friends was reloaded. " +
					"Anyway it is recommended to restart the bungeecord completely because it can be that " +
					"not all features were reloaded/were right reloaded."));
			return false;
		}
		return true;
	}

	/**
	 * Checks if the sender is a player with the method {@link #isPlayer(CommandSender)}.
	 * If so it executes the method {@link #onCommand(OnlinePAFPlayer, String[])}.
	 * If not it reloads the plugin.
	 *
	 * @param pCommandSender The sender of the command.
	 * @param args           The arguments which are given by the player.
	 */
	@Override
	public void execute(final CommandSender pCommandSender, final String[] args) {
		if (isPlayer(pCommandSender))
			ProxyServer.getInstance().getScheduler().runAsync(Main.getInstance(), new Runnable() {
				@Override
				public void run() {
					if (!isDisabledServer((ProxiedPlayer) pCommandSender))
						onCommand(PAFPlayerManager.getInstance().getPlayer((ProxiedPlayer) pCommandSender), args);
				}
			});
	}

	private boolean isDisabledServer(ProxiedPlayer pPlayer) {
		if (Main.getInstance().getConfig().getStringList("General.DisabledServers").contains(pPlayer.getServer().getInfo().getName())) {
			pPlayer.sendMessage((Main.getInstance().getMessages().getString("General.DisabledServer")));
			return true;
		}
		return false;
	}

	/**
	 * Decides if a subcommand should be called and if which one.
	 * If no subcommand should be called it shows the help of the commands.
	 *
	 * @param pPlayer The player who executed the command.
	 * @param args    The arguments which were given by the player.
	 */
	protected abstract void onCommand(OnlinePAFPlayer pPlayer, String args[]);

	/**
	 * Adds a subcommand to the {@link #subCommands} list
	 * and after the subcommand was added it sorts the list again.
	 *
	 * @param pCommand The subcommand which should be added.
	 */
	public void addCommand(T pCommand) {
		subCommands.add(pCommand);
		sort();
	}

	/**
	 * Sorts the subcommands by there priority.
	 * The subcommand with the lowest priority will be the first one in the list
	 * and the one with the highest priority the last one after the list was sorted.
	 */
	protected void sort() {
		Collections.sort(subCommands);
	}

	/**
	 * @param pClass The type of the subcommand object which is searched
	 * @return Returns a subcommand of the {@link #subCommands} list, which has an equal type.
	 */
	public T getSubCommand(Class<? extends SubCommand> pClass) {
		for (T subCommand : subCommands)
			if (subCommand.getClass().equals(pClass))
				return subCommand;
		return null;
	}

	/**
	 * @return Returns the prefix which should be used by all subcommands.
	 */
	public String getPrefix() {
		return PREFIX;
	}

	public void tabComplete(TabCompleteEvent pEvent) {
		
	}

	private void subCommandComplete(TabCompleteEvent pEvent) {
		String partialSubCommand = pEvent.getCursor().toLowerCase();
		if (!isThisCommand(partialSubCommand))
			return;
		int lastSpaceIndex = partialSubCommand.lastIndexOf(' ');
		if (lastSpaceIndex >= 0)
			partialSubCommand = partialSubCommand.substring(lastSpaceIndex + 1);
		for (T subCommand : subCommands) {
			for (String commandName : subCommand.getCommandNames())
				if (commandName.toLowerCase().startsWith(partialSubCommand))
					pEvent.getSuggestions().add(commandName);
		}
	}

	private boolean isThisCommand(String cursor) {
		return cursor.startsWith("/" + getName().toLowerCase());
	}
}
