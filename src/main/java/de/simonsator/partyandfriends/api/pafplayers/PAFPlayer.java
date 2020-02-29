package de.simonsator.partyandfriends.api.pafplayers;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.protocol.packet.Chat;

import java.util.List;
import java.util.UUID;

public interface PAFPlayer {
	String getName();

	String getDisplayName();

	List<PAFPlayer> getFriends();

	UUID getUniqueId();

	void sendMessage(Object pObject);

	void sendMessage(List<String> pMessages);

	void sendMessage(TextComponent pTextComponent);

	void sendMessage(String pText);

	@Deprecated
	void sendPacket(Chat chat);

	void sendPacket(TextComponent chat);

	boolean doesExist();

	int getSettingsWorth(int pSettingsID);

	List<PAFPlayer> getRequests();

	boolean hasRequestFrom(PAFPlayer pPlayer);

	boolean hasPermission(String pPermission);

	void denyRequest(PAFPlayer pPlayer);

	boolean isOnline();

	boolean isAFriendOf(PAFPlayer pPlayer);

	PAFPlayer getLastPlayerWroteTo();

	void sendFriendRequest(PAFPlayer pSender);

	void addFriend(PAFPlayer pPlayer);

	PAFPlayer getPAFPlayer();

	void removeFriend(PAFPlayer pPlayer);

	void setSetting(int pSettingsID, int pNewWorth);

	void setLastPlayerWroteFrom(PAFPlayer pLastWroteTo);

	long getLastOnline();

	boolean deleteAccount();

	void updateLastOnline();
}
