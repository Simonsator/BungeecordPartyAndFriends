package de.simonsator.partyandfriends.api.pafplayers;

import de.simonsator.partyandfriends.api.events.DisplayNameProviderChangedEvent;
import de.simonsator.partyandfriends.utilities.StandardDisplayNameProvider;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.protocol.packet.Chat;

public abstract class PAFPlayerClass implements PAFPlayer {
	private static DisplayNameProvider displayNameProvider = new StandardDisplayNameProvider();

	@Override
	public void sendMessage(TextComponent pTextComponent) {
	}

	@Override
	public void sendMessage(String pText) {
		String[] spited = pText.split("LINE_BREAK");
		for (String split : spited)
			sendMessage(new TextComponent(split));
	}

	@Override
	public void sendPacket(Chat chat) {

	}

	@Override
	public int hashCode() {
		return getUniqueId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PAFPlayer)
			return ((PAFPlayer) obj).getUniqueId().equals(this.getUniqueId());
		return false;
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
	public boolean isOnline() {
		return false;
	}

	public static void setDisplayNameProvider(DisplayNameProvider pDisplayNameProvider) {
		displayNameProvider = pDisplayNameProvider;
		ProxyServer.getInstance().getPluginManager().callEvent(new DisplayNameProviderChangedEvent(pDisplayNameProvider));
	}

	public static DisplayNameProvider getDisplayNameProvider() {
		return displayNameProvider;
	}
}
