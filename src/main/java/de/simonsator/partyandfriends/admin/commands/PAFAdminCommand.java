package de.simonsator.partyandfriends.admin.commands;

import de.simonsator.partyandfriends.admin.commands.subcommands.DeletePlayerAdminSubCommand;
import de.simonsator.partyandfriends.api.AdminCommand;
import de.simonsator.partyandfriends.api.AdminSubCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class PAFAdminCommand extends AdminCommand<AdminSubCommand> {
	private final String PREFIX = "§8[§5PAFAdmin§8]§r§7 ";

	public PAFAdminCommand(String... aliases) {
		super(aliases);
		addSubcommand(new DeletePlayerAdminSubCommand(PREFIX));
	}

	@Override
	protected void executeCommand(CommandSender commandSender, String[] args) {
		if (args.length == 0) {
			commandSender.sendMessage(new TextComponent("§8§m--------------------§8[§5§lPAFADMIN§8]§m--------------------"));
			for (AdminSubCommand command : getSubCommands())
				commandSender.sendMessage(new TextComponent(command.HELP));
			commandSender.sendMessage(new TextComponent("§8§m-----------------------------------------------------"));
			return;
		}
		for (AdminSubCommand command : getSubCommands())
			if (command.isApplicable(args[0])) {
				command.onCommand(commandSender, args);
				return;
			}
		commandSender.sendMessage(new TextComponent(PREFIX
				+ "§cThe command does not exist."));
	}
}
