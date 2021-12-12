package de.simonsator.partyandfriends.api.pafplayers;

import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.api.events.DisplayNameProviderChangedEvent;
import de.simonsator.partyandfriends.api.friends.ServerConnector;
import de.simonsator.partyandfriends.utilities.StandardConnector;
import de.simonsator.partyandfriends.utilities.StandardDisplayNameProvider;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.protocol.packet.Chat;

import java.util.List;
import java.util.Random;

public abstract class PAFPlayerClass implements PAFPlayer {
	private static DisplayNameProvider displayNameProvider = new StandardDisplayNameProvider();
	private static ServerConnector serverConnector = new StandardConnector();
	private final Random RANDOM_GENERATOR = new Random();

	public static ServerConnector getServerConnector() {
		return serverConnector;
	}

	/**
	 * Sets the server connector, which will be used to join a server.
	 *
	 * @param pServerConnector The connector
	 */
	public static void setServerConnector(ServerConnector pServerConnector) {
		serverConnector = pServerConnector;
	}

	public static DisplayNameProvider getDisplayNameProvider() {
		return displayNameProvider;
	}

	public static void setDisplayNameProvider(DisplayNameProvider pDisplayNameProvider) {
		displayNameProvider = pDisplayNameProvider;
		BukkitBungeeAdapter.getInstance().callEvent(new DisplayNameProviderChangedEvent(pDisplayNameProvider));
	}

	@Override
	public void sendMessage(TextComponent pTextComponent) {
		/* If the player is offline no message needs to be send. This method should be overwritten
		 * by a class which extends this class and implements OnlinePAFPlayer*/
	}

	@Override
	public void sendMessage(String pText) {
		String[] spited = pText.split("LINE_BREAK");
		for (String split : spited)
			sendMessage(new TextComponent(TextComponent.fromLegacyText(split)));
	}


	@Override
	public void sendPacket(Chat chat) {
		/* If the player is offline no message needs to be send. This method should be overwritten
		 * by a class which extends this class and implements OnlinePAFPlayer*/
	}

	@Override
	public void sendPacket(TextComponent chat) {
		/* If the player is offline no message needs to be send. This method should be overwritten
		 * by a class which extends this class and implements OnlinePAFPlayer*/
	}


	@Override
	public int hashCode() {
		return getUniqueId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof PAFPlayer && ((PAFPlayer) obj).getUniqueId().equals(this.getUniqueId());
	}

	@Override
	public String toString() {
		return "{Name:\"" + getName() + "\", DisplayName:\"" + getDisplayName() + "\"}";
	}

	@Override
	public String getDisplayName() {
		return displayNameProvider.getDisplayName(this);
	}

	@Override
	public void sendMessage(Object pMessage) {
		if (pMessage instanceof List) {
			sendMessage((List<String>) pMessage);
			return;
		}
		if (pMessage instanceof String)
			sendMessage((String) pMessage);
	}

	@Override
	public void sendMessage(List<String> pMessages) {
		sendMessage(pMessages.get(RANDOM_GENERATOR.nextInt(pMessages.size())));
	}

	@Override
	public boolean isOnline() {
		return false;
	}
}
