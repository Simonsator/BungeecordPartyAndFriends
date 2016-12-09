package de.simonsator.partyandfriends.api.events.command;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.utilities.SubCommand;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

/**
 * @author simonbrungs
 * @version 1.0.0 09.12.16
 */
public abstract class PAFSubCommandEvent<T extends SubCommand> extends Event implements Cancellable {
	private final OnlinePAFPlayer EXECUTOR;
	private final String[] ARGS;
	private final T CALLER;
	private boolean cancelled = false;

	public PAFSubCommandEvent(OnlinePAFPlayer pExecutor, String[] args, T pCaller) {
		EXECUTOR = pExecutor;
		ARGS = args;
		CALLER = pCaller;
	}

	public OnlinePAFPlayer getExecutor() {
		return EXECUTOR;
	}

	public String[] getArgs() {
		return ARGS;
	}

	public T getCaller() {
		return CALLER;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean b) {
		cancelled = b;
	}
}
