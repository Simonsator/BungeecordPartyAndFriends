package de.simonsator.partyandfriends.api.events.message;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import net.md_5.bungee.api.plugin.Event;

/**
 * @author simonbrungs
 * @version 1.0.0 16.11.16
 */
public abstract class SimpleMessageEvent extends Event {
	private final OnlinePAFPlayer SENDER;
	private final String MESSAGE;

	protected SimpleMessageEvent(OnlinePAFPlayer pSender, String pMessage) {
		SENDER = pSender;
		MESSAGE = pMessage;
	}

	public OnlinePAFPlayer getSender() {
		return SENDER;
	}

	public String getMessage() {
		return MESSAGE;
	}
}
