package de.simonsator.partyandfriends.api;

import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;

public abstract class AdminCommand<T extends AdminSubCommand> extends Command {
	private final List<T> subCommands = new ArrayList<>();
	private final TextComponent MUST_BE_EXECUTED_BY_CONSOLE = new TextComponent(TextComponent.fromLegacyText(Main.getInstance().getMessages().getString("PAFAdmin.Command.MustBeExecutedByConsole")));

	public AdminCommand(String... aliases) {
		super(aliases[0], "dsioihusdugb", aliases);
	}

	protected List<T> getSubCommands() {
		return subCommands;
	}

	public void addSubcommand(T pSubCommand) {
		subCommands.add(pSubCommand);
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (commandSender instanceof ProxiedPlayer) {
			commandSender.sendMessage(MUST_BE_EXECUTED_BY_CONSOLE);
			return;
		}
		executeCommand(commandSender, args);
	}

	protected abstract void executeCommand(CommandSender commandSender, String[] args);
}
