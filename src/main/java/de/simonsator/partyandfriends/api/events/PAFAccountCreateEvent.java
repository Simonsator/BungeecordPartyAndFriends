package de.simonsator.partyandfriends.api.events;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import net.md_5.bungee.api.plugin.Event;

public class PAFAccountCreateEvent extends Event {
	private final PAFPlayer CREATED_ACCOUNT;

	public PAFAccountCreateEvent(OnlinePAFPlayer pPlayer) {
		CREATED_ACCOUNT = pPlayer;
	}

	public PAFPlayer getCreatedAccount() {
		return CREATED_ACCOUNT;
	}
}
