package de.simonsator.partyandfriends.api.events.communication.spigot;

import com.google.gson.JsonObject;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import net.md_5.bungee.api.plugin.Event;

/**
 * @author simonbrungs
 * @version 1.0.0 02.12.16
 */
public class SendDataFriendEvent extends Event {
	private final boolean IS_ONLINE;
	private JsonObject friend;
	private PAFPlayer pafFriend;
	private OnlinePAFPlayer caller;

	public SendDataFriendEvent(boolean pIsOnline, JsonObject pFriend, PAFPlayer pPAFFriend, OnlinePAFPlayer pCaller) {
		this.IS_ONLINE = pIsOnline;
		friend = pFriend;
		pafFriend = pPAFFriend;
		caller = pCaller;
	}

	public boolean isOnline() {
		return IS_ONLINE;
	}

	public void addProperty(String property, String value) {
		friend.addProperty(property, value);
	}

	public void addProperty(String property, Number value) {
		friend.addProperty(property, value);
	}

	public void addProperty(String property, Boolean value) {
		friend.addProperty(property, value);
	}

	public void addProperty(String property, Character value) {
		friend.addProperty(property, value);
	}

	public PAFPlayer getPAFFriend() {
		return pafFriend;
	}

	public OnlinePAFPlayer getCaller() {
		return caller;
	}
}
