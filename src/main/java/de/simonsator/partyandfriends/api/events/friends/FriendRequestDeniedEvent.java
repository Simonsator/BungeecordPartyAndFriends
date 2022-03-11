package de.simonsator.partyandfriends.api.events.friends;

import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import net.md_5.bungee.api.plugin.Event;

public class FriendRequestDeniedEvent extends Event {
	public final PAFPlayer PLAYER;
	public final PAFPlayer FRIEND_REQUEST_SENDER;

	public FriendRequestDeniedEvent(PAFPlayer pPlayer, PAFPlayer pRequestSender) {
		PLAYER = pPlayer;
		FRIEND_REQUEST_SENDER = pRequestSender;
	}

}
