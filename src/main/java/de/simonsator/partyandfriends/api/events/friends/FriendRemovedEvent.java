package de.simonsator.partyandfriends.api.events.friends;

import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;

public class FriendRemovedEvent extends FriendListChangedEvent {
	public FriendRemovedEvent(PAFPlayer pFriend1, PAFPlayer pFriend2) {
		super(pFriend1, pFriend2);
	}
}
