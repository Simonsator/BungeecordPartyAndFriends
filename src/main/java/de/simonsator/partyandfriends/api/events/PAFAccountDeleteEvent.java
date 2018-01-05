package de.simonsator.partyandfriends.api.events;

import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

/**
 * @author simonbrungs
 * @version 1.0.0 30.05.17
 */
public class PAFAccountDeleteEvent extends Event implements Cancellable {
	private final PAFPlayer ACCOUNT_TO_DELETE;
	private boolean isCancelled = false;

	public PAFAccountDeleteEvent(PAFPlayer pPlayer) {
		ACCOUNT_TO_DELETE = pPlayer;
	}

	public PAFPlayer getAccountToDelete() {
		return ACCOUNT_TO_DELETE;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean b) {
		isCancelled = b;
	}
}
