package de.simonsator.partyandfriends.api.events.communication.spigot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import net.md_5.bungee.api.plugin.Event;

/**
 * @author simonbrungs
 * @version 1.0.0 19.12.16
 */
public class SendSettingsDataEvent extends Event {
	private final OnlinePAFPlayer PLAYER;
	private JsonArray settings;

	public SendSettingsDataEvent(OnlinePAFPlayer pPlayer, JsonArray pJArray) {
		PLAYER = pPlayer;
		settings = pJArray;
	}

	public OnlinePAFPlayer getPlayer() {
		return PLAYER;
	}

	public void addSetting(int pID, int pWorth) {
		JsonObject jObj = new JsonObject();
		jObj.addProperty("id", pID);
		jObj.addProperty("worth", pWorth);
		settings.add(jObj);
	}
}
