package de.simonsator.partyandfriends.api.events.message;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;

/**
 * @author simonbrungs
 * @version 1.0.0 17.11.16
 */
public class FriendOnlineMessageEvent extends FriendMessageEvent {
	public FriendOnlineMessageEvent(OnlinePAFPlayer receiver, OnlinePAFPlayer sender, String message) {
		super(receiver, sender, message);
	}

	@Override
	public OnlinePAFPlayer getReceiver() {
		return (OnlinePAFPlayer) super.getReceiver();
	}
}
