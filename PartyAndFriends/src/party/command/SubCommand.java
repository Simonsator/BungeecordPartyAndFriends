package party.command;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public abstract class SubCommand {

	private String message, usage;
	private String[] aliases;

	public SubCommand(String message, String usage, String[] aliases) {
		this.message = message;
		this.usage = usage;
		this.aliases = aliases;
	}

	public abstract void onCommand(ProxiedPlayer p, String[] agrs);

	public final String getMessange() {
		return this.message;
	}

	public final String getUsage() {
		return this.usage;
	}

	public final String[] getAliases() {
		return this.aliases;
	}

}
