package de.simonsator.partyandfriends.api.events;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import net.md_5.bungee.api.plugin.Event;

public class PAFAccountCreateEvent extends Event {
	private final OnlinePAFPlayer CREATED_ACCOUNT;

	public PAFAccountCreateEvent(OnlinePAFPlayer pPlayer) {
		CREATED_ACCOUNT = pPlayer;
	}

	public OnlinePAFPlayer getCreatedAccount() {
		return CREATED_ACCOUNT;
	}
}
