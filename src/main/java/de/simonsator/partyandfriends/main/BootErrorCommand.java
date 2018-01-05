package de.simonsator.partyandfriends.main;

import de.simonsator.partyandfriends.main.startup.error.BootErrorType;
import de.simonsator.partyandfriends.main.startup.error.ErrorReporter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * @author simonbrungs
 * @version 1.0.0 07.08.17
 */
public class BootErrorCommand extends Command implements ErrorReporter {
	private final BootErrorType BOOT_ERROR_TYPE;

	public BootErrorCommand(String[] names) {
		super(names[0], "", names);
		BOOT_ERROR_TYPE = BootErrorType.MYSQL_CONNECTION_PROBLEM;
	}

	public BootErrorCommand(String[] names, BootErrorType pType) {
		super(names[0], "", names);
		BOOT_ERROR_TYPE = pType;
	}

	@Override
	public void execute(CommandSender pSender, String[] args) {
		reportError(pSender, BOOT_ERROR_TYPE);
	}
}
