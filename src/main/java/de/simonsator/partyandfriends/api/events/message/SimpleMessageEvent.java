package de.simonsator.partyandfriends.api.events.message;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

/**
 * @author Simonsator
 * @version 1.0.0 16.11.16
 */
public abstract class SimpleMessageEvent extends Event implements Cancellable {
	private final OnlinePAFPlayer SENDER;
	private String message;
	private boolean isCancelled = false;

	protected SimpleMessageEvent(OnlinePAFPlayer pSender, String pMessage) {
		SENDER = pSender;
		message = pMessage;
	}

	public OnlinePAFPlayer getSender() {
		return SENDER;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String pMessage) {
		this.message = pMessage;
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
