package de.simonsator.partyandfriends.api.events.friends;

import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import net.md_5.bungee.api.plugin.Event;

public class FriendListChangedEvent extends Event {
	public final PAFPlayer FRIEND1;
	public final PAFPlayer FRIEND2;

	public FriendListChangedEvent(PAFPlayer pFriend1, PAFPlayer pFriend2) {
		FRIEND1 = pFriend1;
		FRIEND2 = pFriend2;
	}
}
