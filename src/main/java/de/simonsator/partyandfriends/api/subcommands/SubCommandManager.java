package de.simonsator.partyandfriends.api.subcommands;

import de.simonsator.partyandfriends.utilities.SubCommand;

import java.util.*;
import java.util.function.Consumer;

public class SubCommandManager<T extends SubCommand> {
	private final ArrayList<T> subCommandList = new ArrayList<>();
	private final Map<String, T> subCommandMap = new HashMap<>();

	public SubCommandManager(boolean pTabComplete) {

	}

	public void addSubCommands(List<T> pCommands) {
		pCommands.sort(T::compareTo);
		for (T command : pCommands)
			addSubCommandNoSort(command);
		sort();
	}

	public void addSubCommand(T pCommand) {
		addSubCommandNoSort(pCommand);
		sort();
	}

	private void addSubCommandNoSort(T pCommand) {
		subCommandList.add(pCommand);
		for (String commandNames : pCommand.getCommandNames()) {
			subCommandMap.put(commandNames, pCommand);
		}
	}

	public void removeSubCommand(T pCommand) {
		subCommandList.remove(pCommand);
		for (String commandNames : pCommand.getCommandNames()) {
			subCommandMap.remove(commandNames.toLowerCase());
		}
	}

	/**
	 * Sorts the subcommands by there priority.
	 * The subcommand with the lowest priority will be the first one in the list
	 * and the one with the highest priority the last one after the list was sorted.
	 */
	public void sort() {
		Collections.sort(subCommandList);
	}

	/**
	 * @param pClass The type of the subcommand object which is searched
	 * @return Returns a subcommand which was added, which has an equal type.
	 */
	public T getSubCommand(Class<? extends SubCommand> pClass) {
		for (T subCommand : subCommandList)
			if (subCommand.getClass().equals(pClass))
				return subCommand;
		return null;
	}

	public void forEach(Consumer<T> pConsumer) {
		subCommandList.forEach(pConsumer);
	}

	public T getSubCommand(String pCommandName) {
		return subCommandMap.get(pCommandName.toLowerCase());
	}

	public List<String> tabComplete(String pPrefix) {
		return Collections.emptyList();
	}
}
