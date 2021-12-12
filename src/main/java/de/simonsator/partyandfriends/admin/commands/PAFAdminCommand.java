package de.simonsator.partyandfriends.admin.commands;

import de.simonsator.partyandfriends.admin.commands.subcommands.DeletePlayerAdminSubCommand;
import de.simonsator.partyandfriends.api.AdminCommand;
import de.simonsator.partyandfriends.api.AdminSubCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class PAFAdminCommand extends AdminCommand<AdminSubCommand> {
	private final String PREFIX = "§8[§5PAFAdmin§8]§r§7 ";
	private final TextComponent COMMAND_MESSAGE_BEGIN = new TextComponent(TextComponent.fromLegacyText(
			"§8§m--------------------§8[§5§lPAFADMIN§8]§m--------------------"));
	private final TextComponent COMMAND_MESSAGE_END = new TextComponent(TextComponent.fromLegacyText(
			"§8§m-----------------------------------------------------"));
	private final TextComponent SUB_COMMAND_DOES_NOT_EXIST = new TextComponent(TextComponent.fromLegacyText(PREFIX
			+ "§cThe command does not exist."));

	public PAFAdminCommand(String... aliases) {
		super(aliases);
		addSubcommand(new DeletePlayerAdminSubCommand(PREFIX));
	}

	@Override
	protected void executeCommand(CommandSender commandSender, String[] args) {
		if (args.length == 0) {
			commandSender.sendMessage(COMMAND_MESSAGE_BEGIN);
			for (AdminSubCommand command : getSubCommands())
				commandSender.sendMessage(command.HELP);
			commandSender.sendMessage(COMMAND_MESSAGE_END);
			return;
		}
		for (AdminSubCommand command : getSubCommands())
			if (command.isApplicable(args[0])) {
				command.onCommand(commandSender, args);
				return;
			}
		commandSender.sendMessage(SUB_COMMAND_DOES_NOT_EXIST);
	}
}
