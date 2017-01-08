package de.simonsator.partyandfriends.api.events;

import de.simonsator.partyandfriends.api.pafplayers.DisplayNameProvider;
import net.md_5.bungee.api.plugin.Event;

/**
 * @author Simonsator
 * @version 03.01.2017
 */
public class DisplayNameProviderChangedEvent extends Event {
	private final DisplayNameProvider NEW_DISPLAY_NAME_PROVIDER;

	public DisplayNameProviderChangedEvent(DisplayNameProvider pNewDisplayNameProvider) {
		NEW_DISPLAY_NAME_PROVIDER = pNewDisplayNameProvider;
	}

	public DisplayNameProvider getNewDisplayNameProvider() {
		return NEW_DISPLAY_NAME_PROVIDER;
	}
}
