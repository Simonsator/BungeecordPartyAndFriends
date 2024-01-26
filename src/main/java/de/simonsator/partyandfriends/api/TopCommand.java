package de.simonsator.partyandfriends.api;

import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.subcommands.SubCommandManager;
import de.simonsator.partyandfriends.api.system.WaitForTasksToFinish;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.utilities.SubCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Represents a TopCommand like /friend, /party or /command.
 * The top command decides if and which subcommand should be executed.
 * Also if no subcommand should be executed it outputs the help of the subcommands.
 *
 * @param <T> The type of subcommands this class should use
 *            {@link de.simonsator.partyandfriends.utilities.SubCommand}
 */
public abstract class TopCommand<T extends SubCommand> extends Command implements TabExecutor {
	private final SubCommandManager<T> SUB_COMMAND_MANAGER;
	/**
	 * The prefix which gets returned by the method {@link #getPrefix()}
	 */
	private final String PREFIX;
	private final Set<UUID> mutex = new HashSet<>();
	private final WaitForTasksToFinish TASK_COUNTER = new WaitForTasksToFinish();

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
		SUB_COMMAND_MANAGER = new SubCommandManager<>(false);
	}

	/**
	 * @param pCommandSender The sender of the command.
	 * @return Returns true if the sender of the command is a player. If not it returns false and reloads the plugin.
	 */
	public static boolean isPlayer(CommandSender pCommandSender) {
		if (!(pCommandSender instanceof ProxiedPlayer)) {
			Main.getInstance().reload();
			pCommandSender.sendMessage(new TextComponent(TextComponent.fromLegacyText(Friends.getInstance().getPrefix() + "Party and Friends was reloaded. " +
					"Anyway it is recommended to restart the bungeecord completely because it can be that " +
					"not all features were reloaded/were right reloaded.")));
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
		if (isPlayer(pCommandSender)) {
			ProxiedPlayer player = (ProxiedPlayer) pCommandSender;
			UUID uuid = player.getUniqueId();
			if (mutex.contains(uuid))
				return;
			mutex.add(uuid);
			BukkitBungeeAdapter.getInstance().runAsync(Main.getInstance(), () -> {
				try {
					TASK_COUNTER.taskStarts();
					if (!isDisabledServer(player))
						onCommand(PAFPlayerManager.getInstance().getPlayer((ProxiedPlayer) pCommandSender), args);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					mutex.remove(uuid);
					TASK_COUNTER.taskFinished();
				}
			});
		}
	}

	private boolean isDisabledServer(ProxiedPlayer pPlayer) {
		if (Main.getInstance().getGeneralConfig().getStringList("General.DisabledServers").contains(pPlayer.getServer().getInfo().getName())) {
			pPlayer.sendMessage(new TextComponent(TextComponent.fromLegacyText(Main.getInstance().getMessages().getString("General.DisabledServer"))));
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
	protected abstract void onCommand(OnlinePAFPlayer pPlayer, String[] args);

	/**
	 * Adds a subcommand to this top command and after the subcommand was added it sorts the list again.
	 *
	 * @param pCommand The subcommand which should be added.
	 */
	public void addCommand(T pCommand) {
		SUB_COMMAND_MANAGER.addSubCommand(pCommand);
	}

	/**
	 * Adds multiple subcommands to this top command and after all subcommands were added it sorts the list again.
	 *
	 * @param pCommands The subcommands which should be added.
	 */
	public void addCommands(List<T> pCommands) {
		SUB_COMMAND_MANAGER.addSubCommands(pCommands);
	}

	public void forEachSubCommand(Consumer<T> pConsumer) {
		SUB_COMMAND_MANAGER.forEach(pConsumer);
	}

	/**
	 * Sorts the subcommands by there priority.
	 * The subcommand with the lowest priority will be the first one in the list
	 * and the one with the highest priority the last one after the list was sorted.
	 */
	protected void sort() {
		SUB_COMMAND_MANAGER.sort();
	}

	/**
	 * @param pClass The type of the subcommand object which is searched
	 * @return Returns a subcommand which was added to this command, which has an equal type.
	 */
	public T getSubCommand(Class<? extends SubCommand> pClass) {
		return SUB_COMMAND_MANAGER.getSubCommand(pClass);
	}

	/**
	 * @param pCommandName The name of the command which is searched
	 * @return Returns a subcommand which was added to this command, which has the given name (case ignored).
	 */
	public T getSubCommand(String pCommandName) {
		return SUB_COMMAND_MANAGER.getSubCommand(pCommandName);
	}

	/**
	 * @return Returns the prefix which should be used by all subcommands.
	 */
	public String getPrefix() {
		return PREFIX;
	}

	@Deprecated
	public void tabComplete(TabCompleteEvent ignore) {
		// Method was replaced by the method onTabComplete(CommandSender, String[])
	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return onTabCompleteNoLimit(commandSender, strings).stream().limit(10).collect(Collectors.toList());
	}

	public List<String> onTabCompleteNoLimit(CommandSender commandSender, String[] strings) {
		return Collections.emptyList();
	}
}
