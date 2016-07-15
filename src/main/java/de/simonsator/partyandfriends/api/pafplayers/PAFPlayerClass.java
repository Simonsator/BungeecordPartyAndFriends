package de.simonsator.partyandfriends.api.pafplayers;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.protocol.packet.Chat;

public abstract class PAFPlayerClass implements PAFPlayer {
	@Override
	public void sendMessage(TextComponent pTextComponent) {
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
		if (obj.getClass().isAssignableFrom(PAFPlayerClass.class))
			return ((PAFPlayer) obj).getUniqueId().equals(this.getUniqueId());
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "{Name:\"" + getName() + "\", DisplayName:\"" + getDisplayName() + "\"}";
	}

	@Override
	public String getDisplayName() {
		return getName();
	}

	@Override
	public boolean isOnline() {
		return false;
	}

}
